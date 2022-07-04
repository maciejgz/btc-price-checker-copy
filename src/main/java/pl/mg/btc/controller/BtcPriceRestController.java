package pl.mg.btc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.btc.data.BtcPriceHistory;
import pl.mg.btc.data.BtcPriceRepository;
import pl.mg.btc.service.EthService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/btc")
public class BtcPriceRestController {

    private final BtcPriceRepository repository;
    private final BtcPriceDtoMapper mapper;

    private final EthService ethService;

    public BtcPriceRestController(BtcPriceRepository repository, BtcPriceDtoMapper mapper, EthService ethService) {
        this.repository = repository;
        this.mapper = mapper;
        this.ethService = ethService;
    }

    @GetMapping(value = "")
    public ResponseEntity<BtcPriceDto> getCurrentPrice() {
        Optional<BtcPriceHistory> price = repository.findFirstByOrderByTimestampDesc();
        return price.map(btcPriceHistory -> ResponseEntity.ok(mapper.mapToBtcPriceDto(btcPriceHistory))).orElseGet(() -> ResponseEntity.ok(null));
    }

    @GetMapping(value = "/eth")
    public ResponseEntity<Integer> getEthPrice(@RequestParam(value = "amount") int amount) {
        return ResponseEntity.ok(ethService.ethPriceGet(amount));
    }

    @PutMapping(value = "/eth")
    public ResponseEntity<Integer> putEthPrice(@RequestParam(value = "amount") int amount) {
        return ResponseEntity.ok(ethService.ethPricePut(amount));
    }

    @DeleteMapping(value = "/eth")
    public ResponseEntity<Void> evictEthPrice() {
        ethService.clearEthCache();
        return ResponseEntity.ok().build();
    }
}
