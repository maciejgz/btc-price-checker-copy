package pl.mg.btc.crawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import pl.mg.btc.data.BtcPriceHistory;
import pl.mg.btc.data.BtcPriceRepository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Service
@Slf4j
public class BtcPriceCrawler {

    private final BtcPriceRepository repository;

    public BtcPriceCrawler(BtcPriceRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 5000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void getBtcPrice() {
        log.debug("btc");
        Flux<BtcPriceResponse> response = WebClient.create()
                .get()
                .uri("https://api.coincap.io/v2/assets?ids=bitcoin")
                .retrieve()
                .bodyToFlux(BtcPriceResponse.class);
        response.subscribe(btcPriceResponse -> repository.save(mapToEntity(btcPriceResponse)));
    }

    private static BtcPriceHistory mapToEntity(BtcPriceResponse response) {
        BtcPriceHistory entity = new BtcPriceHistory();
        entity.setPrice(new BigDecimal(response.getData().get(0).getPriceUsd()));
        entity.setTimestamp(response.getTimestamp());
        return entity;
    }
}
