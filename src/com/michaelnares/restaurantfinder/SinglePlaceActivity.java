package com.michaelnares.restaurantfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by michael on 05/01/15.
 */
public class SinglePlaceActivity extends Activity {
    private boolean isInternetPresent;
    private final Context context = getApplicationContext();
    private final Utils utils = new Utils(context);
    private final APIClient client = new APIClient();
    private PlaceDetails placeDetails;
    private ProgressDialog pDialog;
    private final Intent i = getIntent();

    public static final String KEY_REFERENCE = "reference";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_place);

        String reference = i.getStringExtra(KEY_REFERENCE);
        new LoadSinglePlaceDetails().execute(reference);
    }

    /**
     * Background Async Task to Load Google places
     */
    class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SinglePlaceActivity.this);
            pDialog.setMessage("Loading profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String reference = args[0];
            try {
                placeDetails = client.getPlaceDetails(reference);
            } catch (Exception e) {
                Log.e("com.michaelnares.restaurantfinder", e.toString());
            }
            return null;
        }// ends doInBackground() method

        @Override
        protected void onPostExecute(String fileUrl) {
            pDialog.dismiss();
            if (placeDetails != null) {
                String status = placeDetails.status;
                if (status.equals("OK")) {
                    if (placeDetails.result != null) {
                        String name = placeDetails.result.name;
                        String address = placeDetails.result.formattedAddress;
                        String phone = placeDetails.result.formattedPhoneNumber;
                        String latitude = Double.toString(placeDetails.result.geometry.location.lat);
                        String longitude = Double.toString(placeDetails.result.geometry.location.lng);

                        Log.d("com.michaelnares.restaurantfinder", "Place: " + name + address + phone + latitude + longitude);

                        TextView lblName = (TextView) findViewById(R.id.name);
                        TextView lblAddress = (TextView) findViewById(R.id.address);
                        TextView lblPhone = (TextView) findViewById(R.id.phone);
                        TextView lblLocation = (TextView) findViewById(R.id.location);

                        //Check for null data
                        name = name == null ? "Not present" : name;
                        address = address == null ? "Not present" : address;
                        phone = phone == null ? "Not present" : phone;
                        latitude = latitude == null ? "Not present" : latitude;
                        longitude = longitude == null ? "Not present" : longitude;

                        lblName.setText(name);
                        lblAddress.setText(address);
                        lblPhone.setText(Html.fromHtml("<strong>Phone:</strong> " + phone));
                        lblLocation.setText(Html.fromHtml("<strong>Latitude:</strong> " + latitude +
                                ", <strong>Longitude:</strong> " + longitude));
                    } // ends if block for if status.equals("OK")
                    else utils.showOtherStatusMessages(status);
                } // ends if block for placeDetails != null
            }
        }
    }
}
