package pl.mg.btc.async;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsyncRepository extends CrudRepository<AsyncEntity, Long> {
}
