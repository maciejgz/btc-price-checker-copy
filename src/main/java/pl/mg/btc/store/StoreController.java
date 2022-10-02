package pl.mg.btc.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.btc.store.model.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(value = "/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    //buy
    @PostMapping(value = "/product/buy")
    public ResponseEntity<BuyProductResponse> buyProduct(@Valid @RequestBody BuyProductCommand command) throws ProductNotFoundException {
        BuyProductResponse buyProductResponse = storeService.buyProduct(command);
        return ResponseEntity.ok(buyProductResponse);
    }


    //add product
    @PostMapping(value = "/product")
    public ResponseEntity<ProductEntity> addProduct(@RequestBody AddProductCommand command) {
        ProductEntity product = storeService.addProduct(command);
        return ResponseEntity.ok(product);
    }

    //get price
    @GetMapping(value = "/product/{productId}/price")
    public ResponseEntity<BigDecimal> getProductPrice(@PathVariable(name = "productId") Long productId) throws ProductNotFoundException {
        BigDecimal productPrice = storeService.getProductPrice(productId);
        return ResponseEntity.ok(productPrice);
    }

    //get product
    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable(name = "productId") Long productId) throws ProductNotFoundException {
        ProductEntity product = storeService.getProduct(productId);
        return ResponseEntity.ok(product);
    }
}
