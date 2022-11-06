package pl.mg.btc.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    private final AsyncRepository asyncRepository;

    public AsyncServiceImpl(AsyncRepository asyncRepository) {
        this.asyncRepository = asyncRepository;
    }

    @Override
    @Async
    public void updateEntityAsynchronous(Long id, String name) {
        Optional<AsyncEntity> entity = asyncRepository.findById(id);
        if (entity.isPresent()) {
            AsyncEntity ent = entity.get();
            ent.setName(name);
            asyncRepository.save(ent);
        }
    }
}
