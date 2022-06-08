package pl.mg.btc.crawler;

import lombok.Data;

import java.util.List;

@Data
public class BtcPriceResponse {
    List<ConcurrencyPriceEntry> data;
    private Long timestamp;
}
