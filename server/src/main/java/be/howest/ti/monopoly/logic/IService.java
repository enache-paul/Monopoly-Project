package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.logic.implementation.Tile;

import java.util.List;

public interface IService {
    String getVersion();

    List<Tile> getTiles();

    Tile getTile(String name);

    Tile getTile(int position);

    List<String> getCommunityChestCard();

    List<String> getChance();

    List<Game> createGame(String prefix, int numberOfPlayers);

    String joinGame(String playerName, String gameId);

    List<Game> getGames();

    String buyProperty(String gameId, String playerName);

    Game rollDice(String playerName, String gameId);

    String dontBuyProperty(String gameId, String playerName);

    Game getGame(String gameId);

    List<Game> getGames(String prefix, int numberOfPlayers, boolean isStarted);
    
    String bankruptPlayer(String gameId, String playerName);
}


