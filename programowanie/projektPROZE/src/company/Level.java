package company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;
import java.util.ArrayList;


/**
 * Klasa odpowiedzialna za rysowanie poziomu ziemi, statku gracza i asteroid oraz obsługe zdarzeń w czasie gry
 */
public class Level extends JPanel{
    /**Zmienna przechowująca tło okna*/
    private ImageIcon backgroundImage;
    /** Obiekt klasy Timer**/
    private Timer timer;
    /** Obiekt klasy Lander**/
    private Lander lander;
    /** Zmienna określająca czy gra jest aktywna*/
    private boolean inGame = true;
    /** Zmienna wykorzystywana do KeyBindings**/
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    /** String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String MOVE_UP = "move up";
    /** String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String MOVE_LEFT = "move left";
    /** String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String MOVE_RIGHT = "move right";
    /** String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String MOVE_DOWN = "move down";
    /** String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String PAUSE = "pause";
    /** String przechowujący nazwę przycisku używany do KeyBindings**/
    private static final String RESUME= "resume";
    /** Numer obecnego poziomu**/
    private int levelNum;
    /** Ilość pozostałych żyć**/
    private int leftLives;
    /** Ilość pozostałego czasu**/
    private int time = 60;
    /** Przechowuje liczbę wygenerowanych asteroid*/
    private int asteroid_counter;
    /** Poziom paliwa*/
    protected float fuelLevel;
    /** przechowuje nicka aktualnego gracza*/
    private String nick;
    /** Lista przechowująca istniejące asteroidy*/
    private ArrayList<Asteroid> asteroids;
    /** Ilość punktów**/
    private int points;
    /** Pukty uzyskane przez gracza w poprzednim poziomie*/
    private int prevPoints;
    /** Napis informujący o prędkości poziomej gracza*/
    JLabel vx = new JLabel("H. Speed: 0");
    /**Napis informujący o prędkości pionowej gracza*/
    JLabel vy = new JLabel("V. Speed: 0");
    /** Ikona Statku przy ilości zyć*/
    JLabel leftLandersLabel = new JLabel();
    /** Napis paliwo przy pasku stanu paliwa*/
    JLabel fuelLabel = new JLabel("Fuel");
    /** Napis informujący ile pozostało czasu za który możemy otrzymać punkty*/
    JLabel timeLabel = new JLabel("Left time: 60 sec");
    /** Pasek pokazujący stan paliwa*/
    JProgressBar fuelBar = new JProgressBar();
    /** Kolor niebieski używany w oknie*/
    Color aqua = new Color (51, 134, 175);
    /** Kolor żółty używany w oknie*/
    Color citron = new Color (223, 234, 24);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customButtonTrue = new ButtonCustomizer(true, Color.lightGray, 40);
    /** Obiekt klasy ButtonCustomizer **/
    ButtonCustomizer customButtonFalse = new ButtonCustomizer(false, Color.BLUE, 40);
    /** Obiekt klasy LabelCustomizer **/
    LabelCustomizer custom = new LabelCustomizer(Color.lightGray, 20);
    /** Obiekt klasy GridBagConstraintsMaker**/
    GridBagConstraintsMaker customGBC = new GridBagConstraintsMaker();
    /** Obiekt klasy NewWindow **/
    NewWindow newWindow = new NewWindow();
    /** Obiekt klasy ScheduledExecutorService **/
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    /** Obiekt klasy ScheduledExecutorService **/
    ScheduledExecutorService boomExecutor = Executors.newScheduledThreadPool(1);
    /** Przycisk Exit **/
    JButton exitButton = new JButton("EXIT");
    /** Przycisk Pause**/
    JButton pauseButton = new JButton("||");
    /** Przycisk Continue**/
    JButton continueButton = new JButton("CONTINUE");
    /** Obrazek statku**/
    JLabel landersLeft = new JLabel(ImageFactory.createImage(Image.Lander));

    /**
     * Konstruktor klasy dodający przyciski, ustawiający poczatkowy rozmiar okna
     * oraz przypisujący pobrane wartości do odpowiednich pól w klasie
     * @param xSize - szerokość poprzedniego okna
     * @param ySize - wysokość poprzedniego okna
     * @param levelNumber - numer poziomu który ma się rozpocząć
     * @param Lives - pozostała ilość żyć
     * @param previousPoints - ilość zdobytych dotychczas punktów
     * @param nickName - nick gracza
     * @param background - tło okna
     */
    public Level(int xSize, int ySize, int levelNumber, int Lives, int previousPoints, String nickName, ImageIcon background) {
        this.removeAll();
        repaint();
        revalidate();


        nick = nickName;
        levelNum = levelNumber;
        leftLives = Lives;
        prevPoints = previousPoints;
        setPreferredSize(new Dimension(xSize, ySize));
        initializeVariables();
        try {
            ConnectionCheck.detectServer();
            if(Client.online) PropertiesLoad.loadMapsConfigsServer(levelNumber);
            else PropertiesLoad.loadMapsConfigs(levelNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.backgroundImage = background;

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.VERTICAL;



        labelUpdate("lives");

        customButtonTrue.customizer(pauseButton);
        customButtonFalse.customizer(continueButton);
        customButtonFalse.customizer(exitButton);

        pauseButton.addActionListener(pauseButtonListener(continueButton, exitButton, pauseButton));
        continueButton.addActionListener(continueButtonListener());
        exitButton.addActionListener(exitButtonListener());

        keyBindings(32, PAUSE);
        keyBindings( 38, MOVE_UP);
        keyBindings( 40, MOVE_DOWN);
        keyBindings( 39, MOVE_RIGHT);
        keyBindings( 37, MOVE_LEFT);

        custom.customizer(vx);
        custom.customizer(vy);
        custom.customizer(timeLabel);
        custom.customizer(leftLandersLabel);
        custom.customizer(fuelLabel);
        custom.customizer(timeLabel);

        fuelBar.setVisible(true);
        fuelBar.setValue(100);
        fuelBar.setBorderPainted(false);
        fuelBar.setStringPainted(false);
        fuelBar.setForeground(citron);
        fuelBar.setBackground(aqua);


        this.add(fuelBar, customGBC.gbcCustomize(2,0,0,0,1, "FIRST_LINE_END"));
        this.add(vx, customGBC.gbcCustomize(0,0,0,0,1, "FIRST_LINE_START"));
        this.add(vy, customGBC.gbcCustomize(0,1,0,0,1, "FIRST_LINE_START"));
        this.add(fuelLabel, customGBC.gbcCustomize(1,0,1,0,1, "FIRST_LINE_END"));
        this.add(timeLabel, customGBC.gbcCustomize(0,2,0,1,1, "FIRST_LINE_START"));
        this.add(pauseButton, customGBC.gbcCustomize(2,2,0,0,1, "FIRST_LINE_END"));
        this.add(exitButton,customGBC.gbcCustomize(0,3,0,0,1, "LAST_LINE_START"));
        this.add(continueButton, customGBC.gbcCustomize(1,3,0,0,2, "LAST_LINE_END"));
        this.add(leftLandersLabel, customGBC.gbcCustomize(2,1,0,0,1, "FIRST_LINE_END"));
        this.add(landersLeft, customGBC.gbcCustomize(2,1,0,0,1, "CENTER"));

    }
    /**
     * Funkcja inicjująca zmienne klasy
     */
    private void initializeVariables(){
        setFocusable(true);
        asteroid_counter = 0;
        this.asteroids = new ArrayList<Asteroid>();
        this.fuelLevel = PropertiesLoad.fuelAmount;
        this.lander = new Lander(this);
        this.lander.landerImageChange(Image.Lander);
        this.timer = new Timer(40, new GameLoop(this));
        this.timer.start();
        timeCounter(true);
    }

    /**
     * Funkcja pauzująca grę
     */
    private void pause(){
        this.timer.stop();
        timeCounter(false);
        for(int i = 37; i<41; i++){
            keyBindings( i, "nothing");
        }
        keyBindings(32, RESUME);
    }
    /**
     * Funkcja wznawiająca grę
     */
    private void resume(){
        this.timer.start();
        keyBindings( 38, MOVE_UP);
        keyBindings( 40, MOVE_DOWN);
        keyBindings(39, MOVE_RIGHT);
        keyBindings(37, MOVE_LEFT);
        keyBindings(32, PAUSE);
        timeCounter(true);
    }
    /** Funkcja odpowiedzialna za rysowanie obrazku reprezentującego gracza oraz generowanie jego hitboxa i skalowanie rozmiarów
     * tych elementów poprzez mnożenie ich wielkości i położenia przez współczynnik skali będący stosunkiem obecnej wielkośi
     * okna do jego początkowej wielkości wczytywanej z pliku konfiguracyjnego
     * @param g - obiekt klasy Graphics
     */
    private void drawPlayer(Graphics g){
        g.drawImage(lander.getImage(), (int)(lander.getX()*((float)(this.getWidth())/PropertiesLoad.xSize)),
                (int)(lander.getY()*((float)this.getHeight()/PropertiesLoad.ySize)), (int)(this.getWidth()/17.5),
                (int)(this.getHeight()/12.5), this);
    }

    /**
     * Generuje hitbox powirzchni księzyca i lądowiska oraz skaluje do rozmiarów okna
     */
    private void drawGround(){
        Polygon moon = new Polygon(scalePoints(PropertiesLoad.xPoints, 'x'), scalePoints(PropertiesLoad.yPoints, 'y'),
                PropertiesLoad.xPoints.length);
        Polygon landing = new Polygon(scalePoints(PropertiesLoad.xLanding, 'x'), scalePoints(PropertiesLoad.yLanding, 'y'), PropertiesLoad.xLanding.length);
        detectCollision(landing, moon);
    }
    /**Rysuje asteroidy i skaluje je odpowiednio do rozmiarów okna
     * @param g - obiekt klasy Graphics
     */
    private void drawAsteroid(Graphics g){
        for (int i =0; i<asteroids.size(); i++)
        {
            g.drawImage(asteroids.get(i).getImage(),
                    (int)(asteroids.get(i).getX()*((float)(this.getWidth())/PropertiesLoad.xSize)), (int)(asteroids.get(i).getY()*((float)this.getHeight()/PropertiesLoad.ySize)),
                    (int)(20*((float)this.getWidth()/PropertiesLoad.xSize)), (int)(20*((float)this.getHeight()/PropertiesLoad.ySize)), this);
        }
    }

    /** Generuje asteroidy na podstawie zmiennych losowych*/
    private void addAsteroid(){
        Random rand = new Random();
        if (rand.nextInt(101)<7) {
            int direction = rand.nextInt(2);
            if (asteroid_counter < levelNum+3) {
                int velx = rand.nextInt(6) + 3;
                int vely = rand.nextInt(6) + 3;
                if (direction == 0) {
                    try {
                        int start_x = rand.nextInt(this.getWidth() / 2 - (int) (this.getWidth() / (17.5 * 2))) + this.getWidth() / 2 + (int) (this.getWidth() / (17.5 * 2));
                        this.asteroids.add(new Asteroid(start_x, -20, velx, vely, direction, this));
                        asteroid_counter+=1;
                    }
                    catch(Exception e){}
                } else {
                    try {
                        int start_x = rand.nextInt(this.getHeight() / 2 - (int) (this.getHeight() / (17.5 * 2)));
                        this.asteroids.add(new Asteroid(start_x, -20, velx, vely, direction, this));
                        asteroid_counter+=1;
                    }
                    catch(Exception e){}

                }
            }
        }
    }

    /**
     * Funkcja skalująca zadaną tablicę intów w zależności od rozmiarów okna
     * @param points tablica intów będąca reprezentacją punktów uzywanych w metodzie drawGround do rysowania powieżchni księżyca
     * @param param zmianna char mówiąca o tym czy punkty które skalujemy są punktami z osi OX czy OY
     * @return zwraca przeskalowaną tablicę intów
     */
    private int[] scalePoints(int[] points, char param)
    {
        int[] scaled_points = new int[points.length];
        for(int i=0; i<points.length; i++){
            if(param == 'y') {
                scaled_points[i] = (int)(points[i] * ((float) getHeight() / PropertiesLoad.ySize));
            }
            else {
                scaled_points[i] = (int)(points[i] * ((float) getWidth() / PropertiesLoad.xSize));
            }
        }
        return scaled_points;
    }

    /**
     * Metoda nadpisująca metodę paintComponent w celu przeskalowania obrazka w tle oraz rysowania obiektów z gry
     * @param g - obiekt klasy Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setDoubleBuffered(true);
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        doDrawing(g);
    }

    /**
     * Funkcja rysująca obiekty gry (gracz i powierzchnia księżyca)
     * @param g - obiekt klasy Graphics
     */
    private void doDrawing(Graphics g) {
        if (inGame) {
            drawPlayer(g);
            drawGround();
            drawAsteroid(g);
        } else {
            if (timer.isRunning()) {
                timer.stop();
            }
            Toolkit.getDefaultToolkit().sync();
        }
    }

    /**
     * Metoda odpowiadająca za odliczanie w oknie gry
     * @param onOff - wartość określająca czy zegar ma być włączony czy zatrzymany
     */
    private void timeCounter(boolean onOff)
    {
        Runnable timeOn = () -> {
            time -= 1;
           labelUpdate("time");
        };

        if(onOff){
            executor.scheduleAtFixedRate(timeOn, 1, 1, SECONDS);
        }
        else {
            executor.shutdown();
            newExecutor("executor");
        }
    }

    /**
     * Metoda tworząca nowy obiekt ScheduledExecutorService i przypisująca go do obiektu,
     * który został zadeklarowany w klasie Level, który został wyłączony
     * @param whichExecutor - string określający który Executor ma zostać "zrestartowany"
     */
    private void newExecutor(String whichExecutor){
        ScheduledExecutorService newExecutor = Executors.newScheduledThreadPool(1);
        if(whichExecutor == "executor") {
            executor = newExecutor;
        }
        else{
            boomExecutor = newExecutor;
        }
    }
    /**
     * Wykrywanie kolizji i wywołanie odpowiednych metod
     * @param landing- wielokąt strefy lądowania
     * @param moon - wielokąt obszaru księzyca poza strefą lądowania
     */
    private void detectCollision(Polygon landing, Polygon moon){
        if(moon.intersects(lander.getRect()))
            boom();

        if(landing.intersects(lander.getRect()))
            goodLanding();

        asteroidsCollision(landing, moon);
    }

    /**
     * Wykrywa i obsługuje kolizję asteroid
     * @param landing - wielokąt strefy lądowania
     * @param moon - wielokąt obszaru księzyca poza strefą lądowania
     */
    private void asteroidsCollision(Polygon landing, Polygon moon){
        for(int i = 0; i<this.asteroids.size();i++){
            if (lander.getRect().intersects(asteroids.get(i).getRect())) boom();

            if (moon.intersects(asteroids.get(i).getRect()) || landing.intersects(asteroids.get(i).getRect())) {
                asteroids.remove(i);
                this.asteroid_counter -= 1;
            }
            if (asteroids.size()==0) break;

            if (i+1<this.asteroids.size()){
                for(int j=i+1; j<this.asteroids.size(); j++) {
                    if (asteroids.get(i).getRect().intersects(asteroids.get(j).getRect())) {
                        asteroids.remove(i);
                        this.asteroid_counter -=1;
                    }
                }
            }
        }
    }

    /**
     * Metoda która zatrzymuje grę na pewien czas i zmienia ikonę statku na wybuch
     */
    private void boom(){
        this.lander.landerImageChange(Image.Boom);
        this.timer.stop();
        BoomCounter();
    }

    /**
     *Metoda odpowiadająca za odliczanie w oknie gry
     */
    private void BoomCounter()
    {
        Runnable crash = this::wreckedShip;
        try {
            boomExecutor.schedule(crash, 1, SECONDS);
            boomExecutor.shutdown();
        }
        catch(Exception E){
    }
    }

    /**
     * Metoda która definiuje zachowanie okna po udanym lądowaniu
     */
    private void goodLanding(){
        if (lander.velx < 7 && lander.vely < 7) {
            countPoints();
            if (levelNum != PropertiesLoad.numberOfLevels) {
                cleanWindow();
                inGame = false;
                add(new IntroLevel(getWidth(), getHeight(), levelNum+1, leftLives, points, nick), newWindow.buttonsClickedBehaviour());
            } else {
                cleanWindow();
                inGame=false;
                add(new WonGame(getWidth(), getHeight(), nick, points), newWindow.buttonsClickedBehaviour());
            }
        } else {
            boom();
        }
    }
    /**
     * Metoda która definiuje zachowanie okna po rozbiciu statku, w zalezności od ilości żyć
     */
    private void wreckedShip(){
        if(leftLives == 0) {
            countPoints();
            inGame=false;
            add(new LostGame(getWidth(), getHeight(), points, nick), buttonsClickedBehaviour());
        }
        else{
            inGame=false;
            add(new Level(getWidth(), getHeight(), levelNum, leftLives - 1, prevPoints, nick, this.backgroundImage), buttonsClickedBehaviour());
        }
     }
    /**
     * Odpowiada za wywołanie odpowiedniej funkcji (outOfLives) gdy statkowi zabraknie paliwa
     */
    protected void noFuel(){
        if (fuelLevel <= 0){
            for(int i = 37; i<41; i++){
                keyBindings(i, "nothing");
            }
        }
    }

    /**
     * Odpowiada za zliczanie punktów
     */
    private void countPoints(){
        points = (PropertiesLoad.bonusPerFuel * (int)(fuelLevel) + (PropertiesLoad.bonusPerSecond * time) + prevPoints);
    }

    /**
     * Aplikuje zmiany wykonane przez gracza oraz odświeża okno gry
     */
    public void doOneLoop(){
        addAsteroid();
        this.update();
        this.repaint();
    }

    /**
     * Aktualizuje połozenie obiektów z gry
     */
    private void update(){
        this.lander.update();
        this.lander.setLevel(this);
        updateAsteroids();
    }

    /**
     * Aktualizuje położenie asteroid
     */
    private void updateAsteroids(){
        for (int i =0; i<asteroids.size(); i++){
            asteroids.get(i).update();
        }
    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi || (pauza)
     * @return actionListener- obiekt klasy ActionListener
     */
    private ActionListener pauseButtonListener(JButton continueButton, JButton exitButton, JButton pauseButton) {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pauseBehaviour();
            }
        };
        return actionListener;
    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi CONTINUE
     * @return actionListener- obiekt klasy ActionListener
     */
    private ActionListener continueButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resumeBehaviour();
            }
        };
        return actionListener;
    }

    /**
     * Odpowiada za wyłączenie pauzy i schowanie oraz pokazanie odpowiednich przycisków
     */
    private void resumeBehaviour(){
        pauseButton.setVisible(true);
        resume();
        continueButton.setVisible(false);
        exitButton.setVisible(false);
    }
    /**
     * Odpowiada za włączenie pauzy i schowanie oraz pokazanie odpowiednich przycisków
     */
    private void pauseBehaviour(){
        continueButton.setVisible(true);
        exitButton.setVisible(true);
        pauseButton.setVisible(false);
        pause();
    }

    /**
     * Odpowiada za przypisanie akcji przyciskowi EXIT
     * @return actionListener - obiekt klasy ActionListener
     */
    private ActionListener exitButtonListener() {
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanWindow();
                timer.stop();
                add(new Menu(), newWindow.buttonsClickedBehaviour());
            }
        };
        return actionListener;
    }

    /**
     * Odpowiada za stworzenie obiektu Action i wybranie odpowiedniej metody dla klikniętego klawisza
     * @param action - string określajcy która akcja ma się wykonać
     * @return newAction - obiekt klasy Action
     */
    private Action action(String action){
        Action newAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                switch(action){
                    case "move up": lander.moveUp();
                        break;
                    case "move left": lander.moveLeft();
                        break;
                    case "move right": lander.moveRight();
                        break;
                    case "move down": lander.moveDown();
                        break;
                    case "pause": pauseBehaviour();
                        break;
                    case "resume": resumeBehaviour();
                        break;
                    case "nothing":
                        break;
                }
            }
        };
        return newAction;
    }

    /** Odpowiada za wyczyszczenie i odświeżenie ekranu, wywoływana tylko w metodzie shipWrecked
     *  @return gbc - obiekt klasy GridBagConstraints
     */
    private GridBagConstraints buttonsClickedBehaviour(){
        removeAll();
        repaint();
        revalidate();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    /**
     * Odpowiada za obłsugę klawiszy
     * @param keyCode - kod klawisza
     * @param keyName - nazwa klawisza
     */
    private void keyBindings(int keyCode, String keyName){
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke(keyCode, 0,false), keyName);
        this.getActionMap().put(keyName, action(keyName));
    }

    /**
     * Odświeża kekst w JLabelach wyświetlanych na ekranie
     * @param label który tekst odświeżyć
     */
    public void labelUpdate(String label){
        DecimalFormat df = new DecimalFormat("#.##");
        switch(label){
            case "vx": vx.setText("H. Speed: " + df.format(lander.velx));
            break;
            case "vy": vy.setText("V. Speed: " + df.format(lander.vely));
            break;
            case "lives": leftLandersLabel.setText(": " + leftLives);
            break;
            case "fuel": fuelLabel.setText("Fuel: "+ fuelLevel);
            break;
            case "time": timeLabel.setText("Left time: " + time+ " sec");
            break;
        }
        super.update(this.getGraphics());
    }

    /**
     * Aktualizuje poziom paliwa w progres barze wyswietlanym na ekranie
     */
    public void BarUpdate(){
        fuelBar.setValue((int)fuelLevel);
        super.update(this.getGraphics());
    }
    /**
     * Odpowiada za wywołanie metody obiektu klasy NewWindow służącej do usunięcia wszystkich elemntów z obecnego JPanelu
     */
    private void cleanWindow(){
        newWindow.layoutMaker(this);
    }
}



