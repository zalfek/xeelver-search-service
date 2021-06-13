package com.xeelver.searchservice.repositories;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.HotelOffer.*;
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
        LOGGER.info("Sending request to Amadeus with following query: " + searchParams);
        return amadeus.shopping.hotelOffers.get(searchParams);
    }

    @SneakyThrows
    public HotelOffer getOfferDetails(Map<String,String> hotelSearchQuery) {
        Params searchParams = Params.with("hotelId", hotelSearchQuery.get("hotelId"));
        hotelSearchQuery.forEach(searchParams::and);
        LOGGER.info("Sending request to Amadeus with following query: " + searchParams);
        return amadeus.shopping.hotelOffersByHotel.get(searchParams);
    }

    @SneakyThrows
    public HotelOffer getRoomDetails(String hotelId) {
        LOGGER.info("Sending request to Amadeus with hotel id: : " + hotelId);
        return amadeus.shopping.hotelOffersByHotel.get(Params.with("hotelId", hotelId));
    }

    @SneakyThrows
    public HotelPrice getPriceDetails(String hotelId) {
        LOGGER.info("Sending request to Amadeus with hotel id: : " + hotelId);
        HotelOffer hotelOffer = amadeus.shopping.hotelOffer(hotelId).get();
        LOGGER.info("Received the following data: " + hotelOffer.toString());
        return hotelOffer.getOffers()[0].getPrice();
    }
}
