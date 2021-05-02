package com.example.searchservice.services;

import com.example.searchservice.repositories.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@AllArgsConstructor
public class HotelSearchService {

    private final HotelRepository hotelRepository;

    public String findOffers(Map<String,String> hotelSearchQuery){
        return hotelRepository.findOffers(hotelSearchQuery)[0].getResponse().getData().toString();

    }

}
