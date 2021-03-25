package dayan;

class MainController {

    static String[][] rooms;

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
            mainMenu.showCompleted();
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
            int[] dimensions = menu.getIntegersFromPrompts();

            menu.createCompletedMessageWithDimensions(dimensions);
            rooms = menu.initializeRooms(dimensions);

            menu.showCompleted();
        }
    }

    static class Process{

        static void loopUserOptions() {
            int choice;
            do {
                Interface.displayUserInterface();
                choice = Console.getUserInput("-> Choose option(1-5)");
                switchUserOptions(choice);
            } while(choice != 5);
        }

        static void switchUserOptions(int choice){
            Occupant occupant = new Occupant();
            switch (choice){
                case 1:
                    HotelLogic.checkIn(occupant);
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

        private static void checkIn(Occupant occupant) {
            occupant.getFloorAndRoomFromUserInput();
            occupant.getNameFromUserInput();
            // check if room is within range
            // check if the room is occupied
            rooms[occupant.floorNo][occupant.roomNo] = occupant.name;
        }

        private static void checkOut(Occupant occupant) {
            occupant.getFloorAndRoomFromUserInput();
            // check if room is within range
            // check if the room is occupied
            rooms[occupant.floorNo][occupant.roomNo] = null;
        }

        private static void showAllRoomDetails() {
            for (int i = 0; i < rooms.length; i++) {
                Console.printRowHead("Floor", i+1);
                for (int j = 0; j < rooms[i].length; j++) {
                    Console.printWithPadding(rooms[i][j]);
                }
                Console.newLine();
            }
        }
    }
}
