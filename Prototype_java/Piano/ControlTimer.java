import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlTimer extends Control implements ActionListener {

    public ControlTimer(Model m, Vue v) {
        super(m, v);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == v.timerJoueTouche) {
            if (m.getPlaceSequence() == 0) {
                joueToucheDelaiDurantSequence(); //Ceci permet de faire correctement la transition entre le premier bouton qui s'active rapidement, et les autres appuie plus long à s'enclencher
                m.avanceSequence();
            } else if (m.getPlaceSequence() <= m.getTailleSequence()) { //On appuie sur un bouton si la séquence qu'on est en train de jouer n'est pas assez longue pour le model
                int ligne = 0;
                int colonne = 0;
                //Ces variables décideront sur quelle bouton appuyer ensuite

                Touche nextTouche = m.getSequenceOrdi()[m.getPlaceSequence()-1];
                if(nextTouche != null) {
                    for (int i = 0; i < v.touches.length; i++) {
                        for (int j = 0; j < v.touches.length; j++) {
                            if (m.getTouches()[i][j] == nextTouche) {
                                ligne = i;
                                colonne = j;
                            }
                        }
                    }
                } else { //Création d'une nouvelle touche dans la séquence
                    ligne = (int) Math.round(Math.random() * ((m.getTAILLE_COTE() - 1) - 0) + 0); //On prend une case au hasard entre [0][0] et [4][4]
                    colonne = (int) Math.round(Math.random() * ((m.getTAILLE_COTE() - 1) - 0) + 0);
                    m.addSequenceOrdi(m.getPlaceSequence()-1, m.getTouches()[ligne][colonne]); //Important de mettre une touche DEJA présente dans les touches du model
                }

                m.setInAction(false);
                v.touches[ligne][colonne].doClick();
                m.setInAction(true);

                System.out.println("Touche " + m.getPlaceSequence() + "/" + m.getTailleSequence() + ": ligne: " + (ligne + 1) + ", colonne: " + (colonne + 1));
                m.avanceSequence();
                //A faire: une variable dans le model qui stocke le résultat que rejouera ce Control Timer en plus d'ajouter une nouvelle note
            } else {
                System.out.println(""); //Permet de mieux voir si la séquence précédent est bien rejouer
                m.setInAction(false);
                m.setTourJoueur(true);
                v.etatAVous();
                m.reinitPlaceSequence();
                joueToucheDelaiDebut();
                v.timerJoueTouche.stop();
            }
        } else if (e.getSource() == v.timerClignotementTouche) {
            for (int i = 0; i < v.touches.length; i++) {
                for (int j = 0; j < v.touches.length; j++) {
                    if (m.getTouches()[i][j].isEstActif()) {
                            if(m.getTouches()[i][j].getCouleur() == Couleur.BLANC) {
                                v.touches[i][j].setBackground(Color.WHITE);
                            } else if (m.getTouches()[i][j].getCouleur() == Couleur.NOIR) {
                                v.touches[i][j].setBackground(Color.BLACK);
                            }
                            m.getTouches()[i][j].setEstActif(false);
                            if(!v.timerJoueTouche.isRunning()) //Pour ne pas laisser accidentellement la main à l'utilisateur pendant qu'une séquence est jouer
                                m.setInAction(false);
                            v.timerClignotementTouche.stop();
                    }
                }
            }
        }
    }

    private void joueToucheDelaiDebut() {
        v.timerJoueTouche.setDelay(100);
    }

    private  void joueToucheDelaiDurantSequence() {
        v.timerJoueTouche.setDelay(2000);
    }

}
