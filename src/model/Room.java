package model;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    /**
     * Room constructor
     * @param roomNumber Room number
     * @param price Price of the room
     * @param roomType Type of room - Single/Double
     */
    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = roomType;
    }

    /**
     * Set room price
     * @param price Room price
     */
    void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Fetch Room Number
     * @return Room Number
     */
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Fetch Room Price
     * @return Room Price
     */
    @Override
    public Double getRoomPrice() {
        return price;
    }

    /**
     * Fetch Room Type
     * @return Room Type
     */
    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    /**
     * Validate if a room is Free
     * @return True if room price is 0, False if not
     */
    @Override
    public boolean isFree() {
        return price==0;
    }

    /**
     * Overriding toString
     * @return Room Object as String
     */
    @Override
    public String toString() {
        return "Room *** " +
                "Room Number: " + roomNumber + " *** " +
                "Price: " + price + " *** " +
                "Room Type: " + enumeration +
                " *** ";
    }
}
