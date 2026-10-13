import javax.swing.SwingUtilities;

public class FitZoneApp {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });

    }
}