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

    public long getISBN() {
        return ISBN;
    }

    public Book getNextBook() {
        return nextBook;
    }
}
