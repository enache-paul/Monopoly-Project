package be.howest.ti.monopoly.logic.implementation;

import java.util.List;

public class Turn {
    private final List<Integer> roll;
    private final String playerName;
    public Turn(String player, List<Integer> lastDiceRoll){
        this.roll = lastDiceRoll;
        this.playerName = player;
    }

    public List<Integer> getRoll(){return this.roll;}

    public String getName() {
        return this.playerName;
    }
}
