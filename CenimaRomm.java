package cinema;

/**
 * The MovieTheatre class represents a booking system for a movie theatre with various functionalities.
 *
 * @version 1.10 31 Aug 2024
 * @author suman dolai
 */
public class CinemaRoom {

    // Current state of the machine
    private BookingSystemState state;

    // Size of the cinema room
    private final int numberOfRows;
    private final int numberOfSeatsPerRow;
    private final int totalSeats;

    // Info for users of the booking system
    private int rowNumber;
    private  int seatNumber;
    private char[][] seatBooked;
    private int ticketsPurchased;

    // Administrative info
    private int currentIncome;
    private final int totalIncome;

    /**
     * Constructor to initialize cinema room of size specified by user.
     *
     * @param numberOfRows Number of rows in the cinema room
     * @param numberOfSeatsPerRow Number of seats per row in the cinema room
     */
    public CinemaRoom(int numberOfRows, int numberOfSeatsPerRow) {
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;
        this.totalSeats = numberOfRows * numberOfSeatsPerRow;
        this.totalIncome = (totalSeats < 61) ? (10 * totalSeats) : (numberOfRows / 2 * numberOfSeatsPerRow * 10) + ((numberOfRows - numberOfRows / 2) * 8 * numberOfSeatsPerRow);

        // Constructs cinema room according to number of rows and seats per row
        arrangeSeats();

        // Display main menu to start interaction
        showMenu();
    }

    /**
     * Displays the main menu to the user.
     */
    private void showMenu() {
        state = BookingSystemState.MENU;
        System.out.println("""
                
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");
    }

    /**
     * Checks if the booking system is operational.
     *
     * @return True if the booking system is working, otherwise False
     */
    public boolean isBooking() {
        return state != BookingSystemState.OFF;
    }

    /**
     * Arranges seats in the cinema room according to user input.
     */
    private void arrangeSeats() {

        // Row and column size are increased by 1
        // Accounts for labels when displaying seating arrangement
        char[][] seatArrangement = new char[numberOfRows + 1][numberOfSeatsPerRow + 1];
        for (int i = 0; i < seatArrangement.length; i++) {
            for (int j = 0; j < seatArrangement[i].length; j++) {
                if (i == 0 & j == 0) {
                    seatArrangement[i][j] = ' ';
                } else if (i == 0 || j == 0) {
                    seatArrangement[i][j] = i == 0 ? (char) (48 + j) : (char) (48 + i);
                } else {
                    seatArrangement[i][j] = 'S';
                }
            }
        }
        seatBooked = seatArrangement;
    }

    /**
     * Outputs arrangement of seats to the console.
     *
     * 3x3 cinema room output would be:
     *   1 2 3
     * 1 S S S
     * 2 S S S
     * 3 S S S
     *
     * @param seatArrangement Seats in cinema room as 2D array
     */
    private void showSeats(char[][] seatArrangement) {
        System.out.println("\nCinema:");
        for (int i = 0; i < seatArrangement.length; i++) {
            for (int j = 0; j < seatArrangement[i].length; j++) {
                System.out.print(seatArrangement[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Processes user input in the main menu.
     *
     * @param input User input
     */
    private void handleSelection(int input) {
        switch (input) {
            case 1 -> {
                showSeats(seatBooked);
                showMenu();
                break;
            }
            case 2 -> {
                state = BookingSystemState.BUYING_INPUT_ROW;
                System.out.println("\nEnter a row number:");
                break;
            }
            case 3 -> {
                showStatistics();
                showMenu();
            }
            case 0 -> {
                state = BookingSystemState.OFF;
                break;
            }
            default -> {
                return;
            }
        }
    }

    /**
     * Handles the purchasing of tickets by the user.
     *
     * @param rowNumber Row selected by the user
     * @param seatNumber Seat selected by the user in the row
     */
    private void handlePurchase(int rowNumber, int seatNumber) {
        int ticketPrice = 0;
        int totalSeats = numberOfSeatsPerRow * numberOfRows;
        if (rowNumber > numberOfRows || rowNumber < 1 || seatNumber > numberOfSeatsPerRow || seatNumber < 1) {
            System.out.println("\nWrong input!");
            handleSelection(2);
            return;
        }

        if (seatBooked[rowNumber][seatNumber] == 'B') {
            System.out.println("\nThat ticket has already been purchased!");
            handleSelection(2);
            return;
        }

        ++ticketsPurchased;
        seatBooked[rowNumber][seatNumber] = 'B';

        if (totalSeats < 61 || rowNumber <= numberOfRows / 2) {
            ticketPrice = 10;
        } else {
            ticketPrice = 8;
        }

        System.out.printf("Ticket price: $%d\n", ticketPrice);
        currentIncome += ticketPrice;
        showMenu();
    }

    /**
     * Displays statistics related to income about cinema room.
     */
    private void showStatistics() {
        double percentage = (double) ticketsPurchased / totalSeats * 100;
        System.out.printf("""
                \nNumber of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """, ticketsPurchased, percentage, currentIncome, totalIncome);
    }

    /**
     * Processes user input according to state of the system.
     *
     * @param input User input
     */
    public void executeMenuCommand(int input) {
        switch (state) {
            case MENU -> {
                handleSelection(input);
                break;
            }
            case BUYING_INPUT_ROW -> {
                rowNumber = input;
                state = BookingSystemState.BUYING_INPUT_SEAT;
                System.out.println("Enter a seat number in that row:");
                break;
            }
            case BUYING_INPUT_SEAT -> {
                seatNumber = input;
                handlePurchase(rowNumber, seatNumber);
                break;
            }
            default -> {
                return;
            }
        }
    }

}
