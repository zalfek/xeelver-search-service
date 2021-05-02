package com.example.searchservice.repositories;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightDestination;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Value;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.logging.Logger;


@Repository
public class FlightRepository {

    private final static Logger LOGGER = Logger.getLogger(FlightRepository.class.getName());

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
    public FlightOfferSearch[] findFlights(Map<String,String> flightSearchQuery) {
        Params searchParams = Params.with("originLocationCode", flightSearchQuery.get("originLocationCode"));
        flightSearchQuery.forEach(searchParams::and);
        LOGGER.info("Sending request to Amadeus with following query: " + searchParams);
        return amadeus.shopping.flightOffersSearch.get(searchParams);
    }

    @SneakyThrows
    public FlightDestination[] getInpiration(Map<String,String> flightSearchQuery) {
        Params searchParams = Params.with("origin", flightSearchQuery.get("origin"));
        flightSearchQuery.forEach(searchParams::and);
        LOGGER.info("Sending request to Amadeus with following query: " + searchParams);
        return amadeus.shopping.flightDestinations.get(searchParams);
    }
}
