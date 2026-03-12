import java.util.*;

public class BookMyStayApp {

    static class Reservation {
        private String guestName;
        private String roomType;
        private String reservationId;

        public Reservation(String guestName, String roomType, String reservationId) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.reservationId = reservationId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getReservationId() {
            return reservationId;
        }
    }

    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    static class AddOnServiceManager {

        private Map<String, List<AddOnService>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, AddOnService service) {
            servicesByReservation
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {
            List<AddOnService> services = servicesByReservation.get(reservationId);
            if (services == null) return 0.0;

            double total = 0.0;
            for (AddOnService service : services) {
                total += service.getCost();
            }
            return total;
        }
    }

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        Reservation reservation = new Reservation("Abhi", "Single", "Single-1");

        AddOnService breakfast = new AddOnService("Breakfast", 500.0);
        AddOnService spa = new AddOnService("Spa", 1000.0);

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        serviceManager.addService(reservation.getReservationId(), breakfast);
        serviceManager.addService(reservation.getReservationId(), spa);

        double totalCost = serviceManager
                .calculateTotalServiceCost(reservation.getReservationId());

        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
