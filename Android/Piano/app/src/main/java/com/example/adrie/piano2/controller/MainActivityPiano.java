package com.example.adrie.piano2.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adrie.piano2.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_piano);

        /**
         * Attribue toutes les touches du piano
         */
        c1 = (Button) findViewById(R.id.button1);

        /**
         * listeners
         */
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w){
                c1.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });
    }
}
