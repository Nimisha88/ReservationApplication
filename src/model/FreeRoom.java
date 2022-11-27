package model;

public class FreeRoom extends Room{
    /**
     * Free room constructor
     * @param roomNumber Room number
     * @param roomType Room Type
     */
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    /**
     * Overriding toString
     * @return FreeRoom Object as String
     */
    @Override
    public String toString() {
        return super.toString() +
                "\u001B[33m" + "FreeRoom" + "\u001B[0m" + " *** ";
    }
}
