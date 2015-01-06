package com.michaelnares.restaurantfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by michael on 05/01/15.
 */
public class PlacesMapActivity extends Activity
{
    private LatLng latLng;
    private GoogleMap map;
    private PlacesList nearPlaces;
    private final Intent i = getIntent();
    private double myLocationLat, myLocationLong;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_places);
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

        // Users current geo location
        String user_latitude = i.getStringExtra("user_latitude");
        String user_longitude = i.getStringExtra("user_longitude");
       try {
           myLocationLat = Double.parseDouble(user_latitude);
           myLocationLong = Double.parseDouble(user_longitude);
       }
       catch (NumberFormatException nfe)
       {
           Log.e("com.michaelnares.restaurantfinder", nfe.toString());
       }

        // Nearplaces list
        nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

        if (nearPlaces.results != null)
        {
            // loop through all the places
            for (Restaurant place : nearPlaces.results)
            {
                double latitude = place.geometry.location.lat; // latitude
                double longitude = place.geometry.location.lng; // longitude
                latLng = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(latLng)).setTitle(place.name);
            }
            LatLng myLocation = new LatLng(myLocationLat, myLocationLong);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_places, menu);
        return true;
    }
}
