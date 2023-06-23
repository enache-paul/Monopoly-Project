package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.Tile;
import be.howest.ti.monopoly.logic.implementation.Game;

import java.util.List;


public class ServiceAdapter implements IService {

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tile> getTiles() { throw new UnsupportedOperationException(); }

    @Override
    public Tile getTile(String name) { throw new UnsupportedOperationException(); }

    @Override
    public Tile getTile(int position) { throw new UnsupportedOperationException(); }

    @Override
    public List<String> getCommunityChestCard() { throw new UnsupportedOperationException(); }

    @Override
    public List<String> getChance() {throw new UnsupportedOperationException();}

    @Override
    public List<Game> createGame(String prefix, int numberOfPlayers) { throw new UnsupportedOperationException(); }

    @Override
    public String joinGame(String playerName, String gameId) { throw new UnsupportedOperationException(); }

    @Override
    public List<Game> getGames() { throw new UnsupportedOperationException(); }

    @Override
    public List<Game> getGames(String prefix, int numberOfPlayers, boolean isStarted) { throw new UnsupportedOperationException(); }

    @Override
    public String buyProperty(String gameId, String playerName) { throw new UnsupportedOperationException(); }

    @Override
    public Game rollDice(String playerName, String gameId) {throw new UnsupportedOperationException();}

    @Override
    public String dontBuyProperty(String gameId, String playerName) { throw new UnsupportedOperationException(); }

    @Override
    public Game getGame(String gameId) { throw new UnsupportedOperationException(); }

    public String bankruptPlayer(String gameId, String playerName) { throw new UnsupportedOperationException();}
}
