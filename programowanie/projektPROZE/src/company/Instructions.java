package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Klasa będąca oknem zawierającym instrukcje do gry
 */
public class Instructions extends JPanel{
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();
    /** Przycisk back **/
    JButton backButton = new JButton("Back");
    /**Zmienna przechowująca obrazek okna*/
    private ImageIcon MainMenuImage;
    /** Zmienne przechowująca wielkość poprzedniego okna*/
    private int a, b;
    /** Kolor (żółty) czcionki używanej w oknie*/
    Color citron = new Color (223, 234, 24);
    /** Obiekt klasy ButtonCustomizer*/
    ButtonCustomizer customButton = new ButtonCustomizer(true, citron, 32);

    /**
     * Konstruktor klasy dodający przyciski oraz ustawiający poczatkowy rozmiar okna
     * @param xSize - szerokośc poprzedniego okna
     * @param ySize - wysokośc poprzedniego okna
     */
    public Instructions(int xSize, int ySize){
        this.removeAll();
        repaint();
        revalidate();
        a = xSize;
        b = ySize;
        setPreferredSize(new Dimension(a,b));
        initializeVariables();
        this.setLayout(new GridBagLayout());

        backButton.addActionListener(returnToMainMenuButtonListener());
        customButton.customizer(backButton);
        this.add(backButton, customGBC.gbcCustomize(0,0,1,1,0,"SOUTHEAST"));
    }

    /**
     * Metoda inicjalizująca obrazek tła za pomocą metody obiektu ImageFactory
     */
    private void initializeVariables() {
        this.MainMenuImage = ImageFactory.createImage(Image.Instruction);
    }

    /**
     * Metoda przesłaniająca metodę paintComponent, w celu odpowiedniego skalowania obrazka w tle
     * @param g - obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MainMenuImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi BACK
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener returnToMainMenuButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanWindow();
                add(new Menu(),newWindow.buttonsClickedBehaviour());
            }
        };
        return actionListener;
    }

    /**
     * Odpowiada za wywołanie metody obiektu klasy NewWindow służącej do usunięcia wszystkich elemntów z obecnego JPanelu
     */
    private void cleanWindow(){
        newWindow.layoutMaker(this);
    }
}
