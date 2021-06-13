package com.xeelver.searchservice.controllers;

import com.xeelver.searchservice.exceptions.NotFoundException;
import com.xeelver.searchservice.services.HotelSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
public class HotelSearchController {

    private final HotelSearchService hotelSearchService;
    private final ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping("/api/v1/search/hotels")
    public @ResponseBody
    ResponseEntity<Object> searchHotels(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        String response = hotelSearchService.findOffers(requestParams);
        if (response==null || response.isEmpty()) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(objectMapper.readValue(response, Object.class), HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping("/api/v1/search/hotels/specific")
    public @ResponseBody
    ResponseEntity<Object> getHotelDetails(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        return new ResponseEntity<Object>(objectMapper.readValue(hotelSearchService.getOfferDetails(requestParams), Object.class), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping("/api/v1/search/hotels/rooms")
    public @ResponseBody
    ResponseEntity<Object> getRoomDetails(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        return new ResponseEntity<Object>(objectMapper.readValue(hotelSearchService.getRoomDetails(requestParams.get("offerId")), Object.class), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping("/api/v1/search/hotels/price/{id}")
    public @ResponseBody
    ResponseEntity<Object> getPrice(@PathVariable String id)  {
        return new ResponseEntity<Object>(hotelSearchService.getOfferPrice(id), HttpStatus.OK);
    }

}
