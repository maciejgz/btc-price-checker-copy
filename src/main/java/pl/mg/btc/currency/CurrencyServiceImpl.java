package pl.mg.btc.currency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Tests of shared cache and optimistic locks on the database/JPA level.
 */
@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repository;
    private final SideCurrencyRepository sideCurrencyRepository;

    private final Lock lock = new ReentrantLock();

    public CurrencyServiceImpl(CurrencyRepository repository, SideCurrencyRepository sideCurrencyRepository) {
        this.repository = repository;
        this.sideCurrencyRepository = sideCurrencyRepository;
    }

    @Override
    @Cacheable(value = "currency", key = "#name")
    public BigDecimal getPrice(String name) {
        Optional<CurrencyEntity> currency = repository.findByName(name);
        return currency.get().getPrice();
    }

    /**
     * Method locked with the highest isolation level. Transaction is locked when one session is opened even in the read state of the entity.
     *
     * @param name
     * @param price
     * @return
     */
    @Override
    @CacheEvict(value = "currency", key = "#name")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public BigDecimal setPrice(String name, BigDecimal price) {
        Optional<CurrencyEntity> currency = repository.findByName(name);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (currency.isPresent()) {
            SideCurrencyEntity side = new SideCurrencyEntity();
            side.setLastVersion(currency.get().getVersion());
            sideCurrencyRepository.save(side);

            currency.get().setPrice(price);
            repository.save(currency.get());
        } else {
            CurrencyEntity currencyEntity = new CurrencyEntity();
            currencyEntity.setName(name);
            currencyEntity.setPrice(price);
            repository.save(currencyEntity);
        }
        return price;
    }

    @Override
    @CacheEvict(value = "currency", allEntries = true)
    public void evict() {

    }

    /**
     * Method locked with ReentrantLock locally in one instance.
     */
    @Override
    public String lockedWithLocalReentrant() {
        log.debug("lockedWithLocalReentrant");
        try {
            boolean acquiredLock = lock.tryLock(1, TimeUnit.SECONDS);
            if (!acquiredLock) {
                System.out.println("lock not acquired");
                return "not acquired";
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
        log.debug("lockedWithLocalReentrant exit");
        return "acquired";
    }
}
