package com.example.codenames.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

public class User{
    public static final String TABLE_NAME = "users";
    public static final String TABLE_USER_NAME = "user_name";
    public static final String TABLE_PASSWORD = "hashed_password";
    public static final String TABLE_GAMES_WON = "games_won";
    public static final String TABLE_GAMES_LOST = "games_lost";
    public static final String TABLE_GAMES_PLAYED = "games_played";
    public static final String TABLE_WINNING_RATE = "winning_rate";
    public static final String TABLE_BLACK_WORD_SELECTED = "black_word_selected";
    public static final String TABLE_REGISTRATION_DATE = "registration_date";
    public static final String TABLE_STATUS = "status";
    public static final String ATTRIBUTE = "User_Attribute";




    private int userID;
    private String username;
    private String hashedPassword;
    private Long gamesWon = 0L;
    private Long gamesLost = 0L;
    private Long gamesPlayed = 0L;
    private double winningRate = 0.0;
    private Long blackWordCounter = 0L;
    private java.sql.Timestamp registrationDate;
    private Role role = Role.PLAYER;


    public User(String username, String password) {
        this.username = username;
        hashPassword(password);
    }

    private void hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] bytes = password.getBytes();
            hashedPassword = hexToString(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public User(int userID, String username, String password, Long gamesWon, Long gamesLost, Long gamesPlayed, double winningRate, Long blackWordCounter, java.sql.Timestamp registrationDate, Role role) {
        this.userID = userID;
        this.username = username;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesPlayed = gamesPlayed;
        this.winningRate = winningRate;
        this.blackWordCounter = blackWordCounter;
        this.registrationDate = registrationDate;
        this.role = role;
        hashPassword(password);

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

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Long getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Long gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Long getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(Long gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getWinningRate() {
        return winningRate;
    }

    public void setWinningRate(double winningRate) {
        this.winningRate = winningRate;
    }

    public Long getBlackWordCounter() {
        return blackWordCounter;
    }

    public void setBlackWordCounter(Long blackWordCounter) {
        this.blackWordCounter = blackWordCounter;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(java.sql.Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", gamesWon=" + gamesWon +
                ", gamesLost=" + gamesLost +
                ", gamesPlayed=" + gamesPlayed +
                ", winningRate=" + winningRate +
                ", blackWordCounter=" + blackWordCounter +
                ", registrationDate=" + registrationDate +
                ", role=" + role +
                '}';
    }
}