package com.example.girardot.boulier;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

//Fait office de vue et de contrôleur en même temps
public class Vue extends AppCompatActivity implements View.OnClickListener {

    public Model m;
    public Button reponse;

    public ImageView[] imagesHaut;
    public ImageView[] imagesBas;

    public Handler handlerDebut;

    public ControlTimer ct;

    

    //OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new Model();
        setContentView(R.layout.activity_boulier);
        reponse = findViewById(R.id.reponse);

        imagesHaut = new ImageView[Ligne.getNbBoules()];
        initImageHaut();

        imagesBas = new ImageView[Ligne.getNbBoules()];
        initImageBas();

        reponse.setOnClickListener(this);

        ct = new ControlTimer(m, this);

        handlerDebut = new Handler();
        ct.start(handlerDebut, 1000);

    }

    public void initImageBas() {
        LinearLayout lB = (LinearLayout) findViewById(R.id.bas);
        for(int i = 0; i < imagesBas.length; i++) {
            imagesBas[i] = new ImageView(getApplicationContext());
            initImage(imagesBas[i], lB);
        }
        //Un Listener uniquement aux boutons du bas
        for(int i = 0; i < imagesBas.length; i++) {
            imagesBas[i].setOnClickListener(this);
        }
        colorBas();
    }

    public void initImageHaut() {
        LinearLayout lH = (LinearLayout) findViewById(R.id.haut);
        for(int i = 0; i < imagesHaut.length; i++) {
            imagesHaut[i] = new ImageView(getApplicationContext());
            initImage(imagesHaut[i], lH);
        }
        colorHaut();
    }

    public void initImage(ImageView image, LinearLayout layout) {
        image.setImageResource(R.drawable.vide);
        image.setPadding(0, 0, -60, 0);
        layout.addView(image);
    }

    /*public void setClickListener(OnClickListener listener) {
        clickListener = listener;
    }*/

    public void colorHaut() {
        colorLigne(imagesHaut, m.getHaut());
    }

    public void deColorHaut() { //Rend la ligne du haut "invisible" (sans pour autant changer le Model)
        for(int i = 0; i < imagesHaut.length; i++) {
            if(m.getBas().getBoules()[i].isActive()) { //Si la boule du bas est correct, la version du haut ne se décolorisera pas
                imagesHaut[i].setImageResource(R.drawable.vide);
            }
        }
    }

    public void colorBas() {
        colorLigne(imagesBas, m.getBas());
    }

    public void colorLigne(ImageView[] ligneImages, Ligne ligne) {
        for(int i = 0; i < ligneImages.length; i++) {

            Boule bouleActuelle = ligne.getBoules()[i];

            if(bouleActuelle.getCouleur() == Couleur.BLEU)
                ligneImages[i].setImageResource(R.drawable.bleu);
            else if(bouleActuelle.getCouleur() == Couleur.JAUNE)
                ligneImages[i].setImageResource(R.drawable.jaune);
            else if(bouleActuelle.getCouleur() == Couleur.ROUGE)
                ligneImages[i].setImageResource(R.drawable.rouge);
            else if(bouleActuelle.getCouleur() == Couleur.VERT)
                ligneImages[i].setImageResource(R.drawable.vert);
            else if(bouleActuelle.getCouleur() == Couleur.VIOLET)
                ligneImages[i].setImageResource(R.drawable.violet);
            else if(bouleActuelle.getCouleur() == Couleur.VIDE)
                ligneImages[i].setImageResource(R.drawable.vide);
            else
                System.out.println("Couleur invalide (ajouter une nouvelle image pour montrer ça)");
                //ligneBoutons[i].setBackground(couleurInvalide);
        }
    }


    //Partie contrôleur
    @Override
    public void onClick(View view) {
        if(m.isInAction()) {
            System.out.println("Interdiction d'appuyer sur l'écran");
            return;
        }

        if(view == reponse)
            m.printLignes();
        else {
            for(int i = 0; i < imagesBas.length; i++) {
                if(view == imagesBas[i]) {
                    m.getBas().getBoules()[i].changeCouleur();
                    colorBas();
                }
            }
        }
    }

}
