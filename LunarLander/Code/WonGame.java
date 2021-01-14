package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Klasa będąca oknem które ukazuje się po wygraniu gry
 */
public class WonGame extends JPanel{
    /** Zmienna przechowująca obrazek tła*/
    private ImageIcon MainMenuImage;
    /** Zmienna przechowująca nick gracza*/
    private String nick;
    /** Zmienna przechowująca nick gracza*/
    private int points;
    /** Zmienne przechowująca wielkość poprzedniego okna*/
    private int a, b;
    /** Kolor niebieski używany w oknie*/
    Color aqua = new Color (51, 134, 175);
    /** Kolor żółty używany w oknie*/
    Color citron = new Color (223, 234, 24);
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();
    /** Napis informujący o ilości zdobytych punktów **/
    JLabel pointsLabel = new JLabel();
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabel = new LabelCustomizer(aqua, 40);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customButton = new ButtonCustomizer(true, citron, 32);
    /** Przycisk Play Again **/
    JButton startButton = new JButton("Play again!");
    /** Przycisk Return to Main Menu **/
    JButton backButton = new JButton("Return to Main Menu");
    /** Napis informujący o wygranej gracza**/
    JLabel wonLabel =new JLabel();


    /**
     * Konstruktor klasy dodający przyciski oraz ustawiający poczatkowy rozmiar okna
     * @param xSize - szerokośc poprzedniego okna
     * @param ySize - wysokośc poprzedniego okna
     * @param nickName - nick gracza
     * @param earnedPoints - ilość zdobytych punktów
     */
    public WonGame(int xSize, int ySize, String nickName,int earnedPoints) {
        this.removeAll();
        repaint();
        revalidate();
        a = xSize;
        b = ySize;
        setPreferredSize(new Dimension(a,b));


        ConnectionCheck.detectServer();


        initializeVariables();
        this.setLayout(new GridBagLayout());


        points = earnedPoints;
        nick = nickName;
        save();

        wonLabel.setText("YOU WON " + nick + "!!!");
        startButton.addActionListener(continueButtonListener());
        backButton.addActionListener(returnToMainMenuButtonListener());

        customButton.customizer(startButton);
        customButton.customizer(backButton);

        customLabel.customizer(wonLabel);
        customLabel.customizer(pointsLabel);
        pointsLabel.setText("Your score is: "+ earnedPoints);

        this.add(backButton, customGBC.gbcCustomize(0,4 ,0,0,3,"none"));
        this.add(wonLabel, customGBC.gbcCustomize(0,1 ,0,0,3,"none"));
        this.add(pointsLabel, customGBC.gbcCustomize(0,2 ,0,0,3,"none"));
        this.add(startButton, customGBC.gbcCustomize(0,3 ,0,0,3,"none"));
        customGBC.gbcCustomize(0,0,1,1,0,"none");

    }
    /**
     * Metoda próbująca zapisać nick oraz punkty gracza do pliku z pomoca metody klasy RankingServer
     */
    private void save(){
        try {
            if (Client.online) Client.saveScore(nick, points);
            if (!Client.online) RankingSaver.saveToFile(nick, points);
        }
        catch(Exception E){
            E.printStackTrace();
        }
    }
    /** metoda inicjalizująca obrazek tła za pomocą metody obiektu ImageFactory*/
    private void initializeVariables() {
        this.MainMenuImage = ImageFactory.createImage(Image.MainMenu);
    }

    /**
     * metoda przesłaniająca metodę paintComponent, w celu odpowiedniego skalowania obrazka w tle
     * @param g - obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MainMenuImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi START
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener continueButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanWindow();
                add(new Name(getWidth(),getHeight()),newWindow.buttonsClickedBehaviour());
            }
        };
        return actionListener;
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
