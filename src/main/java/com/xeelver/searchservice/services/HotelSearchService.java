package com.xeelver.searchservice.services;

import com.amadeus.resources.HotelOffer;
import com.amadeus.resources.Resource;
import com.xeelver.searchservice.cache.HotelQueryCacheObject;
import com.xeelver.searchservice.repositories.AmadeusHotelRepository;
import com.xeelver.searchservice.repositories.CacheRepository;
import com.xeelver.searchservice.repositories.HotelRepository;
import com.xeelver.searchservice.repositories.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class HotelSearchService implements HotelService{

    private final static Logger LOGGER = Logger.getLogger(HotelSearchService.class.getName());
    private final HotelRepository amadeusHotelRepository;
    private final CacheRepository<HotelQueryCacheObject> hotelCacheRepository;

    public String findOffers(Map<String, String> hotelSearchQuery) {
        String response;
        HotelQueryCacheObject cachedValue = null;
        try {
            cachedValue = hotelCacheRepository.getValue(hotelSearchQuery.toString());
        } catch (Exception exception) {
            LOGGER.warning("Request to cache failed. Exception: " + exception);
        }
        if (cachedValue == null) {
            LOGGER.info("Nothing found in cache. Request is forwarded to HotelRepository");
            response = this.getData(amadeusHotelRepository.findOffers(hotelSearchQuery));
            try {
                hotelCacheRepository.putValue(hotelSearchQuery.toString(), new HotelQueryCacheObject(hotelSearchQuery.toString(), response));
                hotelCacheRepository.setExpire(hotelSearchQuery.toString(), 120, TimeUnit.SECONDS);
            } catch (Exception exception) {
                LOGGER.warning("Request to cache failed. Exception: " + exception);
            }
        } else {
            LOGGER.info("Information is found in cache.");
            response = cachedValue.getPayload();
        }
        return response;
    }

    public String getOfferDetails(Map<String, String> hotelSearchQuery) {
        LOGGER.info("Received the following request:" + hotelSearchQuery.toString() + ". Request is forwarded to HotelRepository");
        return amadeusHotelRepository.getOfferDetails(hotelSearchQuery).getResponse().getData().toString();
    }

    public String getRoomDetails(String hotelId) {
        LOGGER.info("Received the following request for following hotel:" + hotelId + ". Request is forwarded to HotelRepository");
        return amadeusHotelRepository.getRoomDetails(hotelId).getResponse().getData().toString();
    }

    public HotelOffer.HotelPrice getOfferPrice(String hotelId) {
        LOGGER.info("Received the following request for following hotel:" + hotelId + ". Request is forwarded to HotelRepository");
        return amadeusHotelRepository.getPriceDetails(hotelId);
    }

    private String getData(Resource[] array) {
        String response = "";
        try {
            response = array == null ? null : String.valueOf(array[0].getResponse().getResult());
        } catch (Exception exception) {
            LOGGER.warning("FlightRepository returned an empty array. Exception: " + exception);
        }
        return response;
    }
}
