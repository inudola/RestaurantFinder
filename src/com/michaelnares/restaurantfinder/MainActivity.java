package com.michaelnares.restaurantfinder;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity
{
    private Boolean isInternetPresent;
    private APIClient client;
    private PlacesList nearPlaces;
    private GPSTracker gpsTracker;
    private Button btnShowOnMap;
    private ProgressDialog pDialog;
    private ListView lv;
    private List<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String, String>>();
    private final Context context = this;
    private final Utils utils = new Utils(context);
    private final int listID = R.id.list;
    private final int mapButtonID = R.id.btn_show_map;
    private final MenuInflater menuInflater = getMenuInflater();

    //Key strings
    public static final String KEY_REFERENCE = "reference";
    public static final String KEY_NAME = "name";
    public static final String KEY_VICINITY = "vicinity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (!utils.hasWiFiOrData())
    {
        utils.buildAlertMessageNoWiFiOrData();
        return;
    }

    else if (!utils.hasGPS())
    {
        utils.buildAlertMessageNoGps();
        return;
    }

    else
    {
        gpsTracker = new GPSTracker(context);
        Log.d("com.michaelnares.restaurantfinder", "latitude:" + gpsTracker.getLatitude() + ", longitude: " + gpsTracker.getLongitude());
    }
        lv = (ListView)findViewById(listID);
        btnShowOnMap = (Button)findViewById(mapButtonID);

        new LoadPlaces().execute();

       btnShowOnMap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(context,
                        PlacesMapActivity.class);

                i.putExtra("user_latitude", Double.toString(gpsTracker.getLatitude()));
                i.putExtra("user_longitude", Double.toString(gpsTracker.getLongitude()));
                i.putExtra("near_places", nearPlaces);
                startActivity(i);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        SinglePlaceActivity.class);

                // Sending place reference id to single place activity
                // place reference id used to get "Place full details"
                in.putExtra(KEY_REFERENCE, reference);
                startActivity(in);
            }
        });
    }

    class LoadPlaces extends AsyncTask<String, String, String>
        {
            /**
             * Before starting background thread show ProgressDialog
             * */

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage(Html.fromHtml("<strong>Search</strong><br/>Loading Places..."));
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... strings)
            {
                client = new APIClient();

                try
                {
                    final String types = "cafe|restaurant";
                    double radius = 1000;
                    nearPlaces = client.search(gpsTracker.getLatitude(), gpsTracker.getLongitude(), radius, types);
                }
                catch (Exception e)
                {
                Log.e("com.michaelnares.restaurantfinder", e.toString());
                }
                return null;
            }

            protected void onPostExecute(String fieURL)
            {
                pDialog.dismiss();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String status = nearPlaces.status;

                        if (status.equals("OK"))
                        {
                            if (nearPlaces.results != null) {
                                for (Restaurant r : nearPlaces.results) {
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put(KEY_REFERENCE, r.reference);
                                    map.put(KEY_NAME, r.name);
                                    placesListItems.add(map);
                                }
                                ListAdapter adapter = new SimpleAdapter(MainActivity.this, placesListItems, listID,
                                        new String[]{KEY_REFERENCE, KEY_NAME}, new int[]{R.id.reference, R.id.name});
                                lv.setAdapter(adapter);
                            }
                        }
                        else utils.showOtherStatusMessages(status);
                    }// ends run method
                }); // ends runOnUiThread() method
                }// ends onPostExecute() method
            }
}




