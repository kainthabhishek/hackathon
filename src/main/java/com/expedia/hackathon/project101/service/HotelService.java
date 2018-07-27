package com.expedia.hackathon.project101.service;

import com.expedia.hackathon.project101.domain.Hotel;
import com.expedia.hackathon.project101.domain.ImageDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.ThreadLocalRandom;


import java.util.ArrayList;

@Service
public class HotelService {

    DBHandler dbHandler;
    RestTemplate restTemplate;
    private final Integer m_limit = 10;
    private final String imagePrefix = "https://images.trvl-media.com";

    @Autowired
    public HotelService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public ArrayList<Hotel> getHotels(ImageDetails imageDetails){

//        try {
//            return dbHandler.readDataBase(imageDetails.getLocation());
//        } catch (Exception e) {
//            return null;
//        }

        ArrayList<Hotel> hotels = new ArrayList<>();

        restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        HttpEntity<?> httpEntity = new HttpEntity<Object>(getRequestHeaders());

        responseEntity = restTemplate.exchange("https://www.expedia.com/m/api/hotel/search/v3?city=" + imageDetails.getLocation()
                + "&enableSponsoredListings=false&enableTravelAdsList=false&filterUnavailable=false&priceType=DEFAULT_POS_PRICE_TYPE_WITH_FEES&resultsPerPage=25&returnOpaqueHotels=false&sendAdaptedResponse=false&shopWithPoints=false&"
                + "" + "checkInDate=2017-06-06&checkOutDate=2017-06-16&room1=2", HttpMethod.GET, httpEntity, String.class);
        String body = responseEntity.getBody();

        System.out.println(body);

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject)parser.parse(body);

            JSONArray hotelList = (JSONArray) object.get("hotelList");

            for(int i = 0; i < m_limit && i < hotelList.size(); i++){

                JSONObject hotel = (JSONObject)hotelList.get(i);

                hotels.add(new Hotel((String)hotel.get("name"), (String)hotel.get("address"),
                        ThreadLocalRandom.current().nextInt(Integer.valueOf(30), Integer.valueOf(200) + 1),
                        Float.valueOf((String)hotel.get("hotelGuestRating")), (String)hotel.get("shortDescription"),
                        imagePrefix + (String)hotel.get("largeThumbnailUrl")));
            }

        } catch (ParseException e) {
            //do nothing
        }

        return hotels;
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
