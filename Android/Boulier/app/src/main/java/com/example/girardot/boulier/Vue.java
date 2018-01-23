package com.example.girardot.boulier;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

//Fait office de vue et de contrôleur en même temps
public class Vue extends AppCompatActivity implements View.OnClickListener {

    Model m;
    Button reponse;
    ImageView[] imagesHaut;

    ImageView[] imagesBas; //A faire

    //OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new Model();
        setContentView(R.layout.activity_boulier);
        reponse = findViewById(R.id.reponse);

        imagesHaut = new ImageView[10]; //Faut voir pour qu'il s'adapte au Model
        initImageHaut();

        imagesBas = new ImageView[10];
        //Initialiser imageBas


        reponse.setOnClickListener(this);
    }

    public void initImageHaut() {
        imagesHaut[0] = findViewById(R.id.haut1);
        imagesHaut[1] = findViewById(R.id.haut2);
        imagesHaut[2] = findViewById(R.id.haut3);
        imagesHaut[3] = findViewById(R.id.haut4);
        imagesHaut[4] = findViewById(R.id.haut5);
        imagesHaut[5] = findViewById(R.id.haut6);
        imagesHaut[6] = findViewById(R.id.haut7);
        imagesHaut[7] = findViewById(R.id.haut8);
        imagesHaut[8] = findViewById(R.id.haut9);
        imagesHaut[9] = findViewById(R.id.haut10);

        //A retirer quand la ligne du bas sera faite
        for(int i = 0; i < imagesHaut.length; i++) {
            imagesHaut[i].setOnClickListener(this);
        }
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
        if(view == reponse)
            System.out.println("Réponse");
        else {
            //En ce qui concerne la suite, il faudrat tous simplement changer tous les appels à imagesHaut/getHaut etc... PAR LEUR VERSION BAS
            for(int i = 0; i < imagesHaut.length; i++) {
                if(view == imagesHaut[i]) {
                    m.getHaut().getBoules()[i].changeCouleur();
                    colorHaut();
                }
            }
        }
    }

}
