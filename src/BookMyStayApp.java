import java.util.*;

public class BookMyStayApp {

    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    static class RoomInventory {
        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            roomAvailability.put("Single", 2);
            roomAvailability.put("Double", 2);
            roomAvailability.put("Suite", 1);
        }

        public boolean isValidRoomType(String roomType) {
            return roomAvailability.containsKey(roomType);
        }

        public int getAvailability(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0);
        }
    }

    static class ReservationValidator {

        public void validate(
                String guestName,
                String roomType,
                RoomInventory inventory
        ) throws InvalidBookingException {

            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }

            if (!inventory.isValidRoomType(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }

            if (inventory.getAvailability(roomType) <= 0) {
                throw new InvalidBookingException("Selected room type is not available.");
            }
        }
    }

    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> requestQueue;

        public BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        public void addRequest(Reservation reservation) {
            requestQueue.offer(reservation);
        }
    }

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {

            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(guestName, roomType, inventory);

            Reservation reservation = new Reservation(guestName, roomType);
            bookingQueue.addRequest(reservation);

            System.out.println("Booking request accepted successfully.");

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}
