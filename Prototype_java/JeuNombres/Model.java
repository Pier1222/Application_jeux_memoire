import javax.swing.*;
import java.util.ArrayList;

public class Model {

    private int score;
    private ArrayList valeurs;
    private int nombreDeCase;
    private ArrayList valXY;
    private JButton tabBouton[][];
    private boolean tabBoolean[][];
    private boolean inAction;

    public Model() {
        score = 0;
        valeurs = new ArrayList<Integer>();
        nombreDeCase = 0;
        valXY=new ArrayList<Integer[]>();
        tabBouton=new JButton[6][6];
        tabBoolean=new boolean[6][6];
        inAction = false;
    }


    public int getScore() {
        return score;
    }

    public JButton[][] getTabBouton() {
        return tabBouton;
    }

    public JButton getTabBouton(int x,int y){return tabBouton[x][y];}

    //permet de remettre à zéro le tableau de jButton
    public void set0tabBouton(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (tabBouton[i][j]!=null){
                    tabBouton[i][j].setText("");
                }
            }

        }
    }

    public void setTabBoolean() {
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j < 6; j++) {
                tabBoolean[i][j]=false;
            }

        }

    }
    //méthode permettant de comparer si une case à déjà été tiré
    public int[] comparerVal(int[] tab){
        int[] tabAlea=new int[2];
        tabAlea=tab;
        while (tabBoolean[tabAlea[0]][tabAlea[1]]==true) {

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
        tabBouton[x][y]=new JButton(button.getText());
    }

    public ArrayList getValeurs() {
        return valeurs;
    }
    public void removeValeur(int valeur){ valeurs.remove(valeur);}

    // fonction qui permet de trouver le nombre de case qu'il faut ajouter au niveau des difficultés
    public int trouverLeNombreDeCase()
    {
        return score+2;
    }

    //on fait le jeu de manière totalement controlé pour les valeurs, vu que pour l'instant
    //on fait qu'un niveau de difficulté, donc ici les valeurs qui seront afficher
    //sont mis dans une liste
    public void modifierLesValeurs()
    {
        valeurs.clear();
        for (int i = 0; i < trouverLeNombreDeCase(); i++) {
            valeurs.add(i+1);
        }
        /**
        switch (trouverLeNombreDeCase()) {
            case 2:
                valeurs.clear();
                valeurs.add(3);
                valeurs.add(5);
                break;

            case 3:
                valeurs.clear();
                valeurs.add(1);
                valeurs.add(3);
                valeurs.add(5);
                break;

            case 4:
                valeurs.clear();
                valeurs.add(1);
                valeurs.add(3);
                valeurs.add(4);
                valeurs.add(5);
                break;

            case 5:
                valeurs.clear();
                valeurs.add(1);
                valeurs.add(2);
                valeurs.add(3);
                valeurs.add(4);
                valeurs.add(5);

        }**/
    }

    public int genenAleaPourTab(){
        int c = (int) (Math.random() * 5);
        return c;
    }
    public int genenAlea(int i){
        int c = (int) (Math.random() * i);
        return c;
    }


    public void setTableauXY(int tab[])
    {
        valXY.add(tab);
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }
}
