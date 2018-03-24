package memory.bestmemorygames.piano;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import memory.bestmemorygames.R;

public class VueEntrainement extends Vue {

    private final static String TAG = "VueEntrainement";

    public void creerDialogPerdu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.erreurEntrainement1) + " " + m.getNbTouchesReussies() + " " + getString(R.string.erreurEntrainement2));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.perduTitre));
        builder.setPositiveButton(getString(R.string.recommencerSequence), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Perdu");
                m.reinitPlaceSequence();
                etatEnCours();
                ctJoueTouche.start(handlerJoueTouche, 100);
            }
        });
        builder.setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Revenir Menu");
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
