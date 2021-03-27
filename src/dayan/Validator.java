package dayan;

import java.util.regex.*;

public class Validator {

    Integer a;
    Integer b;

    public Validator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Check if two integers are within the range of the hotel dimensions.
     * It will adjust for the off by one error, so the user can use a non-zero based index.
     * @param floor Floor number, non-zero index.
     * @param room Room number, non-zero index.
     * @return True if in range.
     */
    boolean isInRange(Integer floor, Integer room){
        if (isMissingBounds()) return false;

        String searchFloor = "[0" + "-" + (this.a - 1) + "]";
        String searchRoom = "[0" + "-" + (this.b - 1) + "]";

        boolean floorIsInRange = Pattern.matches(searchFloor, floor.toString());
        boolean roomIsInRange = Pattern.matches(searchRoom, room.toString());

        return floorIsInRange && roomIsInRange;
    }

    /**
     * Check whether user input is within range of the class.
     * The range is set with the constructor.
     * @param input The number the user input in the console.
     * @return Returns true if input is within range.
     */
    boolean isInRange(Integer input){
        if (isMissingBounds()) return false;
        String search = "^\\d[" + this.a + "-" + this.b + "]$";
        return Pattern.matches(search, input.toString());
    }

    static boolean isDigit(String input){
        String search = "[0-9]{1,2}";
        return Pattern.matches(search, input);
    }

    /**
     * Check if local variables are null.
     * @return Returns true if they are null.
     */
    private boolean isMissingBounds(){
        return this.a == null || this.b == null;
    }

    /**
     * Check if the input is a valid person's name.
     * @param name The name to check.
     * @return True if name is valid for use.
     */
    static boolean isValidEntry(String name){
        if (name == null) return false;
        Pattern pattern = Pattern.compile("[A-Z]*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Check if name matches each other.
     * @param searchPattern Word in array you want to match.
     * @param keyword User inputted word to search for.
     * @return  True if a match is found in the array.
     */
    static boolean isMatchingName(String searchPattern, String keyword){
        if (searchPattern == null || keyword == null) return false;
        Pattern pattern = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(keyword);
        return matcher.matches();
    }
}
