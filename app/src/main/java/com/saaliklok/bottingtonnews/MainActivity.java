package com.saaliklok.bottingtonnews;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private ImageView game;
    private ImageView tech;
    private ImageView map;
    private Button gbutt;
    private Button tbutt;
    private Button sbutt;
    private android.support.v7.app.ActionBar bar;
    private TextToSpeech speaker;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        speaker = new TextToSpeech(this, this);

        bar = getSupportActionBar();
        bar.hide();

        game = (ImageView) findViewById(R.id.gameBot);
        game.setBackgroundResource(R.drawable.gamer_animation);

        tech = (ImageView) findViewById(R.id.techBot);
        tech.setBackgroundResource(R.drawable.tech_animation);

        game = (ImageView)  findViewById(R.id.gameBot);
        game.setOnClickListener(this);

        map = (ImageView) findViewById(R.id.mapImage);
        map.setOnClickListener(this);

        tech = (ImageView)  findViewById(R.id.techBot);
        tech.setOnClickListener(this);

        gbutt = (Button)  findViewById(R.id.gameButton);
        gbutt.setOnClickListener(this);

        tbutt = (Button)  findViewById(R.id.techButton);
        tbutt.setOnClickListener(this);

        sbutt = (Button)  findViewById(R.id.sources);
        sbutt.setOnClickListener(this);

        AnimationRoutine1 task1 = new AnimationRoutine1();
        AnimationRoutine2 task2 = new AnimationRoutine2();

        TechRoutine ttask = new TechRoutine();
        TechRoutine2 ttask2 = new TechRoutine2();

        Timer tt = new Timer();
        tt.schedule(ttask, 9000);
        Timer tt2 = new Timer();
        tt2.schedule(ttask2, 1000);

        Timer t = new Timer();
        t.schedule(task1, 9000);
        Timer t2 = new Timer();
        t2.schedule(task2, 1000);
    }

    public void speak(String output){
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH,null,  "Id 0");

    }

    public void onInit(int status){

        if (status == TextToSpeech.SUCCESS){

            int result = speaker.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){

            }
            else{}
        }
    }

    public void onDestroy(){
        if(speaker != null){
            speaker.stop();
            speaker.shutdown();
        }
        super.onDestroy();
    }

    public void onClick(View v){

        //creating a clickable function for the game bot
        if (v.getId() == R.id.gameBot){
            Intent pageInt = new Intent(MainActivity.this, com.saaliklok.bottingtonnews.GamingBot.class);
            startActivityForResult(pageInt,101);
        }
        if (v.getId() == R.id.gameButton){
            Intent pageInt = new Intent(MainActivity.this, com.saaliklok.bottingtonnews.GamingBot.class);
            startActivityForResult(pageInt,101);
        }
        if (v.getId() == R.id.techBot){
            Intent pageInt = new Intent(MainActivity.this, com.saaliklok.bottingtonnews.TechBot.class);
            startActivityForResult(pageInt,101);
        }
        if (v.getId() == R.id.techButton){
            Intent pageInt = new Intent(MainActivity.this, com.saaliklok.bottingtonnews.TechBot.class);
            startActivityForResult(pageInt,101);
        }
        if (v.getId() == R.id.mapImage) {
            Intent pageInt = new Intent(MainActivity.this, com.saaliklok.bottingtonnews.MapsActivity.class);
            startActivityForResult(pageInt, 101);
            speak("Opening Map");
        }
        if (v.getId() == R.id.sources) {
            Intent pageInt = new Intent(MainActivity.this, com.saaliklok.bottingtonnews.MapsActivity.class);
            startActivityForResult(pageInt, 101);
            speak("Opening Map");
        }
    }

    class AnimationRoutine1 extends TimerTask {

        @Override
        public void run() {
            ImageView game = (ImageView) findViewById(R.id.gameBot);
            AnimationDrawable frameGame = (AnimationDrawable) game.getBackground();
            frameGame.start();
        }
    }

    class AnimationRoutine2 extends TimerTask {

        @Override
        public void run() {
            ImageView game = (ImageView) findViewById(R.id.gameBot);
            AnimationDrawable frameGame = (AnimationDrawable) game.getBackground();
            frameGame.stop();
        }
    }

    class TechRoutine extends TimerTask {

        @Override
        public void run() {
            ImageView tech = (ImageView) findViewById(R.id.techBot);
            AnimationDrawable frameTech = (AnimationDrawable) tech.getBackground();
            frameTech.start();
        }
    }

    class TechRoutine2 extends TimerTask {

        public void run(){
            ImageView tech = (ImageView) findViewById(R.id.techBot);
            AnimationDrawable frameTech = (AnimationDrawable) tech.getBackground();
            frameTech.stop();
        }
    }
}
