package be.howest.ti.monopoly.logic.implementation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final String name;
    private int balance;
    private String currentTile;
    private int position;
    private boolean bankrupt;
    public Map<String, Property> properties;

    public Player(String name) {
        this.name = name;
        this.balance = 1500;
        this.currentTile = "Go";
        this.position = 0;
        this.properties = new HashMap<>();
        this.bankrupt = false;

    }

    public void setCurrentTile(String tileName){
        this.currentTile = tileName;
    }

    public void setCurrentPosition(int position) {
        this.position = position;
    }

    public void bankruptPlayer(){
        this.bankrupt = true;
        this.properties.clear();
        this.balance = 0;
    }

    public String getName(){ return this.name; }

    public int getBalance(){ return this.balance; }

    public int getCurrentPosition() {
        return this.position;
    }

    public String getCurrentTile(){ return this.currentTile; }

    public List<Property> getProperties() {
        return new ArrayList<>(properties.values());
    }

    public void addFunds(int amount){
        this.balance += amount;
    }

    public void removeFunds(int amount){
        this.balance -= amount;
    }

    public boolean isOnSpecialTile() {
        return this.currentTile.matches("Tax Income|Go to Jail|Luxury Tax");
    }

    public boolean isBankrupt() {
        return this.bankrupt;
    }

    public void addProperty(Property property){

        if (!getProperties().contains(property)) {
            this.properties.put(property.getName(), property);
            this.removeFunds(property.getCost());
        }
    }

    public void move(Tile tile) {
        this.currentTile = tile.getName();
        this.position = tile.getPosition();
    }
}
