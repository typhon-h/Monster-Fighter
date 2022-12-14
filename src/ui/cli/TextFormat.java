package ui.cli;

import main.Team;
import monsters.Monster;

/**
 * A class used to print out formatted text
 *
 * @author Jackie Jone
 * @version 1.2, May 2022
 */
public class TextFormat {
    /**
     * Gets the length of the longest string in the array
     *
     * @param strings An array to find the longest string of
     * @return The length of the longest string in the array
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
     * @return Text centered with padding using ' '
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

        for (int i = 0; i < leftRightPadding; i++)
            leftPadding = leftPadding.concat(" ");
        for (int i = 0; i < leftRightPadding; i++)
            rightPadding = rightPadding.concat(" ");
        rightPadding = rightPadding.concat(extraRightPadding ? " " : "");

        return leftPadding + string + rightPadding;
    }

    /**
     * Prints out the text as a fancy header
     *
     * @param text             The text to be printed out
     * @param whiteSpaceBefore Amount of white space to be printed before the header
     * @param horizBorder      Character of the horizontal border of the header
     */
    public static void printHeader(String text, int whiteSpaceBefore, char horizBorder) {
        int maxTextWidth = 36; // header width - 4
        int longestString;
        int headerWidth;

        String[] textArr = text.split(" ", 0);
        longestString = longestStringLen(textArr);

        maxTextWidth = longestString > maxTextWidth ? longestString : maxTextWidth;
        headerWidth = maxTextWidth + 4; // 4 char longer than maxTextWidth for border chars

        // Print white space
        for (int i = 0; i < whiteSpaceBefore; i++)
            System.out.println();

        for (int i = 0; i < headerWidth; i++)
            System.out.print(horizBorder);
        System.out.println();
        String finalString = "";

        int currWord = 0;
        while (currWord < textArr.length) {
            if (currWord == textArr.length - 1) {
                if ((finalString.length() + textArr[currWord].length()) > maxTextWidth) {
                    System.out.println("| " + centerText(finalString, maxTextWidth) + " |");
                    finalString = "";
                }
                finalString = finalString.concat(textArr[currWord]);
                System.out.println("| " + centerText(finalString, maxTextWidth) + " |");
                finalString = "";

            } else if ((finalString.length() + textArr[currWord].length()) == maxTextWidth) {
                finalString = finalString.concat(textArr[currWord]);

            } else if ((finalString.length() + textArr[currWord].length() + 1) < maxTextWidth) {
                finalString = finalString.concat(textArr[currWord].concat(" "));

            } else {
                System.out.println("| " + centerText(finalString, maxTextWidth) + " |");

                finalString = textArr[currWord] + (textArr[currWord].length() < maxTextWidth ? " " : "");
            }
            currWord++;
        }
        for (int i = 0; i < headerWidth; i++)
            System.out.print(horizBorder);
        System.out.println();
    }

    /**
     * Gets a string format of two teams during a battle
     *
     * @param allyTeam     Ally team respective to the player
     * @param opponentTeam Opponent team respective to the player
     * @return String format of both teams
     */
    public static String printBothTeams(Team allyTeam, Team opponentTeam) {
        String output = "| ";
        Monster currMonster;

        for (int i = allyTeam.getAliveMonsters().size() - 1; i >= 0; i--) {
            currMonster = allyTeam.getAliveMonsters().get(i);
            output = output + currMonster.getName() +
                    "(" + currMonster.getCurrentAttackDamage() +
                    "|" + currMonster.getCurrentHealth() + ")";

            if (i != 0) {
                output += " - ";
            }
        }

        output += " >   < ";

        for (int i = 0; i < opponentTeam.getAliveMonsters().size(); i++) {
            currMonster = opponentTeam.getAliveMonsters().get(i);
            output = output + currMonster.getName() +
                    "(" + currMonster.getCurrentAttackDamage() +
                    "|" + currMonster.getCurrentHealth() + ")";

            if (i != opponentTeam.getAliveMonsters().size() - 1) {
                output += " - ";
            }
        }
        output += " |";
        return output;
    }
}
