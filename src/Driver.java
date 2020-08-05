import java.sql.Date;
import java.util.HashMap;
import java.util.Scanner;

import dao.ReservationDao;
import helper.InvalidInputException;
import helper.Validator;
import models.Reservation;
import models.Room;
import printers.ReservationPrinter;

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
    
    public static String promptEmail() {
    	System.out.println("Please enter your email");
    	String email = scanner.next();
    	return email;
    }

    public static Reservation promptNewReservation() throws InvalidInputException {
        System.out.println("Enter the check-in date[YYYY-MM-DD]");
        String checkinDateString = scanner.next();

        while(!Validator.validateDateFormat(checkinDateString)) {
            System.out.println("You should enter valid date!");
            checkinDateString = scanner.next();
        }

        Date checkinDate = Date.valueOf(checkinDateString);

        System.out.println("Enter the checkout date[YYYY-MM-DD]");
        String checkoutDateString = scanner.next();

        while(!Validator.validateDateFormat(checkoutDateString)) {
            System.out.println("You should enter valid date!");
            checkoutDateString = scanner.next();
        }

        Date checkoutDate = Date.valueOf(checkoutDateString);

        if (checkinDate.after(checkoutDate) || checkinDate.equals(checkoutDate)) {
            throw new InvalidInputException("Checkout should be after check-in");
        }

        return new Reservation(checkinDate, checkoutDate);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the WMAD hotel!");

        boolean done = false;

        while (!done) {
            String input = printMenu();
            switch (input) {
<<<<<<< HEAD
            	case "2":
            		String email = promptEmail();
            		ReservationPrinter.printReservations(email);
            		break;
=======
                case "1":
                    try {
                        Reservation request = promptNewReservation();
                        HashMap<Room, Boolean> availability = ReservationDao.findAvailableRoom(request);

                        for (Room r: availability.keySet()) {
                            System.out.printf("ROOM %s : %s\n", r.getRoomNo(), availability.get(r) ? "Available" : "Full");
                        }

                    } catch (InvalidInputException e) {
                        System.err.println("Internal Error occurred");
                        done = true;
                    }
                    break;
>>>>>>> find availability
            	case "4":
            		ReservationPrinter.printReservations("abc@gmail.com");
            		break;
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
