package pl.mg.btc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EthServiceIImpl implements EthService {

    @Override
    @Cacheable(value = "eth", key = "#id")
    public int ethPriceGet(int id) {
        log.debug("eth get: {}", id);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 10 * id;
    }


    @Override
    @CachePut(value = "eth", key = "#id")
    public int ethPricePut(int id) {
        log.debug("eth put: {}", id);
        return 10 * id;
    }

    @Override
    @CacheEvict(value = "eth", allEntries = true)
    public void clearEthCache() {
        log.debug("cache eth evict");
    }


}
