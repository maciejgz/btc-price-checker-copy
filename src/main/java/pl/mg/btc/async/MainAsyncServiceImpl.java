package pl.mg.btc.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class MainAsyncServiceImpl implements MainAsyncService {

    private final AsyncRepository asyncRepository;
    private final AsyncService asyncService;

    public MainAsyncServiceImpl(AsyncRepository asyncRepository, AsyncService asyncService) {
        this.asyncRepository = asyncRepository;
        this.asyncService = asyncService;
    }

    @Override
    @Transactional
    public void updateEntitySynchronous(Long id, String name) {
        Optional<AsyncEntity> entity = asyncRepository.findById(id);
        asyncService.updateEntityAsynchronous(id, name + "async");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (entity.isPresent()) {
            AsyncEntity ent = entity.get();
            ent.setName(name);
            asyncRepository.save(ent);
        }
    }
}
