package com.xeelver.searchservice.services;

import com.amadeus.resources.Resource;
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

    public String searchFlight(Map<String, String> flightSearchQuery) {
        String response;
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

    public String getInpiration(Map<String, String> flightSearchQuery) {
        String response;
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

    public String getAirlineName(Map<String, String> airline) {
        String response;
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


    public String getLocations(Map<String, String> keyword) {
        String response;
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

    protected String getData(Resource[] array) {
        String response = "";
        try {
            response = array == null ? null : String.valueOf(array[0].getResponse().getResult());
        } catch (Exception exception) {
            LOGGER.warning("FlightRepository returned an empty array. Exception: " + exception);
        }
        return response;
    }
}
