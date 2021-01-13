package com.web.controller;

import com.model.Rating;
import com.model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value="/ratingdata")
public class RatingDataResource {
    @GetMapping(value="/{movieId}")
    public Rating getRating(@PathVariable String movieId){
        return new Rating(movieId, 4);
    }

    @GetMapping(value="/users/{userId}")
    public UserRating getUserRating(@PathVariable String userId){
        List<Rating> ratings=Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",5)
        );
        return new UserRating(ratings);
    }
}
