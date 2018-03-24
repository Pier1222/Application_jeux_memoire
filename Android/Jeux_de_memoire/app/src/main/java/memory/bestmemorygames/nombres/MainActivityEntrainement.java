package memory.bestmemorygames.nombres;

import android.app.AlertDialog;
import android.content.DialogInterface;

import memory.bestmemorygames.R;

public class MainActivityEntrainement extends MainActivity {
    //Annuler la fin de partie
    public void beforeAlert() {
        alert();
    }

    public void alert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.LoseEntrainement));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.Title));
        builder.setPositiveButton(getString(R.string.reassayerEnt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                assigneCase(nombreDecase);
            }
        });
        builder.setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
    }
}
