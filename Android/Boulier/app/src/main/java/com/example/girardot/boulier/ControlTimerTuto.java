package com.example.girardot.boulier;


import android.os.Handler;

public class ControlTimerTuto extends ControlTimer implements Runnable {

    protected VueTuto v;

    public ControlTimerTuto(Model m, VueTuto v) {
        super(m, v);
    }

    public void run() { //Pour rendre l'application plus protéger, il aurait été souhaitable que run soit private mais c'est impossible car elle est implémenter
        super.run();
        if(actualHandler == v.handlerTuto) {
            switch (v.phaseTuto) {
                case 1:
                    break;
            }
        }
    }

    private void clignotement() {

    }
}
