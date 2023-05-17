package Solution;

import util.SwingUtils;

import java.util.Locale;

public class Main {
    public static void winMain() {
        //SwingUtils.setLookAndFeelByName("Windows");
        Locale.setDefault(Locale.ROOT);
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));
    }

    public static void main(String[] args) {
        winMain();
    }

}
