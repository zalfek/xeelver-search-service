package com.xeelver.searchservice.services;

import com.amadeus.resources.Resource;
import com.xeelver.searchservice.CacheObjects.HotelQueryCacheObject;
import com.xeelver.searchservice.repositories.HotelRepository;
import com.xeelver.searchservice.repositories.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class HotelSearchService {

    private final static Logger LOGGER = Logger.getLogger(HotelSearchService.class.getName());
    private final HotelRepository hotelRepository;
    private final RedisRepository<HotelQueryCacheObject> hotelCacheRepository;

    public String findOffers(Map<String, String> hotelSearchQuery) {
        String response;
        HotelQueryCacheObject cachedValue = hotelCacheRepository.getValue(hotelSearchQuery.toString());
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to HotelRepository");
            response = this.getData(hotelRepository.findOffers(hotelSearchQuery));
            hotelCacheRepository.putValue(hotelSearchQuery.toString(), new HotelQueryCacheObject(hotelSearchQuery.toString(), response));
            hotelCacheRepository.setExpire(hotelSearchQuery.toString(), 20, TimeUnit.SECONDS);
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
