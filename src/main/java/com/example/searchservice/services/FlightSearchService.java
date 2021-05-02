package com.example.searchservice.services;

import com.amadeus.resources.Resource;
import com.example.searchservice.repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
@AllArgsConstructor
public class FlightSearchService {

    private final FlightRepository flightRepository;

    public String searchFlight(Map<String,String> flightSearchQuery) {
        return flightRepository.findFlights(flightSearchQuery)[0].getResponse().getData().toString();
    }

    public String getInpiration(Map<String,String> flightSearchQuery){
        return this.getData(flightRepository.getInpiration(flightSearchQuery));
    }


    protected String getData(Resource[] array){
        return array == null ? null : array[0].getResponse().getData().toString();
    }
}
