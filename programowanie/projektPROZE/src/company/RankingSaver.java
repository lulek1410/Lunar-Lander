package company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za zapis wyniku gracza do pliku
 */
public class RankingSaver {
    /**
     * Zapisuje podane wartosci nick i points do pliku "Ranking.txt" gdzie znajdują sie wszystkie wyniki graczy
     * @param nick   = nick do zapisania
     * @param points = punkty do zapisania
     * @throws IOException
     */
    static void saveToFile(String nick, int points) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Ranking.txt", true));
        writer.append(nick + "=" + points + "\n");
        writer.close();
    }

}