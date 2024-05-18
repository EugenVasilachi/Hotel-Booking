package com.example.demo.utils;

public class GeoUtils {

    private static final double EARTH_RADIUS = 6367.449;    // km

    public static double calculateDistanceBetweenTwoPositions(
            double latitude1,
            double longitude1,
            double latitude2,
            double longitude2) {
        // Haversine formula
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;
        return distance * 1000;    // meters
    }
}
