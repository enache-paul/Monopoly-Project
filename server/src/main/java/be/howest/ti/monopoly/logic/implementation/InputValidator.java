package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;

public class InputValidator {

    public void validateRollInput(Game game, String playerName) {
        if (game.getDirectSale() != null)
            throw new IllegalMonopolyActionException("The current direct sale must be resolved first.");

        if (!game.isStarted())
            throw new IllegalMonopolyActionException("The game has not started yet.");

        if (!game.getCurrentPlayer().equals(playerName))
            throw new IllegalMonopolyActionException("Only the current player can perform an action.");
    }

    public void validatePurchaseInput(Game game, String playerName) {
        if (game.getDirectSale() == null)
            throw new IllegalMonopolyActionException("There are no properties for direct sale.");

        if (!game.getCurrentPlayer().equals(playerName))
            throw new IllegalMonopolyActionException("Only the current player can interact with direct sale.");
    }

    public void validateJoinGameInput(Game game, String playerName) {
        if (game.numberOfPlayers() == game.getMaxPlayers())
            throw new IllegalMonopolyActionException("The game is already full.");
    }
}
