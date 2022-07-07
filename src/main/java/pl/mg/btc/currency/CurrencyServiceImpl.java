package pl.mg.btc.currency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repository;

    public CurrencyServiceImpl(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "currency", key = "#name")
    public BigDecimal getPrice(String name) {
        Optional<CurrencyEntity> currency = repository.findByName(name);
        return currency.get().getPrice();
    }

    @Override
    @CacheEvict(value = "currency", key = "#name")
    public BigDecimal setPrice(String name, BigDecimal price) {
        Optional<CurrencyEntity> currency = repository.findByName(name);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (currency.isPresent()) {
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
}
