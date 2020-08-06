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

        System.out.println("Enter the number of person to stay[1-2]");
        int person = scanner.nextInt();

        if (person > 2 || person < 1) {
            throw new InvalidInputException("Person should be 1 or 2");
        }

        return new Reservation(checkinDate, checkoutDate, person);
    }

    public static boolean promptAccept() {
        System.out.println("Will you book this reservation? [Y/N]:");
        String answer = scanner.next();

        return answer.equals("Y");
    }

    public static Customer promptCustomerDetail(String email) {
        System.out.println("Enter the first name: ");
        String fname = scanner.next();

        System.out.println("Enter the last name: ");
        String lname = scanner.next();

        System.out.println("Enter the phone number: ");
        String pnum = scanner.next();

        return new Customer(email, fname + " " + lname, pnum);
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
            assert plan != null;
            availability.remove(plan.getKey());

            plan = Utils.findCheapestPlanAndRoom(availability);

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
