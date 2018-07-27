package com.expedia.hackathon.project101.service;

import com.expedia.hackathon.project101.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

@Service
public class GoogleService {

    RestTemplate restTemplate;
    private static final String baseUrl = "https://vision.googleapis.com/v1/images:annotate";
    private static final String KEY = "key";
    private static final String ACCESS_TOKEN = "AIzaSyB8f3u2joF72_6IzqLAl_Rn8GjYK3i9Erc";

    public ImageDetails getImageDetails(String imageb64) throws ImageDetailsException{

        restTemplate = new RestTemplate();

        ImageDetails imageDetails = null;
        ResponseEntity<String> responseEntity = null;

        GoogleServiceRequest googleServiceRequest = buildGoogleServiceRequest(imageb64);

        HttpEntity<?> httpEntity = new HttpEntity<Object>(googleServiceRequest, getRequestHeaders());

        responseEntity = restTemplate.exchange(baseUrl + "?" + KEY + "=" + ACCESS_TOKEN, HttpMethod.POST, httpEntity, String.class);

        String body = responseEntity.getBody();

        System.out.println(body);

        JSONParser parser = new JSONParser();

        try {
            JSONObject object = (JSONObject)parser.parse(body);

            JSONArray responses = (JSONArray) object.get("responses");
            JSONObject response = (JSONObject)responses.get(0);

            JSONArray landmarkAnnotations = (JSONArray)response.get("landmarkAnnotations");

            if(landmarkAnnotations == null){
                throw new ImageDetailsException("Location Not Found", HttpStatus.NOT_FOUND);
            }

            JSONObject landmarkAnnotation = (JSONObject)landmarkAnnotations.get(0);

            JSONArray locations = (JSONArray)landmarkAnnotation.get("locations");
            System.out.println(locations);
            JSONObject location = (JSONObject)locations.get(0);

            JSONObject latlng = (JSONObject) location.get("latLng");

            imageDetails = new ImageDetails((String)landmarkAnnotation.get("description"),
                                                    (Double) latlng.get("latitude"), (Double) latlng.get("longitude"), imageb64);

        } catch (ParseException e) {
            //do nothing
        }
        return imageDetails;
    }

    private HttpHeaders getRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private GoogleServiceRequest buildGoogleServiceRequest(String imageb64) {
        GoogleServiceRequest googleServiceRequest = new GoogleServiceRequest();

        ArrayList<GoogleRequest> googleRequests = new ArrayList<GoogleRequest>();

        GoogleRequest googleRequest = new GoogleRequest();
        googleRequest.setImage(new ImageObj(imageb64));

        ArrayList<GoogleFeatures> googleFeatures = new ArrayList<GoogleFeatures>();
        googleFeatures.add(new GoogleFeatures("LANDMARK_DETECTION", 1));

        googleRequest.setFeatures(googleFeatures);

        googleRequests.add(googleRequest);

        googleServiceRequest.setRequests(googleRequests);

        return googleServiceRequest;
    }
}
