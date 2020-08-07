import java.sql.Date;
import java.util.HashMap;
import java.util.Scanner;

import dao.CustomerDao;
import dao.ReservationDao;
import helper.InvalidInputException;
import helper.KeyValue;
import helper.Utils;
import helper.Validator;
import models.Customer;
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
        System.out.println("[6] Print today's available rooms");
        System.out.println("[7] Print today's used rooms");
        System.out.println("[q] Quit");
        System.out.println();

        return scanner.next();
    }
    
    public static String promptEmail() {
    	System.out.println("Please enter your email");
    	String email = scanner.next();
    	while(!Validator.validateEmailAdd(email)) {
    		System.out.println("The email is wrong, Please try one more time.");
    		email = scanner.next();
    	}
    	return email;
    }

    public static boolean promptAccept() {
        System.out.println("Will you book this reservation? [Y/N]:");
        String answer = scanner.next();

        return answer.equals("Y");
    }

    public static Date promptDate(String type) {
        System.out.println("Enter the " + type + " date [YYYY-MM-DD format or 'today']:");
        String dateString = scanner.next();

        if (dateString.toLowerCase().equals("today")) {
            return new Date(new java.util.Date().getTime());
        }

        while(!Validator.validateDateFormat(dateString)) {
            System.out.println("You should enter valid date!");
            dateString = scanner.next();
        }

        return Date.valueOf(dateString);
    }

    public static void promptDeleteReservation() {
    	System.out.println("Enter the a valid integer to delete your reservation");
        int reservationId = scanner.nextInt();

        ReservationDao.deleteReservation(new Reservation(reservationId));
        System.out.println("Successfully deleted your reservation!");
    }

    public static Customer promptCustomerDetail(String email) {
        System.out.println("Enter the first name: ");
        String fname = scanner.next();

        System.out.println("Enter the last name: ");
        String lname = scanner.next();

        System.out.println("Enter the phone number: ");
        String pnum = scanner.next();

        while(!Validator.validatePhoneNum(pnum)) {
            System.out.println("Enter the valid phone number!");
            pnum = scanner.next();
        }

        return new Customer(email, fname + " " + lname, pnum);
    }

    public static Reservation promptNewReservation() throws InvalidInputException {
        long now = new java.util.Date().getTime();
        Date yesterday = new Date(now - 24 * 60 * 60 * 1000);

        Date checkinDate = promptDate("check-in");
        if (checkinDate.before(yesterday)) {
            throw new InvalidInputException("Cannot make reservation for previous date");
        }

        Date checkoutDate = promptDate("checkout");

        if (checkoutDate.before(yesterday)) {
            throw new InvalidInputException("Cannot make reservation for previous date");
        }

        if (checkinDate.after(checkoutDate) || checkinDate.equals(checkoutDate)) {
            throw new InvalidInputException("Checkout should be after than check-in");
        }

        System.out.println("Enter the number of person to stay[1-2]");
        int person = scanner.nextInt();

        if (person > 2 || person < 1) {
            throw new InvalidInputException("Person should be 1 or 2");
        }

        return new Reservation(checkinDate, checkoutDate, person);
    }

    public static void promptMakeReservationSequence() throws InvalidInputException {
        Reservation request = promptNewReservation();
        HashMap<Room, Boolean> availability = ReservationDao.findAvailableRoom(request);
        KeyValue<Room, Reservation> plan = Utils.findCheapestPlanAndRoom(availability);

        System.out.println("Here's your deal!");

        assert plan != null;
        System.out.printf("Floor: %2s\n", plan.getKey().getFloor());
        System.out.printf("Price: $%s\n", plan.getValue().getPrice());

        while (!promptAccept() || availability.size() < 1) {
            // find room and remove from availability
            availability.remove(plan.getKey());
            if (availability.size() < 1) {
                plan = null;
                break;
            }

            plan = Utils.findCheapestPlanAndRoom(availability);
            if (plan == null) {
                break;
            }

            System.out.println("Here's your new deal!");
            System.out.printf("Floor: %2s\n", plan.getKey().getFloor());
            System.out.printf("Price: $%s\n", plan.getValue().getPrice());
        }

        if (plan == null) {
            throw new InvalidInputException("No plans matches");
        }

        String email = promptEmail();
        Customer customer = CustomerDao.findCustomer(email);

        if (customer == null) {
            customer = promptCustomerDetail(email);
            CustomerDao.addNewCustomer(customer);
        }

        Room room = plan.getKey();
        Reservation reservation = plan.getValue();
        reservation.setPerson(request.getPerson());
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());

        ReservationDao.addNewReservation(reservation, customer, room);

        System.out.println("Success to book a reservation!");
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the WAMD hotel!");

        boolean done = false;

        while (!done) {
            String input = printMenu();
            switch (input) {
                case "1":
                    try {
                        promptMakeReservationSequence();
                        System.out.println();
                    } catch (InvalidInputException e) {
                        System.err.println("Internal Error occurred");
                        done = true;
                    }
                    break;
            	case "2":
            		String email = promptEmail();
            		ReservationPrinter.printReservations(email);
                    System.out.println();
            		break;
            	case "3":
            		promptDeleteReservation();
                    System.out.println();
            		break;
            	case "4":
                    try {
            	        Date checkinDate = promptDate("check-in");
                        ReservationPrinter.printReservations(checkinDate, "check_in");
                        System.out.println();
                    } catch (InvalidInputException e) {
                        System.err.println("Internal Error occurred");
                        done = true;
                    }
            		break;
                case "5":
                    try {
                        Date checkoutDate = promptDate("checkout");
                        ReservationPrinter.printReservations(checkoutDate, "check_out");
                        System.out.println();
                    } catch (InvalidInputException e) {
                        System.err.println("Internal Error occurred");
                        done = true;
                    }
                    break;
                case "6":
                    ReservationPrinter.printAvailabilities();
                    System.out.println();
                    break;
                case "7":
                    ReservationPrinter.printUsedRooms();
                    System.out.println();
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
