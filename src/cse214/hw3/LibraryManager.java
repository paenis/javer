package cse214.hw3;

import java.util.Scanner;

public class LibraryManager {


    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        ReturnStack returnStack = new ReturnStack();
        Scanner scanner = new Scanner(System.in);

        do {
            // print menu
            System.out.println("\n" + "Menu:\n" + "\t(B) - Manage Book Repository\n" + "\t\t(C) - Checkout Book\n" + "\t\t(N) - Add New Book\n" + "\t\t(R) - Remove Book\n" + "\t\t(P) - Print Repository\n" + "\t\t(S) - Sort Shelf\n" + "\t\t\t(I) - ISBN Number\n" + "\t\t\t(N) - Name\n" + "\t\t\t(A) - Author\n" + "\t\t\t(G) - Genre\n" + "\t\t\t(Y) - Year\n" + "\t\t\t(C) - Condition\n" + "\t(R) - Manage Return Stack\n" + "\t\t(R) - Return Book\n" + "\t\t(L) - See Last Return\n" + "\t\t(C) - Check In Last Return\n" + "\t\t(P) - Print Return Stack\n" + "\t(Q) - Quit\n");

            // get option
            System.out.print("Please select what to manage: ");
            switch (scanner.next().toLowerCase()) {
                case "b" -> doManageBookRepository(scanner, bookRepository);
                case "r" -> doManageReturnStack(scanner, returnStack);
                case "q" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. (B, R, Q)");
            }
        } while (true);
    }

    private static void doManageBookRepository(Scanner scanner, BookRepository bookRepository) {
        System.out.print("Please select an option: ");
        switch (scanner.next().toLowerCase()) {
            case "c" -> doCheckoutBook(scanner, bookRepository);
            case "n" -> doAddNewBook(scanner, bookRepository);
            case "r" -> doRemoveBook(scanner, bookRepository);
            case "p" -> doPrintRepository(bookRepository);
            case "s" -> doSortShelf(scanner, bookRepository);
            default -> System.out.println("Invalid option. (C, N, R, P, S)");
        }
    }

    private static void doCheckoutBook(Scanner scanner, BookRepository bookRepository) {
        System.out.print("Please enter your user ID: ");
        long userID = scanner.nextLong();
        System.out.print("Please enter the book's ISBN: ");
        long ISBN = scanner.nextLong();
        System.out.print("Please enter the book's due date (MM/DD/YYYY): ");
        Date dueDate;
        try {
            dueDate = new Date(scanner.next());
        } catch (InvalidDateException e) {
            System.out.println("Invalid date format. (MM/DD/YYYY)");
            return;
        }

        System.out.println("Loading...");
        try {
            bookRepository.checkOutBook(ISBN, userID, dueDate);
            System.out.printf("%s has been checked out by %d and must be returned by %s!%n", bookRepository.findBook(ISBN).getName(), userID, dueDate);
        } catch (InvalidISBNException | InvalidUserIDException | BookAlreadyCheckedOutException e) {
            System.out.printf("Error while checking out: %s%n", e.getMessage());
        }
    }

    private static void doAddNewBook(Scanner scanner, BookRepository bookRepository) {
        // TODO
    }

    private static void doRemoveBook(Scanner scanner, BookRepository bookRepository) {
        // TODO
    }

    private static void doPrintRepository(BookRepository bookRepository) {
        // TODO
    }

    private static void doSortShelf(Scanner scanner, BookRepository bookRepository) {
        System.out.print("Please select a shelf: ");
        int shelfIndex = scanner.nextInt();
        System.out.print("Please select an option: ");
        String sortCriteriaString = switch (scanner.next().toLowerCase()) {
            case "i" -> "ISBN";
            case "n" -> "NAME";
            case "a" -> "AUTHOR";
            case "g" -> "GENRE";
            case "y" -> "YEAR";
            case "c" -> "CONDITION";
            default -> {
                System.out.println("Invalid option. (I, N, A, G, Y, C)");
                yield null;
            }
        };
        if (sortCriteriaString == null) return;

        System.out.println("Loading...");
        try {
            bookRepository.sortShelf(shelfIndex, sortCriteriaString);
            System.out.printf("Shelf %d has been sorted by %s!%n", shelfIndex, (sortCriteriaString.equals("ISBN") ? sortCriteriaString : sortCriteriaString.toLowerCase()));
        } catch (InvalidSortCriteriaException e) {
            System.out.printf("Error while sorting: %s%n", e.getMessage());
        }
    }

    private static void doManageReturnStack(Scanner scanner, ReturnStack returnStack) {
        System.out.print("Please select an option: ");
        switch (scanner.next().toLowerCase()) {
            case "r" -> doReturnBook(scanner, returnStack);
            case "l" -> doSeeLastReturn(returnStack);
            case "c" -> doCheckInLastReturn(returnStack);
            case "p" -> doPrintReturnStack(returnStack);
            default -> System.out.println("Invalid option. (R, L, C, P)");
        }
    }

    private static void doReturnBook(Scanner scanner, ReturnStack returnStack) {
        // TODO
    }

    private static void doSeeLastReturn(ReturnStack returnStack) {
        // TODO
    }

    private static void doCheckInLastReturn(ReturnStack returnStack) {
        // TODO
    }

    private static void doPrintReturnStack(ReturnStack returnStack) {
        System.out.println("Loading...");
        // TODO
    }
}
