package memory.bestmemorygames.nombres;


import android.os.Handler;
import memory.bestmemorygames.memory.Model;

public class ControlTimer implements Runnable {

    Model m;
    MainActivity v;
    Handler actualHandler; //Permet de savoir quel Handler à enclencher ce Runnable
    long nbMillisecondes; //Permet de savoir tous les combiens de temps il faut exécuter ce "Timer" de manière répété

    private static String TAG = "ControlTimer";


    public ControlTimer(Model m, MainActivity v) {
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
        if(actualHandler == v.handlerFin) {
            v.beforeAlert();
        }
    }
}