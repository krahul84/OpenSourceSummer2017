package edu.pdx.final_project.zipbike;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.support.v4.view.MenuItemCompat;

import java.util.ArrayList;
import java.util.Arrays;

/*

   Copyright © 2017 Rahul Kumar, Abhishek Tatke

    This is the activity for the hub list screen

        In this activity the Json data is received from menu activity.
        A listview is implemented to display address of all bike hubs
        Users can search for a hub by entering a string.
        Clicking on a Listview item opens the free bike activity which displays info about the station

        MenuActivity -> HubId Activity ->FreeBikeActivity

        Created By Rahul Kumar ,Abhishek Tatke


  */

public class HubidActivity extends AppCompatActivity {

   //Listview and adapter
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    //string to take search string from user
    String mSearchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubid);

        //get data from previous activity
        final int[] mybikeArray = getIntent().getIntArrayExtra("bikes");
        final String[] hubID = getIntent().getStringArrayExtra("hub");
        final String [] stnAdd = getIntent().getStringArrayExtra("add");

        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.mainListView );

        //add array;ist to list
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll( Arrays.asList(stnAdd) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);


        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter( listAdapter );

        //Listener for the list item
        //clicking on an item opens free bike activity which gives station info
        //Also passing station info to the next activity

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String item = (String) mainListView.getAdapter().getItem(position);

                Intent it = new Intent(HubidActivity.this, FreebikeActivity.class);

                it.putExtra("bikes", mybikeArray[position]);
                it.putExtra("add",item);
                startActivity(it);


            }
        });
    }

    //This for the search widget
    //implements two methods inside the querytext listener- onQueryTextSubmit() and onQueryChange()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        if (mSearchView != null )
        {
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            mSearchView.setIconifiedByDefault(false);

            //This is the listener for the search bar widget
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener()
            {
               //This is the method when user has entered something in the search widget
                public boolean onQueryTextChange(String newText)
                {
                    mSearchString = newText;
                    return true;
                }

                //This method is activated when user hits the submit button in the search widget
                public boolean onQueryTextSubmit(String query)
                {
                    mSearchString = query;
                    listAdapter.getFilter().filter(query);

                    return true;
                }
            };

            mSearchView.setOnQueryTextListener(queryTextListener);
        }

        return true;

    }

}
