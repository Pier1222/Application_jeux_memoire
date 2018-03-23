package memory.bestmemorygames.difficulte;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import memory.bestmemorygames.R;
import memory.bestmemorygames.memory.Model;

public class DifficulteMemory extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulte_memory);
        final Button buttonClassique = (Button) findViewById(R.id.classique);
        buttonClassique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.classiqueMemory();
                finish();
            }
        });
        final Button buttonFacile = (Button) findViewById(R.id.facile);
        buttonFacile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.petitMemory();
                finish();
            }
        });
        final Button buttonMoyen = (Button) findViewById(R.id.moyen);
        buttonMoyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.moyenMemory();
                finish();
            }
        });
        final Button buttonDifficile = (Button) findViewById(R.id.difficile);
        buttonDifficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.grandMemory();
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
