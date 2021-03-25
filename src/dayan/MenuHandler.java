package dayan;

public class MenuHandler {
    public String start;
    public String[] prompts;
    public String completeMessage;

    public MenuHandler(String start, String[] prompts) {
        this.start = start;
        this.prompts = prompts;
    }

    public MenuHandler(String start, String[] prompts, String completeMessage) {
        this.start = start;
        this.prompts = prompts;
        this.completeMessage = completeMessage;
    }

    /**
     * returns a 2D string array from an int array as dimensions
     */
    public String[][] initializeRooms(int[] a){
        return new String[a[0]][a[1]];
    }

    public void setCompleteMessage(String completeMessage) {
        this.completeMessage = completeMessage;
    }

    /**
     * show the message when initialization is complete
     */
    public void showCompleted(){
        Console.printSimple(this.completeMessage);
    }

    /**
     * prints the top header for the proceeding prompts
     */
    void printStart(){
        Console.printSimple(start);
    }

    /**
     * prints a simple menu with no UI prompt
     */
    public void displaySimpleMenu(){
        for (String line: prompts){
            Console.printSimple(line);
        }
    }

    /**
     * prompts the user for size of the hotel via console UI
     * @return
     */
    public int[] getIntegersFromPrompts() {
        int[] indexes = new int[prompts.length];
        for (int i = 0; i < prompts.length; i++) {
            indexes[i] = Console.getUserInput(prompts[i]);
        }
        return indexes;
    }

    /**
     * creates a string showing the dimensions of the hotel
     * @param dimensions an int array with two numbers
     */
    void createCompletedMessageWithDimensions(int[] dimensions) {
        String message =  "=> Hotel is already set up with "
                + dimensions[0] + " floors and " + dimensions[1]
                + " rooms successfully.";
        setCompleteMessage(message);
    }

}
