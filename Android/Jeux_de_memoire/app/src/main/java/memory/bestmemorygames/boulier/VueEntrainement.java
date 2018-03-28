package memory.bestmemorygames.boulier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import memory.bestmemorygames.R;

public class VueEntrainement extends Vue {

    private static final String TAG = "VueEntrainement";

    private int nbTentativesUtilisees = 0;

    private void resetnbTentativesUtilisees() {
        nbTentativesUtilisees = 0;
    }

    //On change le texte des tentatives car elles sont illimités maintenant
    public void changeTentativesRestantes() {
        if(m.getNbTentatives() == Model.getNbTentativesMax()) //Si on est au début de la partie
            tentativesRestantes.setText(getString(R.string.tentativeEnt));
        else {
            nbTentativesUtilisees++;
            tentativesRestantes.setText(nbTentativesUtilisees + " " + getString(R.string.notLastTentativeEnt));
        }
    }

    //Empêche l'utilisateur de perdre
    public void verifGameOver() {
        changeTentativesRestantes();
        tentativesRestantes.setVisibility(View.VISIBLE);
        ct.start(handlerFinEssaie, 5000); //Ce Timer ne s'enclenche que si la partie n'est pas finie
    }

    //Au cas où certains ont l'idée de profiter des tentatives illimités pour avoir un très mauvais score
    public void changeScore() {
        if(m.getScore() > -100)
            showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
        else
            showScore.setText(getString(R.string.easterEgg));
    }

    public void creerDialogSequenceComplete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.sequenceCompleteTexte1) +
                "\n" + getString(R.string.scoreFinal) + ": " + m.getScore() + "." +
                "\n" + getString(R.string.sequenceCompleteTexte2) + ": " + (nbTentativesUtilisees) + ".");
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.sequenceCompleteTitre));
        builder.setPositiveButton(getString(R.string.rejouer), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Séquence complète");
                resetnbTentativesUtilisees();
                reset();
            }
        });
        builder.setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Revenir menu");
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
