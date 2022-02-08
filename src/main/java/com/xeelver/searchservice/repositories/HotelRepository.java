package com.xeelver.searchservice.repositories;

import com.amadeus.resources.HotelOffer;

import java.util.Map;

public interface HotelRepository {

    HotelOffer[] findOffers(Map<String,String> hotelSearchQuery);
    HotelOffer getOfferDetails(Map<String,String> hotelSearchQuery);
    HotelOffer getRoomDetails(String hotelId);
    HotelOffer.HotelPrice getPriceDetails(String hotelId);
}
