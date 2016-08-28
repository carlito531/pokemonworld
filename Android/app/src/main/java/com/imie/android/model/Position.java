package com.imie.android.model;

import java.io.Serializable;

/**
 * Created by charly on 06/08/2016.
 */
public class Position implements Serializable {

    private float latitude;
    private float longitude;
    private Zone zones;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Zone getZones() {
        return zones;
    }

    public void setZones(Zone zones) {
        this.zones = zones;
    }
}
