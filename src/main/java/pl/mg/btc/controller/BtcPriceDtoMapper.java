package pl.mg.btc.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.mg.btc.data.BtcPriceHistory;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface BtcPriceDtoMapper {


    @Mapping(source = "price", target = "price")
    @Mapping(source = "timestamp", target = "timestamp", qualifiedByName = "epochLongToInstant")
    BtcPriceDto mapToBtcPriceDto(BtcPriceHistory history);

    @Named("epochLongToInstant")
    default Instant epochToInstant(Long epochTime) {
        return Instant.ofEpochMilli(epochTime);
    }

}
