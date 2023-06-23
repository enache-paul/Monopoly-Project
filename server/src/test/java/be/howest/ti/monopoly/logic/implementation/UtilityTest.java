package be.howest.ti.monopoly.logic.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    private Property property;

    @BeforeEach
    void setUp(){
        this.property = new Utility("Water Works", "WHITE", 28, 150, 75,20, "Railroad");
    }

    @Test
    void isPurchased() {
        assertFalse(property.isPurchased());
        property.setPurchased();
        assertTrue(property.isPurchased());
    }

    @Test
    void getCost() {
        assertEquals(150, property.getCost());
    }

    @Test
    void getMortgage() {
        assertEquals(75, property.getMortgage());
    }
}