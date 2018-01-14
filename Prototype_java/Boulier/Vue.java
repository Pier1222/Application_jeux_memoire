import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.List;

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

	protected JLabel[] resultats;

	protected JLabel showScore;
	protected JLabel regardezSequence; //Affiche pendant le temps où l'utilisateur doit regarder la séquence
	protected JLabel calculScore;
	protected JLabel tentativesRestantes;

	protected ControlTimer ct;
	protected Timer timerDebut;
	protected Timer timerFinEssaie;

	protected Model m;

	public Vue(Model m) {
		super ("Boulier");
		this.m = m;
		initAttribut();
		addToWindows();
	    setSize(largeurFenetreAAvoir(),400);  // Pour 10 boules, width = 600
	    setVisible(true);                                // Affiche la fenetre
	    setResizable(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); //Cela permet de récupérer la taille de l'écran de l'utilisateur
		this.setLocation((d.width-this.getWidth())/2, (d.height-this.getHeight())/2); //Place la fenêtre au centre de l'écran de l'utilisateur
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
		timerDebut.start();
	}

	private int largeurFenetreAAvoir() {
		if(Ligne.getNbBoules() >= 5)
			return Ligne.getNbBoules()*60;
		return 300; //Largeur minimal de la fenêtre
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
			resultats[i] = new JLabel();
			resultats[i].setFont(new Font("Affichage resultat", 1, 50));
		}
		resetResultats();

		submit = new JButton("Répondre");

		regardezSequence = new JLabel();
		changeRegardezSequence();

		showScore = new JLabel();
		changeScore();

		calculScore = new JLabel("");

		tentativesRestantes = new JLabel("");
		changeTentativesRestantes();

		visibiliteDebutDePartie();

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
		timerFinEssaie = new Timer(10000, ct); //Il aura 10 secondes entre 2 tentatives
	}

	public void setControlButton(ActionListener listener) {
		submit.addActionListener(listener);
		for(int i = 0; i < Ligne.getNbBoules(); i++) {
			boutonsBas[i].addActionListener(listener);
		}
	}

	/*Les trois méthodes qui suivent changent la visibilité:
	- Du bouton de soumission de réponse
	- Du texte qui montre le calcul pour avoir le nombre de points ajoués pour la tentative
	- Du texte qui invite l'utilisateur à regardez la séquence (en lui indiquant combien de temps il lui reste)
	- Du texte qui indique combien de tentatives il reste à l'utilisateur
	 */
	public void visibiliteDebutDePartie() {
		submit.setVisible(false);
		calculScore.setVisible(false);
		regardezSequence.setVisible(true);
		tentativesRestantes.setVisible(true);
	}

	public void visibiliteTentative() {
		submit.setVisible(true);
		calculScore.setVisible(false);
		regardezSequence.setVisible(false);
		tentativesRestantes.setVisible(false);
	}

	public void visibiliteVerification() {
		submit.setVisible(false);
		calculScore.setVisible(true);
		regardezSequence.setVisible(false);
		tentativesRestantes.setVisible(true);
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
    		pano.add(calculScore);
    		pano.add(tentativesRestantes);

    		pano.setBackground(vide);
    		panelHaut.setBackground(vide);
    		panelMillieu.setBackground(vide);
    		panelBas.setBackground(vide);

		setContentPane(pano);
	}

	public void colorHaut() {
		colorLigne(boutonsHaut, m.getHaut());
	}

	public void deColorHaut() { //Rend la ligne du haut "invisible" (sans pour autant changer le Model)
		for(int i = 0; i < boutonsHaut.length; i++) {
			if(m.getBas().getBoules()[i].isActive()) { //Si la boule du bas est correct, la version du haut ne se décolorisera pas
				boutonsHaut[i].setBackground(vide);
			}
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
		}
	}

	public void changeRegardezSequence() {
		regardezSequence.setText("Regardez la séquence (" + m.getNbSecondesVerif() + ").");
	}

	public int changeCalculScore(int juste, int erreur) { //Calcul et renvoie le score à ajouter
		int scoreEnPlus = (juste*2) - erreur;
		calculScore.setText(juste + "X2 - " + erreur + " = " + scoreEnPlus);
		return scoreEnPlus;
	}

	public void changeScore() {
		showScore.setText("Score: " + m.getScore());
	}

	public void changeTentativesRestantes() {
		if(m.getNbTentatives() == Model.getNbTentativesMax()) //Si on est au début de la partie
			tentativesRestantes.setText("Vous possédez " + m.getNbTentatives() + " tentatives pour compléter votre séquence.");
		else {
			if (m.getNbTentatives() > 1) //Si on est pas au début de la partie mais qu'il reste plus d'une tentative
				tentativesRestantes.setText("Tentatives restantes: " + m.getNbTentatives());
			else //Si il ne reste plus qu'une seule tentative
				tentativesRestantes.setText("Dernière tentative!!!");
		}
	}

	public void verification() {
		List<Integer> indicesFaux = m.getErreurs();
		int nbBoulesJustes = 0;
		int nbBoulesFausses = 0;
		int scoreEnPlus = 0;

		m.setInAction(true); //Empêche l'utilisateur de faire quoi que ce soit pendant qu'on lui remontre la séquence et si la partie est finie

		for(int i = 0; i < Ligne.getNbBoules(); i++) {
			Boule bouleActuelle = m.getBas().getBoules()[i];
			if(indicesFaux.contains(i)) {
				resultats[i].setText("X ");
				resultats[i].setForeground(Color.RED);
				nbBoulesFausses++;
			} else if(bouleActuelle.isActive()) {
				resultats[i].setText("V ");
				resultats[i].setForeground(Color.GREEN);
				bouleActuelle.setActive(false); //Etant donné qu'elle est correct, on n'en tiendra plus compte désormais
				nbBoulesJustes++;
			}
		}

		scoreEnPlus = changeCalculScore(nbBoulesJustes, nbBoulesFausses);
		m.augmenteScore(scoreEnPlus);
		visibiliteVerification();
		changeScore();
		colorHaut(); //Pendant la vérification, on montre de nouveau la ligne du haut

		if(indicesFaux.isEmpty()) {
			System.out.println("Séquence complète !");
			creerDialogSequenceComplete("Félicitation! Les deux séquences sont identiques." +
					"\nScore final: " + m.getScore() + "." +
					"\nNombre de tentative(s) utilisée(s): " + (Model.getNbTentativesMax() - m.getNbTentatives() + 1 + "."));
			reset();
		} else {
			m.perdTentative();

			System.out.print("Indice(s) ou les boules ne correspondent pas : ");
			for (Integer indice : indicesFaux) {
				System.out.print(indice + ", ");
			}
			System.out.println();
			m.printLignes();
			if(m.getNbTentatives() > 0) {
				changeTentativesRestantes();
				tentativesRestantes.setVisible(true);
				timerFinEssaie.start(); //Ce Timer ne s'enclenche que si la partie n'est pas finie
			} else {//La partie est perdue
				creerDialogPlusDeTentatives("Votre nombre de tentatives est épuisée..." +
						"\nScore final: " + m.getScore() + ".");
				reset();
			}
		}
	}

	public void resetResultats() {
		for(int i = 0; i < resultats.length; i++) {
			resultats[i].setText("  ");
		}
	}

	public void reset() {
		if(creerDialogReset("Voulez-vous rejouez ?") == JOptionPane.NO_OPTION) //Si l'utilisateur ne veut pas redémarrer une partie, cette méthode ne fait rien
			return;

		m.setValeurs();
		resetResultats();

		changeScore();
		changeTentativesRestantes();
		changeRegardezSequence();

		colorHaut();
		colorBas();
		visibiliteDebutDePartie();
		timerDebut.start();
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

	public void creerDialogSequenceComplete(String messageSequenceComplete) {
		JOptionPane sequenceComplete = new JOptionPane();
		sequenceComplete.showMessageDialog(this, messageSequenceComplete, "Gagner!", JOptionPane.INFORMATION_MESSAGE);
		JDialog fenSequenceComplete = sequenceComplete.createDialog(this, "Gagner!");
	}

	public void creerDialogPlusDeTentatives(String messagePlusDeTentatives) {
		JOptionPane plusDeTentatives = new JOptionPane();
		plusDeTentatives.showMessageDialog(this, messagePlusDeTentatives, "Perdue!", JOptionPane.INFORMATION_MESSAGE);
		JDialog fenPlusDeTentatives = plusDeTentatives.createDialog(this, "Perdue!");
	}

	public int creerDialogReset(String messageReset) {
		JOptionPane reset = new JOptionPane();
		int choix = reset.showConfirmDialog(null, messageReset, "Recommencer ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		JDialog fenReset = reset.createDialog(this, "Recommencer ?");
		return choix;
	}
}
