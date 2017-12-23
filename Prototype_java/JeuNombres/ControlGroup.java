public class ControlGroup {

    private Model model;
    private Vue vue;
    private ControlButton controlButton;
    private ControlTimer controlTimer;

    public ControlGroup(Model model) {
        this.model = model;
        this.vue = new Vue(model);
        this.controlButton = new ControlButton(model, vue);
        this.controlTimer = new ControlTimer(model, vue);
    }
}
