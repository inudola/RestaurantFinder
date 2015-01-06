package com.michaelnares.restaurantfinder;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by michael on 10/12/2014.
 */
public class Restaurant implements Serializable {

    @Key
    public String id;

    @Key
    public String name;

    @Key
    public String reference;

    @Key
    public String icon;

    @Key
    public String vicinity;

    @Key
    public Geometry geometry;

    @Key
    public String formattedAddress;

    @Key
    public String formattedPhoneNumber;

    @Override
    public String toString()
    {
        return name + " - " + id + " - " + reference;
    }

    public static class Geometry implements Serializable
    {
        @Key
        public Location location;
    }

    public static class Location implements Serializable
    {
        @Key
        public double lat;

        @Key
        public double lng;
    }
}


