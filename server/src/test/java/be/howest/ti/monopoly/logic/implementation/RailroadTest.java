package be.howest.ti.monopoly.logic.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RailroadTest {
    private Property property;

    @BeforeEach
    void setUp(){
        this.property = new Railroad("Reading RR", "BLACK", 5, 200, 100, 25, "Railroad");
    }


    @Test
    void isPurchased(){
        assertFalse(property.isPurchased());
        property.setPurchased();
        assertTrue(property.isPurchased());
    }

    @Test
    void getCost(){
        assertEquals(200, property.getCost());
    }

    @Test
    void getMortgage(){
        assertEquals(100, property.getMortgage());
    }
}