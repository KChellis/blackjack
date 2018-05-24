import models.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        System.out.println("Welcome to Blackjack!");
        boolean gameRunning = true;
        int playerBank = 100;
        while (gameRunning) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                Game newGame = new Game();

                System.out.println("Deal or Exit?");
                String gameAction = bufferedReader.readLine().toLowerCase();
                if (gameAction.equals("deal")) {
                    newGame.setDeck();
                    newGame.dealCard(newGame.generateRandomNumber(), "Player");
                    newGame.dealCard(newGame.generateRandomNumber(), "dealer");
                    newGame.dealCard(newGame.generateRandomNumber(), "Player");
                    newGame.dealCard(newGame.generateRandomNumber(), "dealer");

                    boolean betting = true;
                    Integer playerBet = 5;
                    while (betting){
                        System.out.println(String.format("Your bank is %d. What would you like to bet? (5 minimum) ", playerBank));
                        playerBet = Integer.parseInt(bufferedReader.readLine());
                        if(playerBet < 5 || playerBet > playerBank){
                            System.out.println("That is not a valid bet try again");
                        }else{
                            betting=false;
                        }
                    }
                    System.out.println("Here's your hand: ");
                    for (String card : newGame.getPlayerHand()) {
                        System.out.print(String.format("[%s]", card));
                    }
                    System.out.println("");
                    List<List> hands = new ArrayList<List>();
                    hands.add(newGame.getPlayerHand());
                    if (newGame.checkPair()) {

                        boolean doubling = true;
                        while(doubling){
                            System.out.println(" You have a pair would you like to split and double down? Y/N");
                            String split = bufferedReader.readLine().toLowerCase();
                            if(split.equals("y")){
                                newGame.splitHand();
                                hands.add(newGame.getSecondHand());
                                newGame.dealCard(newGame.generateRandomNumber(), "Player");
                                newGame.dealCard(newGame.generateRandomNumber(), "Second");
                                doubling = false;
                            }else if(split.equals("n")){
                                doubling = false;
                            }else{
                                System.out.println("I don't understand that command. Try Again");
                            }
                        }
                    }
                    for(List hand :hands) {
                        String currentHand;
                        if(hand == hands.get(0)){
                            currentHand = "Player";
                        }else {
                            currentHand = "Second";
                        }
                        boolean inPlay = true;
                        while (inPlay) {

                            if(hands.size() == 2){
                                System.out.println(String.format("Here's %s hand: ", currentHand));
                                for (Object card : hand) {
                                    System.out.print(String.format("[%s]", card));
                                }
                                System.out.println("");
                            }

                            if (newGame.checkBlackjack(currentHand)){
                                System.out.println(String.format("Blackjack! %s hand Wins!", currentHand));
                                playerBank += playerBet;
                                inPlay = false;
                            } else {
                                System.out.println("Dealer is showing: ");
                                for (int i = 1; i < newGame.getDealerHand().size(); i++) {
                                    System.out.println(String.format("[%s]", newGame.getDealerHand().get(i)));
                                }
                                boolean hitting = false;
                                boolean choosing = true;
                                while(choosing){
                                    System.out.println(String.format("This hand's score is %d. What would you like to do?", newGame.evaluateHand(currentHand)));
                                    System.out.println("Hit or Stay");
                                    String playerAction = bufferedReader.readLine().toLowerCase();
                                    if(playerAction.equals("hit")){
                                        hitting = true;
                                        choosing = false;
                                    }else if (playerAction.equals("stay")){
                                        choosing = false;
                                        inPlay = false;
                                    }else {
                                        System.out.println("I don't understand that command. Try Again");
                                    }
                                }


                                while(hitting){
                                    newGame.dealCard(newGame.generateRandomNumber(), currentHand);
                                    System.out.println(String.format("Here's %s hand: ", currentHand));
                                    for (Object card : hand) {
                                        System.out.print(String.format("[%s]", card));
                                    }
                                    System.out.println("");
                                    if (newGame.checkBust(currentHand)) {
                                        System.out.println(String.format("%s hand busted!", currentHand));
                                        hitting = false;
                                        inPlay = false;
                                        playerBank -= playerBet;
                                    }else {
                                        choosing = true;
                                        while(choosing){
                                            System.out.println(String.format("This hand's score is %d. What would you like to do?", newGame.evaluateHand(currentHand)));
                                            System.out.println("Hit or Stay");
                                            String playerAction2 = bufferedReader.readLine().toLowerCase();
                                            if(playerAction2.equals("stay")){
                                                hitting =false;
                                                choosing = false;
                                                inPlay=false;
                                            }else if (playerAction2.equals("hit")){
                                                choosing = false;
                                            }else {
                                                System.out.println("I don't understand that command. Try Again");
                                            }
                                        }

                                    }
                                }

                            }

                        }

                    }
                    if((!newGame.checkBlackjack("Player") || !newGame.checkBlackjack("Second"))){
                        System.out.println(String.format("The Dealer has %d: ", newGame.evaluateHand("dealer")));
                        for (String card : newGame.getDealerHand()) {
                            System.out.print(String.format("[%s]", card));
                        }
                        System.out.println("");
                        while(newGame.evaluateHand("dealer") < 17 && (!newGame.checkBust("Player") || !newGame.checkBust("Second")) ){
                            newGame.dealCard(newGame.generateRandomNumber(), "dealer");
                            System.out.println(String.format("The Dealer has %d: ", newGame.evaluateHand("dealer")));
                            for (String card : newGame.getDealerHand()) {
                                System.out.print(String.format("[%s]", card));
                            }
                            System.out.println("");
                            for(List hand: hands){
                                String currentHand;
                                if(hand == hands.get(0)){
                                    currentHand = "Player";
                                }else {
                                    currentHand = "Second";
                                }
                                if (newGame.evaluateHand("dealer") > 21 && !newGame.checkBust(currentHand) && !newGame.checkBlackjack(currentHand) ) {
                                    System.out.println(String.format("Dealer busted! %s hand Wins!", currentHand));
                                    playerBank += playerBet;
                                }
                            }

                        }
                        for(List hand: hands){
                            String currentHand;
                            if(hand == hands.get(0)){
                                currentHand = "Player";
                            }else {
                                currentHand = "Second";
                            }
                            if(!newGame.checkBlackjack(currentHand)  && !newGame.checkBust(currentHand) && newGame.evaluateHand("dealer") <= 21 ) {
                                String result = newGame.checkWin(currentHand);
                                if(result.equals("win")){
                                    System.out.println(String.format("%s hand Wins!", currentHand));
                                    playerBank += playerBet;
                                } else if(result.equals("draw")){
                                    System.out.println(String.format("%s hand didn't win but didn't lose either!", currentHand));
                                } else {
                                    System.out.println(String.format("%s hand Loses!", currentHand));
                                    playerBank -= playerBet;
                                }
                            }
                        }
                    }



                    if(playerBank < 5){
                        System.out.println("You are broke, game over!");
                        gameRunning = false;
                    }else{
                        boolean playAgain = true;
                        while(playAgain){
                            System.out.println("Do you want to play again? Y/N");
                            String playerChoice = bufferedReader.readLine().toLowerCase();
                            if(playerChoice.equals("n")){
                                System.out.println(String.format("Your final bank is %d", playerBank));
                                System.out.println("Thanks for playing!");
                                gameRunning = false;
                                playAgain = false;
                            }else if(playerChoice.equals("y")){
                                playAgain = false;
                            }else{
                                System.out.println("I don't understand that command. Try Again");
                            }
                        }

                    }

                } else if (gameAction.equals("exit")) {
                    System.out.println(String.format("Your final bank is %d", playerBank));
                    System.out.println("Thanks for playing!");
                    gameRunning = false;
                } else {
                    System.out.println("Sorry, what was that?");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
