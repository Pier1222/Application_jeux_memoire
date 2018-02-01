package com.example.girardot.boulier;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.os.Handler;

public class VueTuto extends Vue {

    private static String TAG = "VueTuto"; //Permet de créer des messages de Debug plus comphréensible avec Log.d

    protected ImageView expectedClick;
    protected int phaseTuto;

    protected Handler handlerTuto;

    public void creerDialogTuto(int resId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(resId));
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.tutoTitre));
        builder.setPositiveButton(getString(R.string.tutoOk), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                phaseTuto++;
                Log.d(TAG, "Phase: " + phaseTuto);
                continueTuto();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        phaseTuto = 0;
        super.onCreate(savedInstanceState);
        expectedClick = null;
        continueTuto();
    }

    public void continueTuto() {
        switch (phaseTuto) {
            case 0:
                creerDialogTuto(R.string.tutoTexte1);
                //A voir pour mettre une bordure à une ImageView pour mettre en évidence un élément
                imagesBas[0].setPadding(-5, -5, -5, -5);
                imagesBas[0].setBackgroundColor(Color.RED);
                break;
            default:
                Log.d(TAG, "Phase " + phaseTuto + " inexistante");
                break;
        }
    }

    public void initControlTimer() { //Dans cette version, il ne lance pas la handlerDebut afin de pouvoir expliquer le jeu avant de commencer
        ct = new ControlTimerTuto(m, this);
    }

    //Partie contrôleur
    @Override
    public void onClick(View view) {
        System.out.println("Ce tuto est totalement vide !");
    }
}
