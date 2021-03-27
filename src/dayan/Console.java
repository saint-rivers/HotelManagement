package dayan;

import java.util.Scanner;

/**
 * This class contains functions
 * for inputting and outputting data
 * in various formats.
 *
 */

public class Console {
    static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    static void printEmptyTab(){
        System.out.printf("%2s%-10s", "", "HOTEL");
    }

    /**
     * Prints divider only the size of a cell
     */
    private static void printHorizontalCellDivider(){
        System.out.print("-------------");
    }

    protected static void printVerticalCellDivider(){
        System.out.print("|");
    }

    /**
     * Prints a line to to divide table rows.
     * @param width Number of cells in the table.
     */
    protected static void printHoritontalDivider(int width) {
        Console.printHorizontalCellDivider();
        for (int i = 0; i < width; i++) {
            Console.printHorizontalCellDivider();
        }
        System.out.println();
    }

    /**
     * Iterates over i to print out tag with the i.
     * @param tag String data you wish to show.
     * @param width Width of single header cell
     */
    protected static void printHeaderRow(String tag, int width) {
        printEmptyTab();
        printVerticalCellDivider();
        for (int i = 0; i < width; i++) {
            Console.printWithPadding(tag, i);
            printVerticalCellDivider();
        }
        System.out.println();
    }

    /**
     * Print data with given only 10 spaces
     * @param obj Any data
     */
    static void printWithPadding(Object obj){
        System.out.printf("%2s%-10s", "",obj);
    }

    /**
     * Print given data pair string and other data.
     * Also contained in a space of 10 spaces.
     * @param tag String
     * @param obj   Any data
     */
    static void printWithPadding(String tag, Object obj){
        System.out.printf("%2s%-5s%-2s%3s", "", tag, obj, "");
    }

    static void printRowHead(String tag, int i){
        System.out.printf("%2s%-8s%d", "", tag, i);
    }


    static int getUserInput(String message){
        System.out.print(message + ": ");
        String s = scanner.nextLine();
        if (Validator.isDigit(s))
            return Integer.parseInt(s);
        return 0;
    }

    static String getStringInput(String message){
        System.out.print(message + ": ");
        return scanner.nextLine();
    }
}
