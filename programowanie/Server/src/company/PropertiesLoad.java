package company;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * Klasa odpowiedzialna za wczytywanie danych z plików konfiguracyjnych i rankingu do serwera
 */
public class PropertiesLoad {
    static int port;

    /**
     * Wczytuje z pliku konfiguracyjnego numer portu
     * @throws IOException
     */
    static void loadPort() throws IOException {
        InputStream propertiesFile = new FileInputStream("txtFiles/Config.txt");
        Properties port_prop = new Properties();
        port_prop.load(propertiesFile);
        port = Integer.parseInt(port_prop.getProperty("port"));
        propertiesFile.close();
    }

    /**
     * Wczytuje dane z pliku konfiguracyjnego
     * @return zwraca dane z pliku konfiguracyjnego umieszczone wjendym stringu
     * @throws IOException
     */
    static String loadConfig() throws IOException{
        InputStream propertiesFile = new FileInputStream("txtFiles/Config.txt");
        Properties properties = new Properties();
        properties.load(propertiesFile);
        String configs = "";
        for(int i=1; i<9; i++){
            if(i<9){
                configs += properties.getProperty("prop" + i) + ";";
            }
            else{configs += properties.getProperty("prop" + i);}
        }
        return configs;
    }

    /**
     * Wczytuje dane z pliku konfiguracyjnego dla map
     * @param levelNumber numer mapy która ma zostać wczytana
     * @return zwraca dane konfiguracyjne mapy zamieszczone w jednym stringu
     * @throws IOException
     */
    static String loadMapsConfigs(int levelNumber) throws IOException{
        InputStream propertiesFile = new FileInputStream("txtFiles/Maps.txt");
        Properties mapProps = new Properties();
        mapProps.load(propertiesFile);
        int[] xPoints = Arrays.stream(mapProps.getProperty("xpoints"+levelNumber).split("-")).mapToInt(Integer::parseInt).toArray();
        int[] yPoints = Arrays.stream(mapProps.getProperty("ypoints"+levelNumber).split("-")).mapToInt(Integer::parseInt).toArray();
        int[] xLanding = Arrays.stream(mapProps.getProperty("xlanding"+levelNumber).split("-")).mapToInt(Integer::parseInt).toArray();
        int[] yLanding = Arrays.stream(mapProps.getProperty("ylanding"+levelNumber).split("-")).mapToInt(Integer::parseInt).toArray();
        float mapGravity = Float.parseFloat(mapProps.getProperty("gravity"+levelNumber));
        return (Arrays.toString(xPoints) + ";" +  Arrays.toString(yPoints) + ";" + Arrays.toString(xLanding) + ";" +
                Arrays.toString(yLanding) + ";" + Float.toString(mapGravity)).replace("[","")
                .replace("]","").replace(",", "").trim();
    }
}
