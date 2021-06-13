package com.xeelver.searchservice.controllers;

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
    ResponseEntity<Object> searchFlight(@RequestBody Map<String, String> requestParams) throws JsonProcessingException {
        String response = flightSearchService.searchFlight(requestParams);
        if (response==null || response.isEmpty()) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(objectMapper.readValue(response, Object.class), HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", methods = RequestMethod.GET)
    @GetMapping(value = "/api/v1/search/flights/inspiration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getInpiration(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        String response = flightSearchService.getInpiration(requestParams);
        if (response==null || response.isEmpty()) {
            throw new NotFoundException();
        } else {
            return new ResponseEntity<Object>(objectMapper.readValue(response, Object.class), HttpStatus.OK);
        }
    }
}
