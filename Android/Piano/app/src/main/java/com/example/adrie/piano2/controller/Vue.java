package com.example.adrie.piano2.controller;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.adrie.piano2.R;

public class Vue extends AppCompatActivity implements View.OnClickListener {

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

    protected Button lancer;

    protected Button[] piano;
    protected Son[] sonsPiano;

    protected Handler handlerClignotement;
    protected ControlTimer ct;

    protected Model m;

    private static final String TAG = "Vue";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        m = new Model();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_piano);

        initButtons();
        initSons();

        handlerClignotement = new Handler();
        ct = new ControlTimer(m, this);
    }

    public void initButtons() {
        piano = new Button[m.getNB_TOUCHES()];

        piano[0] = (Button) findViewById(R.id.c1);
        piano[1] = (Button) findViewById(R.id.c11);
        piano[2] = (Button) findViewById(R.id.d1);
        piano[3] = (Button) findViewById(R.id.d11);
        piano[4] = (Button) findViewById(R.id.e1);
        piano[5] = (Button) findViewById(R.id.f1);
        piano[6] = (Button) findViewById(R.id.f11);
        piano[7] = (Button) findViewById(R.id.g1);
        piano[8] = (Button) findViewById(R.id.g11);
        piano[9] = (Button) findViewById(R.id.a1);
        piano[10] = (Button) findViewById(R.id.a11);
        piano[11] = (Button) findViewById(R.id.b1);
        piano[12] = (Button) findViewById(R.id.c2);

        lancer = (Button) findViewById(R.id.lancer);

        for(int i = 0; i < piano.length; i++) {
            piano[i].setOnClickListener(this);
        }

        lancer.setOnClickListener(this);
    }

    public void initSons() {
        sonsPiano = new Son[m.getNB_TOUCHES()];

        for(int i = 0; i < sonsPiano.length; i++) {
            sonsPiano[i] = new Son(m.getTouches()[i].getIdSon(), this);
        }
    }

    /*public void etatEnCours() {
        etatDeLaPartie.setText("Ecoutez la séquence...");
    }*/

    /*public void etatAVous() {
        etatDeLaPartie.setText("A vous !");
    }*/

    /*public void debutPartie() {
        lance.setVisible(false);
        etatEnCours();
        etatDeLaPartie.setVisible(true);
        changeScore();
        showScore.setVisible(true);
    }*/

    /*public void finPartie() {
        lance.setVisible(true);
        etatDeLaPartie.setVisible(false);
        showScore.setVisible(false);
    }*/


    //Partie contrôleur
    @Override
    public void onClick(View view) {
        if(m.isInAction())
            Log.d(TAG, "Ce n'est pas le moment pour ça");
        if(view == lancer) {
            Log.d(TAG, "lancer");
        } else {
            for(int i = 0; i < piano.length; i++) {
                if(view == piano[i]) {
                    lanceAnimationBouton(i);
                }
            }
        }
    }
    public void lanceAnimationBouton(int i) {
        piano[i].setBackgroundColor(Color.RED);
        sonsPiano[i].jouer();
        m.getTouches()[i].setEstActif(true);
        m.setInAction(true);
        ct.start(handlerClignotement, 500);
        //timerClignotementTouche.start();
    }
}
