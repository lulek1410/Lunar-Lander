package company;

import javax.swing.*;
import java.awt.*;

public class TextFieldCustomizer {
    /** Tymczasowy textLabel na potrzeby klasy**/
    JTextField textField = new JTextField();
    /** Kolor przycisków**/
    Color customizedColor;
    /** Rozmiar czcionki**/
    int customizedFont;

    /**
     * Konstruktor przypisujący pobrane wartości do zmiennych stworzonych w klasie
     * @param color - kolor jaki chcemy nadać czcionce w polu tekstowym
     * @param font - rozmiar czcionki w polu tekstowym
     */
    public TextFieldCustomizer(Color color, int font){
        customizedColor = color;
        customizedFont = font;
        customizer(textField);
    }

    /**
     * Odpowiada za wygląd, czcionkę i kolor przycisków
     * @param textField - pole tekstowe którego wygląd ma zostać zmodyfikowany
     */
    public void customizer(JTextField textField){
        textField.setBackground(Color.black);
        textField.setFont(Fonts.getFont(24));
        textField.setForeground(customizedColor);
    }
}
