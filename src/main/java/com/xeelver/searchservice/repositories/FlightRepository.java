package com.xeelver.searchservice.repositories;

import com.amadeus.resources.*;
import com.google.gson.JsonObject;

import java.util.Map;

public interface FlightRepository {

    FlightOfferSearch[] findFlights(Map<String, String> flightSearchQuery);
    FlightDestination[] getInspiration(Map<String, String> flightSearchQuery);
    Airline[] getAirlineName(String airline);
    Location[] getLocations(String keyword);
    FlightPrice getFlightPrice(JsonObject flightOffer);
}
