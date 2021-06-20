package com.xeelver.searchservice.services;

import com.amadeus.Response;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Resource;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xeelver.searchservice.CacheObjects.FlightQueryCacheObject;
import com.xeelver.searchservice.repositories.FlightRepository;
import com.xeelver.searchservice.repositories.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Service
@AllArgsConstructor
public class FlightSearchService {

    private final static Logger LOGGER = Logger.getLogger(FlightSearchService.class.getName());
    private final FlightRepository flightRepository;
    private final RedisRepository<FlightQueryCacheObject> flightCacheRepository;

    public JsonObject searchFlight(Map<String, String> flightSearchQuery) {
        JsonObject response;
        FlightQueryCacheObject cachedValue = null;
        try {
            cachedValue = flightCacheRepository.getValue(flightSearchQuery.toString());
        } catch (Exception exception) {
            LOGGER.warning("Request to cache failed. Exception: " + exception);
        }
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to FlightRepository");
            response = this.getData(flightRepository.findFlights(flightSearchQuery));
            try {
                flightCacheRepository.putValue(flightSearchQuery.toString(), new FlightQueryCacheObject(flightSearchQuery.toString(), response));
                flightCacheRepository.setExpire(flightSearchQuery.toString(), 120, TimeUnit.SECONDS);
            } catch (Exception exception) {
                LOGGER.warning("Request to cache failed. Exception: " + exception);
            }
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }

    public JsonObject getInpiration(Map<String, String> flightSearchQuery) {
        JsonObject response;
        FlightQueryCacheObject cachedValue = null;
        try {
            cachedValue = flightCacheRepository.getValue(flightSearchQuery.toString());
        } catch (Exception exception) {
            LOGGER.warning("Request to cache failed. Exception: " + exception);
        }

        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to FlightRepository");
            response = this.getData(flightRepository.getInpiration(flightSearchQuery));
            try {
                flightCacheRepository.putValue(flightSearchQuery.toString(), new FlightQueryCacheObject(flightSearchQuery.toString(), response));
                flightCacheRepository.setExpire(flightSearchQuery.toString(), 120, TimeUnit.SECONDS);
            } catch (Exception exception) {
                LOGGER.warning("Request to cache failed. Exception: " + exception);
            }
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }

    public JsonObject getAirlineName(Map<String, String> airline) {
        JsonObject response;
        FlightQueryCacheObject cachedValue = null;
        try {
            cachedValue = flightCacheRepository.getValue(airline.toString());
        } catch (Exception exception) {
            LOGGER.warning("Request to cache failed. Exception: " + exception);
        }
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to FlightRepository");
            response = getData(flightRepository.getAirlineName(airline.get("airlineCodes")));
            try {
                flightCacheRepository.putValue(airline.toString(), new FlightQueryCacheObject(airline.toString(), response));
                flightCacheRepository.setExpire(airline.toString(), 120, TimeUnit.SECONDS);
            } catch (Exception exception) {
                LOGGER.warning("Request to cache failed. Exception: " + exception);
            }
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }


    public JsonObject getLocations(Map<String, String> keyword) {
        JsonObject response;
        FlightQueryCacheObject cachedValue = null;
        try {
            cachedValue = flightCacheRepository.getValue(keyword.toString());
        } catch (Exception exception) {
            LOGGER.warning("Request to cache failed. Exception: " + exception);
        }
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to FlightRepository");
            response = this.getData(flightRepository.getLocations(keyword.get("keyword")));
            try {
                flightCacheRepository.putValue(keyword.toString(), new FlightQueryCacheObject(keyword.toString(), response));
                flightCacheRepository.setExpire(keyword.toString(), 120, TimeUnit.SECONDS);
            } catch (Exception exception) {
                LOGGER.warning("Request to cache failed. Exception: " + exception);
            }
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }


    public JsonObject getFlightPrice(JsonObject flightOffer) {
        JsonObject template = new JsonObject();
            JsonObject data = new JsonObject();
        JsonArray flightOffers = new JsonArray();
        flightOffers.add(flightOffer);
        data.addProperty("type", "flight-offers-pricing" );
        data.add("flightOffers", flightOffers);
        template.add("data", data);
        return flightRepository.getFlightPrice(template).getResponse().getResult();
    }


    protected JsonObject getData(Resource[] array) {
        JsonObject response = null;
        try {
            response = array == null ? null : array[0].getResponse().getResult();
        } catch (Exception exception) {
            LOGGER.warning("FlightRepository returned an empty array. Exception: " + exception);
        }
        return response;
    }


}
