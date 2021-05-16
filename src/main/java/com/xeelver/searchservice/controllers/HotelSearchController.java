package com.xeelver.searchservice.controllers;

import com.xeelver.searchservice.services.HotelSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class HotelSearchController {

    private final HotelSearchService hotelSearchService;
    private final ObjectMapper objectMapper;

    @GetMapping("/api/v1/search/hotels")
    public @ResponseBody
    ResponseEntity<Object> searchHotels(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        return new ResponseEntity<Object>(objectMapper.readValue(hotelSearchService.findOffers(requestParams), Object.class), HttpStatus.OK);
    }

}
