import java.awt.*;
import  java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class Fenetre extends JFrame {
	protected ControlButton cB;
	protected static JButton[] mesBoutons;
	protected JButton[] mesBoutons1;
	protected static String[] mesBoutons1Color; // les couleur des bouton du joueur
	protected static String[] mesBoutonsColor;
	protected JButton submit;
	public Fenetre() {
		super ("Boulier");
	    creerWidget();
	    setSize(400,400);                                // Fixe la taille par défaut
	    setVisible(true);                                // Affiche la fenetre
	    setResizable(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Gestion de la fermeture
	  }
	public void creerWidget() {
		mesBoutons = new JButton[8];
		mesBoutons1 = new JButton[8];
		mesBoutonsColor = new String[8];
		mesBoutons1Color = new String[8];
		submit = new JButton("submit");
		
		Color jaune = new Color(255,255,0);
		Color bleu = new Color(0,211,211);
		Color violet = new Color(211,0,211);
		
		cB = new ControlButton(this);
		
		for(int i = 0; i < mesBoutons.length; i++) {
            mesBoutons[i] = new JButton(" ");
            mesBoutons1[i] = new JButton(" ");
            int cL = (int)(Math.random()*3);
            if(cL==1){
            	mesBoutons[i].setBackground(jaune);
                mesBoutonsColor[i]=("jaune");
            }else if(cL==2){
            	mesBoutons[i].setBackground(bleu);
                mesBoutonsColor[i]=("bleu");
            }else{
            mesBoutons[i].setBackground(violet);
            mesBoutonsColor[i]=("violet");
		}
            mesBoutons1[i].setBackground(jaune);
            mesBoutons1Color[i]=("jaune");
            mesBoutons1[i].addActionListener(cB);
		}
			submit.addActionListener(cB);
            JPanel pano = new JPanel();
            JPanel pano1 = new JPanel();
    	    JPanel pano2 = new JPanel();
    	    pano.setLayout(new BoxLayout(pano,BoxLayout.Y_AXIS));
    	    
    	    for (int i = 0; i < mesBoutons.length; i++) {
    			pano1.add(mesBoutons[i]);
    			pano2.add(mesBoutons1[i]);
    			 }
    		pano.add(pano1);
    		pano.add(pano2);
    		pano.add(submit);
		setContentPane(pano);} 
}
