import java.util.Scanner;

public class Driver {
    public static Scanner scanner = new Scanner(System.in);
    public static String printMenu() {
        System.out.println("Please choose:");
        System.out.println("[1] Make a new reservation");
        System.out.println("[2] Print all reservation of customer");
        System.out.println("[3] Delete the reservation");
        System.out.println("[4] Print check-in reservations of day");
        System.out.println("[5] Print check-out reservations of day");
        System.out.println("[6] Print current available rooms");
        System.out.println("[7] Print current used rooms");
        System.out.println("[q] Quit");
        System.out.println();

        return scanner.next();
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the WAMD hotel!");

        boolean done = false;

        while (!done) {
            String input = printMenu();
            switch (input) {
                case "q":
                case "Q":
                    done = true;
                    break;
                default:
                    System.out.println("You should choose valid option!");
            }
        }
    }
}
