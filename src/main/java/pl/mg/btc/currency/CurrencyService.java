package pl.mg.btc.currency;

import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal getPrice(String name);
    BigDecimal setPrice(String name, BigDecimal price);
    void evict();

}
