
import java.util.stream.Stream;

/**
 * This class does some simple string-parsing for the input file so I can
 * avoid needless code complications in the main class.
 * 
 * @author Joshua
 */
public class Utilities {

    /**
     * Parse the String's value after the colon, but return it as a String.
     *
     * @param s
     * @return
     */
    public static String strParseColon (String s) {
        return s.substring(s.indexOf(":") + 2);

    }

    /**
     * Parse the String's value after the colon as an integer. The + 2 factor
     * determines how many spaces after the colon, including the colon to scan.
     *
     * @param s
     * @return
     */
    public static int intParseColon (String s) {
        return Integer.parseInt(s.substring(s.indexOf(":") + 2));
    }

    /**
     * Returns an integer array from a String of integers, separated by a
     * delimiter. This delimiter must be specified.
     *
     * @param arr
     * @param delimiter
     * @return
     */
    public static int[] splitIntegerStr (String arr, String delimiter) {
        return Stream.of(arr.split(delimiter))
                .mapToInt(token -> Integer.parseInt(token))
                .toArray();
    }

    /**
     * Removes all spaces from the string, and returns it as a char[] array.
     *
     * @param arr
     * @return
     */
    public static char[] splitAlphabetStr (String arr) {
        return arr.replaceAll("\\s", "").toCharArray();
    }

}
