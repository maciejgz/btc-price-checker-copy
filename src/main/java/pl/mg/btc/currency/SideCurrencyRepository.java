package pl.mg.btc.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SideCurrencyRepository extends JpaRepository<SideCurrencyEntity, Long> {
}
