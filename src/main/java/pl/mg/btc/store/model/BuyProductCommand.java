package pl.mg.btc.store.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BuyProductCommand {

    @NotNull
    private Long productId;

    @Min(value = 1)
    private Long amount;
}
