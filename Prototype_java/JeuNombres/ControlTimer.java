import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlTimer extends Control implements ActionListener {
    public ControlTimer(Model m, Vue v) {
        super(m, v);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getSource() == vue.timerApparition) {
            for (int i = 0; i < vue.plateau.length; i++) {
                for (int j = 0; j < vue.plateau.length; j++) {
                    if (vue.plateau[i][j].getText() != "") {
                        model.setTabBouton(vue.plateau[i][j], i, j);
                        vue.plateau[i][j].setText("");
                    }
                }
                //enlever affichage et décrémenter le timerApparition
                decrementerTempsApparition();
                model.setInAction(false);
                vue.timerApparition.stop();
            }
        } else if (actionEvent.getSource() == vue.timerErreur) {
            JButton actualButton = null;
            for(int x = 0; x < Model.getTailleCote(); x++) {
                for(int y = 0; y < Model.getTailleCote(); y++) {
                    actualButton = vue.plateau[x][y];
                    if(actualButton.getBackground() == vue.couleurErreur || actualButton.getBackground() == vue.couleurBonneCase) { //Le bouton coloré en rouge et celui en vert retrouvent leur couleur normal
                        actualButton.setBackground(null);
                    }
                }
            }
            //Reset le jeu
            increaseToMaxTempsApparition();
            vue.messagePerdu();
            vue.debutDePartie(0);
            vue.timerErreur.stop();
        } else if (actionEvent.getSource() == vue.timerPointEnPlus) {
            //Remet un set de nombres en augmentant le score
            model.setScore(model.getScore()+1);
            vue.debutDePartie(model.getScore());
            vue.timerPointEnPlus.stop();
        }
    }

    public void decrementerTempsApparition(){
        if(model.getTempsApparition() > 500) {
            model.reduitTempsApparition();
            vue.changeTimerApparition();
        }
    }

    public void increaseToMaxTempsApparition() {
        model.setTempsApparition(Model.getTempsApparitionDebut());
        vue.changeTimerApparition();
    }
}
