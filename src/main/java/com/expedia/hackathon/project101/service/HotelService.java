package com.expedia.hackathon.project101.service;

import com.expedia.hackathon.project101.domain.Hotel;
import com.expedia.hackathon.project101.domain.ImageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
public class HotelService {

    DBHandler dbHandler;

    @Autowired
    public HotelService(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public ArrayList<Hotel> getHotels(ImageDetails imageDetails){

        try {
            return dbHandler.readDataBase(imageDetails.getLocation());
        } catch (Exception e) {
            return null;
        }
    }
}
