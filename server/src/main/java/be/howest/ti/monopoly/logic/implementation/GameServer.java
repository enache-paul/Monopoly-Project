package be.howest.ti.monopoly.logic.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameServer {
    private final Map<String, Game> games;
    private final InputValidator inputValidator;

    public GameServer() {

        this.games = new HashMap<>();
        this.inputValidator = new InputValidator();
    }

    public void createGame(String prefix, int numberOfPlayers, List<Tile> tiles) {
        Game newGame = new Game(prefix, numberOfPlayers, tiles, games.size());
        games.put(newGame.getId(), newGame);
    }

    public void joinGame(String playerName, String gameId) {
        inputValidator.validateJoinGameInput(getGame(gameId), playerName);
        Player player = new Player(playerName);
        games.get(gameId).addPlayer(player);
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public List<Game> getGames() {
        List<Game> listOfGames = new ArrayList<>();
        games.forEach((key, value) -> listOfGames.add(value));

        return listOfGames;
    }

    public List<Game> getGames(String prefix, int numberOfPlayers, boolean isStarted) {
        List<Game> listOfGames = new ArrayList<>();

        games.forEach((key, value) -> {
            System.out.println(value.getPrefix());
            if (value.getPrefix().equals(prefix) && value.getMaxPlayers() == numberOfPlayers && Boolean.compare(value.isStarted(), isStarted) == 0)
                listOfGames.add(value);
        });

        return listOfGames;
    }


    public void rollDice(String playerName, String gameId) {
        inputValidator.validateRollInput(getGame(gameId), playerName);
        games.get(gameId).rollDice(playerName);
    }

    public void buyProperty(String gameId, String playerName) {
        inputValidator.validatePurchaseInput(getGame(gameId), playerName);
        getGame(gameId).purchaseDirectSale(playerName);
    }

    public void dontBuyProperty(String gameId, String playerName) {
        inputValidator.validatePurchaseInput(getGame(gameId), playerName);
        getGame(gameId).refuseDirectSalePurchase(playerName);
    }

    public void bankruptPlayer(String gameId, String playerName) {
        getGame(gameId).bankruptPlayer(playerName);
    }
}
