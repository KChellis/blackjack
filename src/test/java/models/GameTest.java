package models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void newGame_instantiatesCorrectly_true() throws Exception {
        Game testGame = new Game();
        assertEquals(true, testGame instanceof Game);
    }

    @Test
    public void getDeck_returnsGameDeck_ArrayList() {
        Game testGame = new Game();
        List<String> expected = new ArrayList<>();
        assertEquals(expected, testGame.getDeck());
    }

    @Test
    public void getDealerHand_returnsDealerHand_ArrayList() {
        Game testGame = new Game();
        List<String> expected = new ArrayList<>();
        assertEquals(expected, testGame.getDealerHand());
    }

    @Test
    public void getPlayerHand_returnsPlayerHand_ArrayList() {
        Game testGame = new Game();
        List<String> expected = new ArrayList<>();
        assertEquals(expected, testGame.getPlayerHand());
    }

    @Test
    public void getSecondHand_returnsPlayerSecondHand_ArrayList() {
        Game testGame = new Game();
        List<String> expected = new ArrayList<>();
        assertEquals(expected, testGame.getSecondHand());
    }

    @Test
    public void setDeck_populatesDeckWithUniqueCardValues_Ah() {
        Game testGame = new Game();
        testGame.setDeck();
        String expected = "A-H";
        assertEquals("A-H", testGame.getDeck().get(0));
    }

    @Test
    public void generateRandomNumber_generatesRandomNumberCorrectly_true() {
        Game testGame = new Game();
        testGame.setDeck();
        assertEquals(true, testGame.generateRandomNumber() instanceof Integer);
    }

    @Test
    public void dealCard_addsCardToHand_false() {
        Game testGame = new Game();
        testGame.setDeck();
        Integer testNumber = testGame.generateRandomNumber();
        testGame.dealCard(testNumber, "dealer");
        assertEquals(false, testGame.getDealerHand().isEmpty());
    }

    @Test
    public void dealCard_removesCardFromDeck_false() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "dealer");
        assertEquals(false, testGame.getDeck().contains("A-H"));
    }


    @Test
    public void evaluateHand_calculatesHandValue_5() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(1, "Player");
        testGame.dealCard(2, "Player");
        Integer expected = 6;
        assertEquals(expected, testGame.evaluateHand("Player"));

    }
    @Test
    public void evaluateHand_calculatesAceValueHigh_13() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(1, "Player");
        Integer expected = 14;
        assertEquals(expected, testGame.evaluateHand("Player"));

    }
    @Test
    public void evaluateHand_calculatesAceValueLow_13() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(1, "Player");
        testGame.dealCard(9, "Player");
        Integer expected = 14;
        assertEquals(expected, testGame.evaluateHand("Player"));

    }

    @Test
    public void checkBlackjack_returnsTrueIfHandIsBlackjack_true() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(9, "Player");
        assertEquals(true, testGame.checkBlackjack("Player"));
    }

    @Test
    public void checkBlackjack_returnsFalseIfHandIsNotBlackjack_false() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(1, "Player");
        testGame.dealCard(9, "Player");
        assertEquals(false, testGame.checkBlackjack("Player"));
    }

    @Test
    public void checkWin_returnsWinWhenPlayerBeatsDealer_win() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(10, "Player");
        testGame.dealCard(9, "Player");
        testGame.dealCard(8, "dealer");
        testGame.dealCard(9, "dealer");
        assertEquals("win", testGame.checkWin("Player"));
    }

    @Test
    public void checkWin_returnsLoseWhenDealerBeatsPlayer_lose() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(8, "Player");
        testGame.dealCard(9, "Player");
        testGame.dealCard(10, "dealer");
        testGame.dealCard(9, "dealer");
        assertEquals("lose", testGame.checkWin("Player"));
    }

    @Test
    public void checkWin_returnsDrawWhenPlayerHandEqualsDealerHand_draw() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(10, "Player");
        testGame.dealCard(9, "Player");
        testGame.dealCard(10, "dealer");
        testGame.dealCard(9, "dealer");
        assertEquals("draw", testGame.checkWin("Player"));
    }

    @Test
    public void checkBust_returnsTrueWhenPlayerBusts_true() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(10, "Player");
        testGame.dealCard(9, "Player");
        testGame.dealCard(5, "Player");
        assertEquals(true, testGame.checkBust("Player"));
    }

    @Test
    public void getBank_returnsBankValue_100() {
        Game testGame = new Game();
        assertEquals(100, testGame.getBank());
    }

    @Test
    public void setBank_ChangesBankValue_105() {
        Game testGame = new Game();
        testGame.setBank(5);
        assertEquals(105, testGame.getBank());
    }

    @Test
    public void checkPair_returnsTrueIfPlayerHandIsPair_true() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(12, "Player");
        assertEquals(true, testGame.checkPair());
    }

    @Test
    public void checkPair_returnsFalseIfPlayerHandIsPair_false() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(11, "Player");
        assertEquals(false, testGame.checkPair());
    }

    @Test
    public void splitHand_addsCardToSecondHand_true() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(12, "Player");
        testGame.splitHand();
        assertEquals("A-H", testGame.getSecondHand().get(0));
    }

    @Test
    public void splitHand_removesCardFromPlayerHand_false() {
        Game testGame = new Game();
        testGame.setDeck();
        testGame.dealCard(0, "Player");
        testGame.dealCard(12, "Player");
        testGame.splitHand();
        assertEquals(false, testGame.getPlayerHand().contains("A-H"));
    }
}