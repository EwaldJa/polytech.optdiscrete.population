package utils;

import java.util.Random;

/**
 * This class provides useful methods to get random values
 *
 * @author Ewald Janin, Lucas Aupoil
 */
public class RandUtils {

    /**
     * Returns a random int, lower bound included, upper bound excluded
     * @param min lower bound
     * @param max upper bound
     * @return a random int in [min, max[
     */
    public static int randInt(int min , int max) { return (int) randDouble(min, max); }

    /**
     * Returns a random int, lower bound included, upper bound excluded, different from other
     * @param min lower bound
     * @param max upper bound
     * @param other needs to be different from it
     * @return a random int in [min, max[ and != other
     */
    public static int randInt(int min , int max, int other) {
        int rand = (int) randDouble(min, max);
        while (rand == other) {
            rand = (int) randDouble(min, max);
        }
        return rand;
    }

    /**
     * Returns a random double, lower bound included, upper bound excluded
     * @param min lower bound
     * @param max upper bound
     * @return a random double in [min, max[
     */
    public static double randDouble(double min , double max) { return ( min + randDouble() * (max - min) ); }

    /**
     * Returns a random double, lower bound included, upper bound excluded, different from other
     * @param min lower bound
     * @param max upper bound
     * @param other an int, needs to be different from it
     * @return a random double in [min, max[ and != other
     */
    public static double randDouble(double min , double max, double other) {
        double rand = randDouble(min, max);
        while (rand == other) {
            rand = randDouble(min, max);
        }
        return rand;
    }

    /**
     * Returns a random double between 0 included and 1 excluded
     * @return a random double in [0, 1[
     */
    public static double randDouble() { return new Random().nextDouble(); }
}
