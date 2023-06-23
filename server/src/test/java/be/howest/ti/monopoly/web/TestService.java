package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.IService;
import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.*;

import java.util.List;


public class TestService implements IService {

    IService delegate = new ServiceAdapter();

    void setDelegate(IService delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getVersion() {
        return delegate.getVersion();
    }

    @Override
    public List<Tile> getTiles() {return delegate.getTiles();}

    @Override
    public Tile getTile(String name) { return delegate.getTile(name);}

    @Override
    public Tile getTile(int position) { return delegate.getTile(position);}

    @Override
    public  List<String> getCommunityChestCard() { return delegate.getCommunityChestCard();}

    @Override
    public List<String> getChance() {return delegate.getChance();}

    @Override
    public List<Game> createGame(String prefix, int numberOfPlayers) { return delegate.createGame(prefix, numberOfPlayers); }

    @Override
    public String joinGame(String playerName, String gameId) { return delegate.joinGame(playerName, gameId); }

    @Override
    public List<Game> getGames() { return delegate.getGames(); }

    @Override
    public List<Game> getGames(String prefix, int numberOfPlayers, boolean isStarted) {
        return delegate.getGames(prefix, numberOfPlayers, isStarted);
    }

    @Override
    public Game getGame(String gameId) { return delegate.getGame(gameId); }

    @Override
    public Game rollDice(String playerName, String gameId) {return delegate.rollDice(playerName,gameId);}

    @Override
    public String buyProperty(String gameId, String playerName) {
        return delegate.buyProperty(gameId, playerName);
    }

    @Override
    public String dontBuyProperty(String gameId, String playerName) {
        return delegate.dontBuyProperty(gameId, playerName);
    }
    @Override
    public String bankruptPlayer(String gameId, String playerName) {
        return delegate.bankruptPlayer(gameId, playerName);
    }
}
