import java.util.*;

public class BookMyStayApp {

    static class RoomInventory {
        private Map<String, Integer> roomAvailability;

        public RoomInventory() {
            roomAvailability = new HashMap<>();
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public void incrementAvailability(String roomType) {
            roomAvailability.put(roomType,
                    roomAvailability.getOrDefault(roomType, 0) + 1);
        }

        public int getAvailability(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0);
        }
    }

    static class CancellationService {

        private Stack<String> releasedRoomIds;
        private Map<String, String> reservationRoomTypeMap;

        public CancellationService() {
            releasedRoomIds = new Stack<>();
            reservationRoomTypeMap = new HashMap<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {

            if (!reservationRoomTypeMap.containsKey(reservationId)) {
                System.out.println("Invalid cancellation request.");
                return;
            }

            String roomType = reservationRoomTypeMap.get(reservationId);

            releasedRoomIds.push(reservationId);
            inventory.incrementAvailability(roomType);

            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: "
                    + roomType);
        }

        public void showRollbackHistory() {

            System.out.println("\nRollback History (Most Recent First):");

            Stack<String> tempStack = (Stack<String>) releasedRoomIds.clone();

            while (!tempStack.isEmpty()) {
                System.out.println("Released Reservation ID: "
                        + tempStack.pop());
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        String reservationId = "Single-1";
        cancellationService.registerBooking(reservationId, "Single");

        cancellationService.cancelBooking(reservationId, inventory);

        cancellationService.showRollbackHistory();

        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailability("Single"));
    }
}
