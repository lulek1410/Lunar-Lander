package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Klasa implementująca działanie serwera
 */
public class Server {
    /**Zmienna przechowująca port serwera*/
    int port;
    /**Gniazdo komunikacji sieciowej po stronie odbiorcy*/
    ServerSocket ss;
    /** Obiekt klasy ScheduledExecutorService **/
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    /**
     * Konstruktor serwera przydzielający numer portu pobrany z pliku konfiguracyjnego
     */
    public Server() throws IOException {
        PropertiesLoad.loadPort();
        port = PropertiesLoad.port;
    }

    /**
     * Metoda odbierająca połączenie od klienta
     * @throws IOException
     */
    public void run() throws IOException {
        ss = new ServerSocket(port);
        ss.setReuseAddress(true);
        Runnable timeOn = () -> {
            try {
                    Socket clientSocket = ss.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String fromClient = in.readLine();
                    if (fromClient != null) messagesFromClient(out, fromClient);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };
        executor.scheduleAtFixedRate(timeOn, 1, 100, MILLISECONDS);
    };

    /**
     * Metoda przesyłająca wiadomości z serwera do listy w konsoli programu
     * @param out - obiekt klasy PrintWriter przechowujący wiadomości ze strumienia wyjściowego
     * @param fromClient - wiadomosc od klienta
     * @throws IOException
     */
    private void messagesFromClient(PrintWriter out, String fromClient) throws IOException {
        ServerScreen.addMessage("From client: " + fromClient);
        String serverRespond = ServerCommands.serverAction(fromClient);
        out.println(serverRespond);
        out.flush();
        ServerScreen.addMessage("Server respond: " + serverRespond);
    }
}
