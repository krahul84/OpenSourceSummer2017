package krahul.edu.pdx.zipbike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PriceActivity extends AppCompatActivity {

    //list view for plans
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        //get plan data from menu activity
        final String[] nameofPlan = getIntent().getStringArrayExtra("plan");
        final String[] prices = getIntent().getStringArrayExtra("prices");
        final String[] planDesc = getIntent().getStringArrayExtra("desc");
        final String[] signUp = getIntent().getStringArrayExtra("url");

        lv = (ListView) findViewById(R.id.ListView1);

        //set listener for the list view item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Take the value of the item from the list
                String item = (String) lv.getAdapter().getItem(position);

                //based on the item click corresponding data is sent to the Plan Activity

                switch(item) {
                    case "Single Ride":

                        Intent intent1 = new Intent(PriceActivity.this, PlanActivity.class);
                        intent1.putExtra("plan",nameofPlan[0]);
                        intent1.putExtra("price",prices[0]);
                        intent1.putExtra("desc",planDesc[0]);
                        intent1.putExtra("url",signUp[0]);
                        startActivity(intent1);
                        break;

                    case "Day Pass":
                        Intent intent2 = new Intent(PriceActivity.this, PlanActivity.class);
                        intent2.putExtra("plan",nameofPlan[1]);
                        intent2.putExtra("price",prices[1]);
                        intent2.putExtra("desc",planDesc[1]);
                        intent2.putExtra("url",signUp[1]);
                        startActivity(intent2);
                        break;

                    case "Annual Membership":
                        Intent intent3 = new Intent(PriceActivity.this, PlanActivity.class);
                        intent3.putExtra("plan",nameofPlan[2]);
                        intent3.putExtra("price",prices[2]);
                        intent3.putExtra("desc",planDesc[2]);
                        intent3.putExtra("url",signUp[2]);
                        startActivity(intent3);
                        break;
                }
            }
        });

    }

}
