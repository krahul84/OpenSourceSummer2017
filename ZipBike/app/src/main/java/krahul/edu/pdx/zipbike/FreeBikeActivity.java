package krahul.edu.pdx.zipbike;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FreeBikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_bike);

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
