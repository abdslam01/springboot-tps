package com.web.controller;

import com.model.ItemCatalog;
import com.model.Movie;
import com.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoProxy {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackItemCatalog")
    public ItemCatalog getItemCatalog(Rating rating){
        Movie movie=restTemplate.getForObject("http://movie-info-service/movieinfo/"+rating.getMovieId(), Movie.class);
        assert movie!=null;
        return new ItemCatalog(movie.getTitle(), movie.getOverview(), rating.getRating());
    }
    public ItemCatalog getFallBackItemCatalog(Rating rating){
        return new ItemCatalog("Movie name not found","overview service is down", rating.getRating());
    }
}
