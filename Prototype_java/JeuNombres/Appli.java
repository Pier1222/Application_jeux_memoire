public class Appli {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            ControlGroup controler = new ControlGroup(model);
        });
    }
}
