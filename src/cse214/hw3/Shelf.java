package cse214.hw3;

public class Shelf {
    private Book headBook;
    private Book tailBook;

    private int length;

    SortCriteria shelfSortCriteria;

    public Shelf() {
        this.headBook = null;
        this.tailBook = null;
        this.length = 0;
        this.shelfSortCriteria = SortCriteria.TITLE;
    }

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

    }
}
