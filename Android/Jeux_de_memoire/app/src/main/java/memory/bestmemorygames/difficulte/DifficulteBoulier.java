package memory.bestmemorygames.difficulte;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import memory.bestmemorygames.R;
import memory.bestmemorygames.boulier.Ligne;
import memory.bestmemorygames.loto.Grille;

public class DifficulteBoulier extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulte_boulier);
        final Button buttonFacile = (Button) findViewById(R.id.facile);
        buttonFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ligne.setNbBoules(5);
                finish();
            }
        });
        final Button buttonMoyen = (Button) findViewById(R.id.moyen);
        buttonMoyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ligne.setNbBoules(7);
                finish();
            }
        });
        final Button buttonDifficile = (Button) findViewById(R.id.difficile);
        buttonDifficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ligne.setNbBoules(10);
                finish();
            }
        });
        final Button buttonAnnuler = (Button) findViewById(R.id.annuler);
        buttonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
