package company;

import javax.swing.*;
import java.awt.*;
/**
 * Klasa dostosowująca wygląd przycisków
 */
public class ButtonCustomizer {
    /** Tymczasowy przycisk na potrzeby klasy**/
    JButton button = new JButton();
    /** Kolor przycisków**/
    Color customizedColor;
    /** Rozmiar czcionki**/
    int customizedFont;
    /** Wartość boolean która określa czy dany przycisk ma być widoczny czy nie**/
    boolean isVisible;

    /**
     * Konstruktor przypisujący pobrane wartości do zmiennych stworzonych w klasie
     * @param visible - wartość określająca czy przycisk ma być widoczny
     * @param color - kolor jaki chcemy nadać przyciskowi
     * @param font - rozmiar czcionki naszego przycisku
     */
    public ButtonCustomizer(boolean visible, Color color, int font){
        customizedColor = color;
        customizedFont = font;
        isVisible = visible;
        customizer(button);
    }

    /**
     * Odpowiada za wygląd, czcionkę i kolor przycisków
     * @param button - przycisk którego wygląd ma zostać zmodyfikowany
     */
    public void customizer(JButton button){
        button.setFont(Fonts.getFont(customizedFont));
        button.setForeground(customizedColor);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setVisible(isVisible);
    }
}
