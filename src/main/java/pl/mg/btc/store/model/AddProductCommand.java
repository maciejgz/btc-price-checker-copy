package pl.mg.btc.store.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddProductCommand {

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @Min(value = 0)
    @NotNull
    private long amount;

}
