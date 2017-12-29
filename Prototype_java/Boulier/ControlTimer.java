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
                v.regardezSequence.setVisible(false);
                v.submit.setVisible(true);
                m.setInAction(false);
                v.deColorHaut();
                v.timerDebut.stop();
            }
        }


    }

    private void reduitTimer() {
        m.reduitNbSecondesVerif();
        v.changeRegardezSequence();
    }

}
