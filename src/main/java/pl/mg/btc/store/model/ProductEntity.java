package pl.mg.btc.store.model;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "product")
@Entity
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private BigDecimal price;

    private long amount;

    public static ProductEntity ofCommand(AddProductCommand command) {
        ProductEntity entity = new ProductEntity();
        entity.setAmount(command.getAmount());
        entity.setName(command.getName());
        entity.setPrice(command.getPrice());
        return entity;
    }

    public void buy(long amount) throws NotEnoughProductsInStorageException {
        if (amount < 0) {
            throw new IllegalArgumentException("amount of products is too low");
        }
        if (amount > this.amount) {
            throw new NotEnoughProductsInStorageException("bought amount is greater than the product's amount");
        }
        this.amount = this.amount - amount;
    }
}
