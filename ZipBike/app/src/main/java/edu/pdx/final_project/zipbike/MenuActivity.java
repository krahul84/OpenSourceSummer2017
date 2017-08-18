package edu.pdx.final_project.zipbike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;


/*

 Copyright © 2017 Rahul Kumar, Abhishek Tatke

    This is the activity for the menu screen

        In this activity the Json data is received from splash activity.

        Three image buttons are implemented and a listener is assigned to each button.
        This activity wait for button click and next activity is started according to button pressed

        MenuActivity -> locationActivity
                     -> PriceActivity
                     -> HubId Activity

        Created By Abhishek Tatke, Rahul Kumar


  */

public class MenuActivity extends AppCompatActivity  {

    //Text views for loaction price and free bikes
    TextView locatn;
    TextView pricing;
    TextView freeInfo;

    //Image Buttons for loaction price and free bikes
    ImageButton priceBtn;
    ImageButton locnBtn;
    ImageButton bikeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


       //Getting the data from the splash screen activity

        final int[] mybikeArray = getIntent().getIntArrayExtra("bikes");
        final double[] myLatArray = getIntent().getDoubleArrayExtra("latitude");
        final double[] myLonArray = getIntent().getDoubleArrayExtra("longitude");
        final double currentLat=getIntent().getDoubleExtra("currentLat",45.51780695);
        final double currentLon=getIntent().getDoubleExtra("currentLon",-122.87033262);
        final String[] nameofPlan = getIntent().getStringArrayExtra("plan");
        final String[] prices = getIntent().getStringArrayExtra("prices");
        final String[] planDesc = getIntent().getStringArrayExtra("desc");
        final String[] signUp = getIntent().getStringArrayExtra("url");
        final String[] hubID = getIntent().getStringArrayExtra("hub");
        final String[] stnAdd = getIntent().getStringArrayExtra("add");

        //setting the images and text views to the buttons and text views

        priceBtn = (ImageButton)findViewById(R.id.price_button);
        locnBtn = (ImageButton)findViewById(R.id.nearMe_button);
        bikeBtn = (ImageButton)findViewById(R.id.hubId_button) ;

        pricing = (TextView)findViewById(R.id.Pricing);
        locatn = (TextView)findViewById(R.id.Locatn);
        freeInfo  = (TextView)findViewById(R.id.free) ;


        // listener for the price activity
        //passes the relevant data to the next activity

        priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MenuActivity.this, PriceActivity.class);
                it.putExtra("plan",nameofPlan);
                it.putExtra("prices",prices);
                it.putExtra("desc",planDesc);
                it.putExtra("url",signUp);

                startActivity(it);
            }
        });

        //listener for the location activity
        //passes the location data to the activity

        locnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1 = new Intent(MenuActivity.this, LocationActivity.class);

                it1.putExtra("bikes", mybikeArray);
                it1.putExtra("latitude", myLatArray);
                it1.putExtra("longitude", myLonArray);
                it1.putExtra("currentLat",currentLat);
                it1.putExtra("currentLon",currentLon);

                startActivity(it1);
            }
        });

        //listener for the hub id activity
        //pases free bikes hub id and address to the activity

        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it2 = new Intent(MenuActivity.this, HubidActivity.class);

                it2.putExtra("bikes", mybikeArray);
                it2.putExtra("hub",hubID);
                it2.putExtra("add",stnAdd);
                startActivity(it2);
            }
        });


    }



}
