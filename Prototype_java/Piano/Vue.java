import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Vue extends JFrame {

	protected Son[][] sonsTouches;
	public JButton[][] touches;
	public JButton lance;
	protected JPanel general;
	protected JPanel global;
	protected JPanel grille;
	protected JPanel grilleglob;
	private Model m;
	protected JLabel etatDeLaPartie;
	protected JLabel showScore;

	protected ControlTimer ct;
	protected Timer timerJoueTouche;
	protected Timer timerClignotementTouche;
	
    public Vue(Model m){
    	super ("Piano");
		this.m = m;
		initAttribut();
		addToWindows();
	    setSize(600,400);        // Fixe la taille par défaut
	    display();                             // Affiche la fenetre
	    setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //Cela permet de récupérer la taille de l'écran de l'utilisateur
		this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2); //Place la fenêtre au centre de l'écran de l'utilisateur
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
    }

    public void initAttribut() {
		general = new JPanel(new GridLayout(3,1));
		grille = new JPanel();

		grilleglob = new JPanel();
		global = new JPanel();

	    /* grille avec touches */
		touches = new JButton[5][5];

		lance = new JButton("lance");

		for (int i = 0; i < touches.length; i++) {
			for (int j = 0; j < touches.length; j++) {
				touches[i][j] = new JButton();
				if(m.getTouches()[i][j].getCouleur() == Couleur.BLANC) {
					touches[i][j].setBackground(Color.WHITE);
					touches[i][j].setPreferredSize(new Dimension(10, 100));
				} else {
					touches[i][j].setBackground(Color.BLACK);
					touches[i][j].setPreferredSize(new Dimension(10, 50));
				}
			}
		}

		createSonsTouches();

		etatDeLaPartie = new JLabel("");
		etatDeLaPartie.setVisible(false);

		showScore = new JLabel("");
		showScore.setVisible(false);

		ct = new ControlTimer(m, this);
		timerJoueTouche = new Timer(100, ct);
		timerClignotementTouche = new Timer(500, ct);
	}

	public void createSonsTouches() {
    	Son sonActuelle;
    	sonsTouches = new Son[5][5];
		for (int i = 0; i < touches.length; i++) {
			for (int j = 0; j < touches.length; j++) {
				sonActuelle = new Son(m.getTouches()[i][j].getNomSon());
				System.out.println("i = " + i + " j = " + j + ": " + m.getTouches()[i][j].getNomSon());
				sonsTouches[i][j] = sonActuelle;
			}
		}
	}

	public void addToWindows() {

		for (int i = 0; i < touches.length; i++) {
			for (int j = 0; j < touches.length; j++) {
				grille.add(touches[i][j]);
			}
		}
		general.add(grille);
		general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
		general.add(lance);
		general.add(etatDeLaPartie);
		general.add(showScore);
		global.add(general);

		Color couleurFenetre = new Color(254, 252, 224);

		global.setBackground(couleurFenetre);
		general.setBackground(couleurFenetre);
		grilleglob.setBackground(couleurFenetre);

		setContentPane(global);
	}

	public void setControlButton(ActionListener listener) {
		for (int i = 0; i < touches.length; i++) {
			for (int j = 0; j < touches.length; j++) {
				touches[i][j].addActionListener(listener);
			}
		}
			lance.addActionListener(listener);
	}

	public void display() {
		this.setVisible(true);
	}

	public void undisplay() {
		this.setVisible(false);
	}

	public void changeScore() {
    	showScore.setText("Score: " + m.getScore());
	}

	public void etatEnCours() {
    	etatDeLaPartie.setText("Ecoutez la séquence...");
	}

	public void etatAVous() {
    	etatDeLaPartie.setText("A vous !");
	}

	public void debutPartie() {
		lance.setVisible(false);
		etatEnCours();
		etatDeLaPartie.setVisible(true);
		changeScore();
		showScore.setVisible(true);
	}

	public void finPartie() {
		lance.setVisible(true);
		etatDeLaPartie.setVisible(false);
		showScore.setVisible(false);
	}

	public void creerDialogVic(String messageVic, ImageIcon imageVic) {
		JOptionPane victoire = new JOptionPane();
		victoire.showMessageDialog(this, messageVic, "Victoire!", JOptionPane.INFORMATION_MESSAGE, imageVic);
		JDialog fenVic = victoire.createDialog(this, "Victoire!");
	}

	public void creerDialogTuto(String messageTuto) {
		JOptionPane victoire = new JOptionPane();
		victoire.showMessageDialog(this, messageTuto, "Comment jouer", JOptionPane.INFORMATION_MESSAGE);
		JDialog fenTuto = victoire.createDialog(this, "Comment jouer");
	}

	public void creerDialogPerdu(String messagePerdu) {
		JOptionPane perdu = new JOptionPane();
		perdu.showMessageDialog(this, messagePerdu, "Perdu!", JOptionPane.INFORMATION_MESSAGE);
		JDialog fenPerdu = perdu.createDialog(this, "Perdu!");
	}

}