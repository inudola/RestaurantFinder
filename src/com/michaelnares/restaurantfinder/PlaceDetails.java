package com.michaelnares.restaurantfinder;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by michael on 04/01/15.
 */
public class PlaceDetails implements Serializable
{

    @Key
    public String status;

    @Key
    public Restaurant result;

    @Override
    public String toString()
    {
        if (result != null)
        {
         return result.toString();
        }
        else return super.toString();
    }
}
