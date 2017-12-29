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

	JLabel[] resultats;

	protected JLabel showScore;
	protected JLabel regardezSequence; //Affiche pendant le temps où l'utilisateur doit regarder la séquence

	ControlTimer ct;
	Timer timerDebut;

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
		timerDebut.start();
	}

	private void initAttribut() {
		couleurInvalide = new Color(0, 0, 0);
		vide = new Color (8, 68, 164);
		bleu = new Color(0,211,211);
		jaune = new Color(255,255,0);
		rouge = new Color(255, 0, 0);
		vert = new Color(0, 255, 0);
		violet = new Color(211,0,211);

		resultats = new JLabel[Ligne.getNbBoules()];
		for(int i = 0; i < Ligne.getNbBoules(); i++) {
			resultats[i] = new JLabel("X ");
			resultats[i].setFont(new Font("Affichage resultat", 1, 50));
			resultats[i].setForeground(Color.RED);
		}

		submit = new JButton("Répondre");
		submit.setVisible(false);

		regardezSequence = new JLabel();
		changeRegardezSequence();

		showScore = new JLabel();
		changeScore();

		boutonsHaut = new JButton[Ligne.getNbBoules()];
		boutonsBas = new JButton[Ligne.getNbBoules()];

		for(int i = 0; i < boutonsHaut.length; i++) {
			Dimension taille = new Dimension(50, 70);
			boutonsHaut[i] = new JButton("");
			boutonsHaut[i].setPreferredSize(taille);
			boutonsBas[i] = new JButton("");
			boutonsBas[i].setPreferredSize(taille);
		}

		colorHaut();
		colorBas();

		ct = new ControlTimer(m, this);
		timerDebut = new Timer(1000, ct); //ce timer s'enclenchera 15X1 fois
	}

	public void setControlButton(ActionListener listener) {
		submit.addActionListener(listener);
		for(int i = 0; i < Ligne.getNbBoules(); i++) {
			boutonsBas[i].addActionListener(listener);
		}
	}

	private void addToWindows() {
 		JPanel pano = new JPanel();
            JPanel panelHaut = new JPanel();
            JPanel panelMillieu = new JPanel(); //Pour la vérification
    	    JPanel panelBas = new JPanel();

			//panelHaut.setLayout(new GridLayout(1, Ligne.getNbBoules()));
			//panelMillieu.setLayout(new GridLayout(1, Ligne.getNbBoules()));
			//panelBas.setLayout(new GridLayout(1, Ligne.getNbBoules()));

    	    pano.setLayout(new BoxLayout(pano,BoxLayout.Y_AXIS));

    	    for (int i = 0; i < Ligne.getNbBoules(); i++) {
    			panelHaut.add(boutonsHaut[i]);
    			panelMillieu.add(resultats[i]);
    			panelBas.add(boutonsBas[i]);
    		}
    		pano.add(showScore);
    		pano.add(panelHaut);
    		pano.add(panelMillieu);
    		pano.add(panelBas);
    		pano.add(submit);
    		pano.add(regardezSequence);
    		pano.setBackground(vide);
    		panelHaut.setBackground(vide);
    		panelMillieu.setBackground(vide);
    		panelBas.setBackground(vide);
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

	public void changeRegardezSequence() {
		regardezSequence.setText("Regardez la séquence (" + m.getNbSecondesVerif() + ").");
	}

	public void verification() {
		java.util.List<Integer> indicesFaux = m.getErreurs();

		if(indicesFaux.isEmpty()) {
			System.out.println("Séquence complète !");
		} else {
			System.out.print("Indice(s) ou les boules ne correspondent pas : ");
			for (Integer indice : indicesFaux) {
				System.out.print(indice + ", ");
			}
			System.out.println();
			m.printLignes();
		}
	}

	public void changeScore() {
		showScore.setText("Score: " + m.getScore());
	}

	public void display() {
		this.setVisible(true);
	}

	public void undisplay() {
		this.setVisible(false);
	}

	public void creerDialogLigneNonComplete(String messageLigneNonComplete) {
		JOptionPane ligneNonComplete = new JOptionPane();
		ligneNonComplete.showMessageDialog(this, messageLigneNonComplete, "Ligne non complète!", JOptionPane.ERROR_MESSAGE);
		JDialog fenLigneNonComplete = ligneNonComplete.createDialog(this, "Ligne non complète!");
	}
}
