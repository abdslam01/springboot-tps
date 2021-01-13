package com.web.controller;

import com.model.Movie;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/movieinfo")
public class MovieResource {
    @RequestMapping(value="/{movieId}")
    public Movie getMovie(@PathVariable String movieId){
        return new Movie(movieId, "mission impossible 7");
    }
}
