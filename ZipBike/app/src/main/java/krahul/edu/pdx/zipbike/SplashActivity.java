package krahul.edu.pdx.zipbike;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashActivity extends AppCompatActivity {

    //number of stations
    public static final int NUM_STATIONS =118;

    // Button for the start app activity
    Button startBtn;

    //variables for the plan activity
    String nameofPlan[] = new String[5];
    String prices[] = new String[5];
    String planDesc[] = new String[5];
    String signUp[] = new String[5];
    int numBikes[] = new int[NUM_STATIONS];

    //variables for location
    public double lat;
    public double longi;
    Location mprovider;

    //variables for the station info
    String hubID[] = new String[NUM_STATIONS];
    double longit[] = new double[NUM_STATIONS];
    double latit[] = new double[NUM_STATIONS];
    String stnAdd[] = new String[NUM_STATIONS];



    /* method for JSON Parsing of pricing plans*/

    public void parsePricing(String result)
    {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject reader = new JSONObject(result);
            String test=reader.getString("last_updated");
            test=reader.getString("ttl");
            test=reader.getString("data");
            JSONObject ob1 = new JSONObject(test);
            JSONArray w = ob1.getJSONArray("plans");
            for(int i=0;i<3;i++)
            {
                JSONObject jo1 = w.getJSONObject(i);
                String plan_id = jo1.getString("plan_id");
                nameofPlan[i] = jo1.getString("name");
                signUp[i]=jo1.getString("url");
                prices[i] = jo1.getString("price");
                planDesc[i] = jo1.getString("description");

            }

        } catch (Exception e) {}

    }

    /* Method for JSON parsing of station status*/

    public void parseStnStatus(String result)
    {

        String hubID[] = new String[180];

        try {
            JSONObject json = new JSONObject(result);

            JSONObject reader = new JSONObject(result);
            String test=reader.getString("last_updated");
            test=reader.getString("ttl");
            test=reader.getString("data");
            JSONObject ob1 = new JSONObject(test);
            JSONArray w = ob1.getJSONArray("stations");

            for(int i=0;i<NUM_STATIONS;i++)
            {
                JSONObject jo1 = w.getJSONObject(i);
                hubID[i] = jo1.getString("station_id");
                numBikes[i]=jo1.getInt("num_bikes_available");

            }


        } catch (Exception e) { }
    }

    /* Method for JSON parsing of station information*/

    public void parseStnInfo(String result)
    {

        try {
            JSONObject json = new JSONObject(result);
            JSONObject reader = new JSONObject(result);
            String test=reader.getString("last_updated");
            test=reader.getString("ttl");
            test=reader.getString("data");
            JSONObject ob1 = new JSONObject(test);
            JSONArray w = ob1.getJSONArray("stations");

            for(int i=0;i<NUM_STATIONS;i++)
            {
                JSONObject jo1 = w.getJSONObject(i);

                hubID[i] = jo1.getString("station_id");
                test=jo1.getString("name");
                longit[i]=jo1.getDouble("lon");
                latit[i]=jo1.getDouble("lat");
                stnAdd[i] = jo1.getString("address");
            }

        } catch (Exception e) {  }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Request fine permission
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        //Listener for location change .When a location change occurs this listener is activated.
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                longi = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

        };

        //Setting location manager to access GPS settings

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        String locationProvider = LocationManager.GPS_PROVIDER;
        mprovider = locationManager.getLastKnownLocation(locationProvider);

        //checking mprovider and setting the latitude and longitude if mprovider is not null.

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            Location location = mprovider;
            if (location != null) {

                lat = location.getLatitude();
                longi = location.getLongitude();
            } else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }


        // get reference to the views
        //starting menu activity on start button press and send JSON parsed data to menu activity

        startBtn = (Button) findViewById(R.id.start_button);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SplashActivity.this, MenuActivity.class);

                i.putExtra("bikes", numBikes);
                i.putExtra("latitude", latit);
                i.putExtra("longitude", longit);
                i.putExtra("currentLat", lat);
                i.putExtra("currentLon", longi);
                i.putExtra("plan", nameofPlan);
                i.putExtra("prices", prices);
                i.putExtra("desc", planDesc);
                i.putExtra("url", signUp);
                i.putExtra("hub", hubID);
                i.putExtra("add", stnAdd);

                startActivity(i);

            }
        });

        // check if you are connected or not
        if (isConnected()) {

            Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
        }

        // call AsyncTask to perform network operation on separate thread

        try {
            new HttpAsyncTask().execute("http://biketownpdx.socialbicycles.com/opendata/system_pricing_plans.json").get();
            new HttpAsyncTask().execute("http://biketownpdx.socialbicycles.com/opendata/station_information.json").get();
            new HttpAsyncTask().execute("http://biketownpdx.socialbicycles.com/opendata/station_status.json").get();
        } catch (Exception e) {
        }

    }

    //method to get a url and check the response

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {


            // create HttpClient
          //  HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
         //   HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
         //   inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    //method to convert input stream to string

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    //method to check if connection is established or not

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // In onPostExecute the JSON parsing methods are called
        @Override
        protected void onPostExecute(String result) {

            parsePricing(result);
            parseStnInfo(result);
            parseStnStatus(result);

        }

    }}
