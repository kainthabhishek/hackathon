package com.expedia.hackathon.project101.controller;

import com.expedia.hackathon.project101.domain.Hotel;
import com.expedia.hackathon.project101.domain.ImageDetails;
import com.expedia.hackathon.project101.service.GoogleService;
import com.expedia.hackathon.project101.service.HotelService;
import com.expedia.hackathon.project101.service.ImageDetailsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class  WebService {

    GoogleService googleService;
    HotelService hotelService;

    @Autowired
    public WebService(GoogleService googleService, HotelService hotelService) {
        this.googleService = googleService;
        this.hotelService = hotelService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    ResponseEntity<?> getDescription(@RequestBody String request){
        //hit google api and return response

        System.out.println("###################" + request);

        String imageb64 = null;

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject)parser.parse(request);

            imageb64 = (String)object.get("imageb64");
        } catch (ParseException e) {
            //
        }

        System.out.println(imageb64);
        ImageDetails imageDetails = null;
        try {
            imageDetails = googleService.getImageDetails(imageb64);
        } catch (ImageDetailsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(imageDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDetails", method = RequestMethod.POST)
    ResponseEntity<?> getHotelData(@RequestBody String request){

        ObjectMapper objectMapper = new ObjectMapper();

        ImageDetails imageDetails = null;

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject)parser.parse(request);

            imageDetails = new ImageDetails((String)object.get("location"),
                    (Double) object.get("latitude"), (Double) object.get("longitude"), (String)object.get("imageb64"));

        } catch (ParseException e) {
            //
        }


        ArrayList<Hotel> hotels = hotelService.getHotels(imageDetails);

        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

}
