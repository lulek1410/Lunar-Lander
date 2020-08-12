package company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasa zapisująca wyniki do plilu
 */
public class RankingSaver {
    /**
     * Metoda zapisująca wyniki do pliku
     * @param nick - nick gracza
     * @param points - ilość zdobytych punktów
     * @throws IOException
     */
    static void saveToFile(String nick, int points) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Ranking.txt", true));
        writer.append(nick + "=" + points + "\n");
        writer.close();
    }
}
