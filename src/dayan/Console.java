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

    static void printWithPadding(Object obj){
        System.out.printf("%15s", obj);
    }

    static void printRowHead(String tag, int i){
        System.out.printf("%7s %d:%s", tag, i, "    ");
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
