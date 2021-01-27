package com.web.controller;

import com.model.Rating;
import com.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingProxy {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackUserRating")
    public UserRating getUserRating(@PathVariable String userId){
        return restTemplate.getForObject("http://rating-data-service/ratingdata/users/"+userId, UserRating.class);
    }
    public UserRating getFallBackUserRating(@PathVariable String userId){
        return new UserRating(Arrays.asList(new Rating("0",0)));
    }
}
