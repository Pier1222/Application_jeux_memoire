package memory.bestmemorygames.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import memory.bestmemorygames.PageAccueilActivity;
import memory.bestmemorygames.R;

public class MainMemory extends AppCompatActivity implements View.OnClickListener {

    protected int image101,image102,image103,image104,image105,image106;
    protected int[] images;

    protected ImageView[][] grilleCartes;
    protected LinearLayout hautGrilleCarte;
    protected TextView showScore;

    protected Son retournement;
    protected Son paire;
    protected Son pasPaire;
    protected Son toutesCartesRetournees;

    protected Handler handlerCache;
    protected ControlTimer ct;

    protected Model m;

    private static final String TAG = "MainMemory";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_memory);

        //Model.grandMemory();

        m = new Model();

        showScore =(TextView) findViewById(R.id.score);
        changeScore();

        hautGrilleCarte = (LinearLayout) findViewById(R.id.grilleCarte);
        frontOfCardsRessources();
        createGrilleCartes();

        retournement = new Son(R.raw.bip, this); //Son à changer
        paire = new Son(R.raw.correct, this);
        pasPaire = new Son(R.raw.wrong, this);
        toutesCartesRetournees = new Son(R.raw.applaudissement, this);

        handlerCache = new Handler();
        ct = new ControlTimer(m, this);
    }

    public void createGrilleCartes() {
        float poids = 1.0f;
        hautGrilleCarte.setWeightSum(Model.getNbCartesLigne());
        grilleCartes = new ImageView[Model.getNbCartesLigne()][Model.getNbCartesColonne()];
        LinearLayout[] lignes = new LinearLayout[Model.getNbCartesLigne()];
        for(int x = 0; x < Model.getNbCartesLigne(); x++) { //Rajoute des LinearLayout pour créer des lignes
            lignes[x] = new LinearLayout(getApplicationContext());
            lignes[x].setWeightSum(Model.getNbCartesColonne());
            lignes[x].setOrientation(LinearLayout.HORIZONTAL);
            hautGrilleCarte.addView(lignes[x]);
            for(int y = 0; y < Model.getNbCartesColonne(); y++) { //Met les images dans les LinearLayout
                grilleCartes[x][y] = new ImageView(getApplicationContext());
                grilleCartes[x][y].setImageResource(R.mipmap.ic_back);
                grilleCartes[x][y].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, poids);
                grilleCartes[x][y].setLayoutParams(params);
                grilleCartes[x][y].setOnClickListener(this);

                lignes[x].addView(grilleCartes[x][y]);
            }
        }
        //revelerCartes();
    }

    public void revelerCartes() {
        for(int x = 0; x < Model.getNbCartesLigne(); x++) {
            for(int y = 0; y < Model.getNbCartesColonne(); y++) {
                revelerCarte(x, y);
            }
        }
    }

    public void revelerCarte(int x, int y) {
        grilleCartes[x][y].setImageResource(images[m.getCartes()[x][y]]);
    }

    public void cacherCartes() {
        for(int x = 0; x < Model.getNbCartesLigne(); x++) {
            for(int y = 0; y < Model.getNbCartesColonne(); y++) {
                cacherCarte(x, y);
            }
        }
    }

    public void cacherCarte(int x, int y) {
        grilleCartes[x][y].setImageResource(R.mipmap.ic_back);
    }

    private void frontOfCardsRessources(){
        image101 = R.mipmap.ic_image101;
        image102 = R.mipmap.ic_image102;
        image103 = R.mipmap.ic_image103;
        image104 = R.mipmap.ic_image104;
        image105 = R.mipmap.ic_image105;
        image106 = R.mipmap.ic_image106;

        images = new int[]{image101, image102, image103, image104, image105, image106};
        if(images.length != Model.getNbImages()) {
            Log.e(TAG, "Le nombre d'images différentes dans le Model n'est pas correct");
        }
    }

    private void checkEnd(){
        if(m.ifAllCartesRevelees()){
            toutesCartesRetournees.jouer();
            final EditText name = new EditText(this);
            name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            name.setLayoutParams(lp);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMemory.this);
            alertDialogBuilder
                    .setMessage("Bravo vous avez gagné !!\n" +"Vous avez eu "+ m.getScore() + " points.\n"+"Votre nom :")
                    .setView(name)
                    .setCancelable(false)
                    .setPositiveButton("Rejouer", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainMemory.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Arrêter", new DialogInterface.OnClickListener() {
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

    private void doStuff(ImageView iv){
        int posX = 0;
        int posY = 0;

        for(int x = 0; x < Model.getNbCartesLigne(); x++) {
            for (int y = 0; y < Model.getNbCartesColonne(); y++) {
                if(iv == grilleCartes[x][y]) {
                    posX = x;
                    posY = y;
                    revelerCarte(x, y);
                }
            }
        }

        if(!m.getIsDos()[posX][posY]) //Il s'agit d'une carte déjà retourné
            return;

        Son ajouer = retournement;

        if(m.addCartesActu(posX, posY)) {
            if(m.isPaire()) {
                System.out.println("Yeah c'est une paire !");
                ajouer = paire;
                m.resetCartesActu();
                m.augmenteScore();
                changeScore();
                checkEnd();
            } else {
                System.out.println("SHOUT !");
                ajouer = pasPaire;
                m.setInAction(true);
                ct.start(handlerCache, 1500);
            }
        }
        ajouer.jouer();
    }

    public void changeScore() {
        showScore.setText(getString(R.string.debScore) + ": " + m.getScore());
    }


    //Partie contrôleur
    @Override
    public void onClick(View view) {
        if(m.isInAction()) {
            Log.d(TAG, "Ce n'est pas le moment pour agir !");
            return;
        }
        doStuff((ImageView) view);
    }
}
