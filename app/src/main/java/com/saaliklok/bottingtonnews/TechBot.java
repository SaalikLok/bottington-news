package com.saaliklok.bottingtonnews;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class TechBot extends AppCompatActivity{

    private ArrayList<String> newsArray = new ArrayList<String>();
    private String[] urlArray = new String[10];
    private String[] titleArray = new String[10];
    private ListView list = null;
    private ArrayAdapter<String> adapter = null;
    private String newsHeading;
    private String headlineUrl;
    private String headlineTitle;
    private Message titleMessage;
    private Message urlMessage;
    private Message posMessage;
    private int pos;
    private ActionBar bar;
    private TextView titleView;

  android.os.Handler handler = new android.os.Handler() {

    public void handleMessage(Message msg) {

            for(int i =0; i< 10; i++){
                adapter.add(titleArray[i]);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_bot);

        Thread t = new Thread(background);
        t.start();

        //Set the actionbar color to Tech Green.
        bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.rgb(10, 158, 1)));
        bar.setDisplayShowTitleEnabled(false);

        //Set up custom font and apply it to page title.
        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "Titill-Regular.ttf"));
        titleView = (TextView) findViewById(R.id.PageTitle);
        titleView.setTypeface(typeface);

        //Find the listview. Set an onClickListener for each item in the list
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String title = newsArray.get(position);
                headlineUrl = urlArray[position];

                for(int i = 0; i<parent.getChildCount(); i++){
                    if(i == position){
                        parent.getChildAt(i).setBackgroundColor(Color.DKGRAY);
                    }
                    else{
                        parent.getChildAt(i).setBackgroundColor(Color.rgb(10, 158, 1));
                    }
                }

            }
        });

        //Set up the ArrayAdapter
        adapter = new ArrayAdapter<String>(this, R.layout.newsitem, newsArray);
        list.setAdapter(adapter);



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
            case R.id.read:
                Intent intent = new Intent(getApplicationContext(), webview.class);
                intent.putExtra("headlineURL", headlineUrl);
                startActivity(intent);
                return true;

            case R.id.email:
                return true;

            case R.id.exitItem:
                //Close the app.
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    Runnable background = new Runnable() {
        @Override
        public void run() {
            StringBuilder builder = new StringBuilder();

            String Url = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=18484304b64a4221b3b15e84526f991b";

            InputStream is = null;

            try {
                URL url = new URL(Url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.e("JSON", "The response is: " + response);
                if (response != 200) return;
                is = conn.getInputStream();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }	catch(IOException e) {}
            finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch(IOException e) {}
                }
            }

            //convert StringBuilder to String
            String readJSONFeed = builder.toString();
            Log.e("JSON", readJSONFeed);

            try {
                JSONObject obj = new JSONObject(readJSONFeed);
                String source = obj.getString("source");
                Log.i("JSON", "source" + source);
                JSONArray articleArray = new JSONArray();
                articleArray = obj.getJSONArray("articles");
                Log.i("JSON", "Number" + articleArray.length());

                for (int i = 0; i < articleArray.length(); i++){
                    JSONObject articleObject = articleArray.getJSONObject(i);
                    String author = articleObject.getString("author");
                    Log.i("JSON", "author" + author);
                    String title = articleObject.getString("title");
                    Log.i("JSON", "title" + title);
                    String url = articleObject.getString("url");
                    Log.i("JSON", "url" + url);

                    titleArray[i] = title;
                    urlArray[i] = url;
                }
                Message titleMessage = handler.obtainMessage();
                handler.sendMessage(titleMessage);
            }
            catch (JSONException e) {e.getMessage();
                e.printStackTrace();
            }
        }
    };
}
