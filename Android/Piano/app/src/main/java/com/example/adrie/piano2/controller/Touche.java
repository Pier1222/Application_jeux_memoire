package com.example.adrie.piano2.controller;

public class Touche { //représente une touche de piano
    private String nomSon; //Les objets de types Son sont valable que pour la Vue
    private Couleur couleur; //La vue se référera à cette valeur pour colorer la touche
    private boolean estActif; //Montre si c'est la touche sur laquelle on appuie

    public Touche(String nomSon, Couleur couleur) {
        this.nomSon = nomSon;
        this.couleur = couleur;
        estActif = false;
    }

    public String getNomSon() {
        return nomSon;
    }

    public void setNomSon(String nomSon) {
        this.nomSon = nomSon;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public boolean isEstActif() {
        return estActif;
    }

    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }

}
