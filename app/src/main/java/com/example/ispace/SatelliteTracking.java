package com.example.ispace;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class SatelliteTracking extends AppCompatActivity {
    LocationManager mLocationManager;
    double latitude,longitude;
    AppCompatButton search_sat;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        setContentView(R.layout.satellite_tracking);
        int white = Color.parseColor("#ffffff");
        progressBar = findViewById(R.id.satellite_loader);
        progressBar.setVisibility(View.GONE);
        search_sat = (AppCompatButton) findViewById(R.id.search_sat);
        progressBar  = findViewById(R.id.satellite_loader);
        search_sat.setEnabled(false);
        search_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchSat();
            }
        });
        AppCompatButton appCompatButton = (AppCompatButton) findViewById(R.id.my_location);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestLocationPermission();
            }
        });
    }
    private void searchSat(){
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue requestQueue =  Volley.newRequestQueue(this);
        Log.e("Test","Function Called");



// Set the API endpoint URL
        String url = "https://api.n2yo.com/rest/v1/satellite/above/{observer_lat}/{observer_lng}/{observer_alt}/{search_radius}/{category}/&apiKey={api_key}";

// Set the API parameters
        String observerLat = String.valueOf(latitude);
        String observerLng = String.valueOf(longitude);
        String observerAlt = "5";
        String searchRadius = "90";
        String category = "0";
        String apiKey = "JNMAPF-J3BVJC-5ZMHRJ-50WZ";

        url = url.replace("{observer_lat}", observerLat)
                .replace("{observer_lng}", observerLng)
                .replace("{observer_alt}", observerAlt)
                .replace("{search_radius}", searchRadius)
                .replace("{category}", category)
                .replace("{api_key}", apiKey);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response",String.valueOf(response));
                progressBar.setVisibility(View.GONE);

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response","VolleyError");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getBaseContext(),"Error Retrieving Data from the server",Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);



    }

    private void onRequestLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            getLocation();
        }
    }
    private void getLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            enableLocation();
        } else {
            Location location = getLastKnownLocation();
            if (location != null) {
                 latitude = location.getLatitude();
                 longitude = location.getLongitude();
                EditText latitude_et = (EditText) findViewById(R.id.lat_val);
                EditText longitude_et = (EditText) findViewById(R.id.lon_val);
                latitude_et.setText(String.valueOf(latitude));
                longitude_et.setText(String.valueOf(longitude));
                search_sat.setEnabled(true);
                Log.e("Test","Button Enabled");


            } else {

                Toast.makeText(getBaseContext(), "Unable to find location, Please enter the details manually", Toast.LENGTH_LONG).show();
                ;
            }

        }

    }
    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

private void enableLocation(){
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int id) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int id) {
                    dialog.cancel();
                }
            });
    final AlertDialog alert = builder.create();
    alert.show();
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("CallBack",String.valueOf(requestCode));
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Log.e("toast","Toast Shown");
                Toast.makeText(this,"Need Location to Get Satellite Details",Toast.LENGTH_LONG).show();
            }
        }
    }



}
