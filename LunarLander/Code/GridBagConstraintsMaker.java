package company;

import java.awt.*;

/**
 * Klasa odpowiedzialna za graficzne ustawianie przycisków
 */
public class GridBagConstraintsMaker {

    /** Obiekt klasy GridBagConstraints**/
    GridBagConstraints gbc = new GridBagConstraints();

    public GridBagConstraintsMaker(){};

    /**
     * Metoda ustawiająca obiekty w layoucie  wg podanych wartości
     * @param gridX - wartość przypisywana polu gridx w obiekcie klasy GridBagConstraints
     * @param gridY - wartość przypisywana polu gridy w obiekcie klasy GridBagConstraints
     * @param weightX - wartość przypisywana polu weightx w obiekcie klasy GridBagConstraints
     * @param weightY - wartość przypisywana polu weighty w obiekcie klasy GridBagConstraints
     * @param gridWidth - wartość przypisywana polu gridwidth w obiekcie klasy GridBagConstraints
     * @param whichDirection - wartość przekazywana do metody anchorMaker
     * @return gbc - obiekt klasy GridBagConstraints
     */
    public GridBagConstraints gbcCustomize(int gridX, int gridY, double weightX, double weightY, int gridWidth, String whichDirection){
        gbc.gridwidth = gridWidth;
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        gbc.insets = new Insets(15, 15, 15, 15);
        anchorMaker(whichDirection);
        return gbc;
    }
    /**
     * Metoda ustawiająca metode anchor na tę zadaną przez użytkownika
     * @param whichDirection - parametr przekazywany do metody anchor w obiekcie klasy GridBagConstraints
     */
    private void anchorMaker(String whichDirection){
        switch(whichDirection){
            case "LAST_LINE_END": gbc.anchor = GridBagConstraints.LAST_LINE_END;
                break;
            case "LAST_LINE_START": gbc.anchor = GridBagConstraints.LAST_LINE_START;
                break;
            case "CENTER": gbc.anchor = GridBagConstraints.CENTER;
                break;
            case "FIRST_LINE_END": gbc.anchor = GridBagConstraints.FIRST_LINE_END;
                break;
            case "SOUTH": gbc.anchor=GridBagConstraints.SOUTH;
                break;
            case "NORTH": gbc.anchor = GridBagConstraints.NORTH;
                break;
            case "FIRST_LINE_START": gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                break;
            case "NORTHWEST": gbc.anchor = GridBagConstraints.NORTHWEST;
                break;
            case "SOUTHEAST": gbc.anchor = GridBagConstraints.SOUTHEAST;
                break;
            case "WEST": gbc.anchor = GridBagConstraints.WEST;
                break;
            case "EAST": gbc.anchor = GridBagConstraints.EAST;
                break;
            case "none": break;

        }

    }
}
