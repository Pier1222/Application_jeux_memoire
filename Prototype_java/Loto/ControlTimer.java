import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlTimer extends Control implements ActionListener {

    public ControlTimer(Model m, Vue v) {
        super(m, v);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == v.timerDebut) {
            reduitTimer();
            if(m.getNbSecondesVerif() <= 0) {
                v.regardezGrille.setVisible(false);
                v.reclame.setVisible(true);
                v.nombre.setVisible(true);
                m.setInAction(false);
                v.timerDebut.stop();
                v.timerReact.start();
            }
        } else if(e.getSource() == v.timerReact) {
            System.out.println("Aucune réaction");

            Case caseCorrespond = m.nombreActuDansGrille();
            if(caseCorrespond == null) {
                System.out.println("Bonne réponse !\n");
                m.addNombreActuToStatistiques("AB");
                m.nombreActuNonDispo();
            } else {
                System.out.println("Mauvaise réponse !\n");
                m.addNombreActuToStatistiques("AM");
                v.erreurEnEvidence(caseCorrespond);
                v.perdVieAffichage();
            }
            if(!m.isInAction()) { //Si la partie s'est finit par la perte d'une vie, il ne faut pas que le jeu redémarre le timerReact
                v.restartTimerReact();
                v.changeNombre();
            }
        } else if (e.getSource() == v.evidenceErreur) {
            for(int x = 0; x < Grille.getNbLignes(); x++) {
                for (int y = 0; y < Grille.getNbColonnes(); y++) {
                    if(v.buttonsCases[x][y].getBackground() == v.couleurErreur)
                        v.buttonsCases[x][y].setBackground(v.couleurDeBase);
                }
            }
        }


    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezGrille();
    }

}
