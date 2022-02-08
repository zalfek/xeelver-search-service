package com.xeelver.searchservice.services;

import com.google.gson.JsonObject;

import java.util.Map;

public interface FlightService {

    JsonObject searchFlight(Map<String, String> flightSearchQuery);
    JsonObject getInspiration(Map<String, String> flightSearchQuery);
    JsonObject getAirlineName(Map<String, String> airline);
    JsonObject getLocations(Map<String, String> keyword);
    JsonObject getFlightPrice(JsonObject flightOffer);

}
