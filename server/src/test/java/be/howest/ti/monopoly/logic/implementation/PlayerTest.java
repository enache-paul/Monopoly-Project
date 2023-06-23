package be.howest.ti.monopoly.logic.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player dik;

    @BeforeEach
    void setUp() {
        dik = new Player("Dik");
    }

    @Test
    void getName() {
        assertEquals("Dik", dik.getName());
    }

    @Test
    void getBalance(){
        assertEquals(1500, dik.getBalance());
    }

    @Test
    void getCurrentPosition(){
        assertEquals(0, dik.getCurrentPosition());
    }

    @Test
    void setCurrentPosition(){
        dik.setCurrentPosition(5);
        assertEquals(5, dik.getCurrentPosition());
    }

    @Test
    void addFunds(){
        dik.addFunds(100);
        assertEquals(1600, dik.getBalance());
    }

    @Test
    void removeFunds(){
        dik.removeFunds(60);
        assertEquals(1440, dik.getBalance());
    }

    @Test
    void getProperties(){
        dik.addProperty(new Street("Mediterranean","WHITE", 1, 60, 30 , 2, "street"));
        dik.addProperty(new Street("Atlantic", "WHITE",26, 260, 130, 22, "street"));
        assertEquals(2, dik.getProperties().size());
    }

    @Test
    void addProperty(){
        dik.addProperty(new Street("Baltic", "WHITE",3, 60, 30, 4, "street"));
        assertEquals(1, dik.getProperties().size());

        dik.addProperty(new Street("Baltic", "WHITE",3, 60, 30, 4, "street"));
        assertEquals(1, dik.getProperties().size());

        dik.addProperty(new Street("Oriental", "WHITE",6, 100, 50, 6, "street"));
        assertEquals(2, dik.getProperties().size());

    }
}