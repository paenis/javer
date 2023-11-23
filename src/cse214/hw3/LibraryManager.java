package cse214.hw3;

import java.util.Scanner;

public class LibraryManager {

    public static String center(String string, int length) {
        if (string.length() > length) return "%s...".formatted(string.substring(0, length - 3)); // truncate
        return "%s%s%s".formatted(" ".repeat((length - string.length()) / 2), string, " ".repeat((length - string.length() + 1) / 2));
    }


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
                case "r" -> doManageReturnStack(scanner, returnStack, bookRepository);
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
            case "p" -> doPrintRepository(scanner, bookRepository);
            case "s" -> doSortShelf(scanner, bookRepository);
            default -> System.out.println("Invalid option. (C, N, R, P, S)");
        }
    }

    private static void doCheckoutBook(Scanner scanner, BookRepository bookRepository) {
        System.out.print("Please enter your user ID: ");
        long userID = scanner.nextLong();
        System.out.print("Please enter the book's ISBN: ");
        long ISBN = scanner.nextLong();
        System.out.print("Please enter the book's check out date (MM/DD/YYYY): ");
        Date checkOutDate;
        try {
            checkOutDate = new Date(scanner.next());
        } catch (InvalidDateException e) {
            System.out.println("Invalid date format. (MM/DD/YYYY)");
            return;
        }
        System.out.print("Please enter the book's due date (MM/DD/YYYY): ");
        Date dueDate;
        try {
            dueDate = new Date(scanner.next());
        } catch (InvalidDateException e) {
            System.out.println("Invalid date format. (MM/DD/YYYY)");
            return;
        }

        // not specified in instructions, but i think this is a reasonable check
        if (checkOutDate.compareTo(dueDate) > 0) {
            System.out.println("Due date must be after check out date.");
            return;
        }

        System.out.println("Loading...");
        try {
            bookRepository.checkOutBook(ISBN, userID, checkOutDate, dueDate);
            System.out.printf("%s has been checked out by %d and must be returned by %s!%n", bookRepository.findBook(ISBN).getName(), userID, dueDate);
        } catch (InvalidISBNException | InvalidUserIDException | BookAlreadyCheckedOutException e) {
            System.out.printf("Error while checking out: %s%n", e.getMessage());
        }
    }

    private static void doAddNewBook(Scanner scanner, BookRepository bookRepository) {
        System.out.print("Please enter an ISBN: ");
        long ISBN = scanner.nextLong();
        System.out.print("Please enter a name: ");
        String name = scanner.next();
        System.out.print("Please enter an author: ");
        String author = scanner.next();
        System.out.print("Please enter a genre: ");
        String genre = scanner.next();
        // System.out.print("Please enter a year: ");
        // int year = scanner.nextInt()
        System.out.print("Please enter a condition: ");
        Condition condition;
        try {
            condition = Condition.valueOf(scanner.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid condition. (NEW, GOOD, BAD, REPLACE)");
            return;
        }

        System.out.println("Loading...");
        try {
            bookRepository.addBook(ISBN, name, author, genre,/* year,*/ condition);
            System.out.printf("%s has been successfully added to the book repository!%n", name);
        } catch (InvalidISBNException | BookAlreadyExistsException e) {
            System.out.printf("Error while adding: %s%n", e.getMessage());
        }
    }

    private static void doRemoveBook(Scanner scanner, BookRepository bookRepository) {
        System.out.print("Please enter an ISBN: ");
        long ISBN = scanner.nextLong();

        System.out.println("Loading...");

        try {
            String bookName = bookRepository.findBook(ISBN).getName();
            bookRepository.removeBook(ISBN);
            System.out.printf("%s has been successfully removed from the book repository!%n", bookName);
        } catch (InvalidISBNException e) {
            System.out.printf("Error while removing: %s%n", e.getMessage());
        }
    }

    private static void doPrintRepository(Scanner scanner, BookRepository bookRepository) {
        StringBuilder sb = new StringBuilder();

        System.out.print("Please enter a shelf number: ");
        int shelfIndex = scanner.nextInt();
        Shelf shelf = bookRepository.getShelf(shelfIndex);
        SortCriteria sortCriteria = shelf.getShelfSortCriteria();
        System.out.println("Loading...");

        // print shelf header
        sb.append("|%s| Checked Out | Check Out Date | Check Out User ID |".formatted(center(switch (shelf.getShelfSortCriteria()) {
            case ISBN -> "ISBN";
            case NAME -> "Name";
            case AUTHOR -> "Author";
            case GENRE -> "Genre";
            case YEAR -> "Year";
            case CONDITION -> "Condition";
        }, 30)));

        sb.append("\n+------------------------------+-------------+----------------+-------------------+\n");

        Book cursorBook = shelf.getHeadBook();
        while (cursorBook != null) {
            String sortCriteriaString = cursorBook.getSortCriteria(sortCriteria).toString();
            // pad ISBN with leading zeroes
            if (sortCriteria == SortCriteria.ISBN) {
                sortCriteriaString = "%013d".formatted(Long.parseLong(sortCriteriaString));
            }
            String checkedOutString = cursorBook.getCheckedOut() ? "Yes" : "No";
            String checkOutDateString = cursorBook.getCheckedOut() ? cursorBook.getCheckOutDate().toString() : "N/A";
            String checkOutUserIDString = cursorBook.getCheckOutUserID() == 0 ? "N/A" : "%010d".formatted(cursorBook.getCheckOutUserID());

            // print book
            sb.append("|%s|%s|%s|%s|".formatted(
                    center(sortCriteriaString, 30),
                    center(checkedOutString, 13),
                    center(checkOutDateString, 16),
                    center(checkOutUserIDString, 19)
            ));
            sb.append("\n");
            cursorBook = cursorBook.getNextBook();
        }

        System.out.println(sb);
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

    private static void doManageReturnStack(Scanner scanner, ReturnStack returnStack, BookRepository bookRepository) {
        System.out.print("Please select an option: ");
        switch (scanner.next().toLowerCase()) {
            case "r" -> doReturnBook(scanner, returnStack, bookRepository);
            case "l" -> doSeeLastReturn(returnStack, bookRepository);
            case "c" -> doCheckInLastReturn(returnStack, bookRepository);
            case "p" -> doPrintReturnStack(returnStack);
            default -> System.out.println("Invalid option. (R, L, C, P)");
        }
    }

    private static void doReturnBook(Scanner scanner, ReturnStack returnStack, BookRepository bookRepository) {
        System.out.print("Please enter an ISBN: ");
        long ISBN = scanner.nextLong();
        System.out.print("Please enter your user ID: ");
        long userID = scanner.nextLong();
        System.out.print("Please enter the current date (MM/DD/YYYY): ");
        Date returnDate;
        try {
            returnDate = new Date(scanner.next());
        } catch (InvalidDateException e) {
            System.out.println("Invalid date format. (MM/DD/YYYY)");
            return;
        }

        System.out.println("Loading...");

        try {
            if (returnStack.pushLog(ISBN, userID, returnDate, bookRepository)) {
                System.out.printf("%s has been returned on time!%n", bookRepository.findBook(ISBN).getName());
            } else {
                System.out.printf("%s has been returned LATE! Checking everything in...%n", bookRepository.findBook(ISBN).getName());
                // check in everything
                while (returnStack.peekLog() != null) {
                    ReturnLog returnLog = returnStack.popLog();
                    bookRepository.checkInBook(returnLog.getISBN(), returnLog.getUserID());
                }
            }
        } catch (InvalidISBNException | InvalidReturnDateException | BookNotCheckedOutException |
                 BookCheckedOutBySomeoneElseException | InvalidUserIDException | EmptyStackException |
                 BookDoesNotExistException e) {
            System.out.printf("Error while returning: %s%n", e.getMessage());
        }
    }

    private static void doSeeLastReturn(ReturnStack returnStack, BookRepository bookRepository) {
        System.out.println("Loading...");

        ReturnLog lastLog = returnStack.peekLog();
        if (lastLog == null) {
            System.out.println("There are no books in the return stack.");
        } else {
            System.out.printf("%s is the next book to be checked in.%n", bookRepository.findBook(lastLog.getISBN()));
        }
    }

    private static void doCheckInLastReturn(ReturnStack returnStack, BookRepository bookRepository) {
        System.out.println("Loading...");

        try {
            ReturnLog returnLog = returnStack.popLog();
            bookRepository.checkInBook(returnLog.getISBN(), returnLog.getUserID());
            System.out.printf("%s has been checked in!%n", bookRepository.findBook(returnLog.getISBN()).getName());
        } catch (EmptyStackException e) {
            System.out.println("There are no books in the return stack.");
        } catch (InvalidISBNException | BookNotCheckedOutException | BookCheckedOutBySomeoneElseException |
                 InvalidUserIDException | BookDoesNotExistException e) {
            System.out.printf("Error while checking in: %s%n", e.getMessage());
        }
    }

    private static void doPrintReturnStack(ReturnStack returnStack) {
        System.out.println("Loading...");

        System.out.println(returnStack);
    }
}
