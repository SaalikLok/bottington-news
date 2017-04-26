package com.saaliklok.bottingtonnews;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TechBot extends AppCompatActivity {

    private ArrayList<String> newsArray = new ArrayList<String>();
    private ListView list = null;
    private ArrayAdapter<String> adapter = null;
    private String newsHeading;
    String headlineUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_bot);

        //Find the listview. Set an onClickListener for each item in the list
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Go to the Webview activity and show the article in full in the webview.

                //headlineUrl = headline.getUrl();

                headlineUrl = "http://www.google.com";
                Intent intent = new Intent(getApplicationContext(), webview.class);
                intent.putExtra("headlineURL", headlineUrl);
                startActivity(intent);
            }
        });

        //Set up the ArrayAdapter
        adapter = new ArrayAdapter<String>(this, R.layout.newsitem, newsArray);
        list.setAdapter(adapter);

        //This is temporary for testing.
        adapter.add("Extra, extra! Read all about it!");

       android.app.ActionBar actionBar = getActionBar();
    }

    //Create the Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //What happens when a Menu item is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.backItem:
                return true;

            case R.id.refreshItem:
                return true;

            case R.id.exitItem:
                //Close the app.
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
