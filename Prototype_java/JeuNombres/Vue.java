

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class Vue extends JFrame {
    protected JButton[][] plateau;
    protected Model model;
    protected JMenuItem jmi;
    protected ControlButton controlButton;
    protected ControlTimer controlTimer;
    protected Timer timerApparition; //Temps pendant lequel les nombres apparaissent
    protected Timer timerErreur; //Temps pendant lequel l'erreur est mis en évidence avant de redémarrer la partie
    protected Timer timerPointEnPlus; //Temps pendant lequel la grille entièrement juste est montré

    protected Son erreur;
    protected Son pointEnPlus;

    protected Color couleurErreur;
    protected Color couleurBonneCase;

    public Vue(Model model) {
        this.model = model;
        setTitle("Jeu des nombres");
        setSize(450,450);
        initAttribut();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initAttribut() {
        erreur = new Son("Erreur.wav");
        pointEnPlus = new Son("Score.wav");
        couleurErreur = Color.RED;
        couleurBonneCase = Color.GREEN;
        initTimer();
        initMenuBar();
        initPlateau();
    }

    private void initTimer() {
        controlTimer = new ControlTimer(this.model,this);
        timerApparition = new Timer(model.getTempsApparition(), controlTimer);
        timerErreur = new Timer(5000, controlTimer);
        timerPointEnPlus = new Timer(1500, controlTimer);
    }

    public void videPlateau(){
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau.length; j++) {
                if (plateau[i][j].getText()!="") {
                    plateau[i][j].setText("");
                    plateau[i][j].setFocusPainted(false);
                    plateau[i][j].setEnabled(false);
                }
            }
        }
    }

    public void newPlateau(){
        for (int i = 0; i < Model.getTailleCote(); i++) {
            for (int j = 0; j < Model.getTailleCote(); j++) {
                plateau[i][j].setText("");
                plateau[i][j].setFocusPainted(false);
                plateau[i][j].setEnabled(false);
            }

        }
    }

    public void initPlateau() {
        plateau = new JButton[Model.getTailleCote()][Model.getTailleCote()];
        JPanel pGlobal = new JPanel(new GridLayout(Model.getTailleCote(),Model.getTailleCote()));
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau.length; j++) {
                plateau[i][j] = new JButton();
                plateau[i][j].setFocusPainted(false);
                plateau[i][j].setEnabled(false);
                pGlobal.add(plateau[i][j]);
            }
        }
        debutDePartie(0);
        setContentPane(pGlobal);
    }

    private void initMenuBar() {
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Options");
        jmi = new JMenuItem("Nouvelle partie");
        jm.add(jmi);
        jmb.add(jm);
        setJMenuBar(jmb);
    }

    public void setControlButton(ActionListener e) {
        for (int i = 0; i < Model.getTailleCote(); i++) {
            for (int j = 0; j < Model.getTailleCote(); j++) {
                plateau[i][j].addActionListener(e);
            }
        }
    }

    public void afficher(int x,int y,String nombre){
        plateau[x][y].setText(nombre);
    }

    public void debutDePartie(int score) {
        videPlateau();
        if (model.getScore() >=1) videPlateau();
        model.setScore(score);
        model.modifierLesValeurs();
        model.setTabBoolean();
        for (int i = 0; i < model.trouverLeNombreDeCase(); i++) {

            int x=model.genenAleaPourTab();
            int y=model.genenAleaPourTab();
            int tab[]= {x, y};
            model.setTabBoolean(x,y);
            if (i >= 1){
               tab=model.comparerVal(tab);
            }
            x = tab[0];
            y = tab[1];
            model.setTableauXY(tab);
            afficher(x,y, String.valueOf(model.getValeurs().get(i)));
            System.out.println("\nValeur " + (i+1) + ": X = " + x + " Y = " + y);
            plateau[x][y].setEnabled(true);
            model.setTabBouton(plateau[x][y],x,y);
            model.setInAction(true);
            timerApparition.start();
        }
        System.out.println("\nTemps d'apparition: " + model.getTempsApparition()/1000.0 + " seconde(s)");
        model.setTabBoolean();
        System.out.println("Selon la Vue:");
        printPlateau();
        System.out.println("\nSelon le Model:");
        model.printTabButton();
    }

    public void printPlateau() {
        JButton actualButton = null;
        for(int x = 0; x < Model.getTailleCote(); x++) {
            for(int y = 0; y < Model.getTailleCote(); y++) {
                actualButton = plateau[x][y];
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

    public void revelerPlateau() { //Montre tous les nombres dans le plateau en coloriant en vert le prochain bouton qu'il fallait cliquer
        JButton buttonVue = null;
        JButton buttonModel = null;
        for(int x = 0; x < Model.getTailleCote(); x++) {
            for(int y = 0; y < Model.getTailleCote(); y++) {
                buttonVue = plateau[x][y];
                buttonModel = model.getTabBouton(x, y);
                if(buttonModel != null && buttonModel.getText() != null && buttonVue.isEnabled()) {
                    buttonVue.setText(buttonModel.getText());
                    if ((Integer) parseInt(buttonModel.getText()) == model.getValeurs().get(0))
                        buttonVue.setBackground(couleurBonneCase);
                }
            }
        }
    }

    public void changeTimerApparition() {
        timerApparition.setDelay(model.getTempsApparition());
    }

    public void messagePerdu(){
        JOptionPane.showMessageDialog(this, "Vous avez perdu." +
                "\nVotre score: "+model.getScore(), "Dommage!", JOptionPane.INFORMATION_MESSAGE);
        model.set0tabBouton();
        newPlateau();
        debutDePartie(0);
    }
}
