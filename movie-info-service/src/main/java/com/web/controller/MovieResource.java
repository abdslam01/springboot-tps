package com.web.controller;

import com.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/movieinfo")
public class MovieResource {
    // External API: MovieDB --> call via restTemplates
    @Value("${api-key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping(value="/{movieId}")
    public Movie getMovie(@PathVariable String movieId){
        //External API: MovieDB
        Movie movie=restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+movieId +"?api_key="+apiKey,
                Movie.class);
        return new Movie(movieId, movie.getTitle(), movie.getOverview());
    }
}
