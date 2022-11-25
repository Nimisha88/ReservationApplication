package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return price==0;
    }

    @Override
    public String toString() {
        return "Room *** " +
                "Room Number: " + roomNumber + " *** " +
                "Price: " + price + " *** " +
                "Room Type: " + enumeration +
                " *** ";
    }

    void setPrice(Double price) {
        this.price = price;
    }
}