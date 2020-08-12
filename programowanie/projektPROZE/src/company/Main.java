package company;


import java.io.IOException;
import java.awt.EventQueue;
/**
 * Klasa odpowiedzialna za wywołanie głównej ramki oraz pobranie danych z pliku
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PropertiesLoad.loadNecessaryProps();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new MainFrame();
        });

    }
}
