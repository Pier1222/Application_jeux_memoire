public class ControlGroup {

    public ControlGroup(Model m) {
        Vue v = new Vue(m);
        ControlButton cb = new ControlButton(m, v);
        //Controleurs
        v.display();
        //v.creerDialogTuto("Veuillez appuyer sur la deuxième touche noire.");
    }
}