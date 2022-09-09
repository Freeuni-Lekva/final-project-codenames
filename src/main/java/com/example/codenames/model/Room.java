package com.example.codenames.model;

import java.util.*;

public class Room {

    private Player owner;
    private String ID;
    private Set<Player> allPlayers;
    private static final int SIZE = 10;
    private boolean available = true;

    private List<String> categories;

    public Room(Player owner, String ID) {
        this.owner = owner;
        this.ID = ID;
        allPlayers = new HashSet<>(SIZE);
        allPlayers.add(owner);
        categories = new ArrayList<>();
    }

    public Player getOwner() {
        return owner;
    }

    public String getID() {
        return ID;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Player> getRedSpymasters() {
        Iterator iterator = allPlayers.iterator();
        List<Player> redSpymasters = new ArrayList<>();
        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();
            if (player.getPlayerRole() == PlayerRole.RED_SPYMASTER) {
                redSpymasters.add(player);
            }
        }
        return redSpymasters;
    }

    public List<Player> getRedOperatives() {
        Iterator iterator = allPlayers.iterator();
        List<Player> redOperatives = new ArrayList<>();
        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();
            if (player.getPlayerRole() == PlayerRole.RED_OPERATIVE) {
                redOperatives.add(player);
            }
        }
        return redOperatives;
    }

    public List<Player> getBlueSpymasters() {
        Iterator iterator = allPlayers.iterator();
        List<Player> blueSpymasters = new ArrayList<>();
        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();
            if (player.getPlayerRole() == PlayerRole.BLUE_SPYMASTER) {
                blueSpymasters.add(player);
            }
        }
        return blueSpymasters;
    }

    public List<Player> getBlueOperatives() {
        Iterator iterator = allPlayers.iterator();
        List<Player> blueOperatives = new ArrayList<>();
        while (iterator.hasNext()) {
            Player player = (Player) iterator.next();
            if (player.getPlayerRole() == PlayerRole.BLUE_OPERATIVE) {
                blueOperatives.add(player);
            }
        }
        return blueOperatives;
    }

    public Set<Player> getAllPlayers() {
        return allPlayers;
    }

    public void addAsRedSpymaster(Player player) {
        player.setPlayerRole(PlayerRole.RED_SPYMASTER);
    }

    public void addAsRedOperative(Player player) {
        player.setPlayerRole(PlayerRole.RED_OPERATIVE);
    }

    public void addAsBlueSpymaster(Player player) {
        player.setPlayerRole(PlayerRole.BLUE_SPYMASTER);
    }

    public void addAsBlueOperative(Player player) {
        player.setPlayerRole(PlayerRole.BLUE_OPERATIVE);
    }

    public synchronized boolean addPlayer(Player player) {
        if (allPlayers.size() >= SIZE) return false;
        return allPlayers.add(player);
    }

    public synchronized boolean removePlayer(Player player) {
        return allPlayers.remove(player);
    }

    public Player getPlayerByUsername(String username) {
        for (Player player : allPlayers) {
            if (player.getUser().getUsername().equals(username)) return player;
        }
        return null;
    }

    public synchronized boolean isAvailable() {
        return available;
    }

    public synchronized void lock() {
        available = false;
    }
}
