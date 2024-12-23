package me.akitov.task3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class MonteCarloGameProbabilityCalculator {

    public static final int NUMBERS_COUNT = 3;

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 6;

    private final int[] p1Numbers;
    private final int[] p2Numbers;
    private final int throwsCount;

    public MonteCarloGameProbabilityCalculator(int[] p1Numbers, int[] p2Numbers, int throwsCount) {
        if (p1Numbers.length != NUMBERS_COUNT || p2Numbers.length != NUMBERS_COUNT) {
            throw new IllegalArgumentException("invalid amount of numbers");
        }
        if (throwsCount < 0) {
            throw new IllegalArgumentException("number of throws must be non-negative integer");
        }
        for (int n : p1Numbers) {
            if (n < LOWER_BOUND || n > UPPER_BOUND) {
                throw new IllegalArgumentException("invalid player 1 numbers");
            }
        }
        for (int n : p2Numbers) {
            if (n < LOWER_BOUND || n > UPPER_BOUND) {
                throw new IllegalArgumentException("invalid player 2 numbers");
            }
        }
        this.p1Numbers = p1Numbers;
        this.p2Numbers = p2Numbers;
        this.throwsCount = throwsCount;
    }

    public GameProbabilities calculate(int gamesCount) {
        return calculate(gamesCount, Runtime.getRuntime().availableProcessors());
    }

    public GameProbabilities calculate(int gamesCount, int nThreads) {
        if (throwsCount < NUMBERS_COUNT || Arrays.equals(p1Numbers, p2Numbers)) {
            return new GameProbabilities(0, 0, 1f);
        }

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        AtomicInteger p1Wins = new AtomicInteger(0);
        AtomicInteger p2Wins = new AtomicInteger(0);
        AtomicInteger draws = new AtomicInteger(0);
        for (int i = 0; i < nThreads; i++) {
            int gamesForThread = gamesCount / nThreads + (i == 0 ? gamesCount % nThreads : 0);
            tasks.add(
                    CompletableFuture.supplyAsync(() -> calculateImpl(gamesForThread), executor)
                            .thenAccept(counts -> {
                                p1Wins.addAndGet(counts.p1Wins);
                                p2Wins.addAndGet(counts.p2Wins);
                                draws.addAndGet(counts.draws);
                            })
            );
        }

        tasks.forEach(CompletableFuture::join);
        executor.shutdown();

        return new GameProbabilities(
                (double) p1Wins.get() / gamesCount,
                (double) p2Wins.get() / gamesCount,
                (double) draws.get() / gamesCount
        );
    }

    private record GamesCounts(int p1Wins, int p2Wins, int draws) {}

    private GamesCounts calculateImpl(int gamesCount) {
        int[] throwsResults = new int[throwsCount];

        int p1Wins = 0;
        int p2Wins = 0;
        int draws = 0;

        for (int i = 0; i < gamesCount; i++) {
            makeThrows(throwsResults);
            GameResult result = findGameResult(throwsResults);
            switch (result) {
                case PLAYER1_WIN -> p1Wins++;
                case PLAYER2_WIN -> p2Wins++;
                case DRAW -> draws++;
            }
        }

        return new GamesCounts(
                p1Wins,
                p2Wins,
                draws
        );
    }

    private void makeThrows(int[] throwsResults) {
        for (int i = 0; i < throwsResults.length; i++) {
            throwsResults[i] = ThreadLocalRandom.current().nextInt(LOWER_BOUND, UPPER_BOUND);
        }
    }

    private enum GameResult {
        PLAYER1_WIN,
        PLAYER2_WIN,
        DRAW
    }

    private GameResult findGameResult(int[] throwsResults) {
        int p1Score = 0;
        int p2Score = 0;

        int i = 0;
        while (i < throwsResults.length - 3) {
            if (Arrays.equals(throwsResults, i, i + NUMBERS_COUNT, p1Numbers, 0, p1Numbers.length)) {
                p1Score++;
                i += NUMBERS_COUNT;
            } else {
                i++;
            }
        }

        i = 0;
        while (i < throwsResults.length - 3) {
            if (Arrays.equals(throwsResults, i, i + NUMBERS_COUNT, p2Numbers, 0, p2Numbers.length)) {
                p2Score++;
                i += NUMBERS_COUNT;
            } else {
                i++;
            }
        }

        if (p1Score > p2Score) {
            return GameResult.PLAYER1_WIN;
        }
        if (p1Score < p2Score) {
            return GameResult.PLAYER2_WIN;
        }
        return GameResult.DRAW;
    }
}
