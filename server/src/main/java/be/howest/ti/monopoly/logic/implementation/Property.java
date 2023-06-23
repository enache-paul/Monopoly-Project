package be.howest.ti.monopoly.logic.implementation;

public interface Property {
    public String getName();

    public int getCost();

    public int getMortgage();

    public String getColor();

    public int getRent();

    public void setPurchased();

    public boolean isPurchased();
}
