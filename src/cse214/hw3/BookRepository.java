package cse214.hw3;

public class BookRepository {
    private Shelf[] shelves;

    public BookRepository() {
        shelves = new Shelf[10];
        for (int i = 0; i < shelves.length; i++) {
            shelves[i] = new Shelf();
        }
    }

    private static void checkISBN(long ISBN) throws InvalidISBNException {
        if (ISBN > 9999999999999L) {
            throw new InvalidISBNException("ISBN must be 13 digits or less");
        }
    }

    private static void checkUserID(long userID) throws InvalidUserIDException {
        if (userID > 9999999999L) {
            throw new InvalidUserIDException("User ID must be 10 digits or less");
        }
    }

    private int ISBNFirstDigit(long ISBN) {
        return Integer.parseInt(String.format("%013d", ISBN).substring(0, 1));
    }

    private Shelf findShelf(long ISBN) {
        return shelves[ISBNFirstDigit(ISBN)];
    }

    public Book findBook(long ISBN) {
        Book cursorBook = findShelf(ISBN).getHeadBook();
        while (cursorBook != null) {
            if (cursorBook.getISBN() == ISBN) {
                return cursorBook;
            }
            cursorBook = cursorBook.getNextBook();
        }
        return null;
    }

    public void checkInBook(/*int*/long checkedInISBN, /*int*/long checkInUserID) throws InvalidISBNException, BookNotCheckedOutException, BookCheckedOutBySomeoneElseException, InvalidUserIDException, BookDoesNotExistException {
        // isbn must be 13 digits or less
        checkISBN(checkedInISBN);
        // user id must be 10 digits or less
        checkUserID(checkInUserID);

        Book book = findBook(checkedInISBN);

        // check in book
        if (book == null) throw new BookDoesNotExistException("Book not found: %013d".formatted(checkedInISBN));
        if (!book.getCheckedOut()) throw new BookNotCheckedOutException("Book not checked out");
        if (book.getCheckOutUserID() != checkInUserID)
            throw new BookCheckedOutBySomeoneElseException("Book checked out by someone else");

        book.setCheckedOut(false);
        book.setCheckOutUserID(0);
        book.setCheckOutDate(new Date());
    }

    public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date checkOutDate, Date dueDate) throws InvalidISBNException, InvalidUserIDException, BookAlreadyCheckedOutException {
        // isbn must be 13 digits or less
        checkISBN(checkedOutISBN);
        // user id must be 10 digits or less
        checkUserID(checkOutUserID);

        // find book
        Book book = findBook(checkedOutISBN);

        // check out book
        if (book == null) throw new InvalidISBNException("Book not found: %013d".formatted(checkedOutISBN));
        if (book.getCheckedOut()) throw new BookAlreadyCheckedOutException("Book already checked out");

        book.setCheckedOut(true);
        book.setCheckOutUserID(checkOutUserID);
        book.setCheckOutDate(checkOutDate);
        book.setDueDate(dueDate); // unchecked per instructions (due date can be before checkout date)
    }

    public void addBook(/*int*/long addISBN, String addName, String addAuthor, String addGenre, Condition addCondition /*no year!*/) throws InvalidISBNException, BookAlreadyExistsException {
        // isbn must be 13 digits or less
        checkISBN(addISBN);

        // add book
        findShelf(addISBN).addBook(new Book(addName, addAuthor, addGenre, addCondition, addISBN, 0, 0, new Date(), new Date(), null, false));
    }

    public void removeBook(long removeISBN) throws InvalidISBNException {
        // isbn must be 13 digits or less
        checkISBN(removeISBN);

        // remove book
        try {
            findShelf(removeISBN).removeBook(removeISBN);
        } catch (BookDoesNotExistException e) {
            System.out.println("Book not found"); // TODO throw
        }
    }

    /**
     * Sorts a shelf based on the given sort criteria.
     *
     * @param shelfInd     The index of the shelf to be sorted.
     * @param sortCriteria The sort criteria to be used for sorting. The valid sort criteria are:
     *                     ISBN, NAME, AUTHOR, GENRE, YEAR, CONDITION.
     * @throws InvalidSortCriteriaException If an invalid sort criteria is provided.
     */
    // IMPORTANT: caller must sanitize criteria
    public void sortShelf(int shelfInd, String sortCriteria) throws InvalidSortCriteriaException {
        // sort shelf
        try {
            findShelf(shelfInd).sort(SortCriteria.valueOf(sortCriteria));
        } catch (IllegalArgumentException e) {
            throw new InvalidSortCriteriaException("Invalid sort criteria", e);
        }
    }

    public Shelf getShelf(int shelfInd) {
        return shelves[shelfInd];
    }
}
