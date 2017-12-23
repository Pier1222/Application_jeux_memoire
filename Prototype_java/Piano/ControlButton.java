import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlButton extends Control implements ActionListener {

	public ControlButton(Model m, Vue v) {
		super(m, v);
		v.setControlButton(this);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(m.isInAction()) { //L'application fait quelque chose et on ne doit pas l'interrompre
			System.out.println("Attendez que cela soit fini au moins...");
			return;
		}

		if (e.getSource() == v.lance) {
			v.debutPartie();
			m.setInAction(true);
			v.timerJoueTouche.start();
		}

		else if (e.getSource() instanceof JButton) {
			JButton touchePiano = (JButton) e.getSource();
			for (int i = 0; i < v.touches.length; i++) {
				for (int j = 0; j < v.touches.length; j++) {
					JButton toucheActuelle = v.touches[i][j];
					if(touchePiano == toucheActuelle) {
						if(m.isTourJoueur()) {
							Touche toucheAVerifier = m.getTouches()[i][j];
							if(!m.verifToucheJoueur(toucheAVerifier)) {
								v.creerDialogPerdu("Vous avez appuyé sur la mauvaise touche\n Vous avez fait un score de " + m.getScore() + " et vous avez appuyé correctement sur " + m.getNbTouchesReussies() + " touche(s) dans cette séquence.");
								m.reinitPiano();
								v.timerJoueTouche.setDelay(100);
								m.setTourJoueur(false);
								v.finPartie();
								return;
							} else {
								m.avanceSequence();
								m.augmenteNbTouchesReussies();
								lanceAnimationBouton(i, j);
								if(m.getPlaceSequence() >= m.getTailleSequence()) {
									m.succesReproductionSequence();
									v.changeScore();
									v.etatEnCours();
									v.timerJoueTouche.setDelay(2500);
									m.setTourJoueur(false);
									v.timerJoueTouche.start();
								}
							}
						} else {
							lanceAnimationBouton(i, j);
						}
					}
				}
			}
		}
	}

	public void lanceAnimationBouton(int i, int j) {
		v.touches[i][j].setBackground(Color.RED);
		v.sonsTouches[i][j].jouer();
		m.getTouches()[i][j].setEstActif(true);
		m.setInAction(true);
		v.timerClignotementTouche.start();
	}

}
