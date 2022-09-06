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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Player)) return false;
        Player other = (Player) obj;
        return other.user.equals(user);
    }

    @Override
    public int hashCode() {
        return user.getUsername().hashCode();
    }
}
