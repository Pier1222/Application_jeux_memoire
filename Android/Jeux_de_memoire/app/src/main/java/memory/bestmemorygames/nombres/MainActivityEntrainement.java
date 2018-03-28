package memory.bestmemorygames.nombres;

import android.app.AlertDialog;
import android.content.DialogInterface;

import memory.bestmemorygames.R;

public class MainActivityEntrainement extends MainActivity {

    private int nbCaseActu = 0;

    public void resetNbCasesActu() {
        nbCaseActu = 0;
    }

    public void correct() {
        super.correct();
        nbCaseActu++;
    }

    //Annuler la fin de partie
    public void beforeAlert() {
        resetNbCasesActu();
        alert();
    }

    public void alert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.LoseEntrainement1) + " " + nbCaseActu + getString(R.string.LoseEntrainement2));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.Title));
        builder.setPositiveButton(getString(R.string.reassayerEnt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearBoard();
                caseAcocher.clear();
                assigneCase(nombreDecase);
                changeText();
                startTimer();
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
