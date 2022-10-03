package pl.mg.btc.store;

import pl.mg.btc.store.model.*;

import java.math.BigDecimal;
import java.util.List;

public interface StoreService {
    BigDecimal getProductPrice(Long productId) throws ProductNotFoundException;

    ProductEntity getProduct(Long productId) throws ProductNotFoundException;

    ProductEntity addProduct(AddProductCommand command);

    BuyProductResponse buyProduct(BuyProductCommand command) throws ProductNotFoundException, NotEnoughProductsInStorageException;

    List<ProductEntity> getProducts();

    ProductEntity getRandomProduct();
}
