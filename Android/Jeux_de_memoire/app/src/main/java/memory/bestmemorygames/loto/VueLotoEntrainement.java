package memory.bestmemorygames.loto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import memory.bestmemorygames.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VueLotoEntrainement extends VueLoto {

    private static final String TAG = "VueLototEntrainement";
    //Annule l'affichage des vies
    public void createVies() {}
    public void changeAffichageVies() {}

    //Retire la possibilitée de perdre
    public void verifEtEnclencheFinDePartie() {}

    public void creerDialogGrilleComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.completTexte));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.completTitre));
        builder.setPositiveButton(getString(R.string.completOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "GrileComplete");
                nouvelleGrille();
            }
        });
        builder.setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
