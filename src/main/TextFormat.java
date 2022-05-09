package main;

/**
 * A class used to print out formatted text
 */
public class TextFormat {
    /**
     * Gets the length of the longest string in the array
     *
     * @param strings An array to find the longest string of
     * @return        The length of the longest string in the array
     */
    private static int longestStringLen(String[] strings) {
        int longest = 0;
        for (String string : strings) {
            if (string.length() > longest) {
                longest = string.length();
            }
        }
        return longest;
    }

    /**
     * Centers the provided text with a given width
     *
     * @param string String to be centered
     * @param width  Width of the string to center to
     * @return       Text centered with padding using ' '
     */
    private static String centerText(String string, int width) {
        if (string.length() >= width) {
            return string;
        }

        String leftPadding = "";
        String rightPadding = "";
        // Always left-align center
        int padding = width - string.length();
        boolean extraRightPadding = padding % 2 == 1;
        int leftRightPadding = padding / 2;

        for (int i = 0; i < leftRightPadding; i++) leftPadding = leftPadding.concat(" ");
        for (int i = 0; i < leftRightPadding; i++) rightPadding = rightPadding.concat(" ");
        rightPadding = rightPadding.concat(extraRightPadding ? " " : "");

        return leftPadding + string + rightPadding;
    }

    /**
     * Prints out the text as a fancy header
     *
     * @param text The text to be printed out
     */
    public static void printHeader(String text) {
        int maxTextWidth = 36; // header width - 4
        int longestString;
        int headerWidth;
        System.out.println(text);
        String[] textArr = text.split(" ", 0);
        for (String string : textArr) System.out.println(string);
        longestString = longestStringLen(textArr);

        maxTextWidth = longestString > maxTextWidth ? longestString : maxTextWidth;
        headerWidth = maxTextWidth + 4; // 4 char longer than maxTextWidth for border chars

        for (int i = 0; i < headerWidth; i++) System.out.print("-");
        System.out.println();

        String FinalString = "";
        int currStringLen = 0;
        for (String string : textArr) {
            if (currStringLen + string.length() <= maxTextWidth) {
                FinalString = FinalString.concat(string.concat(" "));
                currStringLen += string.length();
            } else {
                FinalString = FinalString.concat("\n");
                currStringLen = 0;
            }
        }

        System.out.print("| ");
        FinalString = centerText(FinalString, maxTextWidth);
        System.out.print(FinalString + " |\n");

        for (int i = 0; i < headerWidth; i++) System.out.print("-");
        System.out.println();
    }
}
