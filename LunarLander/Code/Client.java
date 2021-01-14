package company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Klasa odpowiedzialna za kontakt z serwerem. Wysyła zapytania o udostępniennie danych konfiguracyjnych i rankingu
 */
public class Client {

    private static Socket socket;
    /**Zmienna przechowująca adres ip serwera*/
    static String Address;
    /**Zmienna przechowująca numer portu serwera*/
    static int Port;
    /**Zmienna określająca czy jesteśmy online*/
    static boolean online = false;

    /**
     * Tworzy łącze z serwerem, tworzy obiekt klasy socket.
     * @param address adres ip serwera
     * @param port numer portu serwera
     * @throws UnknownHostException
     * @throws IOException
     */
    static void Connect(String address, int port) throws UnknownHostException, IOException {
        socket = new Socket(address, port);
        Address = address;
        Port = port;
        socket.close();
        System.out.println("Connected");

    }

    /**
     * Wysyła zapytania do serwera
     * @param command komenda odpowiadająca zapytaniu które chcemy wysłać do serwera
     * @return zwraca odpowiedź od serwera
     * @throws IOException
     */
    static String getProperty(String command) throws IOException {
        try {
            socket = new Socket(Address, Port);
        }
        catch(Exception e){
            online = false;
            socket.close();
            return "not";
        }
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println(command);
        InputStream in = socket.getInputStream();
        BufferedReader buf = new BufferedReader(new InputStreamReader(in));
        return buf.readLine();
    }

    /**
     * Wysyła do serwera zapytanie o dane konfiguracyjne przy uzyciu metody getProperty z odpowiednią komendą
     * @return zwraca dane konfiguracyjne pobrane z serwera
     * @throws IOException
     */
    static String getConfigs() throws IOException{
        String configs = getProperty("GetConfigs");
        socket.close();
        return configs;
    }

    /**
     * Wysyła do serwera zapytanie o dane konfiguracyjne przy uzyciu metody getRanking z odpowiednią komendą
     * @return zwraca ranking
     * @throws IOException
     */
    static String getRanking() throws IOException{
        String ranking = getProperty("GetRanking");
        socket.close();
        return ranking;
    }
    /**
     * Wysyła do serwera zapytanie o dane konfiguracyjne przy uzyciu metody getProperty z odpowiednią komendą
     * @param levelNumber numer poziomu którego dane konfiguracyjne chcemy otrzymać
     * @return zwraca dane konfiguracyjne poziomu
     * @throws IOException
     */
    static String getLevel(int levelNumber) throws IOException{
        String levelConfigs = getProperty("GetLevel-" + levelNumber);
        socket.close();
        return levelConfigs;
    }

    /**
     * Sprawdza czy program jest połączony z serwerem
     * @return
     * @throws IOException
     */
    public static String checkConnected() throws IOException {
            String check = getProperty("Check");
            socket.close();
            return check;
    }
    /**
     * Zapisuje wyniku na serwerze, w tym celu wywoluje metode connect z odpowiednim zapytaniem
     * @param nick nick gracza wraz z wynikiem odzielone znakiem "-"
     */
    public static void saveScore(String nick, int points) throws IOException {
        getProperty("SaveScore" + "-" + nick + "-" + points);
        socket.close();
    }

}
