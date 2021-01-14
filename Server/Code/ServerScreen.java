package company;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

public class ServerScreen extends JPanel {
    /** Obiekt klasy DefaultListModel dynamicznie zmieniany
     * w czasie działania programu która znajduje się w konsoli programu*/
    public static DefaultListModel listModel = new DefaultListModel();
    /** Konsola widoczna na ekranie */
    private static JList list;
    /** Scroll w konsoli */
    private JScrollPane vertical;
    /** JLabel z numerem IP serwera **/
    JLabel ip = new JLabel();
    /** JLabel z numerem portu serwera **/
    JLabel port = new JLabel();
    /** Przycisk do resetowania konsoli **/
    JButton resetConsoleButton = new JButton("Clear console");
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customizedLabel = new LabelCustomizer(Color.white);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customizedButton = new ButtonCustomizer();
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy Server**/
    Server server = new Server();

    /**
     * Konstruktor klasy ustalający wygląd okna oraz inicjalizujący komponenty okna.
     * Konstruktor uruchamia również serwer.
     */
    public ServerScreen() throws IOException {
        this.removeAll();
        initializeLayout();
        initializeBackground();
        this.setLayout(new GridBagLayout());

        server.run();

        resetConsoleButton.addActionListener(resetConsoleButtonListener());

        ip.setText("IP adress = " + InetAddress.getLocalHost().getHostAddress());
        port.setText("Port = " + PropertiesLoad.port);

        customizedButton.customizer(resetConsoleButton);
        customizedLabel.customizer(ip);
        customizedLabel.customizer(port);

        listModel.addElement("Messages:");
        list = new JList(listModel);
        list.setPreferredSize(new Dimension(500, 200));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(10);
        list.setFont(new Font("Menlo", Font.PLAIN, 14));
        list.setForeground(Color.white);
        list.setBackground(Color.black);
        list.setOpaque(true);

        vertical = new JScrollPane(list);
        vertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(ip, customGBC.gbcCustomize(0,0,0,0,0,"none"));
        this.add(port, customGBC.gbcCustomize(0,1,0,0,0,"none"));
        this.add(vertical, customGBC.gbcCustomize(0,2,0,1,0,"none"));
        this.add(resetConsoleButton, customGBC.gbcCustomize(0,3,0,0,0,"none"));

    }

    /** metoda przesłaniająca metodę paintComponent, w celu odpowiedniego skalowania obrazka w tle
     * @param g - obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        update();
        vertical.revalidate();
        vertical.repaint();
    }
    /**
     * Aktualizuje komponenty okna
     */
    private void update(){
        revalidate();
        repaint();
    }
    /**
     * Metoda inicjalizująca kolor tła za pomocą metody obiektu ImageFactory
     */
    private void initializeBackground() {
        this.setBackground(Color.BLACK);
    }

    /**
     * Metoda ustawiająca rozmiar okna za pomocą danych z pliku Config.txt
     */
    private void initializeLayout() {
        setPreferredSize(new Dimension(700,500));
    }

    /**
     * Metoda aktualizująca konsole programu
     * @param message - wiadomość która ma zostać wyświetlona
     */
    public static void addMessage(String message){
        listModel.addElement(message);
        list.setPreferredSize(new Dimension(500, listModel.getSize()*20));
    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi reset console
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener resetConsoleButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listModel.removeAllElements();
                listModel.addElement("Messages:");
                list.setPreferredSize(new Dimension(500,200));
            }
        };
        return actionListener;
    }

}
