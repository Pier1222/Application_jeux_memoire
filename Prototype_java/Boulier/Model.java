import java.util.List;

public class Model {

    private Ligne haut; //Ligne à recopier
    private Ligne bas; //Ligne à remplir

    private static int NB_TENTATIVES_MAX = 3;
    private int nbTentatives;
    private int nbSecondesVerif;
    private int score;

    private boolean inAction;

    public Model() {
        haut = null;
        bas = null;
        setValeurs();
    }

    public void setValeurs() {
        haut = new Ligne(true);
        bas = new Ligne(false);
        printLignes();
        nbTentatives = NB_TENTATIVES_MAX;
        nbSecondesVerif = 15;
        score = 0;
        inAction = true;
    }

    public void printLignes() {
        System.out.print("Haut: "); haut.print();
        System.out.print("Bas: "); bas.print();
    }

    public void reduitNbSecondesVerif() {
        nbSecondesVerif--;
    }

    public void resetInactivesBas() {
        for(int i = 0; i < Ligne.getNbBoules(); i++) {
            Boule bouleActuelle = bas.getBoules()[i];
            if(bouleActuelle.isActive())
                bouleActuelle.changeCouleurToVide();
        }
    }

    public void perdTentative() {
        nbTentatives--;
    }

    public List<Integer> getErreurs() {
        return haut.compareLignes(bas);
    }

    public Ligne getHaut() {
        return haut;
    }

    public void setHaut(Ligne haut) {
        this.haut = haut;
    }

    public Ligne getBas() {
        return bas;
    }

    public void setBas(Ligne bas) {
        this.bas = bas;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public static int getNbTentativesMax() {
        return NB_TENTATIVES_MAX;
    }

    public static void setNbTentativesMax(int nbTentativesMax) {
        NB_TENTATIVES_MAX = nbTentativesMax;
    }

    public int getNbTentatives() {
        return nbTentatives;
    }

    public void setNbTentatives(int nbTentatives) {
        this.nbTentatives = nbTentatives;
    }

    public int getNbSecondesVerif() {
        return nbSecondesVerif;
    }

    public void setNbSecondesVerif(int nbSecondesVerif) {
        this.nbSecondesVerif = nbSecondesVerif;
    }

    public int getScore() {
        return score;
    }

    public void augmenteScore(int scoreEnPlus) {
        score += scoreEnPlus;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

