package cse214.hw3;

public class ReturnLog {
    long ISBN;
    long userID;

    Date returnDate;

    ReturnLog nextLog;

    public ReturnLog() {
        this.ISBN = 0;
        this.userID = 0;
        this.returnDate = new Date();
        this.nextLog = null;
    }

    public ReturnLog(long ISBN, long userID, Date returnDate) {
        this.ISBN = ISBN;
        this.userID = userID;
        this.returnDate = returnDate;
        this.nextLog = null;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public ReturnLog getNextLog() {
        return nextLog;
    }

    public void setNextLog(ReturnLog nextLog) {
        this.nextLog = nextLog;
    }

    @Override
    public String toString() {
        return "| %013d | %010d | %s |".formatted(ISBN, userID, returnDate);
    }
}
