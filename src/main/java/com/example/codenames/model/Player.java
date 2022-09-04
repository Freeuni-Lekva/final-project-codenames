package com.example.codenames.model;

public class Player {

    private User user;
    private String roomID;
    private PlayerRole playerRole;

    public Player(User user, String roomID){
        this(user, roomID, PlayerRole.NOT_SELECTED);
    }
    public Player(User user, String roomID, PlayerRole playerRole) {
        this.user = user;
        this.roomID = roomID;
        this.playerRole = playerRole;
    }

    public User getUser() {
        return user;
    }

    public String getRoomID() {
        return roomID;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    public boolean equals(Player player) {
        return user.equals(player.getUser());
    }
}
