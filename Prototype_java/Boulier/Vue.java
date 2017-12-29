import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Vue extends JFrame {
	protected JButton[] boutonsHaut;
	protected JButton[] boutonsBas;
	protected JButton submit;

	protected Color couleurInvalide;
	protected Color vide;
	protected Color bleu;
	protected Color jaune;
	protected Color rouge;
	protected Color vert;
	protected Color violet;

	Model m;
	public Vue(Model m) {
		super ("Boulier");
		this.m = m;
		initAttribut();
		addToWindows();
	    setSize(600,400);                                // Fixe la taille par défaut
	    setVisible(true);                                // Affiche la fenetre
	    setResizable(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //Cela permet de récupérer la taille de l'écran de l'utilisateur
		this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2); //Place la fenêtre au centre de l'écran de l'utilisateur
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
	}

	private void initAttribut() {
		couleurInvalide = new Color(0, 0, 0);
		vide = new Color (255, 255, 255);
		bleu = new Color(0,211,211);
		jaune = new Color(255,255,0);
		rouge = new Color(255, 0, 0);
		vert = new Color(0, 255, 0);
		violet = new Color(211,0,211);

		boutonsHaut = new JButton[Ligne.getNbBoules()];
		boutonsBas = new JButton[Ligne.getNbBoules()];
		submit = new JButton("Répondre");

		for(int i = 0; i < boutonsHaut.length; i++) {
			Dimension taille = new Dimension(50, 70);
			boutonsHaut[i] = new JButton("");
			boutonsHaut[i].setPreferredSize(taille);
			boutonsBas[i] = new JButton("");
			boutonsBas[i].setPreferredSize(taille);
		}

		colorHaut();
		colorBas();
	}

	public void setControlButton(ActionListener listener) {
		submit.addActionListener(listener);
		for(int i = 0; i < Ligne.getNbBoules(); i++) {
			boutonsHaut[i].addActionListener(listener);
			boutonsBas[i].addActionListener(listener);
		}
	}

	private void addToWindows() {
 		JPanel pano = new JPanel();
            JPanel pano1 = new JPanel();
    	    JPanel pano2 = new JPanel();
    	    pano.setLayout(new BoxLayout(pano,BoxLayout.Y_AXIS));

    	    for (int i = 0; i < boutonsHaut.length; i++) {
    			pano1.add(boutonsHaut[i]);
    			pano2.add(boutonsBas[i]);
    		}
    		pano.add(pano1);
    		pano.add(pano2);
    		pano.add(submit);
		setContentPane(pano);
	}

	public void colorHaut() {
		colorLigne(boutonsHaut, m.getHaut());
	}

	public void deColorHaut() { //Rend la ligne du haut "invisible" (sans pour autant changer le Model
		for(int i = 0; i < boutonsHaut.length; i++) {
			boutonsHaut[i].setBackground(vide);
		}
	}

	public void colorBas() {
		colorLigne(boutonsBas, m.getBas());
	}

	public void colorLigne(JButton[] ligneBoutons, Ligne ligne) {
		for(int i = 0; i < ligneBoutons.length; i++) {

			Boule bouleActuelle = ligne.getBoules()[i];

			if(bouleActuelle.getCouleur() == Couleur.BLEU)
				ligneBoutons[i].setBackground(bleu);
			else if(bouleActuelle.getCouleur() == Couleur.JAUNE)
				ligneBoutons[i].setBackground(jaune);
			else if(bouleActuelle.getCouleur() == Couleur.ROUGE)
				ligneBoutons[i].setBackground(rouge);
			else if(bouleActuelle.getCouleur() == Couleur.VERT)
				ligneBoutons[i].setBackground(vert);
			else if(bouleActuelle.getCouleur() == Couleur.VIOLET)
				ligneBoutons[i].setBackground(violet);
			else if(bouleActuelle.getCouleur() == Couleur.VIDE)
				ligneBoutons[i].setBackground(vide);
			else
				ligneBoutons[i].setBackground(couleurInvalide);
			//boutonsBas[i].addActionListener(cB);
		}
	}

	public void display() {
		this.setVisible(true);
	}

	public void undisplay() {
		this.setVisible(false);
	}
}
