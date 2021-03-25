package dayan;

/**
 * This class contains functions
 * for inputting and outputting data
 * in various formats.
 *
 */

import java.util.Scanner;

public class Console {
    static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    static void write(Object obj){
        System.out.print(obj);
    }

    static void writeLine(Object obj){
        System.out.println(obj);
    }

    static void printWithPadding(Object obj){
        System.out.printf("%15s", obj);
    }

    static void printRowHead(String tag, int i){
        System.out.printf("%7s %d:%s", tag, i, "    ");
    }

    static int getUserInput(String message){
        System.out.print(message + ": ");
        return scanner.nextInt();
    }

    static String getStringInput(String message){
        System.out.print(message + ": ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void newLine(){
        System.out.println();
    }
}
