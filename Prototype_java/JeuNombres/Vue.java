

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Vue extends JFrame {
    protected JButton[][] plateau;
    private Model model;
    private JMenuItem jmi;
    private ControlButton controlButton;
    protected ControlTimer controlTimer;
    protected Timer timer;
    protected int temps = 2000;

    public Vue(Model model) {
        this.model = model;
        setTitle("Jeu des nombres");
        setSize(450,450);
        initTimer();
        initMenuBar();
        initPlateau();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
    }

    private void initTimer() {
        this.controlTimer=new ControlTimer(this.model,this);
        this.timer=new Timer(temps,controlTimer);
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
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                plateau[i][j].setText("");
                plateau[i][j].setFocusPainted(false);
                plateau[i][j].setEnabled(false);
            }

        }
    }

    public void initPlateau() {
        plateau = new JButton[6][6];
        JPanel pGlobal = new JPanel(new GridLayout(6,6));
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
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                plateau[i][j].addActionListener(e);
            }
        }
    }
    public void afficher(int x,int y,String nombre){
        plateau[x][y].setText(nombre);
    }
    public void debutDePartie(int score){
        videPlateau();
        if (model.getScore()>=1)videPlateau();
        model.setScore(score);
        model.modifierLesValeurs();
        model.setTabBoolean();
        for (int i = 0; i < model.trouverLeNombreDeCase(); i++) {

            int x=model.genenAleaPourTab();
            int y=model.genenAleaPourTab();
            int tab[]= {x, y};
            model.setTabBoolean(x,y);
            if (i >=1){
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
            timer.start();
        }
        model.setTabBoolean();
    }

    public void messagePerdu(){
        JOptionPane.showMessageDialog(this, "Vous avez perdu, votre score: "+model.getScore(), "Dommage!", JOptionPane.INFORMATION_MESSAGE);
        model.setScore(0);
        model.set0tabBouton();
        newPlateau();
        debutDePartie(0);




    }
}
