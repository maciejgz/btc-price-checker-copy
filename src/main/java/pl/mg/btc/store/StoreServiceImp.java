package pl.mg.btc.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.btc.store.model.*;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public BuyProductResponse buyProduct(BuyProductCommand command) throws ProductNotFoundException, NotEnoughProductsInStorageException {
        ProductEntity product = getProduct(command.getProductId());
        product.buy(command.getAmount());
        try {
            log.debug("sleep...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ProductEntity saved = productRepository.save(product);
        return new BuyProductResponse(saved.getId(), saved.getName(), command.getAmount());
    }

    @Override
    public List<ProductEntity> getProducts() {
        Iterable<ProductEntity> products = productRepository.findAll();
        List<ProductEntity> prods = new ArrayList<>();
        for (ProductEntity product : products) {
            prods.add(product);
        }
        return prods;
    }

    @Override
    public ProductEntity getRandomProduct() {
        Iterable<ProductEntity> products = productRepository.findAll();
        List<ProductEntity> prods = new ArrayList<>();
        for (ProductEntity product : products) {
            prods.add(product);
        }
        SecureRandom random = new SecureRandom();
        if (prods.isEmpty()) {
            return null;
        }
        return prods.get(random.nextInt(prods.size()));
    }
}
