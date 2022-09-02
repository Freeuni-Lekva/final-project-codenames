package com.example.codenames.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Room {

    private Player owner;
    BlockingQueue<Player> allPlayers;
    private static final int SIZE = 10;

    public Room(Player owner, String id) {
        this.owner = owner;
        allPlayers = new ArrayBlockingQueue<>(SIZE);
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

    public BlockingQueue<Player> getAllPlayers() {
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

    public boolean addPlayer(Player player) {
        return allPlayers.add(player);
    }

    public boolean removeUser(Player player) {
        return allPlayers.remove(player);
    }
}
