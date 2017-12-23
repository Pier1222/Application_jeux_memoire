import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;

public class ControlButton extends JFrame  implements ActionListener {
protected static Fenetre fen;
	private int Chance=3;
	boolean win=false;
	public ControlButton(){}
    public ControlButton(Fenetre f){
         fen=f;
    }
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Color jaune = new Color(255,255,0);
		Color bleu = new Color(0,211,211);
		Color violet = new Color(211,0,211);
		Object s = e.getSource();
		for(int i = 0; i<8;i++){
			if(Chance >0 && win==false){
				if(s==fen.mesBoutons1[i]){
					if(fen.mesBoutons1Color[i]=="jaune"){
					fen.mesBoutons1[i].setBackground(bleu);
					fen.mesBoutons1Color[i]="bleu";
					}else if(fen.mesBoutons1Color[i]=="violet"){
						fen.mesBoutons1[i].setBackground(jaune);
						fen.mesBoutons1Color[i]="jaune";
					}else if(fen.mesBoutons1Color[i]=="bleu"){
						fen.mesBoutons1[i].setBackground(violet);
						fen.mesBoutons1Color[i]="violet";
					}
				}
			}
		}
	
		if(s==fen.submit){//verifwin
			if(Chance>0 && fen.mesBoutons1Color[0]==fen.mesBoutonsColor[0] &&
				fen.mesBoutons1Color[1]==fen.mesBoutonsColor[1]	&&	
				fen.mesBoutons1Color[2]==fen.mesBoutonsColor[2]&&
				fen.mesBoutons1Color[3]==fen.mesBoutonsColor[3]&&
				fen.mesBoutons1Color[4]==fen.mesBoutonsColor[4]&&
				fen.mesBoutons1Color[5]==fen.mesBoutonsColor[5]&&
				fen.mesBoutons1Color[6]==fen.mesBoutonsColor[6]&&
				fen.mesBoutons1Color[7]==fen.mesBoutonsColor[7]){
			System.out.println("gagner");
			win=true;
			}else if(Chance>0){

				System.out.println("chance-1 (nombre de tentatives pour réussir à compléter la séquence à décrémenter)");
				Chance-=1;
				for(int i = 0; i<8;i++){
					if(fen.mesBoutons1Color[i]==fen.mesBoutonsColor[i]){//dit au joueur ce qui est juste ou faux
						System.out.println("case "+(i+1)+" juste");
					}else{
						System.out.println("case "+(i+1)+" fausse");
					}
				}
				if(Chance<=0){
					System.out.println("perdu");
				}
			}
		}
	}
}

