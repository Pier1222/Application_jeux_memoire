import com.sun.prism.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class ControlButton extends Control implements ActionListener {


    public ControlButton(Model model, Vue vue) {
        super(model, vue);
        vue.setControlButton(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(model.isInAction()) {
            System.out.println("Ce n'est pas le moment pour appuyer sur les boutons");
            return;
        }
            for (int i = 0; i < vue.plateau.length; i++) {
                for (int j = 0; j < vue.plateau.length; j++) {
                    if (vue.plateau[i][j] == actionEvent.getSource()) {
                        afficherValeurSiVrai(i, j);
                    }
                }
            }
    }

    public void afficherValeurSiVrai(int i, int j) {
        JButton buttonVue = vue.plateau[i][j];
        JButton buttonModel = model.getTabBouton(i, j);
        if ((Integer)parseInt(buttonModel.getText()) == model.getValeurs().get(0)) { //Si c'est la bonne case qui a été cliqué
            buttonVue.setText(model.getTabBouton()[i][j].getText());
            buttonVue.setEnabled(false);
            model.removeValeur(0);
            if (model.getValeurs().isEmpty()) { //Si tous le set de nombres à été découvert
                vue.pointEnPlus.jouer();
                vue.timerPointEnPlus.start();
            }
        }
        else { //Si l'utilisateur s'est trompé
            model.setInAction(true);
            vue.erreur.jouer();
            buttonVue.setBackground(vue.couleurErreur); //La case cliqué est mise en rouge
            vue.revelerPlateau();
            vue.timerErreur.start();
        }
    }
}
