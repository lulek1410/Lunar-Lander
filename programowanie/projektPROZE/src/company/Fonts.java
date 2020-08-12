package company;

import java.awt.*;
import java.io.File;
/**
 * Klasa dostosowująca czcionkę użytą w grze
 */
public class Fonts {
    /**Czcionka używana w razie niepowodzenia pobrania czcionki z pliku**/
    private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 24);

    /**Konstruktor klasy*/
    public Fonts(){ }

    /**
     * Metoda tworząca czcionkę z pliku
     * @param size - rozmiar czcionki
     * @return font - czcionka
     */
    public static Font getFont(int size) {
        Font font = null;
        try {
            String fName = "uni05_53.ttf";
            File fontFile = new File(fName);
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            font = font.deriveFont(Font.PLAIN,size);
        } catch (Exception ex) {
            font = SERIF_FONT;
        }
        return font;
    }
}
