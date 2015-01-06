package com.michaelnares.restaurantfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.HashMap;

/**
 * Created by michael on 12/10/2014.
 */
public class Utils {

    private Context context;

    public Utils (Context context)
    {
        this.context = context;
    }

    public boolean hasGPS()
    {
        final LocationManager locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void buildAlertMessageNoGps()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean hasWiFiOrData()
    {
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING)
        {
           return true;
        }
        else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING)
        {
           return true;
        }
        else return false;
    }

    public void buildAlertMessageNoWiFiOrData()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your Wi-Fi and data are both disabled, do you want to enable them?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void showOtherAlertDialog(String title, String message)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        alertDialog.show();
    }

    public void showOtherStatusMessages(String status)
    {
        if (status.equals("ZERO_RESULTS"))
        {
            // Zero results found
            showOtherAlertDialog("Near Places",
                    "Sorry no places found. Try to change the types of places")
            ;
        } else if (status.equals("UNKNOWN_ERROR"))
        {
            showOtherAlertDialog("Places Error",
                    "Sorry unknown error occured.");
        } else if (status.equals("OVER_QUERY_LIMIT"))
        {
            showOtherAlertDialog("Places Error",
                    "Sorry query limit to google places is reached");
        } else if (status.equals("REQUEST_DENIED"))
        {
            showOtherAlertDialog("Places Error",
                    "Sorry error occured. Request is denied");
        } else if (status.equals("INVALID_REQUEST"))
        {
            showOtherAlertDialog("Places Error",
                    "Sorry error occured. Invalid Request");
        }
        else
        {
            showOtherAlertDialog("Places Error",
                    "Sorry error occured.");
        }
    }
}
