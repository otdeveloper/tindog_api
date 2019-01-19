package com.key.Tindog.service;

import com.key.tindog.service.LocationService;
import org.junit.Test;

public class LocationServiceTests {

    @Test
    public void getDistanceTest() {
//      double[] adressOne = {55.688680, 37.584568};
//      double[] adressTwo = {55.684818, 37.579978};
        double[] adressOne = {1, 1};
        double[] adressTwo = {2, 2};

        System.out.println(LocationService.getDistance(adressOne[0], adressOne[1], adressTwo[0], adressTwo[1]));


    }
}
