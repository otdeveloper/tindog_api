package com.key.tindog.service;

import com.key.tindog.model.Location;

public class LocationService {

    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist *= 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    public static double getDistance(Location location1, Location location2) {
        if ((location1.getLatitude() == location2.getLatitude())
                && (location1.getLongitude() == location2.getLongitude())) {
            return 0;
        } else {
            double theta = location1.getLongitude() - location2.getLongitude();
            double dist = Math.sin(Math.toRadians(location1.getLatitude())) * Math.sin(Math.toRadians(location2.getLatitude())) + Math.cos(Math.toRadians(location1.getLatitude())) * Math.cos(Math.toRadians(location2.getLatitude())) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist *= 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }
}
