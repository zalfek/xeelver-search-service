package com.xeelver.searchservice.repositories;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.*;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.logging.Logger;


@Repository
@AllArgsConstructor
@NoArgsConstructor
public class AmadeusFlightRepository implements FlightRepository{

    private final static Logger LOGGER = Logger.getLogger(AmadeusFlightRepository.class.getName());

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
    public FlightOfferSearch[] findFlights(Map<String, String> flightSearchQuery) {
        Params searchParams = Params
                .with("originLocationCode", flightSearchQuery.get("originLocationCode"));
        flightSearchQuery.forEach(searchParams::and);
        LOGGER.info("Sending request to Amadeus with following query: " + searchParams);
        return amadeus.shopping.flightOffersSearch.get(searchParams);
    }

    @SneakyThrows
    public FlightDestination[] getInspiration(Map<String, String> flightSearchQuery) {
        Params searchParams = Params
                .with("origin", flightSearchQuery.get("origin"));
        flightSearchQuery.forEach(searchParams::and);
        LOGGER.info("Sending request to Amadeus with following query: " + searchParams);
        return amadeus.shopping.flightDestinations.get(searchParams);
    }


    @SneakyThrows
    public Airline[] getAirlineName(String airline) {
        Params requestParams = Params
                .with("airlineCodes", airline);
        LOGGER.info("Sending request to Amadeus with following query: " + requestParams);
        return amadeus.referenceData.airlines.get(requestParams);
    }

    @SneakyThrows
    public Location[] getLocations(String keyword) {
        Params requestParams = Params
                .with("keyword", keyword)
                .and("subType", Locations.AIRPORT);
        return amadeus.referenceData.locations.get(requestParams);
    }

    @SneakyThrows
    public FlightPrice getFlightPrice(JsonObject flightOffer) {
        return amadeus.shopping.flightOffersSearch.pricing.post(flightOffer);

    }
}
