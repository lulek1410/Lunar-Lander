package company;

import javax.swing.*;

/**
 * Klasa odpowiedzialna za utworzenie obiektów ImageIcon z danych ścieżek
 */
public class ImageFactory {

    /**
     * Metoda tworząca obiekt klasy ImageIcon z odpowiedniej ścieżki
     * @param image - nazwa obrazka z klasy Image
     * @return imageIcon - utworzony obiekt klasy ImageIcon z odpowiednim obrazkiem
     */
    public static ImageIcon createImage(Image image) {
        ImageIcon imageIcon = null;

        switch (image) {
            case MainMenu:
                imageIcon = new ImageIcon("Images/MainMenu.png");
                break;
            case Lander:
                imageIcon = new ImageIcon("Images/Lander.png");
                break;
            case Asteroid:
                imageIcon = new ImageIcon("Images/Asteroid.png");
                break;
            case Earth1:
                imageIcon = new ImageIcon("Images/EarthMoon.png");
                break;
            case Earth2:
                imageIcon = new ImageIcon("Images/EarthMoon2.png");
                break;
            case Mars1:
                imageIcon = new ImageIcon("Images/MarsMoon.png");
                break;
            case Mars2:
                imageIcon = new ImageIcon("Images/MarsMoon2.png");
                break;
            case Jupiter1:
                imageIcon = new ImageIcon("Images/JupiterMoon.png");
                break;
            case Jupiter2:
                imageIcon = new ImageIcon("Images/JupiterMoon2.png");
                break;
            case Saturn1:
                imageIcon = new ImageIcon("Images/SaturnMoon.png");
                break;
            case Saturn2:
                imageIcon = new ImageIcon("Images/SaturnMoon2.png");
                break;
            case Boom:
                imageIcon = new ImageIcon("Images/Boom.png");
                break;
            case Instruction:
                imageIcon = new ImageIcon("Images/InstructionScreen.png");
                break;
            default:
                break;
        }
        return imageIcon;
    }
}