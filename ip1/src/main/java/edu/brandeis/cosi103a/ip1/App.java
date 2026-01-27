package edu.brandeis.cosi103a.ip1;

import java.util.Random;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int player1Score = 0;
        int player2Score = 0;
        int turns = 10;

        System.out.println("Welcome to the Dice Game!");

        for (int turn = 1; turn <= turns; turn++) {
            System.out.println("\nTurn " + turn + " for Player 1:");
            player1Score += takeTurn(scanner, random);

            System.out.println("\nTurn " + turn + " for Player 2:");
            player2Score += takeTurn(scanner, random);
        }

        System.out.println("\nGame Over!");
        System.out.println("Player 1's Total Score: " + player1Score);
        System.out.println("Player 2's Total Score: " + player2Score);

        if (player1Score > player2Score) {
            System.out.println("Player 1 wins!");
        } else if (player2Score > player1Score) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }

        scanner.close();
    }

    static int takeTurn(Scanner scanner, Random random) {
        int roll = rollDie(random);
        System.out.println("You rolled: " + roll);

        for (int rerollCount = 0; rerollCount < 2; rerollCount++) {
            System.out.print("Do you want to re-roll? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("yes")) {
                roll = rollDie(random);
                System.out.println("You re-rolled: " + roll);
            } else {
                break;
            }
        }

        System.out.println("Your final roll for this turn is: " + roll);
        return roll;
    }

    static int rollDie(Random random) {
        return random.nextInt(6) + 1; // Generates a number between 1 and 6
    }
}