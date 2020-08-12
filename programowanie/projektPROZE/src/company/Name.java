package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * Klasa będąca oknem, gdzie gracz wpisuje swój nick i rozpoczyna grę
 */

public class Name extends JPanel{
    /**Zmienna wykorzystywana do KeyBindings**/
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    /**String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String ENTER = "enter";
    /** Zmienna przechowująca obrazek tła*/
    private ImageIcon MainMenuImage;
    /** Zmienna przechowująca nick gracza*/
    private String nick;
    /** Zmienne przechowująca wielkość poprzedniego okna*/
    private int a, b;
    /** Kolor niebieski używany w oknie*/
    Color aqua = new Color (51, 134, 175);
    /** Kolor żółty używany w oknie*/
    Color citron = new Color (223, 234, 24);
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabel = new LabelCustomizer(Color.lightGray, 36);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customButton = new ButtonCustomizer(true, citron, 32);
    /** Obiekt klasy TextFieldCustomizer**/
    TextFieldCustomizer customTextField = new TextFieldCustomizer(aqua, 24);
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();
    /** Przycisk Start **/
    JButton startButton = new JButton("Start!");
    /** Przycisk Back **/
    JButton backButton = new JButton("Back");
    /** Etykieta "Type your nick" **/
    JLabel typeNick=new JLabel("Type your nick:");
    /** Pole tekstowe do wpisania nicku gracza **/
    JTextField enterName = new JTextField("Your nick...");

    /**
     * Konstruktor klasy dodający przyciski oraz ustawiający poczatkowy rozmiar okna
     * @param xSize - szerokośc poprzedniego okna
     * @param ySize - wysokośc poprzedniego okna
     */
    public Name(int xSize, int ySize){
        this.removeAll();
        repaint();
        revalidate();
        a = xSize;
        b = ySize;
        setPreferredSize(new Dimension(a,b));
        initializeVariables();
        this.setLayout(new GridBagLayout());

        startButton.addActionListener(startButtonListener());
        backButton.addActionListener(backButtonListener());

        enterName.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                enterName.setText("");
            }
        });

        customButton.customizer(startButton);
        customButton.customizer(backButton);
        customLabel.customizer(typeNick);
        customTextField.customizer(enterName);

        keyBindings(10);

        this.add(typeNick, customGBC.gbcCustomize(0,1,0,0,3, "none"));
        this.add(enterName, customGBC.gbcCustomize(1,2,0,0,2, "none"));
        this.add(startButton, customGBC.gbcCustomize(2,3,0,0,2, "none"));
        this.add(backButton, customGBC.gbcCustomize(0,3,0,0,2, "none"));

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
    private ActionListener startButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startLevel();
            }
        };
        return actionListener;
    }
    /**
     * Odpowiada za wywołanie metody obiektu klasy NewWindow służącej do usunięcia wszystkich elemntów z obecnego JPanelu
     * oraz przejście do intra poziomu
     */
    private void startLevel(){
        nick = enterName.getText();
        String defaultName = "Your nick...";
        if(nick.equals(defaultName))
            nick = "UNKNOWN";

        newWindow.layoutMaker(this);
        add(new IntroLevel(getWidth(),getHeight(), 1, PropertiesLoad.numberOfLives, 0, nick),newWindow.buttonsClickedBehaviour());
    }
    /**
     * Odpowiada za przypisanie akcji przyciskowi BACK
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener backButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanWindow();
                add(new Menu(),newWindow.buttonsClickedBehaviour());
            }
        };
        return actionListener;
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
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke(keyCode, 0,false), ENTER);
        this.getActionMap().put(ENTER, action());
    }
    /**
     * Odpowiada za wywołanie metody obiektu klasy NewWindow służącej do usunięcia wszystkich elemntów z obecnego JPanelu
     */
    private void cleanWindow(){
        newWindow.layoutMaker(this);
    }


}

