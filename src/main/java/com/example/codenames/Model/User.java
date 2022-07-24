package com.example.codenames.Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class User{
    private Long userID;
    private String username;
    private String hashedPassword;
    private Long gamesWon = 0L;
    private Long gamesLost = 0L;
    private Long gamesPlayed = 0L;
    private double winningRate = 0.0;
    private Long blackWordCounter = 0L;
    private Date registrationDate;
    private Role role = Role.PLAYER;

    public User(String username, String password) {
        this.username = username;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] bytes = password.getBytes();
            hashedPassword = hexToString(md.digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User(Long userID, String username, String hashedPassword, Long gamesWon, Long gamesLost, Long gamesPlayed, double winningRate, Long blackWordCounter, Date registrationDate, Role role) {
        this.userID = userID;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesPlayed = gamesPlayed;
        this.winningRate = winningRate;
        this.blackWordCounter = blackWordCounter;
        this.registrationDate = registrationDate;
        this.role = role;
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

    public Long getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Role getRole() {
        return role;
    }
}
