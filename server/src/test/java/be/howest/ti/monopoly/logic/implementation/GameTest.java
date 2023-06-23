package be.howest.ti.monopoly.logic.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private List<Tile> tiles;
    private Player matt;
    private Player tom;

    @BeforeEach
    void setUp() {
        tiles = List.of(
                new Tile("Go", 0, "go"),
                new Street( "Mediterranean", "PURPLE", 1, 20, 20, 20, "street"),
                new Tile("Community Chest I", 2, "community chest"),
                new Street("Baltic", "PURPLE", 3, 20, 20, 20, "street"),
                new Tile("Tax Income", 4, "tax income"),
                new Railroad("Reading RR", "BLACK", 5, 200, 100,25,"railroad"),
                new Street("Oriental", "LIGHTBLUE", 6, 20, 20, 20, "street"),
                new Tile("Chance I", 7, "chance"),
                new Street("Vermont", "LIGHTBLUE",8, 20, 20, 20, "street"),
                new Street("Connecticut","LIGHTBLUE", 9, 20, 20, 20, "street"),
                new Tile("Jail", 10, "jail"),
                new Street("Saint Charles Place","VIOLET", 11, 20, 20, 20, "street"),
                new Utility("Electric Company", "WHITE", 12, 15,75, 20, "utility"),
                new Street("States", "VIOLET",13, 20, 20, 20, "street"),
                new Street("Virginia", "VIOLET",14, 20, 20, 20, "street"),
                new Railroad("Pennsylvania RR", "BLACK", 15, 200,100, 25,"railroad"),
                new Street("Saint James", "ORANGE", 16, 20, 20, 20, "street"),
                new Tile("Community Chest II", 17, "community chest"),
                new Street("Tennessee", "ORANGE", 18, 20, 20, 20, "street"),
                new Street("New York", "ORANGE", 19, 20, 20, 20, "street"),
                new Tile("Free Parking", 20, "free parking"),
                new Street("Kentucky Avenue", "RED",21, 20, 20, 20, "street"),
                new Tile("Chance II", 22, "chance"),
                new Street("Indiana Avenue", "RED",23, 20, 20, 20, "street"),
                new Street("Illinois Avenue", "RED",24, 20, 20, 20, "street"),
                new Railroad("Baltimore and Ohio RR", "BLACK",25,200,100, 25, "railroad"),
                new Street("Atlantic", "YELLOW",26, 20, 20, 20, "street"),
                new Street("Ventnor", "YELLOW", 27, 20, 20, 20, "street"),
                new Utility("Water Works", "WHITE",28, 150, 75, 20,"utility"),
                new Street("Marvin Gardens", "YELLOW", 29, 20, 20, 20, "street"),
                new Tile("Go to Jail", 30, "go to jail"),
                new Street("Pacific", "DARKGREEN", 31, 20, 20, 20, "street"),
                new Street("North Carolina", "DARKGREEN", 32, 20, 20, 20, "street"),
                new Tile("Community Chest III", 33, "community chest"),
                new Street("Pennsylvania", "DARKGREEN", 34, 20, 20, 20, "street"),
                new Railroad("Short Line RR", "BLACK", 35, 200, 100, 25, "railroad"),
                new Tile("Chance III", 36, "chance"),
                new Street("Park Place", "DARKBLUE", 37, 20, 20, 20, "street"),
                new Tile("Luxury Tax", 38, "tax income"),
                new Street("Boardwalk", "DARKBLUE", 39, 20, 20, 20, "street")
        );
        game = new Game("group39",2, tiles, 0);
        matt = new Player("Matt");
        tom = new Player("Tom");
    }
    @Test
    void joinGame() {
        game.addPlayer(tom);
        assertEquals(1, game.getPlayers().size());
        game.addPlayer(matt);
        assertEquals(2, game.getPlayers().size());
    }

    @Test
    void isStartedWhenFull() {
        assertFalse(game.isStarted());

        game.addPlayer(matt);
        game.addPlayer(tom);

        assertTrue(game.isStarted());
    }

    @Test
    void getCurrentPlayer() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        assertEquals("Matt", game.getCurrentPlayer());

        game.rollDice("Matt", 2);
        assertEquals("Tom", game.getCurrentPlayer());

        Game secondGame = new Game("group39",2, tiles, 1);
        secondGame.addPlayer(tom);
        secondGame.addPlayer(matt);

        assertEquals("Tom", secondGame.getCurrentPlayer());
    }

    @Test
    void getDirectSale() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice("Matt", 1);
        assertEquals("Mediterranean", game.getDirectSale());
        game.refuseDirectSalePurchase("Matt");

        game.rollDice("Tom", 3);
        assertEquals("Baltic", game.getDirectSale());
        game.refuseDirectSalePurchase("Tom");

        game.rollDice("Matt", 1);
        assertNull(game.getDirectSale());
    }

    @Test
    void getPlayers() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        Player[] players = game.getPlayers().toArray(new Player[2]);
        assertArrayEquals(new Player[] { matt, tom }, players);
    }

    @Test
    void getMaxPlayers() {
        assertEquals(2, game.getMaxPlayers());
        Game secondGame = new Game("group39",4, tiles, 1);
        assertEquals(4, secondGame.getMaxPlayers());
    }

    @Test
    void changeCurrentPlayer() {
        Game secondGame = new Game("group39",3, tiles, 1);
        Player josh = new Player("Josh");

        secondGame.addPlayer(tom);
        secondGame.addPlayer(matt);
        secondGame.addPlayer(josh);

        assertEquals("Tom", secondGame.getCurrentPlayer());
        secondGame.rollDice("Tom", 2);
        assertEquals("Matt", secondGame.getCurrentPlayer());
        secondGame.rollDice("Matt", 2);
        assertEquals("Josh", secondGame.getCurrentPlayer());
        secondGame.rollDice("Josh", 2);
        assertEquals("Tom", secondGame.getCurrentPlayer());
    }

    @Test
    void rollDice() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        assertEquals(0, matt.getCurrentPosition());
        assertEquals(0, tom.getCurrentPosition());

        game.rollDice("Matt", 3);
        assertEquals(3, matt.getCurrentPosition());
        assertEquals("Baltic", game.getDirectSale());
        game.refuseDirectSalePurchase("Matt");


    }

    @Test
    void getGameId() {
        Game game = new Game("group39", 2, tiles, 2);
        assertEquals("group39_2", game.getId());
        Game secondGame = new Game("group39",2, tiles, 3);
        assertEquals("group39_3", secondGame.getId());
    }

    @Test
    void purchaseCurrentSale() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice(matt.getName(), 3);
        assertEquals("Matt", game.getCurrentPlayer());
        assertEquals("Baltic", game.getDirectSale());
        game.purchaseDirectSale(matt.getName());
        assertEquals(1480, matt.getBalance());

        assertEquals("Tom", game.getCurrentPlayer());
        assertNull(game.getDirectSale());
    }

    @Test
    void landOnAlreadyBoughtProperty() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice(matt.getName(), 3);
        game.purchaseDirectSale(matt.getName());

        assertEquals(1480, matt.getBalance());
        assertNull(game.getDirectSale());
        assertEquals(1500, tom.getBalance());

        game.rollDice(tom.getName(), 3);

        assertEquals(1480, tom.getBalance());
        assertEquals(1500, matt.getBalance());
        assertNull(game.getDirectSale());
    }

    @Test
    void landOnTaxIncome(){
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice(matt.getName(), 4);
        assertEquals(1300, matt.getBalance());
    }

    @Test
    void landOnLuxuryTax(){
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice(matt.getName(), 38);
        assertEquals(1300, matt.getBalance());
    }

    @Test
    void landOnGoToJail() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice(matt.getName(), 30);
        assertEquals("Jail", matt.getCurrentTile());
    }

    @Test
    void bankruptcyTest() {
        Game game2 = new Game("group39", 3, tiles, 2);
        game2.addPlayer(matt);
        game2.addPlayer(tom);
        game2.addPlayer(new Player("Dan"));

        assertEquals("Matt", game2.getCurrentPlayer());
        game2.rollDice("Matt", 2);

        assertEquals("Tom", game2.getCurrentPlayer());
        game2.bankruptPlayer("Tom");

        assertEquals("Dan", game2.getCurrentPlayer());
    }

    @Test
    void isDoubleRollTest() {
        Game game2 = new Game("group39", 2, tiles, 2);
        game2.previousRoll.add(1);
        game2.previousRoll.add(1);
        assertEquals(game2.previousRoll.get(0), game2.previousRoll.get(1));
    }

    @Test
    void goingBankrupt() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        assertEquals(matt.getName(), game.getCurrentPlayer());
        tom.removeFunds(1501);

        game.rollDice("Matt", 2);
        game.rollDice("Tom", 2);

        assertTrue(tom.isBankrupt());
    }

    @Test
    void goingBankruptAfterBuying() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        game.rollDice(matt.getName(), 2);
        tom.removeFunds(1481);
        assertEquals(19, tom.getBalance());

        game.rollDice(tom.getName(), 3);
        game.purchaseDirectSale(tom.getName());
        assertTrue(tom.isBankrupt());
    }

    @Test
    void goingBankruptAfterRefusing() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        matt.removeFunds(1501);
        game.rollDice(matt.getName(), 3);
        game.refuseDirectSalePurchase(matt.getName());

        assertTrue(matt.isBankrupt());
    }

    @Test
    void goingBankruptTaxIncome() {
        game.addPlayer(matt);
        game.addPlayer(tom);

        matt.removeFunds(1400);
        game.rollDice(matt.getName(), 4);
        assertTrue(matt.isBankrupt());
    }
}