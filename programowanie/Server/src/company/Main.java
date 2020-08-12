package company;

import java.awt.*;
import java.io.IOException;


/**
 * Klasa odpowiedzialna za wywołanie głównej ramki
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new MainFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
