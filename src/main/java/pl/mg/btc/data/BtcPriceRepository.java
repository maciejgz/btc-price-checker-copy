package pl.mg.btc.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BtcPriceRepository extends JpaRepository<BtcPriceHistory, Long> {

    Optional<BtcPriceHistory> findFirstByOrderByTimestampDesc();

}
