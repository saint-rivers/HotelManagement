package dayan;

class MainController {

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

            MenuHandler mainMenu = new MenuHandler(header, menuOptions, footer);
            Console.clearScreen();
            mainMenu.printStart();
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

            MenuHandler menu = new MenuHandler(setupTitle, setupPrompts);
            Console.clearScreen();
            menu.printStart();
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

            Console.writeLine(FAREWELL_MESSAGE);
        }

        static void switchUserOptions(int choice){
            Occupant occupant = new Occupant();
            switch (choice){
                case 1:
                    HotelLogic.checkIn(occupant);
                    HotelLogic.showAllRoomDetails();
                    break;
                case 2:
                    HotelLogic.checkOut(occupant);
                    break;
                case 3:
                    HotelLogic.showAllRoomDetails();
                    break;
                default:
                    break;
            }
        }
    }

    static class HotelLogic{

        static final String INFORMATION_HEADER = "---------- Display hotel information ----------";
        static final String CHECK_IN_HEADER = "---------- Checkin to hotel ----------";
        static final String CHECK_OUT_HEADER = "---------- Checkout from hotel ----------";
        static final String[] ROOM_PROMPTS = MenuHandler.createRoomFindInputPrompt(hotelDimensions);
        static String checkInComplete;

        static int[] currentRoom;

        private static void checkIn(Occupant occupant) {
            MenuHandler checkInHandler = new MenuHandler(CHECK_IN_HEADER, ROOM_PROMPTS);

            getFloorAndRoomInput(checkInHandler, occupant);
            if (cannotFindRoom(occupant)) return;

            String name = getOccupantNameInput(occupant);
            boolean successful = checkInOccupant(occupant);
            if (successful){
                displayCheckInResults(checkInHandler, name);
                return;
            }
            Console.writeLine("-> This room is already checked in, Please find another room!");
        }

        private static void checkOut(Occupant occupant) {
            MenuHandler checkoutHandler = new MenuHandler(CHECK_OUT_HEADER, ROOM_PROMPTS);

            getFloorAndRoomInput(checkoutHandler, occupant);
            if (cannotFindRoom(occupant)) return;
            checkOutOccupantDialogue(occupant);
        }

        private static void checkOutOccupantDialogue(Occupant occupant) {
            if (!isVacant(rooms, occupant)){
                String name = rooms[occupant.floorNo][occupant.roomNo];
                String prompt = "Press 1 to checkout and 0 to cancel: 1";
                String checkOutDialogue = "-> Guest's Name: "+ name +", ";

                Console.write(checkOutDialogue);
                int checkOutConfirmed = Console.getUserInput(prompt);

                if (checkOutConfirmed == 0) {
                    cancelCheckOut(name);
                    return;
                }
                removeOccupantFromRoom(occupant, name);
            }
        }

        private static void cancelCheckOut(String name) {
            String checkOutCancelled = "=> "+ name +" has cancelled the check out.";
            Console.writeLine(checkOutCancelled);
        }

        private static void removeOccupantFromRoom(Occupant occupant, String name) {
            String checkOutSuccessful = "=> "+ name +" has been checked out successfully!";
            rooms[occupant.floorNo][occupant.roomNo] = null;
            Console.writeLine(checkOutSuccessful);
        }

        private static boolean checkInOccupant(Occupant occupant) {
            if (isVacant(rooms, occupant)) {
                rooms[occupant.floorNo][occupant.roomNo] = occupant.name;
                return true;
            }
            return false;
        }

        private static String getOccupantNameInput(Occupant occupant) {
            String name = Console.getStringInput("-> Enter guest's name");
            occupant.setName(name);
            return name;
        }

        private static void displayCheckInResults(MenuHandler checkInHandler, String name) {
            checkInComplete = "=> "+ name +" checked in successfully!";
            checkInHandler.setCompleteMessage(checkInComplete);
            checkInHandler.displayCompleted();
        }

        private static boolean cannotFindRoom(Occupant occupant) {
            if (!hotelRoomValidator.isInRange(occupant.floorNo, occupant.roomNo))
            {
                String errorMessage = "Room doesn't exist.";
                Console.writeLine(errorMessage);
                return true;
            }
            return false;
        }

        private static void getFloorAndRoomInput(MenuHandler menu, Occupant occupant) {
            currentRoom = menu.getIntegersFromPrompts();
            occupant.setFloorNo(currentRoom[0] - 1);    // off by one error
            occupant.setRoomNo(currentRoom[1] - 1);     // off by one error
        }

        private static void showAllRoomDetails() {
            Console.writeLine(INFORMATION_HEADER);
            for (int i = 0; i < rooms.length; i++) {
                Console.printRowHead("Floor", i+1);
                for (int j = 0; j < rooms[i].length; j++) {
                    Console.printWithPadding(rooms[i][j]);
                }
                Console.newLine();
            }
        }

        private static boolean isVacant(String[][] rooms, Occupant occupant){
            if (rooms[occupant.floorNo][occupant.roomNo] == null) return true;
            return false;
        }
    }
}
