import javax.swing.*;
import java.util.ArrayList;

public class Model {

    private static int TAILLE_COTE = 6;

    //La première valeur d'un set se trouvera entre 1 et 5
    private static int MIN_DEBUT = 1;
    private static int MAX_DEBUT = 5;

    //Pour une valeur à la place i d'un set, la valeur à la place i+1 vaut +1 ou +2 ou +3 par rapport à la valeur à la place i
    private static int MIN_INTERVALE = 1;
    private static int MAX_INTERVALE = 3;

    private static int TEMPS_APPARITION_DEBUT = 3000; //Au début d'une partie, les nombres apparaissent pendant 3 secondes

    private int score;
    private ArrayList valeurs;
    private ArrayList valXY;
    private JButton tabBouton[][];
    private boolean tabBoolean[][];
    private boolean inAction;

    private int tempsApparition;

    public Model() {
        score = 0;
        valeurs = new ArrayList<Integer>();
        valXY=new ArrayList<Integer[]>();
        tabBouton=new JButton[TAILLE_COTE][TAILLE_COTE];
        tabBoolean=new boolean[TAILLE_COTE][TAILLE_COTE];
        inAction = false;
        initTempsApparition();
    }


    public int getScore() {
        return score;
    }

    public JButton[][] getTabBouton() {
        return tabBouton;
    }

    public JButton getTabBouton(int x,int y){return tabBouton[x][y];}

    //permet de remettre à zéro le tableau de jbutton
    public void set0tabBouton(){
        for (int i = 0; i < TAILLE_COTE; i++) {
            for (int j = 0; j < TAILLE_COTE; j++) {
                if (tabBouton[i][j] != null){
                    tabBouton[i][j].setText("");
                }
            }
        }
    }

    public void setTabBoolean() {
        for (int i = 0; i < TAILLE_COTE; i++) {
            for (int j = 0; j < TAILLE_COTE; j++) {
                tabBoolean[i][j]=false;
            }
        }
    }

    public void videButtons() {
        for(int x = 0; x < TAILLE_COTE; x++) {
            for(int y = 0; y < TAILLE_COTE; y++) {
                tabBouton[x][y] = null;
            }
        }
    }

    //méthode permettant de comparer si une case à déjà été tiré
    public int[] comparerVal(){
        int[] tabAlea = {genenAleaPourTab(), genenAleaPourTab()};
        while (tabBoolean[tabAlea[0]][tabAlea[1]] == true) {
            tabAlea[0] = genenAleaPourTab();
            tabAlea[1] = genenAleaPourTab();
            System.out.println("valeur en while "+tabAlea[0]+" "+tabAlea[1]);
        }
        System.out.println("valeur en dehors du while "+tabAlea[0]+" "+tabAlea[1]);
        setTabBoolean(tabAlea[0],tabAlea[1]);
        return tabAlea;
    }

    public void setTabBoolean(int x,int y){
        tabBoolean[x][y]=true;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void setTabBouton(JButton button,int x, int y) {
        tabBouton[x][y] = new JButton(button.getText());
    }

    public ArrayList getValeurs() {
        return valeurs;
    }
    public void removeValeur(int valeur){ valeurs.remove(valeur);}

    // fonction qui permet de trouver le nombre de case qu'il faut ajouter au niveau des difficultés
    public int trouverLeNombreDeCase()
    {
        int nbCasesSansLimite = score + 2; //Combien il aurait de cases si on était pas limité en nombre de cases
        int limite = TAILLE_COTE*TAILLE_COTE; //De base on est limité à 6X6 = 36 cases
        if(nbCasesSansLimite <= limite)
            return nbCasesSansLimite;
        else
            return limite;
    }

    // on fait le jeu de manière totalement controlé pour les valeurs, vu que pour l'instant
    // on fait qu'un niveau de difficulté, donc ici les valeurs qui seront afficher
    // sont mis dans une liste
    public void modifierLesValeurs()
    {
        int prochainNombre = (int) Math.round(Math.random() * ((MAX_DEBUT) - MIN_DEBUT) + MIN_DEBUT);
        valeurs.clear();

        for (int i = 0; i < trouverLeNombreDeCase(); i++) {
            valeurs.add(prochainNombre);
            prochainNombre = prochainNombre + (int) Math.round(Math.random() * ((MAX_INTERVALE) - MIN_INTERVALE) + MIN_INTERVALE);
        }
    }

    public int genenAleaPourTab() {
        int c = (int) (Math.random() * TAILLE_COTE);
        return c;
    }
    public int genenAlea(int i){
        int c = (int) (Math.random() * i);
        return c;
    }

    public void printTabButton() {
        JButton actualButton = null;
        for(int x = 0; x < TAILLE_COTE; x++) {
            for(int y = 0; y < TAILLE_COTE; y++) {
                actualButton = tabBouton[x][y];
                System.out.print("| ");
                if(actualButton == null)
                    System.out.print("B null");
                else if (actualButton.getText() == null)
                    System.out.print("T null");
                else
                    System.out.print(actualButton.getText());

                System.out.print(" ");
            }
            System.out.println("|");
        }
    }

    public void printTabBoolean() {
        for(int x = 0; x < TAILLE_COTE; x++) {
            for(int y = 0; y < TAILLE_COTE; y++) {
                System.out.print("| " + tabBoolean[x][y] + " ");
            }
            System.out.println("|");
        }
    }

    //Vérifie si toutes les cases du tableau de booléen sont à true
    public boolean isTabBooleaanIsFull() {
        for(int x = 0; x < TAILLE_COTE; x++) {
            for(int y = 0; y < TAILLE_COTE; y++) {
                if(tabBoolean[x][y] == false)
                    return false;
            }
        }
        return true;
    }

    public static int getTailleCote() {
        return TAILLE_COTE;
    }

    public static void setTailleCote(int tailleCote) {
        TAILLE_COTE = tailleCote;
    }

    public static int getTempsApparitionDebut() {
        return TEMPS_APPARITION_DEBUT;
    }

    public static void setTempsApparitionDebut(int tempsApparitionDebut) {
        TEMPS_APPARITION_DEBUT = tempsApparitionDebut;
    }

    public void setTableauXY(int tab[]) {
        valXY.add(tab);
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public int getTempsApparition() {
        return tempsApparition;
    }

    public void setTempsApparition(int tempsApparition) {
        this.tempsApparition = tempsApparition;
    }

    public void reduitTempsApparition() {
        tempsApparition -= 150;
    }

    public void initTempsApparition() {
        tempsApparition = TEMPS_APPARITION_DEBUT;
    }
}
