package edu.pdx.final_project.zipbike;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/*

 Copyright © 2017 Rahul Kumar, Abhishek Tatke

    This is the activity for the location screen

        In this activity the Json data is received from menu activity.
        It calaculates the nearest four hubs present using the distanceto method.
        It displays the hub addresses and distance and free bikes info and user can
        click on the show on map button to display the hubs on a map


        LocationActivity -> MapsActivity

        Created By Rahul Kumar, Abhishek Tatke

  */

public class LocationActivity extends AppCompatActivity {

   //number of stations
    public static final int NUM_STATIONS =118;

    //butoon for the show on map
    Button MapBtn;

    double mi =0.000621371;

   //object for storing json data
    Station[] s = new Station[NUM_STATIONS];

    // geocoder is used to convert latitude longitude to addresses
    Geocoder geocoder;
    List<Address> addresses0;
    List<Address> addresses1;
    List<Address> addresses2;
    List<Address> addresses3;

    //variables to store the distances,latitude,longitude,free bikes of the nearest hubs
    double d[]=new double[120];
    double min_dist[] = new double[4];
    double near_latitude[]=new double[4];
    double near_longitude[]=new double[4];
    int free_bikes[]=new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //getting data from menu activity
        final int[] mybikeArray = getIntent().getIntArrayExtra("bikes");
        final double[] myLatArray = getIntent().getDoubleArrayExtra("latitude");
        final double[] myLonArray = getIntent().getDoubleArrayExtra("longitude");
        final double currentLat = getIntent().getDoubleExtra("currentLat", 45.51780695);
        final double currentLon = getIntent().getDoubleExtra("currentLon", -122.87033262);

        //storing data into the station objects
        for (int i = 0; i < NUM_STATIONS; i++) {
            s[i] = new Station();
            s[i].setNumBikes(mybikeArray[i]);
            s[i].setLat(myLatArray[i]);
            s[i].setLon(myLonArray[i]);
        }

        //Setting text views for four hubs - displays address distance,free bikes

        TextView near = (TextView) findViewById(R.id.nearst);
        TextView other = (TextView) findViewById(R.id.other);
        near.setTextColor(Color.BLACK);
        other.setTextColor(Color.BLACK);

        TextView add1 = (TextView) findViewById(R.id.Add1);
        TextView dist1 = (TextView) findViewById(R.id.dist1);
        TextView free1 = (TextView) findViewById(R.id.free1);

        TextView add2 = (TextView) findViewById(R.id.Add2);
        TextView dist2 = (TextView) findViewById(R.id.dist2);
        TextView free2 = (TextView) findViewById(R.id.free2);

        TextView add3 = (TextView) findViewById(R.id.Add3);
        TextView dist3 = (TextView) findViewById(R.id.dist3);
        TextView free3 = (TextView) findViewById(R.id.free3);

        TextView add4 = (TextView) findViewById(R.id.Add4);
        TextView dist4 = (TextView) findViewById(R.id.dist4);
        TextView free4 = (TextView) findViewById(R.id.free4);


        //create 2 location objects
        //one for current location,one for the hubs location
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");

        locationA.setLatitude(currentLat);
        locationA.setLongitude(currentLon);

        //filling min dist with a high value for comparison
        int i;
        Arrays.fill(min_dist, 1000000);

            //loop to compare the distances
            //location A is our current location
            //Location B is set and distances are calculated using distanceto() and compared.distance is returned in meters
            //Null pointer exception is caught because somreimes Latitudes and longitudes are not parsed correctly which crashes the app

            for (i = 0; i < NUM_STATIONS; i++) {
                locationB.setLatitude(s[i].getLat());
                locationB.setLongitude(s[i].getLon());

                //another method to calculate distance
                //d[i] = acos(sin(s[i].getLat()) * sin(currentLat) + cos(s[i].getLat()) * cos(currentLat) * cos(s[i].getLon() - currentLon));

               try
               {
                   d[i] = locationA.distanceTo(locationB);
               }catch (NullPointerException e) {d[i]=100000;}


                //Algorithm for the comparision of distances
                //This sets the min dist,latitude,longitude and the free bikes of nearest hub

                if (min_dist[0] > d[i]) {

                    min_dist[3] = min_dist[2];
                    near_latitude[3] = near_latitude[2];
                    near_longitude[3] = near_longitude[2];
                    free_bikes[3] = free_bikes[2];

                    min_dist[2] = min_dist[1];
                    near_latitude[2] = near_latitude[1];
                    near_longitude[2] = near_longitude[1];
                    free_bikes[2] = free_bikes[1];

                    min_dist[1] = min_dist[0];
                    near_latitude[1] = near_latitude[0];
                    near_longitude[1] = near_longitude[0];
                    free_bikes[1] = free_bikes[0];

                    min_dist[0] = d[i];
                    near_latitude[0] = s[i].getLat();
                    near_longitude[0] = s[i].getLon();
                    free_bikes[0] = s[i].getNumBikes();

                } else if (min_dist[1] > d[i]) {

                    min_dist[3] = min_dist[2];
                    near_latitude[3] = near_latitude[2];
                    near_longitude[3] = near_longitude[2];
                    free_bikes[3] = free_bikes[2];

                    min_dist[2] = min_dist[1];
                    near_latitude[2] = near_latitude[1];
                    near_longitude[2] = near_longitude[1];
                    free_bikes[2] = free_bikes[1];

                    min_dist[1] = d[i];
                    near_latitude[1] = s[i].getLat();
                    near_longitude[1] = s[i].getLon();
                    free_bikes[1] = s[i].getNumBikes();


                } else if (min_dist[2] > d[i]) {

                    min_dist[3] = min_dist[2];
                    near_latitude[3] = near_latitude[2];
                    near_longitude[3] = near_longitude[2];
                    free_bikes[3] = free_bikes[2];

                    min_dist[2] = d[i];
                    near_latitude[2] = s[i].getLat();
                    near_longitude[2] = s[i].getLon();
                    free_bikes[2] = s[i].getNumBikes();


                } else if (min_dist[3] > d[i]) {
                    min_dist[3] = d[i];
                    near_latitude[3] = s[i].getLat();
                    near_longitude[3] = s[i].getLon();
                    free_bikes[3] = s[i].getNumBikes();
                }

            }


        //get address using latitude and longitude
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addresses0 = geocoder.getFromLocation(near_latitude[0], near_longitude[0], 1);
            addresses1 = geocoder.getFromLocation(near_latitude[1], near_longitude[1], 1);
            addresses2 = geocoder.getFromLocation(near_latitude[2], near_longitude[2], 1);
            addresses3 = geocoder.getFromLocation(near_latitude[3], near_longitude[3], 1);
        } catch (Exception e) {
        }

        //Getting the first line of address
        // If any additional address line present, check with max available address lines by getMaxAddressLineIndex()

        String address0 = addresses0.get(0).getAddressLine(0);
        String address1 = addresses1.get(0).getAddressLine(0);
        String address2 = addresses2.get(0).getAddressLine(0);
        String address3 = addresses3.get(0).getAddressLine(0);


        //Set the font colour for the txt view

        add1.setTextColor(Color.RED);
        dist1.setTextColor(Color.BLUE);
        free1.setTextColor(Color.rgb(255,165,0));
        add1.setText(address0);
        dist1.setText("Dist: ~"  + new DecimalFormat("#.##").format(mi*min_dist[0])+"mi  ");
        free1.setText("Free Bikes: " + free_bikes[0]);

        dist2.setTextColor(Color.BLUE);
        free2.setTextColor(Color.rgb(255,165,0));
        add2.setText(address1);
        dist2.setText("Dist: ~"  + new DecimalFormat("#.##").format(mi*min_dist[1])+"mi  ");
        free2.setText("Free Bikes: " + free_bikes[1]);

        dist3.setTextColor(Color.BLUE);
        free3.setTextColor(Color.rgb(255,165,0));
        add3.setText(address2);
        dist3.setText("Dist: ~"  + new DecimalFormat("#.##").format(mi*min_dist[2])+"mi  ");
        free3.setText("Free Bikes: " + free_bikes[2]);

        dist4.setTextColor(Color.BLUE);
        free4.setTextColor(Color.rgb(255,165,0));
        add4.setText(address3);
        dist4.setText("Dist: ~"  + new DecimalFormat("#.##").format(mi*min_dist[3])+"mi  ");
        free4.setText("Free Bikes: " + free_bikes[3]);

        //Setting the listener for the show on map button
        //Also send the nearest four hubs info calculated to the maps activity

        MapBtn = (Button) findViewById(R.id.map_button);
        MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent it1 = new Intent(LocationActivity.this, MapsActivity.class);
                it1.putExtra("curr_lat",currentLat);
                it1.putExtra("curr_long",currentLon);

                it1.putExtra("lat0",near_latitude[0]);
                it1.putExtra("long0",near_longitude[0]);

                it1.putExtra("lat1",near_latitude[1]);
                it1.putExtra("long1",near_longitude[1]);

                it1.putExtra("lat2",near_latitude[2]);
                it1.putExtra("long2",near_longitude[2]);

                it1.putExtra("lat3",near_latitude[3]);
                it1.putExtra("long3",near_longitude[3]);

                startActivity(it1);


            }
        });
    }
}