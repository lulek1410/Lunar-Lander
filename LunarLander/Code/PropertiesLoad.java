package company;


import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Klasa odpowiedzialna za wczytywanie danych z plików i serwera (na razie tylko pliki, docelowo również z serwera)
 */
public class PropertiesLoad {
    /** Okresla startowa szerokosc okna */
    static int xSize;
    /** Okresla startowa wysokosc okna */
    static int ySize;
    /** Okresla startowa wysokosc okna */
    static int LanderWidth;
    /** Okresla odpowiednia predkosc w plaszczyznie X */
    static float enginePowerVx;
    /** Okresla odpowiednia predkosc w plaszczyznie Y */
    static float enginePowerVy;
    /** Okresla poczatkowa ilosc paliwa */
    static int fuelAmount;
    /** Okresla ilosc statkow */
    static int numberOfLives;
    /** Okresla ilosc punktow przyznawanych za pozostale sekundy */
    static int bonusPerSecond;
    /** Określa ilość punktów przyznawanych za pozostałe paliwo*/
    static int bonusPerFuel;
    /** tablica przechowujaca x'owe wspolrzedne wierzcholkow wielokata bedacych powierzchnia ksiezyca */
    static int[] xPoints;
    /** tabblica przechowujaca y'owe wspolrzedne wierzcholkow wielokata bedacych powierzchnia ksiezcya */
    static int[] yPoints;
    /** tablica przechowujaca x'owe wspolrzedne wierzcholkow wielokata bedacego ladowiskiem */
    static int[] xLanding;
    /** tablica przechowujaca y'owe wspolrzedne wierzcholkow wielokata bedacego ladowiskiem */
    static int[] yLanding;
    /** Określa przyśpieszenie grawitacyjne na danej mapie*/
    static float mapGravity;
    /** Ilość poziomów*/
    static int numberOfLevels;
    /** Metoda wczytuje dane z lokalnego pliku i zapisuje do odpowiednich pól w klasie
     * @throws IOException
     */
    static void loadNecessaryProps() throws IOException {
        InputStream propertiesFile = new FileInputStream("txtFiles/NecessaryConfig.txt");
        Properties gameProps = new Properties();
        gameProps.load(propertiesFile);
        xSize = Integer.parseInt(gameProps.getProperty("xSize"));
        ySize = Integer.parseInt(gameProps.getProperty("ySize"));
        propertiesFile.close();
    }
     /** Metoda wczytuje dane z lokalnego pliku i zapisuje do odpowiednich pól w klasie
     * @throws IOException
     */
    static void loadProps() throws IOException {

            InputStream propertiesFile = new FileInputStream("txtFiles/Config.txt");
            Properties gameProps = new Properties();
            gameProps.load(propertiesFile);
            LanderWidth = Integer.parseInt(gameProps.getProperty("LanderWidth"));
            enginePowerVx = Float.parseFloat(gameProps.getProperty("enginePowerVx"));
            enginePowerVy = Float.parseFloat(gameProps.getProperty("enginePowerVy"));
            fuelAmount = Integer.parseInt(gameProps.getProperty("fuelAmount"));
            numberOfLives = Integer.parseInt(gameProps.getProperty("numberOfLives"));
            bonusPerSecond = Integer.parseInt(gameProps.getProperty("bonusPerSecond"));
            bonusPerFuel = Integer.parseInt(gameProps.getProperty("bonusPerFuel"));
            numberOfLevels = Integer.parseInt(gameProps.getProperty("numberOfLevels"));
            propertiesFile.close();
        }

    /** Metoda wczytuje dane z serwera i zapisuje do odpowiednich pól w klasie
     * @throws IOException
     */
    static void loadPropsServer() throws IOException {
            String serv_response = Client.getConfigs();
            double[] configs = Arrays.stream(serv_response.split(";")).mapToDouble(Double::parseDouble).toArray();
            LanderWidth = (int)configs[0];
            enginePowerVx = (int)configs[1];
            enginePowerVy = (float)configs[2];
            fuelAmount = (int)configs[3];
            numberOfLives = (int)configs[4];
            numberOfLevels = (int)configs[5];
            bonusPerSecond = (int)configs[6];
            bonusPerFuel = (int)configs[7];
    }

    /**
     * Metoda wczytująca współrzędne ukształtowania planet (Hit Box) i jej współczynnik grawitacji z pliku lokalnego
     * @param levelnumber - numer poziomu, którego ww. dane mają zostać wczytane
     * @throws IOException
     */
        static void loadMapsConfigs(int levelnumber) throws IOException{
            InputStream propertiesFile_maps = new FileInputStream("txtFiles/Maps.txt");
            Properties mapProps = new Properties();
            mapProps.load(propertiesFile_maps);
            xPoints = Arrays.stream(mapProps.getProperty("xpoints"+levelnumber).split("-")).mapToInt(Integer::parseInt).toArray();
            yPoints = Arrays.stream(mapProps.getProperty("ypoints"+levelnumber).split("-")).mapToInt(Integer::parseInt).toArray();
            xLanding = Arrays.stream(mapProps.getProperty("xlanding"+levelnumber).split("-")).mapToInt(Integer::parseInt).toArray();
            yLanding = Arrays.stream(mapProps.getProperty("ylanding"+levelnumber).split("-")).mapToInt(Integer::parseInt).toArray();
            mapGravity = Float.parseFloat(mapProps.getProperty("gravity"+levelnumber));
            propertiesFile_maps.close();
        }

    /**
     * Metoda wczytująca współrzędne ukształtowania planet (Hit Box) i jej współczynnik grawitacji z serwera
     * @param levelnumber - numer poziomu, którego ww. dane mają zostać wczytane
     * @throws IOException
     */
    static void loadMapsConfigsServer(int levelnumber) throws IOException{
            String serv_response = Client.getLevel(levelnumber);
            String[] configs = serv_response.split(";");
            xPoints = Arrays.stream(configs[0].split(" ")).mapToInt(Integer::parseInt).toArray();
            yPoints = Arrays.stream(configs[1].split(" ")).mapToInt(Integer::parseInt).toArray();
            xLanding = Arrays.stream(configs[2].split(" ")).mapToInt(Integer::parseInt).toArray();
            yLanding = Arrays.stream(configs[3].split(" ")).mapToInt(Integer::parseInt).toArray();
            mapGravity = Float.parseFloat(configs[4]);

    }



}