import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton extends Control implements ActionListener {

    public ControlButton(Model m, Vue v) {
        super(m, v);
        v.setControlButton(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(m.isInAction()) {
            System.out.println("Ce n'est pas le moment pour ça");
            return;
        }

        if(e.getSource() == v.reclame) {
            System.out.println("Réaction");

            Case caseCorrespond = m.nombreActuDansGrille();
            if(caseCorrespond != null) {
                System.out.println("Bonne réponse !\n");
                m.addNombreActuToStatistiques("RB");
                m.nombreActuNonDispo();
                caseCorrespond.setActive(false); //La case ne fait plus partie de la partie
                v.changeButtonsCases();
                v.changeScore();
            } else {
                System.out.println("Mauvaise réponse !\n");
                m.addNombreActuToStatistiques("RM");
                v.erreurEnEvidence(caseCorrespond);
                v.perdVieAffichage();
            }
            if(!m.isInAction()) { //Si la partie s'est finit par la perte d'une vie, il ne faut pas que le jeu redémarre le timerReact
                v.restartTimerReact();
                v.changeNombre();
            }

        }

    }
}
