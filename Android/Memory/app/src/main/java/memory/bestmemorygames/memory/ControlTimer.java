package memory.bestmemorygames.memory;


import android.os.Handler;

public class ControlTimer implements Runnable {

    Model m;
    MainMemory v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété

    private static String TAG = "ControlTimer";


    public ControlTimer(Model m, MainMemory v) {
        this.m = m;
        this.v = v;
        actualHandler = null;
        nbMillisecondes = 0;
    }

    public void start(Handler handler, long nbMillisecondes) {
        actualHandler = handler;
        this.nbMillisecondes = nbMillisecondes;
        prerun();
    }

    private void prerun() {
        actualHandler.postDelayed(this, nbMillisecondes);
    }

    public void run() {
        if(actualHandler == v.handlerClignotementCorrect || actualHandler == v.handlerClignotementWrong) {
            int x1 = m.getCartesActu()[0];
            int y1 = m.getCartesActu()[1];
            int x2 = m.getCartesActu()[2];
            int y2 = m.getCartesActu()[3];

            if (actualHandler == v.handlerClignotementWrong) {
                if(m.getNbClignotements()%2 == 0) {
                    v.revelerWrongCarte(x1, y1);
                    v.revelerWrongCarte(x2, y2);
                    v.derniereVieBarree();
                } else {
                    v.revelerCarte(x1, y1);
                    v.revelerCarte(x2, y2);
                    v.derniereViePasBarree();
                }

                m.augmenteClignotements();

                if(m.getNbClignotements() > 10) {
                    v.changeAffichageVies();
                    m.retourneToDos();
                    m.resetCartesActu();
                    m.resetClignotement();
                    if(m.getNbVies() > 0) {
                        v.cacherCarte(x1, y1);
                        v.cacherCarte(x2, y2);
                        v.retournement.jouer();
                        m.setInAction(false);
                    } else {
                        v.plusVies.jouer();
                        v.revelerCartes(); //On montre toutes les cartes car le joueur à perdu
                        start(v.handlerPerdue, 5000);
                    }
                } else
                    prerun();

            } else if (actualHandler == v.handlerClignotementCorrect) {
                if(m.getNbClignotements()%2 == 0) {
                    v.revelerCorrectCarte(x1, y1);
                    v.revelerCorrectCarte(x2, y2);
                } else {
                    v.revelerCarte(x1, y1);
                    v.revelerCarte(x2, y2);
                }

                m.augmenteClignotements();

                if(m.getNbClignotements() > 5) {
                    v.revelerCarte(x1, y1);
                    v.revelerCarte(x2, y2);
                    m.resetCartesActu();
                    m.resetClignotement();
                    v.checkEnd();
                    m.setInAction(false);
                } else
                    prerun();
            }
        } else if (actualHandler == v.handlerPerdue) {
            v.fenetreGameOver();
        }
    }
}