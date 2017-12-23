import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Vue extends JFrame {

    protected Model m;
    protected JButton[][] buttonsCases;
    protected JButton reclame;
    protected JLabel nombre; //Désigne le nombre qui s'affichera en haut
    protected JLabel showScore;
    protected JLabel regardezGrille; //Affiche pendant le temps où l'utilisateur doit regarder la grille
    protected JLabel affichageVies; //V = vie possédée/X = vie perdue

    protected ControlTimer ct;
    protected Timer timerDebut;
    protected Timer timerReact;
    protected Timer evidenceErreur; //Fait passer une case au rouge une demi-seconde si le nombreActu du model y correspondait mais qu'on a rien fait

    protected Son erreur;

    protected Color couleurDeBase;
    protected Color couleurDesactive;
    protected Color couleurErreur;
    protected Color couleurFinit; //Ligne/Colonne finit plus précisemment


    public Vue(Model m){
        super ("Loto");
        this.m = m;
        initAttribut();
        addToWindows();
        setSize(600,400);        // Fixe la taille par défaut
        display();                             // Affiche la fenetre
        setResizable(false);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //Cela permet de récupérer la taille de l'écran de l'utilisateur
        this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2); //Place la fenêtre au centre de l'écran de l'utilisateur
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
        timerDebut.start();
    }

    private void initAttribut() {
        couleurDeBase = Color.WHITE;
        couleurDesactive = Color.LIGHT_GRAY;
        couleurErreur = Color.RED;
        couleurFinit = Color.GREEN;


        buttonsCases = new JButton[Grille.getNbLignes()][Grille.getNbColonnes()];
        initButtonsCases();
        reclame = new JButton("Réclamer");
        reclame.setPreferredSize(new Dimension(100, 50));

        regardezGrille = new JLabel();
        changeRegardezGrille();

        nombre = new JLabel();
        changeNombre();
        nombre.setFont(new Font("Affichage nombre", 1, 20));

        showScore = new JLabel();
        showScore.setText("Score: " + m.getScore());

        affichageVies = new JLabel();
        changeAffichageVies();

        ct = new ControlTimer(m, this);
        timerDebut = new Timer(1000, ct); //ce timer s'enclenchera 15X1 fois
        timerReact = new Timer(5000, ct); //5 secondes de réactions au lieu des 3 prévus à la base
        evidenceErreur = new Timer(500, ct);

        erreur = new Son("Erreur.wav");
    }

    private void initButtonsCases() {
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for(int y = 0; y < Grille.getNbColonnes(); y++) {
                buttonsCases[x][y] = new JButton();
                buttonsCases[x][y].setBorder(new LineBorder(Color.BLACK));
            }
        }
        adaptButtonsCasesToGrille();
    }

    private void adaptButtonsCasesToGrille() {
        Case caseActuelle = null;
        JButton boutonActuel = null;
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                caseActuelle = m.getGrille().getCases()[x][y];
                boutonActuel = buttonsCases[x][y];
                if(!caseActuelle.isValeurNegative()) {
                    boutonActuel.setText("" + caseActuelle.getValeur());
                    boutonActuel.setEnabled(true);
                    boutonActuel.setBackground(couleurDeBase);
                } else {
                    boutonActuel.setText("");
                    boutonActuel.setEnabled(false);
                    boutonActuel.setBackground(couleurDesactive);
                }
            }
        }
    }

    public void changeButtonsCases() {
        Case caseActuelle = null;
        JButton boutonActuel = null;
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                caseActuelle = m.getGrille().getCases()[x][y];
                boutonActuel = buttonsCases[x][y];
                if(!caseActuelle.isActive() && boutonActuel.getBackground() != couleurFinit) {
                    boutonActuel.setEnabled(false);
                    boutonActuel.setBackground(couleurDesactive);
                }
            }
        }
    }

    public void restartTimerReact() {
        timerReact.stop();
        timerReact.start();
    }

    public void changeRegardezGrille() {
        regardezGrille.setText("Regardez ces nombres (" + m.getNbSecondesVerif() + ").");
    }

    public void changeNombre() {
        m.setNombreActu();
        nombre.setText("" + m.getNombreActu());
    }

    public void changeScore() {
        int scoreEnPlus = 1;
        int ligneAColorer = m.getGrille().nouvelleLigneComplete();
        int colonneAColorer = m.getGrille().nouvelleColonneComplete();

        if(ligneAColorer >= 0) {
            scoreEnPlus += 5; //Points bonus pour ligne complète
            coloriseLigne(ligneAColorer);
        }

        if(colonneAColorer >= 0) {
            scoreEnPlus += 5; //Points bonus pour colonne Complète
            coloriseColonne(colonneAColorer);
        }

        if(m.getGrille().isGrilleComplete()) {
            scoreEnPlus += 50;
            timerReact.stop();
            m.printAndClearStatistique();
            m.nouvelleGrille();
            creerDialogGrilleComplete("Vous avez complété cette Grille !!!!");
            nombre.setVisible(false);
            adaptButtonsCasesToGrille();
            changeRegardezGrille();
            reclame.setVisible(false);
            regardezGrille.setVisible(true);
            timerDebut.start();
        }
        m.augmenteScore(scoreEnPlus);
        showScore.setText("Score: " + m.getScore());
    }

    public void coloriseLigne(int numeroLigne) {
        for(int y = 0; y < Grille.getNbColonnes(); y++) {
            buttonsCases[numeroLigne][y].setBackground(couleurFinit);
        }
    }

    public void coloriseColonne(int numeroColonne) {
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            buttonsCases[x][numeroColonne].setBackground(couleurFinit);
        }
    }

    public void changeAffichageVies() {
        String textVie = "";
        for(int i = 0; i < m.getNbVies(); i++) { //Vies actuelles
            textVie += "V\n";
        }
        for(int i = m.getNbVies(); i < Model.getNbViesMax(); i++) { //Vies perdues
            textVie += "X\n";
        }
        affichageVies.setText(textVie);
    }

    public void perdVieAffichage() {
        m.perdVie();
        changeAffichageVies();
        verifEtEnclencheFinDePartie();
    }

    public void verifEtEnclencheFinDePartie() {
        if(m.finDePartie()) {
            timerReact.stop();
            creerDialogPerdu("Vous avez perdu tous vos vies...");
        }
    }

    public void setControlButton(ActionListener listener) {
        reclame.addActionListener(listener);
    }

    private void addToWindows() {
        JPanel general = new JPanel();
        general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));

        JPanel ligneDuHaut = new JPanel();
        ligneDuHaut.setLayout(new GridLayout(1, 3));

        ligneDuHaut.add(affichageVies);
        ligneDuHaut.add(nombre);
        nombre.setVisible(false);
        ligneDuHaut.add(showScore);

        general.add(ligneDuHaut);

        JPanel grillePanel = new JPanel(new GridLayout(Grille.getNbLignes(), Grille.getNbColonnes()));
        for(int x = 0; x < Grille.getNbLignes(); x++) {
            for (int y = 0; y < Grille.getNbColonnes(); y++) {
                grillePanel.add(buttonsCases[x][y]);
            }
        }
        general.add(grillePanel);

        general.add(reclame);
        reclame.setVisible(false);

        general.add(regardezGrille);

        Color couleurFenetre = new Color(255, 102, 36);
        general.setBackground(couleurFenetre);
        grillePanel.setBackground(couleurFenetre);
        ligneDuHaut.setBackground(couleurFenetre);

        setContentPane(general);
    }

    public void erreurEnEvidence(Case caseAClignoter) {
        erreur.jouer();

        if(caseAClignoter != null) {
            Case caseActu = null;
            for(int x = 0; x < Grille.getNbLignes(); x++) {
                for (int y = 0; y < Grille.getNbColonnes(); y++) {
                    caseActu = m.getGrille().getCases()[x][y];
                    if(caseActu == caseAClignoter) {
                        buttonsCases[x][y].setBackground(couleurErreur);
                        evidenceErreur.start();
                    }
                }
            }
        }
    }

    public void display() {
        this.setVisible(true);
    }

    public void undisplay() {
        this.setVisible(false);
    }

    public void creerDialogPerdu(String messagePerdu) {
        JOptionPane perdu = new JOptionPane();
        perdu.showMessageDialog(this, messagePerdu, "Perdu!", JOptionPane.INFORMATION_MESSAGE);
        JDialog fenPerdu = perdu.createDialog(this, "Perdu!");
    }

    public void creerDialogGrilleComplete(String messageGrilleComplete) {
        JOptionPane victoire = new JOptionPane();
        victoire.showMessageDialog(this, messageGrilleComplete, "Grille complète", JOptionPane.INFORMATION_MESSAGE);
        JDialog fenVic = victoire.createDialog(this, "Grille complète");
    }
}
