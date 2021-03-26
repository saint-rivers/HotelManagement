package dayan;

public class Menu {
    public String header;
    public String[] prompts;
    public String completeMessage;

    public Menu(String header, String[] prompts) {
        this.header = header;
        this.prompts = prompts;
    }

    public Menu(String header, String[] prompts, String completeMessage) {
        this.header = header;
        this.prompts = prompts;
        this.completeMessage = completeMessage;
    }

    public Menu(String header) {
        this.header = header;
    }

    /**
     * returns a 2D string array from an int array as dimensions
     */
    public String[][] initializeRooms(int[] a){
        return new String[a[0]][a[1]];
    }

    /**
     * Method to initialize message for when a process is completed successfully,
     * but meant for use after the running process.
     * @param completeMessage Message for when a process is completed
     */
    public void setCompleteMessage(String completeMessage) {
        this.completeMessage = completeMessage;
    }

    /**
     * show the message when initialization is complete
     */
    public void displayCompleted(){
        System.out.println(this.completeMessage);
    }
    public static void displayCompleted(String s){
        System.out.println(s);
    }

    /**
     * prints the top header for the proceeding prompts
     */
    void displayHeader(){
        System.out.println(this.header);
    }

    /**
     * prints a simple menu with no UI prompt
     */
    public void displaySimpleMenu(){
        for (String line: prompts){
            System.out.println(line);
        }
    }

    /**
     * prompts the user for size of the hotel via console UI
     * @return returns a list of numbersm inputted by user.
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

    static String[] createRoomFindInputPrompt(int[] dimensions){
        return new String[] {
                "-> Enter floor number("+1+"-"+dimensions[0]+")",
                "-> Enter room number("+1+"-"+dimensions[1]+")"
        };
    }

    void displayCheckInResult(String name, boolean successful) {
        if (successful){
            String complete = "=> "+ name +" checked in successfully!";
            displayCompleted(complete);
            return;
        }
        String failed = "-> This room is already checked in, Please find another room!";
        displayCompleted(failed);
    }
}
