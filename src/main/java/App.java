import models.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
                    boolean inPlay = true;
                    newGame.setDeck();
                    newGame.dealCard(newGame.generateRandomNumber(), "player");
                    newGame.dealCard(newGame.generateRandomNumber(), "dealer");
                    newGame.dealCard(newGame.generateRandomNumber(), "player");
                    newGame.dealCard(newGame.generateRandomNumber(), "dealer");

                    while (inPlay) {
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
                        if (newGame.checkBlackjack("player")){
                            System.out.println("Blackjack!");
                            playerBank += playerBet;
                            inPlay = false;
                        } else {
                            System.out.println("Dealer is showing: ");
                            for (int i = 1; i < newGame.getDealerHand().size(); i++) {
                                System.out.println(String.format("[%s]", newGame.getDealerHand().get(i)));
                            }
                            System.out.println(String.format("Your score is %d. What would you like to do?", newGame.evaluateHand("player")));
                            System.out.println("Hit or Stay");
                            String playerAction = bufferedReader.readLine().toLowerCase();
                            boolean hitting = false;
                            if(playerAction.equals("hit")){
                                hitting = true;
                            }

                            while(hitting){
                                newGame.dealCard(newGame.generateRandomNumber(), "player");
                                System.out.println("Here's your hand: ");
                                for (String card : newGame.getPlayerHand()) {
                                    System.out.print(String.format("[%s]", card));
                                }
                                System.out.println("");
                                if (newGame.checkBust()) {
                                    System.out.println("You busted!");
                                    hitting = false;
                                    inPlay = false;
                                    playerBank -= playerBet;
                                }else {
                                    System.out.println(String.format("Your score is %d. What would you like to do?", newGame.evaluateHand("player")));
                                    System.out.println("Hit or Stay");
                                    String playerAction2 = bufferedReader.readLine().toLowerCase();
                                    if(playerAction2.equals("stay")){
                                        hitting =false;
                                    }
                                }
                            }
                            System.out.println(String.format("The Dealer has %d: ", newGame.evaluateHand("dealer")));
                            for (String card : newGame.getDealerHand()) {
                                System.out.print(String.format("[%s]", card));
                            }
                            System.out.println("");
                            while(newGame.evaluateHand("dealer") < 17 && !newGame.checkBust()){
                                newGame.dealCard(newGame.generateRandomNumber(), "dealer");
                                System.out.println(String.format("The Dealer has %d: ", newGame.evaluateHand("dealer")));
                                for (String card : newGame.getDealerHand()) {
                                    System.out.print(String.format("[%s]", card));
                                }
                                System.out.println("");
                                if (newGame.evaluateHand("dealer") > 21) {
                                    System.out.println("Dealer busted! You Win!");
                                    playerBank += playerBet;
                                    inPlay = false;
                                }
                            }
                            if(!newGame.checkBust() && newGame.evaluateHand("dealer") <= 21 ) {
                                String result = newGame.checkWin();
                                if(result.equals("win")){
                                    System.out.println("You Win!");
                                    playerBank += playerBet;
                                    inPlay = false;
                                } else if(result.equals("draw")){
                                    System.out.println("You didn't win but you didn't lose either!");
                                    inPlay = false;
                                } else {
                                    System.out.println("You Lose!");
                                    playerBank -= playerBet;
                                    inPlay = false;
                                }
                            }
                        }
                    }
                    if(playerBank < 5){
                        System.out.println("You are broke, game over!");
                        gameRunning = false;
                    }else{
                        System.out.println("Do you want to play again? Y/N");
                        String playerChoice = bufferedReader.readLine().toLowerCase();
                        if(playerChoice.equals("n")){
                            System.out.println(String.format("Your final bank is %d", playerBank));
                            System.out.println("Thanks for playing!");
                            gameRunning = false;
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
