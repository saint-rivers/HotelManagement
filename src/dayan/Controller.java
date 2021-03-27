package dayan;

class Controller {

    static String[][] rooms;

    static int[] hotelDimensions;
    static Validator hotelRoomValidator;

    static class Interface{
        static void displayUserInterface(){
            String[] menuOptions = {
                    "1. Check in",
                    "2. Check out",
                    "3. Display all",
                    "4. Search for guest name",
                    "5. EXIT"
            };
            String header = "---------- Hotel Management System ----------";
            String footer = "---------------------------------------------";

            Menu mainMenu = new Menu(header, menuOptions, footer);
            mainMenu.displayHeader();
            mainMenu.displaySimpleMenu();
            mainMenu.displayCompleted();
        }
        static void createHotel(){

            String[] setupPrompts = {
                    "-> Enter number of floors",
                    "-> Enter number of rooms in each floor"
            };
            String setupTitle =
                    "---------- Setting up hotel ----------";

            Menu menu = new Menu(setupTitle, setupPrompts);
            menu.displayHeader();
            hotelDimensions = menu.getIntegersFromPrompts();
            hotelRoomValidator = new Validator(hotelDimensions[0], hotelDimensions[1]);

            menu.createCompletedMessageWithDimensions(hotelDimensions);
            rooms = menu.initializeRooms(hotelDimensions);

            menu.displayCompleted();
        }
    }

    static class Process{

        private final static int EXIT_CODE = 5;
        private final static String INPUT_PROMPT = "-> Choose option(1-5)";
        private final static String FAREWELL_MESSAGE = "-> Good bye!";

        static void loopUserOptions() {
            int choice;
            Validator menuValidator = new Validator(1, 5);

            do {
                Interface.displayUserInterface();
                choice = Console.getUserInput(INPUT_PROMPT);

                if (menuValidator.isInRange(choice)) continue;

                switchUserOptions(choice);
            } while(choice != EXIT_CODE);

            System.out.println(FAREWELL_MESSAGE);
        }

        private static void switchUserOptions(int choice){
            switch (choice){
                case 1:
                    HotelService.CheckInInterface.checkIn();
                    HotelService.HotelIO.showAllRoomDetails();
                    break;
                case 2:
                    HotelService.CheckOutInterface.checkOut();
                    break;
                case 3:
                    HotelService.HotelIO.showAllRoomDetails();
                    break;
                case 4:
                    HotelService.HotelIO.displayRoomNumberOfOccupant();
                    break;
                default:
                    break;
            }
        }
    }

    static class HotelService {

        static final String INFORMATION_HEADER = "---------- Display hotel information ----------";
        static final String CHECK_IN_HEADER = "---------- Check in to hotel ----------";
        static final String CHECK_OUT_HEADER = "---------- Checkout from hotel ----------";
        static final String SEARCH_GUEST_HEADER = "---------- Search Guest's Name ----------";
        static final String NAME_INPUT_PROMPT = "-> Enter guest's name";
        static final String[] ROOM_PROMPTS = Menu.createRoomFindInputPrompt(hotelDimensions);

        static class CheckInInterface {
            private static void checkIn() {
                Menu menu = new Menu(CHECK_IN_HEADER, ROOM_PROMPTS);

                UserInput userInput;
                userInput = HotelIO.getFloorAndRoomInput(menu);
                if (cannotFindRoom(userInput)) return;

                userInput.name = HotelIO.getOccupantNameInput(NAME_INPUT_PROMPT);
                System.out.println(userInput.name);
                boolean isSuccessful = attemptToCheckIn(userInput);
                menu.displayCheckInResult(userInput.name, isSuccessful);
            }

            private static boolean attemptToCheckIn(UserInput userInput) {
                if (isVacant(rooms, userInput)) {
                    rooms[userInput.floorNo][userInput.roomNo] = userInput.name;
                    System.out.println(userInput.name);
                    return true;
                }
                return false;
            }
        }

        static class CheckOutInterface {
            private static void checkOut() {
                UserInput userInput;
                Menu checkoutMenu = new Menu(CHECK_OUT_HEADER, ROOM_PROMPTS);

                userInput = HotelIO.getFloorAndRoomInput(checkoutMenu);
                if (cannotFindRoom(userInput)) return;
                checkOutOccupantDialogue(userInput);
            }
            private static void checkOutOccupantDialogue(UserInput userInput) {
                if (!isVacant(rooms, userInput)){
                    String name = rooms[userInput.floorNo][userInput.roomNo];
                    String prompt = "Press 1 to checkout and 0 to cancel";
                    String checkOutDialogue = "-> Guest's Name: "+ name +", ";

                    System.out.println(checkOutDialogue);
                    int isConfirmed = Console.getUserInput(prompt);

                    if (isConfirmed == 0) {
                        displayCancelCheckOut(name);
                        return;
                    }
                    removeOccupantFromRoom(userInput);
                    displayCheckedOutOccupant(name);
                    return;
                }
                displayMissingCheckout();
            }

            private static void displayMissingCheckout() {
                String checkOutFailed = "=> No one check in in this room, you can't checkout!";
                System.out.println(checkOutFailed);
            }

            private static void displayCancelCheckOut(String name) {
                String checkOutCancelled = "=> "+ name +" has cancelled the check out.";
                System.out.println(checkOutCancelled);
            }

            private static void removeOccupantFromRoom(UserInput userInput) {
                rooms[userInput.floorNo][userInput.roomNo] = null;
            }

            private static void displayCheckedOutOccupant(String name) {
                String checkOutSuccessful = "=> "+ name +" has been checked out successfully!";
                System.out.println(checkOutSuccessful);
            }
        }

        static class HotelIO{

            private static UserInput getFloorAndRoomInput(Menu menu) {
                UserInput userInput = new UserInput();
                int[] currentRoom = menu.getIntegersFromPrompts();
                userInput.setFloorNo(currentRoom[0] - 1);    // off by one error
                userInput.setRoomNo(currentRoom[1] - 1);     // off by one error
                return userInput;
            }

            private static String getOccupantNameInput(String prompt) {
                return Console.getStringInput(prompt);
            }

            private static void showAllRoomDetails() {
                int width = rooms[0].length;

                System.out.println(INFORMATION_HEADER);

                Console.printHoritontalDivider(width);
                Console.printHeaderRow("Room", width);
                Console.printHoritontalDivider(width);

                for (int i = 0; i < rooms.length; i++) {
                    Console.printWithPadding("Floor", i+1);
                    for (int j = 0; j < rooms[i].length; j++) {
                        Console.printVerticalCellDivider();
                        Console.printWithPadding(rooms[i][j]);
                    }
                    Console.printVerticalCellDivider();
                    System.out.println();
                    Console.printHoritontalDivider(width);
                }
                System.out.println();
            }

            private static void displayRoomNumberOfOccupant(){
                Menu menu = new Menu(SEARCH_GUEST_HEADER);
                menu.displayHeader();
                String name = getOccupantNameInput("-> enter guest's name to search");
                int[][] foundRooms = searchForKey(rooms, name);
                if (foundRooms != null) {
                    if (foundRooms.length <= 0) {
                        String message = "=> Result of searching : \n" +
                                "Guest : " + name + " is not staying here.";
                        System.out.println(message);
                        return;
                    }
                    for (int[] foundRoom : foundRooms) {
                        if (foundRoom == null) continue;
                        System.out.println();
                        int floor = foundRoom[0];
                        int room = foundRoom[1];
                        String message = "=> Result of search : \n" +
                                "Guest's Name : " + name + " is in Room : '" + room + "' On Floor : '" + floor + "' ";
                        System.out.println(message);
                    }
                }
            }
        }

        private static boolean cannotFindRoom(UserInput userInput) {
            if (hotelRoomValidator.isInRange(userInput.floorNo, userInput.roomNo)) {
                return false;
            }
            String errorMessage = "Room doesn't exist.";
            System.out.println(errorMessage);
            return true;
        }

        private static boolean isVacant(String[][] rooms, UserInput userInput){
            return rooms[userInput.floorNo][userInput.roomNo] == null;
        }

        public static int[][] searchForKey(String[][] arr, String search){
            if (!Validator.isValidEntry(search)) return null;

            int count = 0;
            int[][] tempResults = new int[5][2];
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    if (Validator.isMatchingName(arr[i][j], search)) {
                        tempResults[count][0] = i + 1;  // off by one error
                        tempResults[count][1] = j + 1;  // off by one error
                        ++count;
                    }
                }
            }

            int[][] results = new int[count][2];
            if (count >= 0) System.arraycopy(tempResults, 0, results, 0, count);
            return results;
        }
    }
}
