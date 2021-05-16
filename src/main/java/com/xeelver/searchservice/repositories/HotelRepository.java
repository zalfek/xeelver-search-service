package com.xeelver.searchservice.repositories;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.HotelOffer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.logging.Logger;


@Repository
public class HotelRepository {

    private final static Logger LOGGER = Logger.getLogger(HotelRepository.class.getName());

    @Value("${amadeusApiKey}")
    private String amadeusApiKey;

    @Value("${amadeusApiSecret}")
    private String amadeusApiSecret;

    private Amadeus amadeus;

    @PostConstruct
    public void init() {
        amadeus = Amadeus
                .builder(amadeusApiKey, amadeusApiSecret)
                .setLogger(LOGGER)
                .setLogLevel("debug")
                .build();
    }

    @SneakyThrows
    public HotelOffer[] findOffers(Map<String,String> hotelSearchQuery) {
        Params searchParams = Params.with("cityCode", hotelSearchQuery.get("cityCode"));
        hotelSearchQuery.forEach(searchParams::and);
        return amadeus.shopping.hotelOffers.get(searchParams);
    }

}
