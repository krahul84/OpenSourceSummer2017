package edu.pdx.final_project.zipbike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/*
    Copyright © 2017 Rahul Kumar, Abhishek Tatke

    This is the activity for the hub info screen

        In this activity the data about the hub is received from the hubid activty.
        This activty displays the hub info on the screen

        MenuActivity -> HubId Activity ->FreeBikeActivity

        Created By Rahul Kumar, Abhishek Tatke


  */
public class FreebikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freebike);

        //Getting station info from previous activity

        final int bike = getIntent().getIntExtra("bikes",0);
        final String  stnAdd = getIntent().getStringExtra("add");

        //setting data on the screen

        TextView free = (TextView) findViewById(R.id.bikes);
        TextView add = (TextView) findViewById(R.id.Address);
        add.setText(stnAdd);
        free.setText("Free Bikes: " + bike);


    }
}
