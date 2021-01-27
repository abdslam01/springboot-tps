package com.web.controller;

import com.model.ItemCatalog;
import com.model.Movie;
import com.model.Rating;
import com.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public MovieInfoProxy movieInfoProxy;

    @Autowired
    public UserRatingProxy userRatingProxy;

    @GetMapping(value="/{userId}")
    /*@HystrixCommand(fallbackMethod = "getFallBackCatalogue",
            commandProperties = {
                @HystrixProperty(name="execution.isolation.thread.timeoutMilliseconds", value="2000"),
                @HystrixProperty(name="circuitBreaker.requestVolumeThreadHold", value="5"),
                @HystrixProperty(name="circuitBreaker.errorThreadHoldPersontage", value="50"),
                @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
            }
    )*/
    public List<ItemCatalog> getCatalog(@PathVariable String userId) {
        return userRatingProxy.getUserRating(userId).getUserRating().stream().map(
                rating -> {return movieInfoProxy.getItemCatalog(rating);}
        ).collect(Collectors.toList());
    }
    public List<ItemCatalog> getFallBackCatalogue(@PathVariable String userId) {
        return Arrays.asList(
                new ItemCatalog("No Movie", "This response is from getFallBackCatalogue", 0)
        );
    }
}
    /*
    @HystrixCommand(fallbackMethod = "getFallBackCatalogue")
    public List<ItemCatalog> getCatalogue(@PathVariable String userId) {
        UserRating ratings=restTemplate.getForObject("http://rating-data-service/ratingdata/users/"+userId, UserRating.class);
        assert ratings != null;
        return ratings.getUserRating().stream().map(
                rating -> {
                    Movie movie=restTemplate.getForObject("http://movie-info-service/movieinfo/"+rating.getMovieId(), Movie.class);
                    assert movie!=null;
                    return new ItemCatalog(movie.getTitle(), movie.getOverview(), rating.getRating());
                }).collect(Collectors.toList());
    }
    public List<ItemCatalog> getFallBackCatalogue(@PathVariable String userId){
        return Arrays.asList(
                new ItemCatalog("No Movie", "This response is from getFallBackCatalogue", 0)
        );
    }*/
