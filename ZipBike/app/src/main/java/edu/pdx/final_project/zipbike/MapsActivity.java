package edu.pdx.final_project.zipbike;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/*

 Copyright © 2017 Rahul Kumar, Abhishek Tatke

    This is the activity for the maps screen

        In this activity nearest hub marker and our current location is displayed on the map screen
        users can click on a marker and click on the route button which opens google maps app

        Created By Abhishek Tatke,Rahul Kumar

  */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //variables for the markers
    public double curr_lat=120;
    public double curr_long=40;
    public double lat0=120;
    public double long0=40;
    public double lat1=120;
    public double long1=40;
    public double lat2=120;
    public double long2=40;
    public double lat3=120;
    public double long3=40;

    //zoom level on opening the screen
    //can set from 0 to 21
    public float zoomLevel = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the latitude longitude for current location 4 nearest hubs from previous activity

        curr_lat = getIntent().getDoubleExtra("curr_lat",120);
        curr_long =getIntent().getDoubleExtra("curr_long",40);

        lat0 = getIntent().getDoubleExtra("lat0",120);
        long0 =getIntent().getDoubleExtra("long0",40);

        lat1 = getIntent().getDoubleExtra("lat1",120);
        long1 =getIntent().getDoubleExtra("long1",40);

        lat2 = getIntent().getDoubleExtra("lat2",120);
        long2 =getIntent().getDoubleExtra("long2",40);

        lat3 = getIntent().getDoubleExtra("lat3",120);
        long3 =getIntent().getDoubleExtra("long3",40);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we have added markers to current location and four bike hubs
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers to current location and hib locations and move the camera

        LatLng curr_loc = new LatLng(curr_lat, curr_long);
        mMap.addMarker(new MarkerOptions().position(curr_loc).title(" current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curr_loc,zoomLevel));

        LatLng loc0 = new LatLng(lat0, long0);
        mMap.addMarker(new MarkerOptions().position(loc0).title("nearest location 0"));


        LatLng loc1 = new LatLng(lat1, long1);
        mMap.addMarker(new MarkerOptions().position(loc1).title("nearest location 1"));


        LatLng loc2 = new LatLng(lat2, long2);
        mMap.addMarker(new MarkerOptions().position(loc2).title("nearest location 2"));

        LatLng loc3 = new LatLng(lat3, long3);
        mMap.addMarker(new MarkerOptions().position(loc3).title("nearest location 3"));



    }
}
