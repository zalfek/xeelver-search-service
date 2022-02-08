package com.xeelver.searchservice.services;

import com.amadeus.resources.HotelOffer;

import java.util.Map;

public interface HotelService {
    String findOffers(Map<String, String> hotelSearchQuery);
    String getOfferDetails(Map<String, String> hotelSearchQuery);
    String getRoomDetails(String hotelId);
    HotelOffer.HotelPrice getOfferPrice(String hotelId);


}
