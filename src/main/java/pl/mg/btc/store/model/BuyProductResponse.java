package pl.mg.btc.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyProductResponse {

    private Long productId;
    private String productName;
    private Long amount;

}
