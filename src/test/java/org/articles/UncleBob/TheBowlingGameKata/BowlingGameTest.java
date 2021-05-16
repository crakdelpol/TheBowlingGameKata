package org.articles.UncleBob.TheBowlingGameKata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    private Game game;

    private class Game {

        private int roundNumber = 0;
        private LinkedList<Frame> frames = new LinkedList<>();

        public void roll(int pins){
            roundNumber++;

            if(roundNumber % 2 == 0){
                frames.getLast().addSecondShot(pins);
            } else {
                if(frames.size() > 0 && frames.getLast().isSpare()) {
                    frames.getLast().addBonusSpare(pins);
                }
                frames.add(new Frame(pins));
            }

            if(frames.size() > 1){
                Frame frame = frames.get(frames.size() - 2);
                if(frame.isStrike()){
                    frame.addBonusStrike(pins);
                }
            }

        }

        public Integer score(){
            return frames.stream().filter(Frame::isCompleted).map(Frame::calculateScore).reduce(Integer::sum).orElse(0);
        }

        private static class Frame {
            private Integer firstShotScore;
            private Integer secondShotScore;
            private int bonusSpare;
            private int bonusStrike = 0;

            public Frame(int pins) {
                firstShotScore = pins;
            }

            public void addSecondShot(int pins) {
                secondShotScore = pins;
            }

            public boolean isSpare() {
                return firstShotScore + secondShotScore == 10 && secondShotScore != 0;
            }

            public void addBonusSpare(int bonusSpare) {
                this.bonusSpare = bonusSpare;

            }

            public int calculateScore() {
                return firstShotScore + secondShotScore + bonusSpare + bonusStrike;
            }

            public void addBonusStrike(int pins) {
                bonusStrike += pins;
            }

            public boolean isStrike() {
                return firstShotScore == 10;
            }

            public boolean isCompleted() {
                return firstShotScore != null && secondShotScore != null;
            }

        }
    }

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void allGameAcceptanceTest() {
        Integer[] pillsDown = new Integer[]{1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 0, 1, 7, 3, 6, 4, 10, 0, 2, 8, 6};
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

    @Test
    void verifySpareWorkCorrectly() {
        Integer[] pillsDown = new Integer[]{1, 9, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,7 ,3 ,8 ,2 ,1, 1};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(54, game.score());
    }

    @Test
    void verifyStrikeHappyPathWorkCorrectly() {
        Integer[] pillsDown = new Integer[]{1, 1, 10, 0 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(30, game.score());
    }

    @Test
    void mixStrikeAndSpare() {
        Integer[] pillsDown = new Integer[]{1, 1, 10, 0 ,5 ,5 ,2 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1, 1};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(49, game.score());
    }

    @Test
    void allStrike() {
        Integer[] pillsDown = new Integer[]{10, 0, 10, 0 ,10, 0 ,10, 0 ,10, 0 ,10, 0 ,10, 0 ,10, 0 ,10, 0 ,1, 1};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(174, game.score());
    }

    @Test
    void lastFrameWithSpareHaveExtraRoll() {
        Integer[] pillsDown = new Integer[]{1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 , 1 ,1 , 5, 5, 5};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(33, game.score());
    }

    @Test
    void lastFrameWithStrikeHaveExtraRoll() {
        Integer[] pillsDown = new Integer[]{1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 , 1 ,1 , 10, 0, 5};
        for (Integer integer : pillsDown) {
            game.roll(integer);
        }
        assertEquals(33, game.score());
    }


}