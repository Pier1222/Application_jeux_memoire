import java.util.List;

public class Ligne {
    private static int NB_BOULES = 10;
    private Boule[] boules;

    public Ligne(boolean isColored) {
        boules = new Boule[NB_BOULES];

        if(isColored)
            initBoulesToColor();
        else
            initBoulesToVide();
    }

    public void initBoulesToColor() {
        Couleur[] couleursDispo = {Couleur.BLEU, Couleur.JAUNE, Couleur.ROUGE, Couleur.VERT, Couleur.VIOLET};
        int numeroCouleur = 0;
        for(int i = 0; i < NB_BOULES; i++) {
            numeroCouleur  = (int) Math.round(Math.random() * ((couleursDispo.length - 1) - 0) + 0);
            boules[i] = new Boule(couleursDispo[numeroCouleur]); //Affecte à la boule une couleur choisie aléatoirement
        }
    }

    public void initBoulesToVide() {
        for(int i = 0; i < NB_BOULES; i++) {
            boules[i] = new Boule(Couleur.VIDE);
        }
    }

    public List<Integer> compareLignes (Ligne l) { //Renvoie la liste des indices de boules qui ne sont pas identiques
        if (boules.length != l.boules.length) {
            return null;
        }
        return null;
    }

    public static int getNbBoules() {
        return NB_BOULES;
    }

    public static void setNbBoules(int nbBoules) {
        NB_BOULES = nbBoules;
    }

    public Boule[] getBoules() {
        return boules;
    }

    public void setBoules(Boule[] boules) {
        this.boules = boules;
    }

    public void print() {
        System.out.print(" | ");
        for(int i  = 0; i < NB_BOULES; i++) {
            System.out.print( boules[i].getCouleur() + " | ");
        }
        System.out.println("");
    }
}
