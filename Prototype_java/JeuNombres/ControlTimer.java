import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlTimer extends Control implements ActionListener {
    public ControlTimer(Model m, Vue v) {
        super(m, v);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        for (int i = 0; i < vue.plateau.length; i++) {
            for (int j = 0; j < vue.plateau.length; j++) {
                if (vue.plateau[i][j].getText()!=""){
                    model.setTabBouton(vue.plateau[i][j],i,j);
                    vue.plateau[i][j].setText("");
                }
            }
            //enlever affichage et décrémenter le timerApparition
            decrementer();
            model.setInAction(false);
            vue.timerApparition.stop();
        }
    }

    public void decrementer(){
        vue.tempsApparition -= 150;
    }
}
