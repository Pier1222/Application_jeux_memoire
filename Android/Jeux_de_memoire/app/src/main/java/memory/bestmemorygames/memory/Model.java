package memory.bestmemorygames.memory;

import android.util.Log;

public class Model {

    private static int nbCartesLigne = 3;
    private static int nbCartesColonne = 4;

    private static int NB_VIES_MAX = 7;
    private int nbVies;

    private static int[] paires = new int[]{1, 2, 2, 0, 1, 0};
    private static final int NB_IMAGES = 6; //Permet de savoir dans quel case du tableau "images" de la Vue on devra regarder

    private int[] cartesActu; //Contient les x et y de la première (position 1 et 2) et de la deuxième (position 3 et 4) carte
    private boolean[][] isDos;
    private int[][] cartes;
    private int score;
    private int nbClignotements;

    private boolean inAction;

    private static final String TAG = "Model";

    public Model() {
        nbVies = NB_VIES_MAX;
        cartes = new int[nbCartesLigne][nbCartesColonne];
        affecterPaires();
        cartesActu = new int[4];
        resetCartesActu();
        isDos = new boolean[nbCartesLigne][nbCartesColonne];
        initIsDos();
        score = 0;
        resetClignotement();
        inAction = false;
    }

    public void resetCartesActu() {
        for(int i = 0; i < cartesActu.length; i++) {
            cartesActu[i] = -1;
        }
    }

    public void initIsDos() {
        for(int x = 0; x < nbCartesLigne; x++) {
            for(int y = 0; y < nbCartesColonne; y++) {
                isDos[x][y] = true;
            }
        }
    }

    public boolean ifAllCartesRevelees() {
        for(int x = 0; x < nbCartesLigne; x++) {
            for(int y = 0; y < nbCartesColonne; y++) {
                if(isDos[x][y])
                    return false;
            }
        }
        return true;
    }

    private void affecterPaires() {
        int[] nbCartes = new int[NB_IMAGES];
        int placeActu = (int) Math.round(Math.random() * ((nbCartes.length - 1) - 0) + 0);

        //Si on le fait en ayant des nombres de paires précis
        for(int i = 0; i < nbCartes.length; i++) {
            nbCartes[i] = -1; //On considère qu'ils n'ont pas de nombre de paires pour le moment
        }

        for(int i = 0; i < paires.length; i++) {
            while(nbCartes[placeActu] >= 0) {
                placeActu = (int) Math.round(Math.random() * ((nbCartes.length - 1) - 0) + 0);
            }
            nbCartes[placeActu] = paires[i]*2;
        }

        for(int i = 0; i < nbCartes.length; i++) {
            Log.d(TAG, "Cartes pour l'image: " + nbCartes[i]);
        }
        affecterCartes(nbCartes);
    }

    private void affecterCartes(int[] nbCartes) {
        int placeActu = 0;
        for(int x = 0; x < Model.getNbCartesLigne(); x++) {
            for(int y = 0; y < Model.getNbCartesColonne(); y++) {
                do {
                    placeActu = (int) Math.round(Math.random() * ((nbCartes.length - 1) - 0) + 0);
                } while(nbCartes[placeActu] <= 0); //Tant qu'il reste des cartes à affecter à la position choisi
                cartes[x][y] = placeActu;
                nbCartes[placeActu]--;
            }
        }
    }

    public boolean isPaire() {
        int x1 = cartesActu[0];
        int y1 = cartesActu[1];
        int x2 = cartesActu[2];
        int y2 = cartesActu[3];

        return(cartes[x1][y1] == cartes[x2][y2]);
    }

    public void retourneToDos() {
        int x1 = cartesActu[0];
        int y1 = cartesActu[1];
        int x2 = cartesActu[2];
        int y2 = cartesActu[3];

        isDos[x1][y1] = true;
        isDos[x2][y2] = true;
    }

    public static void classiqueMemory() {
        nbCartesLigne = 3;
        nbCartesColonne = 4;
        paires = new int[]{1, 1, 1, 1, 1, 1};
    }

    public static void petitMemory() {
        nbCartesLigne = 3;
        nbCartesColonne = 4;
        paires = new int[]{1, 2, 2, 0, 1, 0};
    }

    public static void moyenMemory() {
        nbCartesLigne = 4;
        nbCartesColonne = 4;
        paires = new int[]{1, 2, 2, 1, 1, 1};
    }

    public static void grandMemory() {
        nbCartesLigne = 4;
        nbCartesColonne = 6;
        paires = new int[]{2, 3, 2, 0, 2, 3};
    }

    public static int getNbCartesLigne() {
        return nbCartesLigne;
    }

    public static void setNbCartesLigne(int nbCartesLigne) {
        Model.nbCartesLigne = nbCartesLigne;
    }

    public static int getNbCartesColonne() {
        return nbCartesColonne;
    }

    public static void setNbCartesColonne(int nbCartesColonne) {
        Model.nbCartesColonne = nbCartesColonne;
    }

    public static int getNbImages() {
        return NB_IMAGES;
    }

    public static int[] getPaires() {
        return paires;
    }

    public boolean addCartesActu(int x, int y) { //Renvoie vrai si on en est à la deuxième carte
        System.out.println("X: " + x + " / Y:" + y);
        isDos[x][y] = false; //Dans tous les cas, la carte est considérer comme retourné
        if(cartesActu[0] < 0) {
            cartesActu[0] = x;
            cartesActu[1] = y;
            return false;
        } else {
            cartesActu[2] = x;
            cartesActu[3] = y;
            return true;
        }
    }

    public void perdVie() {
        nbVies--;
    }

    public int getNbVies() {
        return nbVies;
    }

    public static int getNbViesMax() {
        return NB_VIES_MAX;
    }

    public void augmenteScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void augmenteClignotements() {
        nbClignotements++;
    }

    public void resetClignotement() {
        nbClignotements = 0;
    }

    public int getNbClignotements() {
        return nbClignotements;
    }

    public int[] getCartesActu() {
        return cartesActu;
    }

    public int[][] getCartes() {
        return cartes;
    }

    public boolean[][] getIsDos() {
        return isDos;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }
}