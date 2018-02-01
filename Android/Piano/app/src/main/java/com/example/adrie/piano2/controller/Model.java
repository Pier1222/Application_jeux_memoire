package com.example.adrie.piano2.controller;

import java.util.Random;

public class Model {
    //Rappel: dans le Model: TOUT en private
    private final int TAILLE_COTE = 5;


    private int score;
    private int nbTouchesReussies; //Indique le nombre de touches réussites à la séquence raté
    private Touche[][] touches;

    private Touche[] sequenceOrdi;

    private boolean inAction; //Indique si l'application est en train de faire quelque chose que l'utilisateur ne doit pas interrompre
    private boolean tourJoueur; //Indique si le joueur doit essayer de reproduire la séquence ou pas

    private int tailleSequence;
    private int placeSequence; //Ce sera l'endroit où on est dans la séquence (que cela soit pour l'ordi qui joue une séquence ou pour l'utilisateur qui doit la reproduire

    public Model() {
        score = 0;
        nbTouchesReussies = 0;
        tailleSequence = 1;
        placeSequence = 0;
        sequenceOrdi = new Touche[tailleSequence];
        sequenceOrdi[0] = null;
        inAction = false;
        tourJoueur = false;
        initTouches();

    }

    public void initTouches() {
        String[][] tabSons = new String[TAILLE_COTE][TAILLE_COTE];
        touches = new Touche[TAILLE_COTE][TAILLE_COTE];

        tabSons[0][0] = "Son1.wav";
        tabSons[0][1] = "Son2.wav";
        tabSons[0][2] = "Son3.wav";
        tabSons[0][3] = "Son4.wav";
        tabSons[0][4] = "Son5.wav";

        tabSons[1][0] = "Son6.wav";
        tabSons[1][1] = "Son7.wav";
        tabSons[1][2] = "Son8.wav";
        tabSons[1][3] = "Son9.wav";
        tabSons[1][4] = "Son10.wav";

        tabSons[2][0] = "Son11.wav";
        tabSons[2][1] = "Son12.wav";
        tabSons[2][2] = "Son13.wav";
        tabSons[2][3] = "Son14.wav";
        tabSons[2][4] = "Son15.wav";

        tabSons[3][0] = "Son16.wav";
        tabSons[3][1] = "Son17.wav";
        tabSons[3][2] = "Son18.wav";
        tabSons[3][3] = "Son19.wav";
        tabSons[3][4] = "Son20.wav";

        tabSons[4][0] = "Son21.wav";
        tabSons[4][1] = "Son22.wav";
        tabSons[4][2] = "Son23.wav";
        tabSons[4][3] = "Son24.wav";
        tabSons[4][4] = "Son25.wav";

        touches[0][0] = new Touche(tabSons[0][0], Couleur.BLANC);
        touches[0][1] = new Touche(tabSons[0][1], Couleur.NOIR);
        touches[0][2] = new Touche(tabSons[0][2], Couleur.BLANC);
        touches[0][3] = new Touche(tabSons[0][3], Couleur.NOIR);
        touches[0][4] = new Touche(tabSons[0][4], Couleur.BLANC);

        touches[1][0] = new Touche(tabSons[1][0], Couleur.BLANC);
        touches[1][1] = new Touche(tabSons[1][1], Couleur.NOIR);
        touches[1][2] = new Touche(tabSons[1][2], Couleur.BLANC);
        touches[1][3] = new Touche(tabSons[1][3], Couleur.NOIR);
        touches[1][4] = new Touche(tabSons[1][4], Couleur.BLANC);

        touches[2][0] = new Touche(tabSons[2][0], Couleur.NOIR);
        touches[2][1] = new Touche(tabSons[2][1], Couleur.BLANC);
        touches[2][2] = new Touche(tabSons[2][2], Couleur.BLANC);
        touches[2][3] = new Touche(tabSons[2][3], Couleur.NOIR);
        touches[2][4] = new Touche(tabSons[2][4], Couleur.BLANC);

        touches[3][0] = new Touche(tabSons[3][0], Couleur.NOIR);
        touches[3][1] = new Touche(tabSons[3][1], Couleur.BLANC);
        touches[3][2] = new Touche(tabSons[3][2], Couleur.BLANC);
        touches[3][3] = new Touche(tabSons[3][3], Couleur.NOIR);
        touches[3][4] = new Touche(tabSons[3][4], Couleur.BLANC);

        touches[4][0] = new Touche(tabSons[4][0], Couleur.NOIR);
        touches[4][1] = new Touche(tabSons[4][1], Couleur.BLANC);
        touches[4][2] = new Touche(tabSons[4][2], Couleur.NOIR);
        touches[4][3] = new Touche(tabSons[4][3], Couleur.BLANC);
        touches[4][4] = new Touche(tabSons[4][4], Couleur.BLANC);


        Couleur couleurIndice = Couleur.NOIR;
    }

    private Couleur reverseCouleurIndice(Couleur couleurIndice) {
        if (couleurIndice == Couleur.NOIR)
            return Couleur.BLANC;
        return Couleur.NOIR;
    }

    public void agrandiSequence() {
        Touche[] exSequence = sequenceOrdi; //On sauvegarde l'état de la séquence avant
        sequenceOrdi = new Touche[tailleSequence];
        for(int i = 0; i < exSequence.length; i++) {
            sequenceOrdi[i] = exSequence[i]; //copie
        }
        sequenceOrdi[tailleSequence-1] = null; //La dernière touche qui n'est pas encore défini vaut null
    }

    public void succesReproductionSequence() {
        score++;
        nbTouchesReussies = 0;
        augmenteTailleSequence();
        agrandiSequence();
        reinitPlaceSequence();
    }

    public void reinitPiano() {
        reinitSequence();
        reinitPlaceSequence();
        score = 0;
        nbTouchesReussies = 0;
    }

    public boolean verifToucheJoueur(Touche touche) { //Vérifie si la touche correspond à celle attendu actuellement dans la séquence
        return touche == sequenceOrdi[placeSequence];
    }

    public int getScore() {
        return score;
    }

    public int getNbTouchesReussies() {
        return nbTouchesReussies;
    }

    public Touche[] getSequenceOrdi() {
        return sequenceOrdi;
    }

    public void setSequenceOrdi(Touche[] sequenceOrdi) {
        this.sequenceOrdi = sequenceOrdi;
    }

    public void addSequenceOrdi(int place, Touche touche) {
        sequenceOrdi[place] = touche;
    }

    public int getTAILLE_COTE() {
        return TAILLE_COTE;
    }

    public Touche[][] getTouches() {
        return touches;
    }

    public void setTouches(Touche[][] touches) {
        this.touches = touches;
    }

    public int getTailleSequence() {
        return tailleSequence;
    }

    public void augmenteTailleSequence() {
        this.tailleSequence += 1;
    }

    public int getPlaceSequence() {
        return placeSequence;
    }

    public void avanceSequence() {
        placeSequence += 1;
    }

    public void augmenteNbTouchesReussies() {
        nbTouchesReussies ++;
    }

    public void reinitPlaceSequence() {
        placeSequence = 0;
    }

    public void reinitSequence() {
        this.tailleSequence = 1;
        sequenceOrdi = new Touche[tailleSequence];
        sequenceOrdi[0] = null;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public boolean isTourJoueur() {
        return tourJoueur;
    }

    public void setTourJoueur(boolean tourJoueur) {
        this.tourJoueur = tourJoueur;
    }
}
