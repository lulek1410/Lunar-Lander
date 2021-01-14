package company;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Klasa reprezentująca okno rankingu
 */
public class Ranking extends JPanel{
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();
    /** Przycisk Return to Main Menu**/
    JButton backButton = new JButton("Return to Main Menu");
    /** Napis Ranking**/
    JLabel lost =new JLabel("RANKING");
    /** Zmienna przechowująca obrazek w tle okna*/
    private ImageIcon MainMenuImage;
    /** Zmienne przechowująca wielkość poprzedniego okna*/
    private int a, b;
    /** Kolor (niebieski) czcionki używanej w oknie*/
    Color aqua = new Color (51, 134, 175);
    /** Kolor (żółty) czcionki używanej w oknie*/
    Color citron = new Color (223, 234, 24);
    /** Tablica nagłówków używanych w graficznej tablicy w oknie**/
    String[] columnNames = {"NICK", "SCORE"};
    /** Obiekt klasy RankingServer**/
    RankingLoader rs = new RankingLoader();
    /** Dwuwymiarowa tablica przechowująca nicki i punkty 5 najlepszych graczy**/
    Object[][] data = rs.bestScores();
    /** Graficzna tablica używana w oknie**/
    JTable ranking = new JTable(data, columnNames);
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer customLabel = new LabelCustomizer(aqua, 40);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customButton = new ButtonCustomizer(true, citron, 32);

    /**
     * Konstruktor klasy dodający przyciski oraz ustawiający poczatkowy rozmiar okna
     * @param xSize - szerokośc poprzedniego okna
     * @param ySize - wysokośc poprzedniego okna
     */
    public Ranking(int xSize, int ySize) {
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
        customLabel.customizer(lost);
        tableCustomizer(ranking);

        this.add(lost, customGBC.gbcCustomize(0,1,0,0,3,"none"));
        this.add(backButton, customGBC.gbcCustomize(0,3,0,0,3,"none"));
        this.add(ranking, customGBC.gbcCustomize(0,2,0,0,3,"none"));
        customGBC.gbcCustomize(0,0,1,1,0,"none");

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
     * Odpowiada za wygląd graficznej tabeli
     * @param ranking - Obiekt klasy JTabel - graficzna tabela
     */
    private void tableCustomizer(JTable ranking){
        ranking.setOpaque(true);
        ranking.setFont(Fonts.getFont(20));
        ranking.setBackground(Color.BLACK);
        ranking.setForeground(Color.WHITE);
        ranking.setGridColor(Color.BLACK);
        TableColumn column = ranking.getColumnModel().getColumn(0);
        column.setPreferredWidth(150);

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


