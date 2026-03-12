import java.io.*;
import java.util.*;

public class BookMyStayApp {

    static class RoomInventory {
        private Map<String, Integer> roomAvailability = new HashMap<>();

        public RoomInventory() {
            roomAvailability.put("Single", 5);
            roomAvailability.put("Double", 3);
            roomAvailability.put("Suite", 2);
        }

        public Map<String, Integer> getAllAvailability() {
            return roomAvailability;
        }

        public void setAvailability(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }
    }

    static class FilePersistenceService {

        public void saveInventory(RoomInventory inventory, String filePath) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                for (Map.Entry<String, Integer> entry :
                        inventory.getAllAvailability().entrySet()) {

                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }

                System.out.println("Inventory saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        public void loadInventory(RoomInventory inventory, String filePath) {

            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("=");

                    if (parts.length == 2) {
                        String roomType = parts[0];
                        int count = Integer.parseInt(parts[1]);
                        inventory.setAvailability(roomType, count);
                    }
                }

            } catch (Exception e) {
                System.out.println("Error loading inventory. Starting fresh.");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        persistenceService.loadInventory(inventory, filePath);

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry :
                inventory.getAllAvailability().entrySet()) {

            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        persistenceService.saveInventory(inventory, filePath);
    }
}
