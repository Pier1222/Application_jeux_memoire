import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlTimer extends Control implements ActionListener {

    public ControlTimer(Model m, Vue v) {
        super(m, v);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == v.timerDebut) {
            reduitTimer();
            if (m.getNbSecondesVerif() <= 0) {
                v.visibiliteTentative();

                m.setInAction(false);
                v.deColorHaut();
                v.timerDebut.stop();
            }
        } else if(e.getSource() == v.timerFinEssaie) {
            v.visibiliteTentative();

            //Remet les cases du bas fausses à vide
            m.resetInactivesBas();
            v.colorBas();

            v.resetResultats();
            m.setInAction(false);
            v.deColorHaut();
            v.timerFinEssaie.stop();
        }
    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezSequence();
    }

}
