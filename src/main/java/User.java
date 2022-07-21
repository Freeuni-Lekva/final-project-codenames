import java.util.Date;

public interface User {
    String getUsername();
    void setUsername(String username);
    String getHashedPassword();
    int getGamesWon();
    void setGamesWon(int gamesWon);
    int getGamesLost();
    void setGamesLost(int gamesLost);
    int getGamesPlayed();
    void setGamesPlayed(int gamesPlayed);
    double getWinningRate();
    void setWinningRate(double winningRate);
    int getBlackWordCounter();
    void setBlackWordCounter(int blackWordCounter);
    Date getRegistrationDate();
    boolean isAdmin();
}
