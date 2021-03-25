package dayan;

class Occupant {
    int floorNo;
    int roomNo;
    String name;

    /**
     * get int input via command prompt
     * and subtract one to compensate
     * for the off by one error
     */
    public void setFloorUserInput() {
        this.floorNo = Console.getUserInput("Enter floor number") - 1;
    }

    /**
     * get int input via command prompt
     * and subtract one to compensate
     * for the off by one error
     */
    public void setRoomUserInput() {
        this.roomNo = Console.getUserInput("Enter room number") - 1;
    }

    public void getNameFromUserInput() {
        this.name = Console.getStringInput("Enter your name");
    }

    public void getFloorAndRoomFromUserInput(){
        setFloorUserInput();
        setRoomUserInput();
    }
}
