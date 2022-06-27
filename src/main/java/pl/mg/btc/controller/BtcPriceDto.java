package pl.mg.btc.controller;

import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class BtcPriceDto {

    BigDecimal price;

    Instant timestamp;
}
