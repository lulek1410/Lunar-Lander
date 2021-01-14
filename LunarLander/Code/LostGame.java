package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Klasa będąca oknem które ukazuje się po przegranej grze
 */
public class LostGame extends JPanel {

    /** Zmienna przechowująca obrazek tła*/
    private ImageIcon MainMenuImage;
    /** Zmienna przechowująca nick gracza*/
    private String nick;
    /** Zmienne przechowująca wielkość poprzedniego okna*/
    private int a, b;
    /**Ilość punktów zdobytych przez gracza**/
    private int points;
    /** Kolor (niebieski) czcionki używanej w oknie*/
    Color aqua = new Color (51, 134, 175);
    /** Kolor (żółty) czcionki używanej w oknie*/
    Color citron = new Color (223, 234, 24);
    /** Przycisk zagrania jeszcze raz*/
    JButton startButton = new JButton("Play again");
    /** Przycisk powrotu do głównego menu*/
    JButton backButton = new JButton("Return to main menu");
    /** Napis informujący o niepowodzeniu*/
    JLabel lost = new JLabel("THE MISSION HAS FAILED");
    /** Napis informujący o ilości zdobytych punktów*/
    JLabel pointsLabel = new JLabel();
    /** Obiekt klasy LabelCustomizer*/
    LabelCustomizer customLabelAqua = new LabelCustomizer(aqua, 40);
    /** Obiekt klasy LabelCustomizer*/
    LabelCustomizer customLabelWhite = new LabelCustomizer(Color.WHITE, 40);
    /** Obiekt klasy ButtonCustomizer*/
    ButtonCustomizer customButton = new ButtonCustomizer(true, citron, 32);
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();

    /**
     * Konstruktor klasy dodający przyciski, ustawiający poczatkowy rozmiar okna
     * oraz przypisujący pobrane wartości do odpowiednich pól w klasie
     * @param xSize - szerokość poprzedniego okna
     * @param ySize - wysokość poprzedniego okna
     * @param earnedPoints - ilość zdobytych punktów
     * @param nickName - nick gracza
     */
    public LostGame(int xSize, int ySize, int earnedPoints, String nickName) {
        this.removeAll();
        repaint();
        revalidate();
        points = earnedPoints;
        nick = nickName;
        a = xSize;
        b = ySize;
        setPreferredSize(new Dimension(a,b));

        ConnectionCheck.detectServer();

        initializeVariables();
        this.setLayout(new GridBagLayout());

        save();

        startButton.addActionListener(playAgainButtonListener());
        backButton.addActionListener(returnToMainMenuButtonListener());

        pointsLabel.setText("Your score is: "+ earnedPoints);

        customButton.customizer(startButton);
        customButton.customizer(backButton);
        customLabelAqua.customizer(lost);
        customLabelWhite.customizer(pointsLabel);

        this.add(lost, customGBC.gbcCustomize(0,2, 0, 0,3, "none"));
        this.add(pointsLabel, customGBC.gbcCustomize(0,3, 0, 0,3, "none"));
        this.add(startButton, customGBC.gbcCustomize(0,4, 0, 0,3, "none"));
        this.add(backButton, customGBC.gbcCustomize(0,5, 0, 0,3, "none"));
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
        /** metoda przesłaniająca metodę paintComponent, w celu odpowiedniego skalowania obrazka w tle
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
        private ActionListener playAgainButtonListener() {
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cleanWindow();
                    add(new IntroLevel(getWidth(),getHeight(),1, PropertiesLoad.numberOfLives, 0, nick),newWindow.buttonsClickedBehaviour());
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

