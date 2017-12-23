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
        if ((Integer)parseInt(model.getTabBouton(i, j).getText()) == model.getValeurs().get(0)) {
            vue.plateau[i][j].setText(model.getTabBouton()[i][j].getText());
            vue.plateau[i][j].setEnabled(false);
            model.removeValeur(0);
            if (model.getValeurs().isEmpty()){

                model.setScore(model.getScore()+1);
                vue.debutDePartie(model.getScore());
            }
        }
        else {
            vue.messagePerdu();
            vue.debutDePartie(0);
        }
    }
}
