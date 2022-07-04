package pl.mg.btc.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface EthService {
    @Cacheable(value = "eth", key = "#id")
    int ethPriceGet(int id);

    @CachePut(value = "eth", key = "#id")
    int ethPricePut(int id);

    @CacheEvict
    void clearEthCache();
}
