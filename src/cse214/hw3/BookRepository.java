package cse214.hw3;

public class BookRepository {
    private Shelf[] shelves;

    public BookRepository() {
        shelves = new Shelf[10];
        for (int i = 0; i < shelves.length; i++) {
            shelves[i] = new Shelf();
        }
    }

    public int ISBNFirstDigit(long ISBN) {
        while (ISBN > 10) {
            ISBN /= 10;
        }
        return (int) ISBN;
    }

    private Shelf findShelf(long ISBN) {
        return shelves[ISBNFirstDigit(ISBN)];
    }

    private Book findBook(long ISBN) {
        Book cursorBook = findShelf(ISBN).getHeadBook();
        while (cursorBook != null) {
            if (cursorBook.getISBN() == ISBN) {
                return cursorBook;
            }
            cursorBook = cursorBook.getNextBook();
        }
        return null;
    }

    public void checkInBook(/*int*/long checkedInISBN, /*int*/long checkInUserID) /*throws???*/ {
        // find book
        Book book = findBook(checkedInISBN);

        // check in book
        if (book == null) {
            System.out.println("Book not found"); // TODO throw
            return;
        }
        if (!book.getCheckedOut()) {
            System.out.println("Book not checked out"); // TODO throw
            return;
        }
        if (book.getCheckOutUserID() != checkInUserID) { // i guess???
            System.out.println("Book not checked out by user"); // TODO throw
            return;
        }
        book.setCheckedOut(false);
        book.setCheckOutUserID(0);
    }

    public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date dueDate/*checkedOutDate?*/) throws InvalidISBNException, InvalidUserIDException, BookAlreadyCheckedOutException {
        // isbn must be 13 digits or less
        if (checkedOutISBN > 9999999999999L) {
            throw new InvalidISBNException("ISBN must be 13 digits or less");
        }

        //find book
        Book book = findBook(checkedOutISBN);

        // check out book
        if (book == null) {
            throw new InvalidISBNException("ISBN not found");
        }
        if (book.getCheckedOut()) {
            throw new BookAlreadyCheckedOutException("Book already checked out");
        }
        book.setCheckedOut(true);
        book.setCheckOutUserID(checkOutUserID);
        book.setCheckOutDate(dueDate);
    }

    public void addBook(/*int*/long addISBN, String addName, String addAuthor, String addGenre, Condition addCondition /*no year!*/) throws InvalidISBNException, BookAlreadyExistsException {
        // isbn must be 13 digits or less
        if (addISBN > 9999999999999L) {
            throw new InvalidISBNException("ISBN must be 13 digits or less");
        }

        // add book
        findShelf(addISBN).addBook(new Book(addName, addAuthor, addGenre, addCondition, addISBN, 0, 0, new Date(), null, false));
    }

    public void removeBook(long removeISBN) throws InvalidISBNException {
        // isbn must be 13 digits or less
        if (removeISBN > 9999999999999L) {
            throw new InvalidISBNException("ISBN must be 13 digits or less");
        }

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
}
