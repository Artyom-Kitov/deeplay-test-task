package me.akitov.task3;

import me.akitov.TaskRunner;

import java.util.Arrays;

public class Task3Runner extends TaskRunner {

    public Task3Runner() {
        super("Task 3");
    }

    private static final int[] PLAYER1_NUMBERS = new int[]{1, 2, 3};
    private static final int[] PLAYER2_NUMBERS = new int[]{4, 2, 4};
    private static final int THROWS_COUNT = 100;
    private static final int GAMES_COUNT = 100000;

    @Override
    protected void runTask() {
        System.out.println("Player 1 numbers: " + Arrays.toString(PLAYER1_NUMBERS));
        System.out.println("Player 2 numbers: " + Arrays.toString(PLAYER2_NUMBERS));
        System.out.println("Number of throws: " + THROWS_COUNT);
        System.out.println("Number of games: " + GAMES_COUNT);

        var calculator = new MonteCarloGameProbabilityCalculator(PLAYER1_NUMBERS, PLAYER2_NUMBERS, THROWS_COUNT);
        GameProbabilities probabilities = calculator.calculate(GAMES_COUNT);

        System.out.println("Player 1 wins with a probability of " + probabilities.player1Probability());
        System.out.println("Player 2 wins with a probability of " + probabilities.player2Probability());
        System.out.println("Probability of a draw: " + probabilities.drawProbability());
    }
}
