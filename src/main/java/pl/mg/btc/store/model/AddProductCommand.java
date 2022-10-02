package pl.mg.btc.store.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductCommand {

    private String name;

    private BigDecimal price;

    private long amount;

}
