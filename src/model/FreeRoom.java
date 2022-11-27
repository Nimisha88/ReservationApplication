package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\u001B[33m" + "FreeRoom" + "\u001B[0m" + " *** ";
    }
}
