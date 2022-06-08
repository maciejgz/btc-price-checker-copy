package pl.mg.btc.controller;

import lombok.Value;
import pl.mg.btc.data.BtcPriceHistory;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class BtcPriceDto {

    BigDecimal price;

    Instant timestamp;

    public static BtcPriceDto ofEntity(BtcPriceHistory entity) {
        return new BtcPriceDto(entity.getPrice(), Instant.ofEpochMilli(entity.getTimestamp()));
    }
}
