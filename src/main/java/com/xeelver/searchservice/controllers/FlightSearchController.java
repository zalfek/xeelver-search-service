package com.xeelver.searchservice.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xeelver.searchservice.exceptions.NotFoundException;
import com.xeelver.searchservice.services.FlightSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@AllArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;
    private final ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping("/api/v1/search/heatlthz")
    public String health() {
        return "Search service is alive!!!";
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.POST)
    @PostMapping(value = "/api/v1/search/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> searchFlight(@RequestBody Map<String, String> requestParams) {
        JsonObject response = flightSearchService.searchFlight(requestParams);
        if (response == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping(value = "/api/v1/search/flights/inspiration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getInpiration(@RequestParam Map<String, String> requestParams) {
        JsonObject response = flightSearchService.getInpiration(requestParams);
        if (response == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping(value = "/api/v1/search/flights/airline", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getAirlineName(@RequestParam Map<String, String> airline) {
        JsonObject response = flightSearchService.getAirlineName(airline);
        if (response == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping(value = "/api/v1/search/flights/airports", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getLocations(@RequestParam Map<String, String> keyword) {
        JsonObject response = flightSearchService.getLocations(keyword);
        if (response == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @PostMapping(value = "/api/v1/search/flights/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> getFlightPrice(@RequestBody JsonObject flightOffer) {
        JsonObject response = flightSearchService.getFlightPrice(flightOffer);
        if (response == null) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        }
    }

}
