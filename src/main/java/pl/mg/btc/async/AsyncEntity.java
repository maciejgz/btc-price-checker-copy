package pl.mg.btc.async;

import lombok.Data;

import javax.persistence.*;

@Table(name = "async")
@Entity
@Data
public class AsyncEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
