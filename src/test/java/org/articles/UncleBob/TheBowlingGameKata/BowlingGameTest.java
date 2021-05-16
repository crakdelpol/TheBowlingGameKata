package org.articles.UncleBob.TheBowlingGameKata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    private Game game;

    private class Game {
        public void roll(int pins){}
        public Integer score(){
            return null;
        }
    }

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    @Disabled
    void allGameAcceptanceTest() {
        Integer[] pillsDown = new Integer[]{1 ,4 ,4 ,5 ,6 ,4 ,5 ,5 ,10 ,0 ,1 ,7 ,3 ,6 ,4 ,10 ,2 ,8 ,6};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(game.score(), 133);
    }


}