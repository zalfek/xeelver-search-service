package com.xeelver.searchservice.controllers;

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


    @GetMapping("/api/v1/search/health")
    public String health() {
        return "Search service is alive!!!";
    }

    @PostMapping(value = "/api/v1/search/flights", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> searchFlight(@RequestBody Map<String, String> requestParams) throws JsonProcessingException {
        return new ResponseEntity<Object>(objectMapper.readValue(flightSearchService.searchFlight(requestParams), Object.class), HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/search/flights/inspiration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getInpiration(@RequestParam Map<String, String> requestParams) throws JsonProcessingException {
        return new ResponseEntity<Object>(objectMapper.readValue(flightSearchService.getInpiration(requestParams), Object.class), HttpStatus.OK);
    }
}
