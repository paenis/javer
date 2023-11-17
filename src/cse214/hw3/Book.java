package cse214.hw3;

public class Book {
    private String name;
    private String author;
    private String genre;

    private Condition bookCondition;

    private long ISBN;
    private long checkOutUserID;

    private int yearPublished;

    private Date checkOutDate;

    private Book nextBook;

    private boolean checkedOut;

    public Book() {
        this.name = "";
        this.author = "";
        this.genre = "";
        this.bookCondition = Condition.NEW;
        this.ISBN = 0;
        this.checkOutUserID = 0;
        this.yearPublished = 0;
        this.checkOutDate = new Date();
        this.nextBook = null;
        this.checkedOut = false;
    }

    public Book(String name, String author, String genre, long ISBN, int yearPublished) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.bookCondition = Condition.NEW;
        this.ISBN = ISBN;
        this.checkOutUserID = 0;
        this.yearPublished = yearPublished;
        this.checkOutDate = new Date();
        this.nextBook = null;
        this.checkedOut = false;
    }

    public Book(String name, String author, String genre, Condition bookCondition, long ISBN, long checkOutUserID, int yearPublished, Date checkOutDate, Book nextBook, boolean checkedOut) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.bookCondition = bookCondition;
        this.ISBN = ISBN;
        this.checkOutUserID = checkOutUserID;
        this.yearPublished = yearPublished;
        this.checkOutDate = checkOutDate;
        this.nextBook = nextBook;
        this.checkedOut = checkedOut;
    }

    public long getISBN() {
        return ISBN;
    }

    public Book getNextBook() {
        return nextBook;
    }

    public void setNextBook(Book nextBook) {
        this.nextBook = nextBook;
    }

    // i don't really like this
    public <C extends Comparable<C>> C getSortCriteria(SortCriteria sortCriteria) {
        return (C) switch (sortCriteria) {
            case ISBN -> this.ISBN;
            case NAME -> this.name;
            case AUTHOR -> this.author;
            case GENRE -> this.genre;
            case YEAR -> this.yearPublished;
            case CONDITION -> this.bookCondition;
        };
    }

    public boolean getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public long getCheckOutUserID() {
        return checkOutUserID;
    }

    public void setCheckOutUserID(long checkOutUserId) {
        this.checkOutUserID = checkOutUserId;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
