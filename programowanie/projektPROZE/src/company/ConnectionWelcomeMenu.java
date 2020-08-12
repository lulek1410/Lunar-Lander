package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Klasa reprezentujaca panel służacy do nawiązywania połączenia z serwerem
 */
public class ConnectionWelcomeMenu extends JPanel{
    /** Zmienna przechowująca obrazek tła*/
    private ImageIcon MainMenuImage;
    /** Zmienna przechowująca ip serwera*/
    private String ip;
    /** Zmienna przechowująca port serwera*/
    private int port;
    /** Kolor niebieski używany w oknie*/
    Color aqua = new Color (51, 134, 175);
    /** Kolor żółty używany w oknie*/
    Color citron = new Color (200, 220, 24);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customButton = new ButtonCustomizer(true, citron, 32);
    /** Obiekt klasy TextFieldCustomizer**/
    TextFieldCustomizer customTextField = new TextFieldCustomizer(aqua, 24);
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabel = new LabelCustomizer(aqua, 36);
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabelCitron = new LabelCustomizer(citron, 24);
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();
    /** Przycisk Start **/
    JButton startOfflineButton = new JButton("Start offline");
    /** Przycisk Best Scores **/
    JButton startOnlineButton = new JButton("Start online");
    /** Przycisk Exit **/
    JButton exitButton = new JButton("Exit");
    /** Pole tekstowe do wpisania IP serwera **/
    JTextField enterIP = new JTextField("Enter Server IP...");
    /** Pole tekstowe do wpisania portu serwera **/
    JTextField enterPort = new JTextField("Enter Server Port...");
    /** Etykieta "Welcome!" **/
    JLabel welcomeLabel =new JLabel("WELCOME!");
    /** Etykieta "IP" **/
    JLabel ipLabel =new JLabel("IP:");
    /** Etykieta "PORT" **/
    JLabel portLabel =new JLabel("Port:");

    /**konstruktor klasy*/
    public ConnectionWelcomeMenu(){
        this.removeAll();
        initializeLayout();
        initializeVariables();

        this.setLayout(new GridBagLayout());

        startOnlineButton.addActionListener(startOnlineListener());
        startOfflineButton.addActionListener(startOfflineListener());
        exitButton.addActionListener(exitButtonListener());


        customButton.customizer(startOfflineButton);
        customButton.customizer(startOnlineButton);
        customButton.customizer(exitButton);
        customTextField.customizer(enterIP);
        customTextField.customizer(enterPort);
        customLabel.customizer(welcomeLabel);
        customLabelCitron.customizer(ipLabel);
        customLabelCitron.customizer(portLabel);

        enterIP.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                enterIP.setText("");
            }
        });
        enterPort.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                enterPort.setText("");
            }
        });


        this.add(startOfflineButton, customGBC.gbcCustomize(0,5,0,0,2, "none"));
        this.add(startOnlineButton, customGBC.gbcCustomize(2,5,0,0,2, "none"));
        this.add(exitButton, customGBC.gbcCustomize(1,6,0,0,3, "none"));
        this.add(welcomeLabel, customGBC.gbcCustomize(1,2,0,0,3, "none"));
        this.add(enterIP, customGBC.gbcCustomize(2,3,0,0,3, "none"));
        this.add(enterPort, customGBC.gbcCustomize(2,4,0,0,3, "none"));
        this.add(ipLabel, customGBC.gbcCustomize(0,3,0,0,3, "none"));
        this.add(portLabel, customGBC.gbcCustomize(0,4,0,0,3, "none"));
    }
    /** metoda inicjalizująca obrazek tła za pomocą metody obiektu ImageFactory*/
    private void initializeVariables() {
        this.MainMenuImage = ImageFactory.createImage(Image.MainMenu);
    }

    /** metoda ustawiająca rozmiar okna za pomocą danych z pliku Config.txt*/
    private void initializeLayout() {setPreferredSize(new Dimension(PropertiesLoad.xSize, PropertiesLoad.ySize));}

    /** metoda przesłaniająca metodę paintComponent, w celu odpowiedniego skalowania obrazka w tle
     * @param g - obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(MainMenuImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi Start online
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener startOnlineListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startOnline();
            }
        };
        return actionListener;
    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi Start offline
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener startOfflineListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client.online = false;
                try {
                    PropertiesLoad.loadProps();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                cleanWindow();
                add(new Menu(), newWindow.buttonsClickedBehaviour());
            }
        };
        return actionListener;
    }

    /**
     * metoda próbująca utworzyć połączenie z serwerem
     */
    private void startOnline(){
        try {
            ip = enterIP.getText();
            port = Integer.parseInt(enterPort.getText());
            Client.Connect(ip, port);
            Client.online = true;
            PropertiesLoad.loadPropsServer();
            JOptionPane.showMessageDialog(new JFrame(), "Connected successfully!", "Connected", JOptionPane.INFORMATION_MESSAGE);
            newWindow.layoutMaker(this);
            add(new Menu(), newWindow.buttonsClickedBehaviour());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(new JFrame(), "Incorrect data or server offline", "Error", JOptionPane.ERROR_MESSAGE);
            Client.online = false;
            try {
                PropertiesLoad.loadProps();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //newWindow.layoutMaker(this);
        //add(new Menu(), newWindow.buttonsClickedBehaviour());
    }
    /**
     * Odpowiada za przypisanie akcji przyciskowi EXIT
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener exitButtonListener() {

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
