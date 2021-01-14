package company;

import java.io.IOException;

public class ServerCommands {
    /**
     * Na podstawie żądania klienta wybierana jest odpowiednia odpowiedź
     * @param command treść żądania klienta
     * @return odpowiedź serwera
     */
    public static String serverAction(String command) throws IOException {
        String serverMessage;
        String[] commands = command.split("-");
        switch (commands[0]) {
            case "GetConfigs":
                serverMessage = PropertiesLoad.loadConfig();
                break;
            case "GetLevel":
                serverMessage = PropertiesLoad.loadMapsConfigs(Integer.parseInt(commands[1]));
                break;
            case "GetRanking":
                serverMessage = RankingLoad.passRanking();
                break;
            case "SaveScore":
                RankingSaver.saveToFile(commands[1], Integer.parseInt(commands[2]));
                serverMessage = "Score saved";
                break;
            case "Check":
                serverMessage = "connected";
                break;
            default:
                serverMessage = "Invalid command";
        }
        return serverMessage;
    }
}
