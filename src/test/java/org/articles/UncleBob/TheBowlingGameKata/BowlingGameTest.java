package org.articles.UncleBob.TheBowlingGameKata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    private Game game;

    private class Game {
        private int score = 0;
        public void roll(int pins){
            score = score + pins;
        }
        public Integer score(){
            return score;
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
        assertEquals(133, game.score());
    }

    @Test
    void sumOfPinKnockedDown() {
        for (int i = 0; i < 20; i++) {
            game.roll(1);
        }
        assertEquals(20, game.score());
    }
}