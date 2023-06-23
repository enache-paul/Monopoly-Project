package be.howest.ti.monopoly.logic.implementation;

public class Utility extends Tile implements Property{

    private final int cost;
    private final String color;
    private final int mortgage;
    private boolean purchased;
    private final int rent;

    public Utility(String name, String color, int position, int cost, int mortgage, int rent, String type){
        super(name, position, type);
        this.cost = cost;
        this.color = color;
        this.mortgage = mortgage;
        this.rent = rent;
        this.purchased = false;
    }

    public boolean isPurchased() {return this.purchased;}

    public void setPurchased() {this.purchased = true;}

    public int getCost() {return this.cost;}

    public int getRent() { return this.rent; }

    public String getColor() { return this.color; }

    public int getMortgage() {return this.mortgage;}

    //TODO fix rent for Utility
}
