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
        FlightQueryCacheObject cachedValue = flightCacheRepository.getValue(flightSearchQuery.toString());
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to FlightRepository");
            response = this.getData(flightRepository.findFlights(flightSearchQuery));
            flightCacheRepository.putValue(flightSearchQuery.toString(), new FlightQueryCacheObject(flightSearchQuery.toString(), response));
            flightCacheRepository.setExpire(flightSearchQuery.toString(), 20, TimeUnit.SECONDS);
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }

    public String getInpiration(Map<String, String> flightSearchQuery) {
        String response;
        FlightQueryCacheObject cachedValue = flightCacheRepository.getValue(flightSearchQuery.toString());
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to FlightRepository");
            response = this.getData(flightRepository.getInpiration(flightSearchQuery));
            flightCacheRepository.putValue(flightSearchQuery.toString(), new FlightQueryCacheObject(flightSearchQuery.toString(), response));
            flightCacheRepository.setExpire(flightSearchQuery.toString(), 20, TimeUnit.SECONDS);
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }


    protected String getData(Resource[] array) {
        return array == null ? null : array[0].getResponse().getData().toString();
    }
}
