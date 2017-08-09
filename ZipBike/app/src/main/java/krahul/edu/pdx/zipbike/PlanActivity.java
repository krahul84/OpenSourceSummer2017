package krahul.edu.pdx.zipbike;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //get plan info from price activity
        final String nameofPlan = getIntent().getStringExtra("plan");
        final String prices = getIntent().getStringExtra("price");
        final String planDesc = getIntent().getStringExtra("desc");
        final String signUp = getIntent().getStringExtra("url");

        //setting up the ride button
        //Clicking on this button redirects to url specified in the string signUp

        Button rideBtn = (Button)findViewById(R.id.signUp_button);
        rideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(signUp);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //Setting up the information in Textviews

        TextView text = (TextView) findViewById(R.id.Plan);
        text.setText(nameofPlan);

        text = (TextView) findViewById(R.id.Price);
        text.setTextColor(Color.RED);
        text.setText("You pay: " + prices + "$");

        text = (TextView) findViewById(R.id.Description);
        text.setText( planDesc);

    }
}
