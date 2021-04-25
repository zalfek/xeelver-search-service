package com.example.searchservice.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @GetMapping("/api/search")
    public String getInfo(){
        return "Reply from Search Service";
    }


}
