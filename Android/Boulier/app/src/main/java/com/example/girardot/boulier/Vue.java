package com.example.girardot.boulier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

//Fait office de vue et de contrôleur en même temps
public class Vue extends AppCompatActivity implements View.OnClickListener {

    protected Model m;
    protected Button reponse;

    protected ImageView[] imagesHaut;
    protected ImageView[] imagesBas;

    protected TextView showScore;
    protected TextView regardezSequence;
    protected TextView calculScore;
    protected TextView tentativesRestantes;

    protected Handler handlerDebut;
    protected Handler handlerFinEssaie;

    protected ControlTimer ct;


    //OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new Model();
        setContentView(R.layout.activity_boulier);

        reponse = findViewById(R.id.reponse);
        reponse.setOnClickListener(this);

        showScore = findViewById(R.id.score);
        changeScore();

        regardezSequence = findViewById(R.id.sequence);
        changeRegardezSequence();

        calculScore = findViewById(R.id.calculScore);

        tentativesRestantes = findViewById(R.id.tentativesRestantes);
        changeTentativesRestantes();

        imagesHaut = new ImageView[Ligne.getNbBoules()];
        initImageHaut();

        imagesBas = new ImageView[Ligne.getNbBoules()];
        initImageBas();

        ct = new ControlTimer(m, this);

        handlerDebut = new Handler();
        handlerFinEssaie = new Handler();
        ct.start(handlerDebut, 1000);

        visibiliteDebutDePartie();

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
        image.setPadding(-((Ligne.getNbBoules()*3)-5), 0, -(Ligne.getNbBoules()*4), 0); //Les boules sont plus ou moins espacé en fonction du nombre qu'il y a (le maximum est de pouvoir en avoir 15 au maximum)
        layout.addView(image);
    }

    public void visibiliteDebutDePartie() {
        reponse.setVisibility(View.GONE);
        calculScore.setVisibility(View.GONE);
        regardezSequence.setVisibility(View.VISIBLE);
        tentativesRestantes.setVisibility(View.VISIBLE);
    }

    public void visibiliteTentative() {
        reponse.setVisibility(View.VISIBLE);
        calculScore.setVisibility(View.GONE);
        regardezSequence.setVisibility(View.GONE);
        tentativesRestantes.setVisibility(View.GONE);
    }

    public void visibiliteVerification() {
        reponse.setVisibility(View.GONE);
        calculScore.setVisibility(View.VISIBLE);
        regardezSequence.setVisibility(View.GONE);
        tentativesRestantes.setVisibility(View.VISIBLE);
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

    public void changeRegardezSequence() {
        regardezSequence.setText(getString(R.string.sequence) + "(" + m.getNbSecondesVerif() + ")");
    }

    public int changeCalculScore(int juste, int erreur) { //Calcul et renvoie le score à ajouter
        int scoreEnPlus = (juste*2) - erreur;
        calculScore.setText(juste + "X2 - " + erreur + " = " + scoreEnPlus);
        return scoreEnPlus;
    }

    public void changeScore() {
        showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
    }

    public void changeTentativesRestantes() {
        if(m.getNbTentatives() == Model.getNbTentativesMax()) //Si on est au début de la partie
            tentativesRestantes.setText(getString(R.string.debTentative) + " " + m.getNbTentatives() + " " + getString(R.string.endTentative));
        else {
            if (m.getNbTentatives() > 1) //Si on est pas au début de la partie mais qu'il reste plus d'une tentative
                tentativesRestantes.setText(getString(R.string.notLastTentative) + " " + m.getNbTentatives() + ".");
            else //Si il ne reste plus qu'une seule tentative
                tentativesRestantes.setText(getString(R.string.lastTentative));
        }
    }

    public void verification() {
        List<Integer> indicesFaux = m.getErreurs();
        int nbBoulesJustes = 0;
        int nbBoulesFausses = 0;
        int scoreEnPlus = 0;

        m.setInAction(true); //Empêche l'utilisateur de faire quoi que ce soit pendant qu'on lui remontre la séquence et si la partie est finie

        for(int i = 0; i < Ligne.getNbBoules(); i++) {
            Boule bouleActuelle = m.getBas().getBoules()[i];
            if(indicesFaux.contains(i)) {
                //resultats[i].setText("X ");
                //resultats[i].setForeground(Color.RED);
                nbBoulesFausses++;
            } else if(bouleActuelle.isActive()) {
                //resultats[i].setText("V ");
                //resultats[i].setForeground(Color.GREEN);
                bouleActuelle.setActive(false); //Etant donné qu'elle est correct, on n'en tiendra plus compte désormais
                nbBoulesJustes++;
            }
        }

        scoreEnPlus = changeCalculScore(nbBoulesJustes, nbBoulesFausses);
        m.augmenteScore(scoreEnPlus);
        visibiliteVerification();
        changeScore();
        colorHaut(); //Pendant la vérification, on montre de nouveau la ligne du haut

        if(indicesFaux.isEmpty()) {
            System.out.println("Séquence complète !");
            creerDialogSequenceComplete();
            //reset();
        } else {
            m.perdTentative();

            System.out.print("Indice(s) ou les boules ne correspondent pas : ");
            for (Integer indice : indicesFaux) {
                System.out.print(indice + ", ");
            }
            System.out.println();
            m.printLignes();
            if(m.getNbTentatives() > 0) {
                changeTentativesRestantes();
                tentativesRestantes.setVisibility(View.VISIBLE);
                ct.start(handlerFinEssaie, 10000); //Ce Timer ne s'enclenche que si la partie n'est pas finie
            } else {//La partie est perdue
                creerDialogPlusDeTentatives();
                //reset();
            }
        }
    }

    public void creerDialogLigneNonComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.ligneNonCompleteTexte));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.LigneNonCompleteTitre));
        builder.setPositiveButton(getString(R.string.LigneNonCompleteOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("ok");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void creerDialogSequenceComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.sequenceCompleteTexte1) +
                "\n" + getString(R.string.scoreFinal) + ": " + m.getScore() + "." +
                "\n" + getString(R.string.sequenceCompleteTexte2) + ": " + (Model.getNbTentativesMax() - m.getNbTentatives() + 1 + "."));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.sequenceCompleteTitre));
        builder.setPositiveButton(getString(R.string.sequenceCompleteOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("ok");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void creerDialogPlusDeTentatives() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.tentativeEpuiseesTexte) +
                "\n" + getString(R.string.scoreFinal) + ": " + m.getScore() + ".");
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.tentativeEpuiseesTitre));
        builder.setPositiveButton(getString(R.string.tentativeEpuisseOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("ok");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Partie contrôleur
    @Override
    public void onClick(View view) {
        if(m.isInAction()) {
            System.out.println("Interdiction d'appuyer sur l'écran");
            return;
        }

        if(view == reponse) {
            if (!m.getBas().isLigneColored())
                creerDialogLigneNonComplete();
            else
                verification();
        } else {
            for(int i = 0; i < imagesBas.length; i++) {
                if(view == imagesBas[i]) {
                    m.getBas().getBoules()[i].changeCouleur();
                    colorBas();
                }
            }
        }
    }
}
