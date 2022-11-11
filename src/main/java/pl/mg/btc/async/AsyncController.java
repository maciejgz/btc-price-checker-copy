package pl.mg.btc.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/async")
@Slf4j
public class AsyncController {

    private final MainAsyncService mainAsyncService;

    public AsyncController(MainAsyncService mainAsyncService) {
        this.mainAsyncService = mainAsyncService;
    }

    @GetMapping(value = "/{id}/{name}")
    public ResponseEntity<Void> changeName(@PathVariable(name = "id") Long id, @PathVariable(name = "name") String name) {
        mainAsyncService.updateEntitySynchronous(id, name);
        return ResponseEntity.ok().build();
    }
}
