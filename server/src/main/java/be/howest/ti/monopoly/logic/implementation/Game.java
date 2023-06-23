package be.howest.ti.monopoly.logic.implementation;

import java.util.*;

public class Game {
    private final String id;
    private final List<Player> players;
    private int maxPlayers;
    private boolean started;
    private final List<Tile> tiles;
    private String currentPlayer;
    public String directSale;
    public List<Integer> previousRoll;
    public List<Turn> turns;


    public Game(String prefix, int maxPlayers, List<Tile> tiles, Integer amountOfGames) {
        this.id = prefix + "_" + amountOfGames.toString();
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.started = false;
        this.tiles = tiles;
        this.previousRoll = new ArrayList<>();
        this.directSale = null;
        this.turns = new ArrayList<>();
    }

    public void archiveTurn() {
        Turn turn = new Turn(this.currentPlayer, new ArrayList<>(this.previousRoll));
        this.turns.add(turn);
    }

    public String getId() {
        return this.id;
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(String name) {
        for(Player player : players) {
            if (player.getName().equals(name))
                return player;
        }
        return null;
    }

    public String getPrefix() {
        return this.id.substring(0, this.id.indexOf("_"));
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void start() {
        this.started = true;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public String getDirectSale() {
        return this.directSale;
    }

    public List<Integer> getPreviousRoll() { return this.previousRoll; }

    public Tile getTile(String tileName) {
        for (Tile tile : this.tiles) {
            if (tile.getName().equals(tileName))
                return tile;
        }
        return null;
    }

    public Tile getTile(int position) {
        return tiles.get(position);
        /*
        for (Tile tile : this.tiles) {
            if (tile.getPosition() == position)
                return tile;
        }
        return null;
         */
    }

    public boolean isStarted() {
        return this.started;
    }

    public void addPlayer(Player player) {
        if (this.currentPlayer == null)
            this.currentPlayer = player.getName();

        this.players.add(player);

        if (this.numberOfPlayers() == this.maxPlayers)
            this.start();
    }

    public int randomDiceResult() {
        Random random = new Random();
        previousRoll.clear();
        previousRoll.add(0,random.nextInt(7-1) + 1);
        previousRoll.add(1,random.nextInt(7-1) + 1);
        return previousRoll.get(0) + previousRoll.get(1);
    }

    private void changeDirectSale(Player player) {
        Tile currentTile = tiles.get(player.getCurrentPosition());

        if (currentTile instanceof Property && !((Property)currentTile).isPurchased())
            this.directSale = currentTile.getName();
        else
            this.directSale = null;
    }

    private void checkBalance(Player player) {
        if (player.getBalance() < 0)
            bankruptPlayer(player.getName());
    }

    private void changeCurrentPlayer(Player player) {
        int indexOfCurrentPlayer = players.indexOf(player);
        if (indexOfCurrentPlayer + 1 > maxPlayers - 1)
            this.currentPlayer = players.get(0).getName();
        else
            this.currentPlayer = players.get(indexOfCurrentPlayer + 1).getName();

        if (getPlayer(getCurrentPlayer()).isBankrupt())
            changeCurrentPlayer(getPlayer(getCurrentPlayer()));
    }

    public void rollDice(String playerName) {
        Player currentTurnPlayer = getPlayer(playerName);
        int diceResult = randomDiceResult();
        checkBalance(currentTurnPlayer);
        movePlayer(currentTurnPlayer, diceResult);
        changeDirectSale(currentTurnPlayer);

        if (directSale == null) {
            archiveTurn();
            inspectRoll(currentTurnPlayer);
            checkBalance(currentTurnPlayer);
        }
    }

    private void inspectRoll(Player player) {
        if (!isDoubleRoll())
            changeCurrentPlayer(player);
    }

    private boolean isDoubleRoll() {
        return Objects.equals(previousRoll.get(0), previousRoll.get(1));
    }
    public void rollDice(String playerName, int diceResult) {
        Player currentTurnPlayer = getPlayer(playerName);
        movePlayer(currentTurnPlayer, diceResult);
        this.previousRoll.add(0, 6);
        this.previousRoll.add(1, 4);

        changeDirectSale(currentTurnPlayer);

        if (directSale == null) {
            archiveTurn();
            inspectRoll(currentTurnPlayer);
            checkBalance(currentTurnPlayer);
        }
    }

    public Player findPropertyOwner(Property property) {
        for (Player player : this.players) {
            if (player.getProperties().contains(property))
                return player;
        }
        return null;
    }

    public void collectRent(Player player) {
        Property property = (Property) getTile(player.getCurrentPosition());
        findPropertyOwner(property).addFunds(property.getRent());
        player.removeFunds(property.getRent());
    }

    public boolean isPlayerOnSomeonesProperty(Player player) {
        Tile tile = getTile(player.getCurrentPosition());
        if (tile instanceof Property)
            return ((Property)tile).isPurchased();
        return false;
    }

    public void handleSpecialTiles(Player player) {
        switch (getTile(player.getCurrentPosition()).getType()) {
            case "tax income":
                player.removeFunds(200);
                break;

            case "go to jail":
                player.move(getTile("Jail"));
                break;
        }
    }

    public void movePlayer(Player player, int diceResult) {
        archiveTurn();

        int currentPlayerPosition = player.getCurrentPosition();
        currentPlayerPosition += diceResult;

        if (currentPlayerPosition > 40) {
            player.addFunds(200);
            currentPlayerPosition -= 40;
        }

        player.move(getTile(currentPlayerPosition));

        if (this.isPlayerOnSomeonesProperty(player))
            collectRent(player);

        if (player.isOnSpecialTile())
            handleSpecialTiles(player);
    }

    public void purchaseDirectSale(String playerName) {
        Player player = getPlayer(playerName);
        Property property = (Property) getTile(player.getCurrentTile());

        if (property.getName().equals(this.directSale)) {
            player.addProperty(property);

            property.setPurchased();
            this.directSale = null;
            inspectRoll(player);
            checkBalance(player);
        }
    }

    public void refuseDirectSalePurchase(String playerName) {
        this.directSale = null;
        inspectRoll(getPlayer(playerName));
        checkBalance(getPlayer(playerName));
    }

    public void bankruptPlayer(String playerName) {
        Player player = getPlayer(playerName);
        player.bankruptPlayer();

        if (this.directSale != null && this.currentPlayer.equals(playerName))
            this.directSale = null;

        changeCurrentPlayer(player);
    }
}
