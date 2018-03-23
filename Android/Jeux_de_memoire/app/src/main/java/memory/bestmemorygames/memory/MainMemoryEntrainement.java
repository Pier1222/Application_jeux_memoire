package memory.bestmemorygames.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import memory.bestmemorygames.PageAccueilActivity;
import memory.bestmemorygames.R;

public class MainMemoryEntrainement extends MainMemory implements View.OnClickListener {

    //Annule l'affichage des vies
    public void createVies() {}
    public void changeAffichageVies() {}
    public void derniereVieBarree() {}
    public void derniereViePasBarree() {}

    //Retire la possibilitée de perdre
    public void verifGameOver(int x1, int y1, int x2, int y2) {
        retournesMauvaisesCartes(x1, y1, x2, y2);
    }

}
