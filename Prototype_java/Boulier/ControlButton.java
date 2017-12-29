import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControlButton extends Control implements ActionListener {

    public ControlButton(Model m, Vue v){
         super(m, v);
         v.setControlButton(this);
    }
	public void actionPerformed(ActionEvent e) {
    	if(m.isInAction()) {
			System.out.println("On ne triche pas s'il vous plaît");
			return;
		}

    	if(e.getSource() == v.submit) {
    		if(!m.getBas().isLigneColored())
				v.creerDialogLigneNonComplete("Vous devez compléter la ligne du bas avant de répondre.");
    		else {
				v.verification();
			}
		} else { //Si ce n'est pas le bouton de validation, c'est une boule alors
    		for(int i = 0; i < Ligne.getNbBoules(); i++) {
    			if(v.boutonsBas[i] == e.getSource() && m.getBas().getBoules()[i].isActive()) {
    				m.getBas().getBoules()[i].changeCouleur();
    				v.colorBas();
				}
			}
		}
	}
}

