package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.ServiceAdapter;

import java.util.List;


public class MonopolyService extends ServiceAdapter {
    private final GameServer games;

    public MonopolyService() { this.games = new GameServer(); }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public Tile getTile(String name) {
        List<Tile> tileList = getTiles();

        for(Tile tile : tileList) {
            if(tile.getName().equals(name))
                return tile;
        }
        return null;
    }

    public Tile getTile(int position) {
        List<Tile> tileList = getTiles();

        for(Tile tile : tileList) {
            if(tile.getPosition() == position)
                return tile;
        }
        return null;
    }

    @Override
    public List<Tile> getTiles() {
        return List.of(
                new Tile("Go", 0, "go"),
                new Street( "Mediterranean", "PURPLE", 1, 60, 30, 10, "street"),
                new Tile("Community Chest I", 2, "community chest"),
                new Street("Baltic", "PURPLE", 3, 60, 30, 10, "street"),
                new Tile("Tax Income", 4, "tax income"),
                new Railroad("Reading RR", "BLACK", 5, 200, 100,25,"railroad"),
                new Street("Oriental", "LIGHTBLUE", 6, 100, 50, 14, "street"),
                new Tile("Chance I", 7, "chance"),
                new Street("Vermont", "LIGHTBLUE",8, 100, 50, 14, "street"),
                new Street("Connecticut","LIGHTBLUE", 9, 120, 60, 18, "street"),
                new Tile("Jail", 10, "jail"),
                new Street("Saint Charles Place","VIOLET", 11, 140, 70, 20, "street"),
                new Utility("Electric Company", "WHITE", 12, 150,75, 25, "utility"),
                new Street("States", "VIOLET",13, 140, 70, 22, "street"),
                new Street("Virginia", "VIOLET",14, 160, 80, 23, "street"),
                new Railroad("Pennsylvania RR", "BLACK", 15, 200,100, 25,"railroad"),
                new Street("Saint James", "ORANGE", 16, 180, 90, 25, "street"),
                new Tile("Community Chest II", 17, "community chest"),
                new Street("Tennessee", "ORANGE", 18, 180, 90, 25, "street"),
                new Street("New York", "ORANGE", 19, 200, 100, 26, "street"),
                new Tile("Free Parking", 20, "free parking"),
                new Street("Kentucky Avenue", "RED",21, 220, 110, 27, "street"),
                new Tile("Chance II", 22, "chance"),
                new Street("Indiana Avenue", "RED",23, 220, 110, 30, "street"),
                new Street("Illinois Avenue", "RED",24, 240, 120, 30, "street"),
                new Railroad("Baltimore and Ohio RR", "BLACK",25,200,100, 25, "railroad"),
                new Street("Atlantic", "YELLOW",26, 260, 130, 35, "street"),
                new Street("Ventnor", "YELLOW", 27, 260, 130, 37, "street"),
                new Utility("Water Works", "WHITE",28, 150, 75, 25,"utility"),
                new Street("Marvin Gardens", "YELLOW", 29, 280, 140, 40, "street"),
                new Tile("Go to Jail", 30, "go to jail"),
                new Street("Pacific", "DARKGREEN", 31, 300, 150, 50, "street"),
                new Street("North Carolina", "DARKGREEN", 32, 300, 150, 55, "street"),
                new Tile("Community Chest III", 33, "community chest"),
                new Street("Pennsylvania", "DARKGREEN", 34, 320, 160, 60, "street"),
                new Railroad("Short Line RR", "BLACK", 35, 200, 100, 25, "railroad"),
                new Tile("Chance III", 36, "chance"),
                new Street("Park Place", "DARKBLUE", 37, 350, 175, 65, "street"),
                new Tile("Luxury Tax", 38, "tax income"),
                new Street("Boardwalk", "DARKBLUE", 39, 400, 200, 75, "street")
        );
    }

    @Override
    public List<String> getCommunityChestCard(){
        return List.of(
                "Advance to Go (Collect $200)",
                "Bank error in your favor, collect $200",
                "Doctor's fee. Pay $50",
                "From sale of stock you get $50",
                "Get out of Jail Free",
                "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
                "Holiday fund matures. Collect $20",
                "Income tax refund. Collect $200",
                "It is your birthday. Collect $10 from each player",
                "Life insurance matures. Collect $100",
                "Pay hospital fees of $100",
                "Pay school fees of $50",
                "Receive $25 consultancy fee",
                "You are assessed for street repair. $40 per house. $115 per hotel",
                "You have won second prize in a beauty contest. Collect $10",
                "You inherit $100"
        );
    }

    @Override
    public List<String> getChance() {
        return List.of(
                "Advance to Boardwalk",
                "Advance to Go (Collect $200)",
                "Advance to Illinois Avenue. If you pass Go, collect $200",
                "Advance to St. Charles Place. If you pass Go, collect $200",
                "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled",
                "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown",
                "Bank pays you dividend of $50",
                "Get Out of Jail Free",
                "Go Back 3 Spaces",
                "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200",
                "Make general repairs on all your property. For each house pay $25. For each hotel pay $100",
                "Speeding fine $15",
                "Take a trip to Reading Railroad. If you pass Go, collect $200",
                "You have been elected Chairman of the Board. Pay each player $50",
                "Your building loan matures. Collect $150"
        );
    }

    @Override
    public List<Game> createGame(String prefix, int numberOfPlayers) {
        this.games.createGame(prefix, numberOfPlayers, getTiles());
        return getGames();
    }

    @Override
    public String joinGame(String playerName, String gameId) {
        this.games.joinGame(playerName, gameId);
        return "The game has been found.";
    }

    @Override
    public List<Game> getGames() {
        return this.games.getGames();
    }

    @Override
    public List<Game> getGames(String prefix, int numberOfPlayers, boolean isStarted) {
        return this.games.getGames(prefix, numberOfPlayers, isStarted);
    }


    @Override
    public String buyProperty(String gameId, String playerName) {
        this.games.buyProperty(gameId, playerName);
        return "Property has been bought by " + playerName;
    }

    @Override
    public String dontBuyProperty(String gameId, String playerName) {
        this.games.dontBuyProperty(gameId, playerName);
        return "Property has not been bought.";
    }

    @Override
    public Game getGame(String gameId) {
        return this.games.getGame(gameId);
    }

    @Override
    public Game rollDice(String playerName, String gameId) {
        this.games.rollDice(playerName, gameId);
        return getGame(gameId);
    }
    @Override
    public String bankruptPlayer(String gameId, String playerName) {
        this.games.bankruptPlayer(gameId, playerName);
        return  "player has been bankrupt";
    }
}
