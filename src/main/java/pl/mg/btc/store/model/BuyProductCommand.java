package pl.mg.btc.store.model;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BuyProductCommand {

    private Long productId;

    @Min(value = 1)
    private Long amount;
}
