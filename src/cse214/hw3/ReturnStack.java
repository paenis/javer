package cse214.hw3;


public class ReturnStack {
    ReturnLog topLog;

    public ReturnStack() {
        this.topLog = null;
    }

    public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, InvalidReturnDateException, BookNotCheckedOutException, BookCheckedOutBySomeoneElseException, InvalidUserIDException {
        // TODO
        return false;
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
