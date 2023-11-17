package cse214.hw3;

public class Shelf {
    SortCriteria shelfSortCriteria;
    private Book headBook;
    private Book tailBook;
    private int length;

    public Shelf() {
        this.headBook = null;
        this.tailBook = null;
        this.length = 0;
        this.shelfSortCriteria = SortCriteria.NAME;
    }

    public Shelf(SortCriteria shelfSortCriteria) {
        this.headBook = null;
        this.tailBook = null;
        this.length = 0;
        this.shelfSortCriteria = shelfSortCriteria;
    }

    public Book getTailBook() {
        return tailBook;
    }

    public Book getHeadBook() {
        return headBook;
    }

    /**
     * Adds a book to the shelf.
     *
     * @param book The book to be added.
     * @throws BookAlreadyExistsException If a book with the same ISBN already exists in the shelf.
     */
    public void addBook(Book book) throws BookAlreadyExistsException {
        // empty shelf
        if (this.headBook == null) {
            this.headBook = book;
            this.tailBook = book;
            this.length++;
            return;
        }

        // traverse for isbn
        Book cursorBook = this.headBook;
        while (cursorBook != null) {
            if (cursorBook.getISBN() == book.getISBN()) {
                throw new BookAlreadyExistsException("isbn %d already exists".formatted(book.getISBN()));
            }
            cursorBook = cursorBook.getNextBook();
        }

        // add book
        addBookUnchecked(book);
    }

    /**
     * Adds a book to the shelf without checking for duplicates.
     * <p>
     * Useful when duplicates are not possible, such as when sorting.
     *
     * @param book The book to be added.
     */
    // all this generic means is that the sortCriteria is Comparable and that all the books in the shelf have the same sortCriteria
    private <C extends Comparable<C>> void addBookUnchecked(Book book) {
        // empty shelf
        if (this.headBook == null) {
            this.headBook = book;
            this.tailBook = book;
            book.setNextBook(null);
            this.length++;
            return;
        }

        // insert according to sort criteria
        Book cursorBook = this.headBook;
        Book previousBook;
        C bookSortCriteria = book.getSortCriteria(this.shelfSortCriteria);

        // if new book is "less than" head book, insert at head
        if (bookSortCriteria.compareTo(cursorBook.getSortCriteria(this.shelfSortCriteria)) < 0) {
            book.setNextBook(cursorBook);
            this.headBook = book;
            this.length++;
            return;
        }

        // at this point, we know that the new book is "greater than" the head book, so we can advance the cursor
        previousBook = cursorBook;
        cursorBook = cursorBook.getNextBook();

        while (cursorBook != null) {
            C cursorBookSortCriteria = cursorBook.getSortCriteria(this.shelfSortCriteria);

            // if new book is "less than" cursor book, insert between previous and cursor
            if (bookSortCriteria.compareTo(cursorBookSortCriteria) < 0) {
                previousBook.setNextBook(book);
                book.setNextBook(cursorBook);
                this.length++;
                return;
            }

            // advance cursor
            previousBook = cursorBook;
            cursorBook = cursorBook.getNextBook();
        }

        // if we get here, we know that the new book is "greater than" the tail book, so we can insert at tail
        previousBook.setNextBook(book);
        book.setNextBook(null);
        this.tailBook = book;
        this.length++;
    }

    public void removeBook(long ISBN) throws InvalidISBNException, BookDoesNotExistException {
        // isbn must be 13 digits or less
        if (ISBN > 9999999999999L) {
            throw new InvalidISBNException("ISBN must be 13 digits or less");
        }

        // empty shelf
        if (this.headBook == null) {
            throw new BookDoesNotExistException("isbn %d does not exist".formatted(ISBN));
        }

        // if head book is the book to be removed
        if (this.headBook.getISBN() == ISBN) {
            this.headBook = this.headBook.getNextBook();
            this.length--;
            return;
        }

        // traverse for isbn
        Book cursorBook = this.headBook.getNextBook();
        Book previousBook = this.headBook;

        while (cursorBook != null) {
            if (cursorBook.getISBN() == ISBN) {
                // if tail book is the book to be removed, update tail book
                if (cursorBook == this.tailBook) {
                    this.tailBook = previousBook;
                }
                // remove book
                previousBook.setNextBook(cursorBook.getNextBook());
                this.length--;
                return;
            }
            previousBook = cursorBook;
            cursorBook = cursorBook.getNextBook();
        }
        throw new BookDoesNotExistException("isbn %d does not exist".formatted(ISBN));
    }

    public void sort(SortCriteria sortCriteria) {
        this.shelfSortCriteria = sortCriteria;

        // if shelf is empty or has one book, it is already sorted
        if (this.headBook == null || this.headBook.getNextBook() == null) {
            return;
        }

        // insertion sort by way of addBookUnchecked
        Shelf tempShelf = new Shelf(sortCriteria);
        Book cursorBook = this.headBook;
        while (cursorBook != null) {
            tempShelf.addBookUnchecked(cursorBook);
            cursorBook = cursorBook.getNextBook();
        }
        this.headBook = tempShelf.getHeadBook();
        this.tailBook = tempShelf.getTailBook();
        // length is unchanged
    }
}
