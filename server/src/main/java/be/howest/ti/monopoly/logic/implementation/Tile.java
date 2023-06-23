package be.howest.ti.monopoly.logic.implementation;

public class Tile {
    private final String type;
    private final String name;
    private final int position;

    public Tile(String name, int position, String type) {
        this.name = name;
        this.position = position;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

    public String getType() { return this.type; }
}
