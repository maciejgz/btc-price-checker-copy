package pl.mg.btc.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.btc.store.model.*;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class StoreServiceImp implements StoreService {

    private final ProductRepository productRepository;

    public StoreServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getProductPrice(Long productId) throws ProductNotFoundException {
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("product " + productId + " not found");
        }
        return product.get().getPrice();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEntity getProduct(Long productId) throws ProductNotFoundException {
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("product " + productId + " not found");
        }
        return product.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductEntity addProduct(AddProductCommand command) {
        ProductEntity entity = ProductEntity.ofCommand(command);
        return productRepository.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BuyProductResponse buyProduct(BuyProductCommand command) throws ProductNotFoundException {
        ProductEntity product = getProduct(command.getProductId());
        product.buy(command.getAmount());
        ProductEntity saved = productRepository.save(product);
        return new BuyProductResponse(saved.getId(), saved.getName(), command.getAmount());
    }
}
