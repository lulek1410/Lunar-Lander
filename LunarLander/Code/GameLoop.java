package company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Klasa odpowiedzialna za obsługę zdarzeń gry
 */
public class GameLoop implements ActionListener{

    /** obiekt klasy Level*/
    private Level level;

    /**
     * Konstruktor przypisujący pobrany obiekt do obiektu stworzonego w tej klasie
     * @param level - obiekt klasy Level (bieżący poziom)
     */
    public GameLoop(Level level) {
        this.level = level;
    }

    /**
     * Metoda wywołująca pojedynczą pętlę gry
     * @param a - obiekt klasy ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        this.level.doOneLoop();
    }
}
