package company;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa dostosowująca wygląd napisów
 */
public class LabelCustomizer {
    /** Tymczasowy napis na potrzeby klasy**/
    JLabel label = new JLabel();
    /** Kolor napisów**/
    Color customizedColor;


    /**
     * Konstruktor przypisujący pobrane wartości do zmiennych stworzonych w klasie
     * @param color - kolor jaki chcemy nadać napisowi
     */

    public LabelCustomizer(Color color){
        customizedColor = color;
        customizer(label);
    }

    /**
     *Odpowiada za wygląd, czcionkę i kolor napisów
     *@param label - napis którego wygląd ma zostać zmodyfikowany
     */
    public void customizer(JLabel label){
        label.setBackground(Color.black);
        label.setFont(new Font("Menlo", Font.PLAIN, 14));

        label.setForeground(customizedColor);

    }
}
