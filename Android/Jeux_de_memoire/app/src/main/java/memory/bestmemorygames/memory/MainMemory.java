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

    //Images normales
    protected int image101,image102,image103,image104,image105,image106;
    protected int[] images;

    //Images entourées de vert
    protected int image101C,image102C,image103C,image104C,image105C,image106C;
    protected int[] imagesCorrect;

    //Images entrourées de rouge
    protected int image101W,image102W,image103W,image104W,image105W,image106W;
    protected int[] imagesWrong;

    protected ImageView[][] grilleCartes;
    protected LinearLayout hautGrilleCarte;
    protected TextView showScore;

    protected ImageView[] imageVies;
    protected LinearLayout vies;

    protected Son retournement;
    protected Son paire;
    protected Son pasPaire;
    protected Son toutesCartesRetournees;
    protected Son plusVies;

    protected Handler handlerClignotementCorrect;
    protected Handler handlerClignotementWrong;
    protected Handler handlerPerdue;
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

        vies = findViewById(R.id.vies);
        createVies();
        changeAffichageVies();

        retournement = new Son(R.raw.carte, this); //Son à changer
        paire = new Son(R.raw.correct, this);
        pasPaire = new Son(R.raw.wrong, this);
        toutesCartesRetournees = new Son(R.raw.applaudissement, this);
        plusVies = new Son(R.raw.erreur2, this);

        handlerClignotementCorrect = new Handler();
        handlerClignotementWrong = new Handler();
        handlerPerdue = new Handler();
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

    public void createVies() {
        imageVies = new ImageView[Model.getNbViesMax()];
        for(int i = 0; i < Model.getNbViesMax(); i++) {
            imageVies[i] = new ImageView(getApplicationContext());
            vies.addView(imageVies[i]);
        }
    }

    public void changeAffichageVies() {
        for(int i = 0; i < m.getNbVies(); i++) { //Vies actuelles
            imageVies[i].setImageResource(R.drawable.vietete);
        }
        for(int i = m.getNbVies(); i < Model.getNbViesMax(); i++) { //Vies perdues
            imageVies[i].setImageResource(R.drawable.vieteteperdue);
        }
    }

    public void derniereVieBarree() {
        imageVies[m.getNbVies()].setImageResource(R.drawable.vieteteperdue);
    }

    public void derniereViePasBarree() {
        imageVies[m.getNbVies()].setImageResource(R.drawable.vietete);
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

    public void revelerCorrectCarte(int x, int y) {
        grilleCartes[x][y].setImageResource(imagesCorrect[m.getCartes()[x][y]]);
    }

    public void revelerWrongCarte(int x, int y) {
        grilleCartes[x][y].setImageResource(imagesWrong[m.getCartes()[x][y]]);
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


        image101C = R.mipmap.ic_image101_c;
        image102C = R.mipmap.ic_image102_c;
        image103C = R.mipmap.ic_image103_c;
        image104C = R.mipmap.ic_image104_c;
        image105C = R.mipmap.ic_image105_c;
        image106C = R.mipmap.ic_image106_c;

        imagesCorrect = new int[]{image101C, image102C, image103C, image104C, image105C, image106C};


        image101W = R.mipmap.ic_image101_w;
        image102W = R.mipmap.ic_image102_w;
        image103W = R.mipmap.ic_image103_w;
        image104W = R.mipmap.ic_image104_w;
        image105W = R.mipmap.ic_image105_w;
        image106W = R.mipmap.ic_image106_w;

        imagesWrong = new int[]{image101W, image102W, image103W, image104W, image105W, image106W};
    }

    public void checkEnd(){
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
                    .setMessage(getString(R.string.cartesRevelees) + "\n" + getString(R.string.points1) + " " + m.getScore() + " " + getString(R.string.points2) + "\n" + getString(R.string.demandeNom))
                    .setTitle(getString(R.string.pairesTrouvees))
                    .setView(name)
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.rejouer), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), MainMemory.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
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

    public void verifGameOver(int x1, int y1, int x2, int y2) {
        if(m.getNbVies() > 0) {
            retournesMauvaisesCartes(x1, y1, x2, y2);
        } else {
            plusVies.jouer();
            revelerCartes(); //On montre toutes les cartes car le joueur à perdu
            ct.start(handlerPerdue, 5000);
        }
    }

    public void retournesMauvaisesCartes(int x1, int y1, int x2, int y2) {
        cacherCarte(x1, y1);
        cacherCarte(x2, y2);
        retournement.jouer();
        m.setInAction(false);
    }

    public void fenetreGameOver() {
        final EditText name = new EditText(this);
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        name.setLayoutParams(lp);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMemory.this);
        alertDialogBuilder
                .setMessage(getString(R.string.manqueVies) + "\n" + getString(R.string.points1) + " " + m.getScore() + " " + getString(R.string.points2) + "\n" + getString(R.string.demandeNom))
                .setView(name)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.rejouer), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), MainMemory.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.revenir), new DialogInterface.OnClickListener() {
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
            m.setInAction(true);
            if(m.isPaire()) {
                Log.d(TAG, "Yeah c'est une paire !");
                ajouer = paire;
                m.augmenteScore();
                changeScore();
                ct.start(handlerClignotementCorrect, 150);
            } else {
                Log.d(TAG, "Non, ce n'est pas une paire !");
                ajouer = pasPaire;
                m.perdVie();
                ct.start(handlerClignotementWrong, 150);
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
