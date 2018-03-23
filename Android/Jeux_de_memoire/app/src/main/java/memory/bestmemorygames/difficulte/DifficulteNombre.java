package memory.bestmemorygames.difficulte;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import memory.bestmemorygames.R;
import memory.bestmemorygames.loto.Grille;
import memory.bestmemorygames.nombres.MainActivity;

public class DifficulteNombre extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulte_nombre);
        final Button buttonFacile = (Button) findViewById(R.id.facile);
        buttonFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.petitNombre();
                finish();
            }
        });
        final Button buttonMoyen = (Button) findViewById(R.id.moyen);
        buttonMoyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.moyenNombre();
                finish();
            }
        });
        final Button buttonDifficile = (Button) findViewById(R.id.difficile);
        buttonDifficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.grandNombre();
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
