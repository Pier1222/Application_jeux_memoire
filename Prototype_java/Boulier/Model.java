public class Model {

    private Ligne haut;
    private Ligne bas;

    private static int NB_TENTATIVES_MAX = 3;
    private int nbTentatives;

    private boolean inAction;

    public Model() {
        haut = new Ligne(true);
        bas = new Ligne(false);
        printLignes();
        nbTentatives = NB_TENTATIVES_MAX;
        inAction = false;
    }

    public void printLignes() {
        System.out.print("Haut: "); haut.print();
        System.out.print("Bas: "); bas.print();
    }

    public Ligne getHaut() {
        return haut;
    }

    public void setHaut(Ligne haut) {
        this.haut = haut;
    }

    public Ligne getBas() {
        return bas;
    }

    public void setBas(Ligne bas) {
        this.bas = bas;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }
}

