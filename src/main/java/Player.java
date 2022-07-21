import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Player implements User{
    private String username;
    private String hashedPassword;
    private int gamesWon;
    private int gamesLost;
    private int gamesPlayed;
    private double winningRate;
    private int blackWordCounter;
    private Date registrationDate;
    private boolean isAdmin;

    public Player(String username, String password) {
        this.username = username;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] bytes = password.getBytes();
            hashedPassword = hexToString(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;
            if (val < 16) buff.append('0');
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getHashedPassword() {
        return hashedPassword;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    @Override
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    @Override
    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    @Override
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getWinningRate() {
        return winningRate;
    }

    @Override
    public void setWinningRate(double winningRate) {
        this.winningRate = winningRate;
    }

    @Override
    public int getBlackWordCounter() {
        return blackWordCounter;
    }

    @Override
    public void setBlackWordCounter(int blackWordCounter) {
        this.blackWordCounter = blackWordCounter;
    }

    @Override
    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }
}
