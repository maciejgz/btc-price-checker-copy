package pl.mg.btc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.btc.data.BtcPriceHistory;
import pl.mg.btc.data.BtcPriceRepository;

import java.util.Optional;

@RestController
@RequestMapping(value = "/btc")
public class BtcPriceRestController {

    private final BtcPriceRepository repository;
    private final BtcPriceDtoMapper mapper;

    public BtcPriceRestController(BtcPriceRepository repository, BtcPriceDtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping(value = "")
    public ResponseEntity<BtcPriceDto> getCurrentPrice() {
        Optional<BtcPriceHistory> price = repository.findFirstByOrderByTimestampDesc();
        return price.map(btcPriceHistory -> ResponseEntity.ok(mapper.mapToBtcPriceDto(btcPriceHistory))).orElseGet(() -> ResponseEntity.ok(null));
    }
}
