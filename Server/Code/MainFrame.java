package company;

import javax.swing.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    /**
     * Konstruktor klasy wywołujący metodę initalizeLayout
     */
    public MainFrame() throws IOException {
        initializeLayout();
    }

    /** Metoda tworząca obiekt głównego menu, tytułuje okno, oraz ustawia kilka podstawowych własciowści gry:
     * możliwość zmiany rozmiaru, zamykanie aplikacji
     * */
    private void initializeLayout() throws IOException {
        setTitle("Lunar Lander Server");
        add(new ServerScreen());
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
