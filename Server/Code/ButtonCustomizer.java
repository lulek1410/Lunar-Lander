package company;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa dostosowująca wygląd przycisków
 */
public class ButtonCustomizer {
    /** Tymczasowy przycisk na potrzeby klasy**/
    JButton button = new JButton();

    /**
     * Konstruktor przypisujący pobrane wartości do zmiennych stworzonych w klasie
     */
    public ButtonCustomizer(){
        customizer(button);
    }

    /**
     * Odpowiada za wygląd, czcionkę i kolor przycisków
     * @param button - przycisk którego wygląd ma zostać zmodyfikowany
     */
    public void customizer(JButton button){
        button.setFont(new Font("Menlo", Font.PLAIN, 14));
        button.setForeground(Color.white);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

    }
}
