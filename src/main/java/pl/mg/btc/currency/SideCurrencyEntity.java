package pl.mg.btc.currency;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "currency_side")
@Data
public class SideCurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long lastVersion;

}
