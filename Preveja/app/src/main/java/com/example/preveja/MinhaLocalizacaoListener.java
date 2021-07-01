package com.example.preveja;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MinhaLocalizacaoListener implements LocationListener {

    public static double Latitude;
    public static double Longitude;

    @Override
    public void onLocationChanged(Location location) {
        this.Latitude = location.getLatitude();
        this.Longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
