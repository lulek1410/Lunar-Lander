package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Klasa będąca oknem które ukazuje się przed rozpoczęciem poziomu
 */
public class IntroLevel extends JPanel{

    /** Zmienna przechowująca obrazek tła*/
    private ImageIcon backgroundImage;
    /**Zmienna wykorzystywana do KeyBindings**/
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    /**String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String SPACE = "space";
    /**Obecna ilość punktów gracza**/
    private int points;
    /**Numer poziomu który zacznie sie po tym oknie**/
    private int level;
    /**Ilość pozostałych żyć gracza**/
    private int lives;
    /**Nick gracza**/
    private String nick;
    /** Zmienne przechowująca wielkość poprzedniego okna*/
    int a,b;
    /**Napis ukazujący numer poziomu, który zacznie się po tym oknie**/
    JLabel levelLabel = new JLabel();
    /**Napis instruujący jak zacząć grę**/
    JLabel spaceLabel = new JLabel("Press space to begin...");
    /** Kolor (niebieski) czcionki używanej w oknie*/
    Color aqua = new Color(51, 134, 175);
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabel = new LabelCustomizer(aqua, 54);
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabelSpace = new LabelCustomizer(aqua, 40);
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();

    /**
     * Konstruktor klasy dodający przyciski, ustawiający poczatkowy rozmiar okna
     * oraz przypisujący pobrane wartości do odpowiednich pól w klasie
     * @param xSize - szerokość poprzedniego okna
     * @param ySize - wysokość poprzedniego okna
     * @param levelNumber - numer poziomu który ma się rozpocząć
     * @param livesNumber - pozostała ilość żyć
     * @param previousPoints - ilość zdobytych dotychczas punktów
     * @param nickName - nick gracza
     */
    public IntroLevel(int xSize, int ySize, int levelNumber, int livesNumber, int previousPoints, String nickName) {

        this.removeAll();
        repaint();
        revalidate();
        a = xSize;
        b = ySize;
        setPreferredSize(new Dimension(a,b));
        this.setLayout(new GridBagLayout());

        nick = nickName;
        points = previousPoints;
        level = levelNumber;
        lives = livesNumber;
        keyBindings(32);
        setBackground(levelNumber);
        customLabel.customizer(levelLabel);
        customLabelSpace.customizer(spaceLabel);
        levelLabel.setText("LEVEL"+ levelNumber);

        this.add(levelLabel, customGBC.gbcCustomize(0,1,0,0,0, "SOUTH"));
        this.add(spaceLabel, customGBC.gbcCustomize(0,2,0,0,0, "SOUTH"));

    }

    /**
     * Metoda wybierająca odpowiedni obrazek tła w zależności od numeru poziomu
     * @param levelNumber - numer poziomu
     */
    private void setBackground(int levelNumber) {
        switch (levelNumber) {
            case 1:
                this.backgroundImage = ImageFactory.createImage(Image.Earth1);
                break;
            case 2:
                this.backgroundImage = ImageFactory.createImage(Image.Mars1);
                break;
            case 3:
                this.backgroundImage = ImageFactory.createImage(Image.Jupiter1);
                break;
            case 4:
                this.backgroundImage = ImageFactory.createImage(Image.Saturn1);
                break;
            case 5:
                this.backgroundImage = ImageFactory.createImage(Image.Earth2);
                break;
            case 6:
                this.backgroundImage = ImageFactory.createImage(Image.Mars2);
                break;
            case 7:
                this.backgroundImage = ImageFactory.createImage(Image.Jupiter2);
                break;
            case 8:
                this.backgroundImage = ImageFactory.createImage(Image.Saturn2);
                break;
        }
    }
    /**
     * Metoda nadpisująca metodę paintComponent w celu przeskalowania obrazka w tle oraz rysowania obiektów z gry
     * @param g - obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        revalidate();
        repaint();
    }


    /**
     * Metoda tworząca obiekt klasy Action wywołujący daną funkcje
     * @return newAction - stwoworzony obiekt
     */
    private Action action(){
        Action newAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                startLevel();
            }
        };
        return newAction;
    }
    /**
     * Odpowiada za obłsugę klawiszy
     * @param keyCode - kod klawisza
     */
    private void keyBindings(int keyCode){
       this.getInputMap(IFW).put(KeyStroke.getKeyStroke(keyCode, 0,false), SPACE);
       this.getActionMap().put(SPACE, action());
    }
    /**
     * Odpowiada za wywołanie metody obiektu klasy NewWindow służącej do usunięcia wszystkich elemntów z obecnego JPanelu
     * oraz rozpoczęcie poziomu
     */
    private void startLevel(){
        newWindow.layoutMaker(this);
        add(new Level(getWidth(),getHeight(), level, lives, points, nick, this.backgroundImage),newWindow.buttonsClickedBehaviour());
    }

}
