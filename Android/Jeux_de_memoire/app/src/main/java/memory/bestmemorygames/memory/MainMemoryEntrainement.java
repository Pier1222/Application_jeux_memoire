package memory.bestmemorygames.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import memory.bestmemorygames.PageAccueilActivity;
import memory.bestmemorygames.R;

public class MainMemoryEntrainement extends MainMemory {

    private int nbErreurs = 0;

    public void resetnbErreurs() {
        nbErreurs = 0;
    }

    //Annule l'affichage des vies
    public void createVies() {}
    public void changeAffichageVies() {}
    public void derniereVieBarree() {}
    public void derniereViePasBarree() {}

    //Retire la possibilitée de perdre
    public void verifGameOver(int x1, int y1, int x2, int y2) {
        retournesMauvaisesCartes(x1, y1, x2, y2);
        nbErreurs++;
    }

    public void checkEnd() {
        if(m.ifAllCartesRevelees()) {
            toutesCartesRetournees.jouer();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setTitle(getString(R.string.pairesTrouvees))
                    .setMessage(getString(R.string.endEntrainementTexte1) + " " + nbErreurs + " " + getString(R.string.endEntrainementTexte2))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.rejouer), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainMemoryEntrainement.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), PageAccueilActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
