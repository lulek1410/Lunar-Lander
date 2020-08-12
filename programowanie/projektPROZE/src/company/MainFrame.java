package company;

import javax.swing.*;

/**
 * Klasa odpowiedzialna za utworzenie okna głównego i stworzenie w nim okna głównego menu
 */
public class MainFrame extends JFrame {
    /**
     * Konstruktor klasy wywołujący metodę initalizeLayout
     */
    public MainFrame() {
        initializeLayout();
    }

    /** Metoda tworząca obiekt głównego menu, tytułuje okno, oraz ustawia kilka podstawowych własciowści gry:
     * możliwość zmiany rozmiaru, zamykanie aplikacji
     * */
    private void initializeLayout() {
        setTitle("Lunar Lander");
        setIconImage(ImageFactory.createImage(Image.Lander).getImage());
        add(new ConnectionWelcomeMenu());
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }
}

