package memory.bestmemorygames.memory;


import android.os.Handler;
import android.util.Log;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
        if(actualHandler == v.handlerCache) {
            int x1 = m.getCartesActu()[0];
            int y1 = m.getCartesActu()[1];
            int x2 = m.getCartesActu()[2];
            int y2 = m.getCartesActu()[3];

            v.cacherCarte(x1, y1);
            v.cacherCarte(x2, y2);
            v.retournement.jouer();
            m.retourneToDos();
            m.resetCartesActu();
            m.setInAction(false);
        }
    }
}