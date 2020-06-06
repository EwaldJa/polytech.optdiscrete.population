package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class provides useful methods to format values
 *
 * @author Ewald Janin, Lucas Aupoil
 */
public class FormatUtils {

    /**
     * Rounds the input double with the specified number of decimal places
     * @param value double to round
     * @param places number of decimal places
     * @return the rounded double
     * @throws IllegalArgumentException if places is negative
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
