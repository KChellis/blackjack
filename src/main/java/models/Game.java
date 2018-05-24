package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private List<String> deck = new ArrayList<>();
    private List<String> dealerHand = new ArrayList<>();
    private List<String> playerHand = new ArrayList<>();
    private List<String> secondHand = new ArrayList<>();
    private int bank = 100;


    public List<String> getDeck() {
        return deck;
    }

    public List<String> getDealerHand() {
        return dealerHand;
    }

    public List<String> getPlayerHand() {
        return playerHand;
    }

    public void setDeck() {
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"H", "S", "C", "D"};
        for (String suit : suits){
            for (String value : values) {
                this.deck.add(value + "-" + suit);
            }
        }
    }

    public Integer generateRandomNumber() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(this.deck.size());
    }

    public void dealCard(Integer randomNumber, String hand) {
        String card = this.deck.get(randomNumber);
        if(hand.equals("Player")){
            this.playerHand.add(card);
        } else if(hand.equals("Second")){
            this.secondHand.add(card);
        }else {
            this.dealerHand.add(card);
        }

        this.deck.remove(card);
    }

    public Integer evaluateHand(String hand){
        List<String> currentHand;
        if(hand.equals("Player")){
            currentHand = this.playerHand;

        } else if(hand.equals("Second")){
            currentHand = this.secondHand;

        }else {
            currentHand = this.dealerHand;
        }
        Integer handScore;
        Integer tempScoreHigh = 0;
        Integer tempScoreLow = 0;
        for (String card : currentHand){
            Character value = card.charAt(0);
            if(value.toString().matches ("[KQJ1]")){
                tempScoreHigh += 10;
                tempScoreLow += 10;
            }else if(value.toString().matches ("[2-9]")){
                tempScoreLow += Integer.parseInt(value.toString());
                tempScoreHigh += Integer.parseInt(value.toString());
            }else {
                tempScoreHigh += 11;
                tempScoreLow += 1;
            }
        }
        if(tempScoreHigh != tempScoreLow && tempScoreHigh > 21){
            handScore = tempScoreLow;
        }else {
            handScore = tempScoreHigh;
        }
        return handScore;
    }

    public boolean checkBlackjack(String hand){
        List<String> currentHand;
        if(hand.equals("Player")){
            currentHand = this.playerHand;
        }else if(hand.equals("Second")){
            currentHand = this.secondHand;

        }else {
            currentHand = this.dealerHand;
        }
        Character value1 = currentHand.get(0).charAt(0);
        Character value2 = currentHand.get(1).charAt(0);
        if(value1 == 'A' && value2.toString().matches("[KQJ1]") || value2 == 'A' && value1.toString().matches("[KQJ1]") ){
            return true;
        }else {
            return false;
        }
    }

    public String checkWin(String hand){
        if (this.evaluateHand("dealer") > this.evaluateHand(hand) && this.evaluateHand("dealer") <= 21) {
            return "lose";
        } else if (this.evaluateHand("dealer") == this.evaluateHand(hand)){
            return "draw";
        } else  {
            return "win";
        }
    }

    public boolean checkBust(String hand) {
        if (this.evaluateHand(hand) > 21) {
            return true;
        }else {
            return false;
        }
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bet) {
        this.bank += bet;
    }

    public List<String> getSecondHand() {
        return secondHand;
    }

    public boolean checkPair(){
        Character value1 = this.playerHand.get(0).charAt(0);
        Character value2 = this.playerHand.get(1).charAt(0);
        if (value1 == value2) {
            return true;
        } else {
            return false;
        }
    }

    public void splitHand(){
        this.secondHand.add(this.playerHand.get(0));
        this.playerHand.remove(0);
    }
}
