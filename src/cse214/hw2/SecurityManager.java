package cse214.hw2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class SecurityManager {
    static SecurityCheck securityCheck;

    public static void main(String[] args) {
        securityCheck = new SecurityCheck();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println();
            // print line status
            System.out.println(securityCheck);
            // print menu
            System.out.println("Menu:\n" + "\t(A) - Add Person\n" + "\t(N) - Next Person\n" + "\t(R) - Remove Lines\n" + "\t(L) - Add Lines\n" + "\t(P) - Print All Lines\n" + "\t(Q) - Quit\n");
            // get user input
            System.out.print("Please select an option: ");
            switch (scanner.next().toLowerCase()) {
                case "a" -> doAdd(scanner);
                case "n" -> doNext();
                case "r" -> doRemoveLines(scanner);
                case "l" -> doAddLines(scanner);
                case "p" -> doPrint();
                case "q" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. (A, N, R, L, P, Q)");
            }
        } while (true);
    }

    /**
     * Adds a person to a line, maintaining constraints.
     *
     * @param scanner the Scanner object used for user input
     */
    public static void doAdd(Scanner scanner) {
        System.out.print("Please enter a name: ");
        String name = scanner.next();
        System.out.print("Please enter a seat number: ");
        int seatNumber = scanner.nextInt();

        System.out.println("Loading...");
        try {
            securityCheck.addPerson(name, seatNumber);
        } catch (RuntimeException | TakenSeatException e) {
            System.out.printf("Error while adding person: %s. Please try again!%n", e.getMessage());
            return;
        }

        System.out.printf("%s successfully added to line %d!%n", name, securityCheck.getCursorLineNumber());

    }


    /**
     * Removes the next person from the line, maintaining constraints.
     */
    private static void doNext() {
        System.out.println("Loading...");
        try {
            Person next = securityCheck.removeNextAttendee();
            System.out.printf("%s from seat %d removed from line %d!", next.getName(), next.getSeatNumber(), securityCheck.getCursorLineNumber());
        } catch (AllLinesEmptyException e) {
            System.out.printf("Error while removing next person: %s%n", e.getMessage());
        }
    }

    /**
     * Removes the specified lines, maintaining constraints.
     *
     * @param scanner the Scanner object used for user input
     */
    private static void doRemoveLines(Scanner scanner) {
        System.out.print("Lines to remove: "); // comma separated
        String lineNumbers = scanner.next();

        // split and parse
        int[] lines = Arrays.stream(lineNumbers.split(",")).map(String::strip).mapToInt(Integer::parseInt).toArray();

        System.out.println("Loading...");
        try {
            securityCheck.removeLines(lines);
        } catch (LineDoesNotExistException | SingleLineRemovalException e) {
            System.out.printf("Error while removing lines: %s. Please try again!%n", e.getMessage());
            return;
        }

        // print removed lines
        switch (lines.length) {
            case 1 -> System.out.printf("Line %d has been decommissioned!%n", lines[0]);
            case 2 -> System.out.printf("Lines %d and %d have been decommissioned!%n", lines[0], lines[1]);
            // more than 2: join with commas
            default ->
                    System.out.printf("Lines %s, and %d have been decommissioned!%n", Arrays.stream(lines, 0, lines.length - 1).mapToObj(String::valueOf).reduce((a, b) -> a + ", " + b).orElseThrow(), lines[lines.length - 1]);
        }
    }

    /**
     * Adds the specified number of lines to the security check.
     *
     * @param scanner the Scanner object used for user input
     */
    private static void doAddLines(Scanner scanner) {
        System.out.print("Add how many more lines?: ");
        int lineCount = scanner.nextInt();

        System.out.println("Loading...");
        try {
            securityCheck.addNewLines(lineCount);
        } catch (InvalidLineCountException e) {
            System.out.printf("Error while adding lines: %s. Please try again!%n", e.getMessage());
        }

        // print added lines
        int lines = securityCheck.getLineCount();
        switch (lineCount) {
            case 1 -> System.out.printf("Line %d introduced!%n", lines);
            case 2 -> System.out.printf("Lines %d and %d introduced!%n", lines - 1, lines);
            // more than 2: join with commas
            default ->
                    System.out.printf("Lines %s, and %d introduced!%n", IntStream.range((lines - lineCount) + 1, lines).mapToObj(String::valueOf).reduce((a, b) -> a + ", " + b).orElseThrow(), lines);
        }
    }

    /**
     * Prints all the lines in the security check in tabular format.
     */
    public static void doPrint() {
        securityCheck.printAllLines();
    }
}
