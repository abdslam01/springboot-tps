package com.web.controller;

import com.model.ItemCatalog;
import com.model.Movie;
import com.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/{userId}")
    public List<ItemCatalog> getCatalog(@PathVariable String userId) {
        UserRating ratings=restTemplate.getForObject("http://localhost:8081/ratingdata/users/"+userId, UserRating.class);

        return ratings.getUserRating().stream().map(
                rating -> {
                    Movie movie=restTemplate.getForObject("http://localhost:8080/movieinfo/"+rating.getMovieId(), Movie.class);
                    return new ItemCatalog(movie.getName(), "movie mission impossible 2020", rating.getRating());
                }).collect(Collectors.toList());
        /*return Arrays.asList(
                new ItemCatalog("Transformers","Test",4)
        );*/
    }
}
