package be.howest.ti.monopoly.logic.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {
    private Turn turn;
    @BeforeEach
    void setUp(){
        turn = new Turn("bob", List.of(1,2));
    }

    @Test
    void getRollTest() {assertEquals(List.of(1,2),turn.getRoll());}

    @Test
    void getName() {assertEquals("bob", turn.getName());}
}