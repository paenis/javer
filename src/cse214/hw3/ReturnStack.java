package cse214.hw3;

public class ReturnStack {
    ReturnLog topLog;

    public ReturnStack() {
        this.topLog = null;
    }

    public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, InvalidReturnDateException, BookNotCheckedOutException, BookCheckedOutBySomeoneElseException, InvalidUserIDException {
        if (returnISBN > 9999999999999L) throw new InvalidISBNException("ISBN must be 13 digits or less");
        if (returnUserID > 9999999999L) throw new InvalidUserIDException("User ID must be 10 digits or less");

        Book checkedOutBook = bookRepoRef.findBook(returnISBN);
        if (checkedOutBook == null) throw new InvalidISBNException("Book not found");
        if (!checkedOutBook.getCheckedOut()) throw new BookNotCheckedOutException("Book not checked out");
        if (checkedOutBook.getCheckOutUserID() != returnUserID) throw new BookCheckedOutBySomeoneElseException("Book checked out by someone else");
        if (returnDate.compareTo(checkedOutBook.getCheckOutDate()) < 0) throw new InvalidReturnDateException("Return date is before checkout date");
        // book is NOT checked in at this point, only returned

        ReturnLog newLog = new ReturnLog(returnISBN, returnUserID, returnDate);
        newLog.setNextLog(topLog);
        topLog = newLog;

        return true;
    }

    /**
     * Removes and returns the top log from the stack.
     *
     * @return the top log
     * @throws EmptyStackException if the stack is empty
     */
    public ReturnLog popLog() throws EmptyStackException {
        if (topLog == null) throw new EmptyStackException("ReturnStack is empty");

        ReturnLog poppedLog = topLog;
        topLog = topLog.getNextLog();
        return poppedLog;
    }
}
