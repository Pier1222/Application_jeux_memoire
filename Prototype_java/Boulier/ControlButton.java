import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton extends Control implements ActionListener {

    public ControlButton(Model m, Vue v){
         super(m, v);
         v.setControlButton(this);
    }
	public void actionPerformed(ActionEvent e) {
		System.out.println("ACTION !");
		// TODO Auto-generated method stub
		/*Color jaune = new Color(255,255,0);
		Color bleu = new Color(0,211,211);
		Color violet = new Color(211,0,211);
		Object s = e.getSource();
		for(int i = 0; i < Ligne.getNbBoules() ;i++){
			if(chance > 0 && win==false){
				if(s== v.boutonsBas[i]){
					if(v.mesBoutons1Color[i]=="jaune"){
					v.boutonsBas[i].setBackground(bleu);
					v.mesBoutons1Color[i]="bleu";
					}else if(v.mesBoutons1Color[i]=="violet"){
						v.boutonsBas[i].setBackground(jaune);
						v.mesBoutons1Color[i]="jaune";
					}else if(v.mesBoutons1Color[i]=="bleu"){
						v.boutonsBas[i].setBackground(violet);
						v.mesBoutons1Color[i]="violet";
					}
				}
			}
		}
	
		if(s== v.submit){//verifwin
			if(chance >0 && v.mesBoutons1Color[0]== v.mesBoutonsColor[0] &&
				v.mesBoutons1Color[1]== v.mesBoutonsColor[1]	&&
				v.mesBoutons1Color[2]== v.mesBoutonsColor[2]&&
				v.mesBoutons1Color[3]== v.mesBoutonsColor[3]&&
				v.mesBoutons1Color[4]== v.mesBoutonsColor[4]&&
				v.mesBoutons1Color[5]== v.mesBoutonsColor[5]&&
				v.mesBoutons1Color[6]== v.mesBoutonsColor[6]&&
				v.mesBoutons1Color[7]== v.mesBoutonsColor[7]){
			System.out.println("gagner");
			win=true;
			}else if(chance >0){

				System.out.println("chance-1");
				chance -=1;
				for(int i = 0; i < Ligne.getNbBoules() ;i++){
					if(v.mesBoutons1Color[i]== v.mesBoutonsColor[i]){//dit au joueur ce qui est juste ou faux
						System.out.println("case "+(i+1)+" juste");
					}else{
						System.out.println("case "+(i+1)+" fausse");
					}
				}
				if(chance <=0){
					System.out.println("perdu");
				}
			}
		}*/
	}
}

