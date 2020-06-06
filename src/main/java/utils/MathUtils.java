package utils;

/**
 * This class provides useful math methods
 *
 * @author Ewald Janin, Lucas Aupoil
 */
public class MathUtils {

    /**
     * Checks if the two given double are equals more or less epsilon (in order to avoid rounding errors) : basically performs ((Math.abs(d1-d2)) < epsilon)
     * @param d1 a double
     * @param d2 another double
     * @param epsilon a double to establish a minimum limit to check if d1 and d2 are equals
     * @return true if they are equals more or less epsilon, false otherwise
     */
    public static boolean areEquals(double d1, double d2, double epsilon) {
        return ((Math.abs(d1-d2)) < epsilon);
    }

    /**
     * Returns the exponential value of exp((-((dist - baseDist))/temp))
     * @param baseDist a double value
     * @param dist a double value
     * @param temp a double value
     * @return the result
     */
    public static double exp(double baseDist, double dist, double temp) {
        return Math.exp((-((dist - baseDist))/temp));
    }

}
