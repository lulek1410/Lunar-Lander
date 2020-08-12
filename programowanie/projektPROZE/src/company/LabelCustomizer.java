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
    /** Rozmiar czcionki**/
    int customizedFont;

    /**
     * Konstruktor przypisujący pobrane wartości do zmiennych stworzonych w klasie
     * @param color - kolor jaki chcemy nadać napisowi
     * @param font - rozmiar czcionki naszego napisu
     */
    public LabelCustomizer(Color color, int font){
        customizedColor = color;
        customizedFont = font;
        customizer(label);
    }

    /**
     *Odpowiada za wygląd, czcionkę i kolor napisów
     *@param label - napis którego wygląd ma zostać zmodyfikowany
     */
    public void customizer(JLabel label){
        label.setBackground(Color.black);
        label.setFont(Fonts.getFont(customizedFont));
        label.setForeground(customizedColor);
    }
}
