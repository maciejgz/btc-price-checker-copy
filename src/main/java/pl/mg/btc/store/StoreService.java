package pl.mg.btc.store;

import pl.mg.btc.store.model.*;

import java.math.BigDecimal;

public interface StoreService {
    BigDecimal getProductPrice(Long productId) throws ProductNotFoundException;

    ProductEntity getProduct(Long productId) throws ProductNotFoundException;

    ProductEntity addProduct(AddProductCommand command);

    BuyProductResponse buyProduct(BuyProductCommand command) throws ProductNotFoundException;
}
