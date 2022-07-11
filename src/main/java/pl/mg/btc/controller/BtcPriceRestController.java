package pl.mg.btc.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mg.btc.currency.CurrencyService;
import pl.mg.btc.data.BtcPriceHistory;
import pl.mg.btc.data.BtcPriceRepository;
import pl.mg.btc.service.EthService;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(value = "/btc")
public class BtcPriceRestController {

    private final BtcPriceRepository repository;
    private final BtcPriceDtoMapper mapper;

    private final EthService ethService;

    private final CurrencyService currencyService;

    public BtcPriceRestController(BtcPriceRepository repository, BtcPriceDtoMapper mapper, EthService ethService, CurrencyService currencyService) {
        this.repository = repository;
        this.mapper = mapper;
        this.ethService = ethService;
        this.currencyService = currencyService;
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

    @GetMapping(value = "/currency")
    public ResponseEntity<BigDecimal> getCurrency(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(currencyService.getPrice(name));
    }

    @PutMapping(value = "/currency")
    public ResponseEntity<BigDecimal> putCurrency(@RequestParam(value = "name") String name, @RequestParam(value = "price") BigDecimal price) {
        return ResponseEntity.ok(currencyService.setPrice(name, price));
    }

    @DeleteMapping(value = "/currency")
    public ResponseEntity<Void> evictCurrency() {
        currencyService.evict();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/reentrant")
    public ResponseEntity<String> reentrantLock() {
        String result = currencyService.lockedWithLocalReentrant();
        return ResponseEntity.ok(result);
    }
}
