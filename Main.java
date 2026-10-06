import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    String type;
    double price;
    boolean available;

    Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.available = true;
    }

    void displayRoom() {
        System.out.println(
            "Room " + roomNumber +
            " | " + type +
            " | Rs." + price +
            " per night | " +
            (available ? "Available" : "Booked")
        );
    }
}

class Reservation {
    String customerName;
    int roomNumber;
    String roomType;
    double amount;
    boolean paid;

    Reservation(String customerName, int roomNumber,
                String roomType, double amount) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.amount = amount;
        this.paid = false;
    }

    void displayBooking() {
        System.out.println("\n===== BOOKING DETAILS =====");
        System.out.println("Customer Name : " + customerName);
        System.out.println("Room Number   : " + roomNumber);
        System.out.println("Room Type     : " + roomType);
        System.out.println("Amount        : Rs." + amount);
        System.out.println("Payment       : " + (paid ? "Paid" : "Pending"));
    }
}

public class Main {

    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Room data
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));

        int choice;

        do {
            System.out.println("\n==============================");
            System.out.println("     HOTEL RESERVATION SYSTEM");
            System.out.println("==============================");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Make Payment");
            System.out.println("5. View Booking Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelReservation();
                    break;

                case 4:
                    makePayment();
                    break;

                case 5:
                    viewBookings();
                    break;

                case 6:
                    System.out.println(
                        "Thank you for using Hotel Reservation System!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }

    // Search available rooms
    static void searchRooms() {

        System.out.println("\n===== AVAILABLE ROOMS =====");

        boolean found = false;

        for (Room room : rooms) {
            if (room.available) {
                room.displayRoom();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available.");
        }
    }

    // Book a room
    static void bookRoom() {

        System.out.println("\n===== BOOK ROOM =====");

        System.out.print("Enter customer name: ");
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();

        Room selectedRoom = null;

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("Room is already booked.");
            return;
        }

        Reservation reservation = new Reservation(
            name,
            selectedRoom.roomNumber,
            selectedRoom.type,
            selectedRoom.price
        );

        reservations.add(reservation);
        selectedRoom.available = false;

        System.out.println("\nRoom booked successfully!");
        System.out.println("Customer: " + name);
        System.out.println("Room No: " + selectedRoom.roomNumber);
        System.out.println("Room Type: " + selectedRoom.type);
        System.out.println("Amount: Rs." + selectedRoom.price);
    }

    // Cancel reservation
    static void cancelReservation() {

        System.out.println("\n===== CANCEL RESERVATION =====");

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();

        Reservation foundReservation = null;

        for (Reservation reservation : reservations) {
            if (reservation.roomNumber == roomNumber) {
                foundReservation = reservation;
                break;
            }
        }

        if (foundReservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        for (Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                room.available = true;
                break;
            }
        }

        reservations.remove(foundReservation);

        System.out.println("Reservation cancelled successfully.");
    }

    // Payment
    static void makePayment() {

        System.out.println("\n===== PAYMENT =====");

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();

        for (Reservation reservation : reservations) {

            if (reservation.roomNumber == roomNumber) {

                if (reservation.paid) {
                    System.out.println("Payment is already completed.");
                    return;
                }

                System.out.println(
                    "Amount to pay: Rs." + reservation.amount
                );

                System.out.print("Confirm payment? (yes/no): ");
                String answer = sc.next();

                if (answer.equalsIgnoreCase("yes")) {
                    reservation.paid = true;
                    System.out.println("Payment successful!");
                } else {
                    System.out.println("Payment cancelled.");
                }

                return;
            }
        }

        System.out.println("Reservation not found.");
    }

    // View booking details
    static void viewBookings() {

        System.out.println("\n===== ALL BOOKINGS =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation reservation : reservations) {
            reservation.displayBooking();
        }
    }
}