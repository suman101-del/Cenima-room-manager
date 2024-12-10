package cinema;

import java.util.Scanner;

/**
 * The Cinema class serves as an entry point for the cinema room manager.
 *
 * It initializes the cinema room, processes user inputs, and controls the booking system loop.
 *
 * @version 1.10 31 Aug 2024
 * @author suman dolai
 */
public class Cinema {

    /**
     * The main method that starts the booking system.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {

        // Create a Scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numberOfSeatsPerRow = scanner.nextInt();

        // Creates seating arrangement of the cinema room according to user inputs
        CinemaRoom EmperorCinemas = new CinemaRoom(numberOfRows, numberOfSeatsPerRow);

        // Process user input until the booking system is exited
        while (EmperorCinemas.isBooking()) {

            // Read and process user input according to state of system
            EmperorCinemas.executeMenuCommand(scanner.nextInt());
        }
        scanner.close();
    }

}
