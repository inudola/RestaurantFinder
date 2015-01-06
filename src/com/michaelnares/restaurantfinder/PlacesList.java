package com.michaelnares.restaurantfinder;

import com.google.api.client.util.Key;

import java.io.Serializable;
import java.util.List;

/**
 * Created by michael on 04/01/15.
 */
public class PlacesList implements Serializable
{
    @Key
    public String status;

    @Key
    public List<Restaurant> results;

}
