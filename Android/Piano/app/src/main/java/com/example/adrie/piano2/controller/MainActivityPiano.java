package com.example.adrie.piano2.controller;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.adrie.piano2.R;

import java.io.IOException;

public class MainActivityPiano extends AppCompatActivity {

    // findViewById(id); sert à référencer les éléments graphiques     DOIT ETRE DANS LE onCreate() A LA SUITE !
    /*

    button.setOnClickListener(new View.OnClickListener() {     listener du button
        @Override
        public void onClick(View w){

        }
    });

    AlertDialog
    DialogInterface

     */

    private Button c1;
    private Button c11;
    private Button d1;
    private Button d11;
    private Button e1;
    private Button f1;
    private Button f11;
    private Button g1;
    private Button g11;
    private Button a1;
    private Button a11;
    private Button b1;
    private Button c2;
    private Button lancer;

    private Button[] piano = {c11, c1, d1, d11, e1, f1, f11, g1, g11, a1, a11, b1, c2};

    int sonId;
    boolean loaded = false;

    private static final String TAG = "MainActivityPiano";

    private SoundPool soundPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_piano);

        /*
         * Attribue toutes les touches du piano
         */
        c1 = (Button) findViewById(R.id.button1);

        /*
         * listeners
         */
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w){
                c1.setBackgroundColor(Color.RED);
                soundPool.play(sonId, 1, 1, 0, 0, 1);
            }
        });

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        AssetManager assetManager = getAssets();

        AssetFileDescriptor descriptor = null;
        //try {
            sonId = soundPool.load(this, R.raw.son, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    Log.d(TAG, "Son prêt !");
                    loaded = true;
                    soundPool.play(sonId, 100, 100, 0, 0, 1);
                }
            });
       /* catch (IOException e) {
            Log.d(TAG, "erreur lors de la création du son: " + e.getMessage());
            e.printStackTrace();
        }*/
    }
}
