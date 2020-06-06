package utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides useful methods to get random colors
 *
 * @author Lucas Aupoil, Ewald Janin
 */
public class ColorPicker {

    private static List<Color> availableColors;
    private static Color[] colorPool = {
//      new Color(0x99, 0x00, 0x99),  //purple
//      new Color(0xcc, 0x33, 0x00),  //red
//      new Color(0xff, 0xcc, 0x66),  //beige?
//      // new Color(0x00, 0x00, 0xff),
//      // new Color(0x66, 0x00, 0x99),
//      new Color(0x99, 0x66, 0x00), //brown
//      new Color(0xcc, 0x66, 0x00), //tan
//      new Color(0xff, 0xcc, 0xff), //pink
//      new Color(0x0, 0x66, 0x00),  //dark green
//      new Color(0x66, 0x00, 0xff), //blue
//      new Color(0x99, 0x66, 0xff), //off-blue
//      new Color(0xcc, 0x66, 0x66), //another tan
//      new Color(0xff, 0xff, 0x00), //yellow
//      new Color(0x0, 0x99, 0x00),  //green
//      new Color(0x66, 0x66, 0x99), //slate
//      new Color(0x99, 0x99, 0x99), //grey
//      new Color(0xcc, 0x99, 0x00), //off-tan
//      new Color(0x00, 0x99, 0x99), //puce
//      new Color(0x66, 0xcc, 0xff), //aqua
//      new Color(0x99, 0xcc, 0x00), //peagreen
//      new Color(0xcc, 0x99, 0xcc), //washedoutpurple
//      new Color(0x00, 0xff, 0x66), //limegreen
//      new Color(0xcc, 0xff, 0x00), //yellow-green
//      new Color(0x00, 0xff, 0xff), //light blue
            // new Color(0x66, 0x00, 0x00),
            // new Color(0x00, 0x00, 0x99)
            new Color(0x56, 0xae, 0x57),  //  dark pastel green
            new Color(0x95, 0xd0, 0xfc),  //  light blue
            new Color(0xcb, 0x77, 0x23),  //  brownish orange
            new Color(0x05, 0x69, 0x6b),  //  dark aqua
            new Color(0xce, 0x5d, 0xae),  //  purplish pink
            new Color(0xc8, 0x5a, 0x53),  //  dark salmon
            new Color(0x96, 0xae, 0x8d),  //  greenish grey
            new Color(0x1f, 0xa7, 0x74),  //  jade
            new Color(0xfd, 0x41, 0x1e),  //  orange red
            new Color(0x9a, 0x02, 0x00),  //  deep red
            new Color(0x03, 0x0a, 0xa7),  //  cobalt blue
            new Color(0xfe, 0x01, 0x9a),  //  neon pink
            new Color(0x88, 0x71, 0x91),  //  greyish purple
            new Color(0xb0, 0x01, 0x49),  //  raspberry
            new Color(0x12, 0xe1, 0x93),  //  aqua green
            new Color(0xfe, 0x7b, 0x7c),  //  salmon pink
            new Color(0x8b, 0x2e, 0x16),  //  red brown
            new Color(0x69, 0x61, 0x12),  //  greenish brown
            new Color(0x0a, 0x48, 0x1e),  //  pine green
            new Color(0x34, 0x38, 0x37),  //  charcoal
            new Color(0x6a, 0x79, 0xf7),  //  cornflower
            new Color(0x5d, 0x06, 0xe9),  //  blue violet
            new Color(0x3d, 0x1c, 0x02),  //  chocolate
            new Color(0x82, 0xa6, 0x7d),  //  greyish green
            new Color(0xbe, 0x01, 0x19),  //  scarlet
            new Color(0x03, 0x43, 0xdf),  //  blue
            new Color(0x15, 0xb0, 0x1a),  //  green
            new Color(0x7e, 0x1e, 0x9c) }; //  purple

    public static void initColors(){
        availableColors = new ArrayList<>(Arrays.asList(colorPool));
    }

    public static Color getRandomColor(){
        int rand = RandUtils.randInt(0, availableColors.size());
        Color c = availableColors.get(rand);
        availableColors.remove(rand);
        return c;
    }

}
