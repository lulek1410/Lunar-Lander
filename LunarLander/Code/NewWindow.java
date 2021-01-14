package company;

import javax.swing.*;
import java.awt.*;
/**
 * Klasa odpowiedzialna za usunięcie elementów danego okna w celu wczytania nowego
 */
public class NewWindow extends JPanel {

    public NewWindow() {
    }
    /**
     * Metoda ustawiająca nowy layout okna i rozciągająca JPanel na cały ekran
     * @return gbc - obiekt klasy GridBagConstraints
     */
    public GridBagConstraints buttonsClickedBehaviour() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Metoda czyszcząca i usuwająca wszystkie elementy z panelu
     * @param panel - panel który ma zostać wyczyszczony
     */
    public void layoutMaker(JPanel panel) {
        panel.removeAll();
        panel.repaint();
        panel.revalidate();
        panel.setLayout(new GridBagLayout());
    }
}