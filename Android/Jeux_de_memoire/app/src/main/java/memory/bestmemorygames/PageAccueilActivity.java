package memory.bestmemorygames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import memory.bestmemorygames.boulier.Vue;
import memory.bestmemorygames.loto.VueLoto;
import memory.bestmemorygames.memory.MainMemory;
import memory.bestmemorygames.memory.MainMemoryEntrainement;
import memory.bestmemorygames.score2.MainScore;

public class PageAccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Button jeu memory
        final ImageButton buttonMemory = (ImageButton) findViewById(R.id.btn_memory);
        buttonMemory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {getString(R.string.joueraccueil), getString(R.string.entrainementaccueil), getString(R.string.difficulte), getString(R.string.regles)};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.memory.MainMemory.class);
                                startActivity(intent);
                                //finish(); //C'est mieux de laisser cette instruction empêchant l'utilisateur de quitter la partie ou de la commenter quitte à ce que l'utilise quitte la partie en faisant un retour arrière sans forcément le faire exprès ?
                                break;
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.memory.MainMemoryEntrainement.class);
                                startActivity(intent1);
                                //finish();
                                break;
                            case 2: Intent intent2 = new Intent(getApplicationContext(), memory.bestmemorygames.difficulte.DifficulteMemory.class);
                                startActivity(intent2);
                                //finish();
                                break;
                            case 3: Intent intent3 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AideMemory.class);
                                startActivity(intent3);
                                //finish(); //Par contre pour les aides, c'est clairement plus pratique de retirer le finish
                                break;
                        }
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu piano
        ImageButton buttonPiano = (ImageButton) findViewById(R.id.btn_piano);
        buttonPiano.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {getString(R.string.joueraccueil), getString(R.string.entrainementaccueil), getString(R.string.difficulte), getString(R.string.regles)};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.piano.Vue.class);
                                startActivity(intent);
                                //finish();
                                break;
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.piano.VueEntrainement.class);
                                startActivity(intent1);
                                //finish();
                                break;
                            case 2: Intent intent2 = new Intent(getApplicationContext(),memory.bestmemorygames.difficulte.DifficultePiano.class);
                                startActivity(intent2);
                                //finish();
                                break;
                            case 3: Intent intent3 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AidePiano.class);
                                startActivity(intent3);
                                //finish();
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu boulier
        ImageButton buttonBoulier = (ImageButton) findViewById(R.id.btn_boulier);
        buttonBoulier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {getString(R.string.joueraccueil), getString(R.string.entrainementaccueil), getString(R.string.difficulte), getString(R.string.regles)};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.boulier.Vue.class);
                                startActivity(intent);
                                //finish();
                                break;
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.boulier.VueEntrainement.class);
                                startActivity(intent1);
                                //finish();
                                break;
                            case 2: Intent intent2 = new Intent(getApplicationContext(), memory.bestmemorygames.difficulte.DifficulteBoulier.class);
                                startActivity(intent2);
                                //finish();
                                break;
                            case 3: Intent intent3 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AideBoulier.class);
                                startActivity(intent3);
                                //finish();
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu Loto
        ImageButton buttonLoto = (ImageButton) findViewById(R.id.btn_loto);
        buttonLoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {getString(R.string.joueraccueil), getString(R.string.entrainementaccueil), getString(R.string.difficulte), getString(R.string.regles)};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.loto.VueLoto.class);
                                startActivity(intent);
                                //finish();
                                break;
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.loto.VueLotoEntrainement.class);
                                startActivity(intent1);
                                //finish();
                                break;
                            case 2: Intent intent2 = new Intent(getApplicationContext(), memory.bestmemorygames.difficulte.DifficulteLoto.class);
                                startActivity(intent2);
                                //finish();
                                break;
                            case 3: Intent intent3 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AideLoto.class);
                                startActivity(intent3);
                                //finish();
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        ImageButton buttonNombres = (ImageButton) findViewById(R.id.btn_nombre);
        buttonNombres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PageAccueilActivity.this);
                alertDialogBuilder.setTitle("Choississez ce que vous voulez faire :");

                // add a list
                String[] choix = {getString(R.string.joueraccueil), getString(R.string.entrainementaccueil), getString(R.string.difficulte), getString(R.string.regles)};
                alertDialogBuilder.setItems(choix, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: Intent intent = new Intent(getApplicationContext(), memory.bestmemorygames.nombres.MainActivity.class);
                                startActivity(intent);
                                //finish();
                                break;
                            case 1: Intent intent1 = new Intent(getApplicationContext(), memory.bestmemorygames.nombres.MainActivityEntrainement.class);
                                startActivity(intent1);
                                //finish();
                                break;
                            case 2: Intent intent2 = new Intent(getApplicationContext(), memory.bestmemorygames.difficulte.DifficulteNombre.class);
                                startActivity(intent2);
                                //finish();
                                break;
                            case 3: Intent intent3 = new Intent(getApplicationContext(),memory.bestmemorygames.aide.AideNombres.class);
                                startActivity(intent3);
                                //finish();
                                break;
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //Button jeu score
        ImageButton buttonScore = (ImageButton) findViewById(R.id.btn_score);
        buttonScore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainScore.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.page_accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ParametreActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}