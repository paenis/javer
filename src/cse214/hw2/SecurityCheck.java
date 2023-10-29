package cse214.hw2;

import java.util.Arrays;

public class SecurityCheck {
    private Line headLine;
    private Line tailLine;
    private Line cursorLine;
    private int lineCount;
    private int cursorLineNumber;

    public SecurityCheck() {
        Line line = new Line();
        headLine = line;
        tailLine = line;
        cursorLine = line;
        lineCount = 1;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getCursorLineNumber() {
        return cursorLineNumber;
    }

    /**
     * Adds a person to a line, maintaining constraints.
     *
     * @param name       the name of the person
     * @param seatNumber the seat number of the person
     * @throws TakenSeatException if the seat is already taken
     */
    public void addPerson(String name, int seatNumber) throws TakenSeatException {
        // traverse the lines and record their lengths, while also checking if the seat is taken
        // if the seat is taken, throw a TakenSeatException
        cursorLine = headLine;
        int[] lineLengths = new int[lineCount];
        for (int i = 0; cursorLine != null; cursorLine = cursorLine.getLineLink(), i++) {
            lineLengths[i] = cursorLine.getLength();
            if (cursorLine.seatTaken(seatNumber)) {
                throw new TakenSeatException("seat %d is already taken".formatted(seatNumber));
            }
        }

        // find the first line with the least amount of people
        int minLineLength = Arrays.stream(lineLengths).min().orElseThrow(); // safety: there will always be at least one line
        cursorLine = headLine;
        int lineNum = 1;
        while (cursorLine.getLength() != minLineLength) {
            cursorLine = cursorLine.getLineLink();
            lineNum++;
        }

        // add the person to the line
        cursorLine.addPerson(new Person(name, seatNumber));
        cursorLineNumber = lineNum;
    }

    /**
     * Adds a person to a line, maintaining constraints.
     *
     * @param person the person to add
     * @throws TakenSeatException if the seat is already taken
     */ // this is just a convenience overload
    public void addPerson(Person person) throws TakenSeatException {
        addPerson(person.getName(), person.getSeatNumber());
    }

    /**
     * Removes the next attendee, maintaining constraints.
     *
     * @return the person who was removed
     * @throws AllLinesEmptyException if all lines are empty
     */
    public Person removeNextAttendee() throws AllLinesEmptyException {
        // should remove from longest line(s) first

        // record the lengths and lowest seat numbers of each line
        cursorLine = headLine;
        int[] lineLengths = new int[lineCount];
        int[] lineLowestSeats = new int[lineCount];
        for (int i = 0; cursorLine != null; cursorLine = cursorLine.getLineLink(), i++) {
            lineLengths[i] = cursorLine.getLength();
            Person firstPerson = cursorLine.getHeadPerson();
            if (firstPerson == null) {
                lineLowestSeats[i] = Integer.MAX_VALUE;
            } else {
                lineLowestSeats[i] = firstPerson.getSeatNumber();
            }
        }

        int maxLineLength = Arrays.stream(lineLengths).max().orElseThrow(); // safety: there will always be at least one line
        // assert that there is at least one person in the longest line
        if (maxLineLength == 0) {
            throw new AllLinesEmptyException("all lines are empty");
        }

        // find the index of the line with the longest length and the lowest seat number
        int maxLineIndex = 0;
        for (int i = 0; i < lineCount; i++) {
            if (lineLengths[i] == maxLineLength && lineLowestSeats[i] < lineLowestSeats[maxLineIndex]) {
                maxLineIndex = i;
            }
        }

        // remove the person from the line
        cursorLine = headLine;
        for (int i = 0; i < maxLineIndex; i++) {
            cursorLine = cursorLine.getLineLink();
        }
        cursorLineNumber = maxLineIndex + 1;
        return cursorLine.removeFrontPerson();
    }

    /**
     * Adds the specified number of new lines to the security check.
     *
     * @param newLines the number of new lines to add
     * @throws InvalidLineCountException if the number of new lines is negative
     */
    public void addNewLines(int newLines) throws InvalidLineCountException {
        if (newLines < 0) {
            throw new InvalidLineCountException("cannot add negative lines");
        }

        cursorLine = tailLine;
        for (int i = 0; i < newLines; i++) {
            Line line = new Line();
            cursorLine.setLineLink(line);
            cursorLine = line;
            lineCount++;
        }
        tailLine = cursorLine;

        // remove people from the longest line(s) and add them to the new lines

        // total number of people
        int totalPeople = 0;
        cursorLine = headLine;
        while (cursorLine != null) {
            totalPeople += cursorLine.getLength();
            cursorLine = cursorLine.getLineLink();
        }

        // if there are no people, there is no need to move people to the new lines
        if (totalPeople == 0) {
            return;
        }

        // number of people per line
        int peoplePerLine = totalPeople / lineCount;

        // approx. number of people to move to the new lines, rounded up
        int peopleToMove = (peoplePerLine + 1) * newLines;

        for (int i = 0; i < peopleToMove; i++) {
            try {
                addPerson(removeNextAttendee());
            } catch (AllLinesEmptyException e) {
                throw new IllegalStateException("all lines are empty", e); // safety: peopleToMove is less than the total number of people
            } catch (TakenSeatException e) {
                throw new IllegalStateException("seat conflict in lines", e); // safety: all people in the lines have already been checked for seat conflicts
            }
        }

        // check if constraints are still satisfied
        cursorLine = headLine;
        int maxLineLength = 0;
        int minLineLength = Integer.MAX_VALUE;
        while (cursorLine != null) {
            if (cursorLine.getLength() > maxLineLength) {
                maxLineLength = cursorLine.getLength();
            }
            if (cursorLine.getLength() < minLineLength) {
                minLineLength = cursorLine.getLength();
            }
            cursorLine = cursorLine.getLineLink();
        }

        if (maxLineLength - minLineLength > 1) {
            throw new RuntimeException("constraints not satisfied after adding lines");
        }

        // reset cursor
        cursorLine = headLine;
        cursorLineNumber = 1;
    }

    /**
     * Removes the specified lines, maintaining constraints.
     *
     * @param removedLines an array of lines to be removed
     * @throws LineDoesNotExistException  if a line does not exist in the list
     * @throws SingleLineRemovalException if all lines are being removed
     */
    public void removeLines(int[] removedLines) throws LineDoesNotExistException, SingleLineRemovalException {
        // assert that the lines exist
        if (Arrays.stream(removedLines).anyMatch(line -> (line > lineCount || line < 1))) {
            throw new LineDoesNotExistException("line does not exist: %d. linecount: %d".formatted(Arrays.stream(removedLines).filter(line -> (line > lineCount || line < 1)).findFirst().orElseThrow(), lineCount));
        }
        if (removedLines.length == 0) {
            throw new LineDoesNotExistException("no lines specified");
        }
        // assert that there are lines remaining after removal (assuming no duplicates)
        if (lineCount - removedLines.length < 1) {
            throw new SingleLineRemovalException("cannot remove all lines");
        }

        // 1 = head, 2 = second, etc.
        Line tempLine = new Line();
        cursorLine = headLine;
        int lineIndex = 1;
        int linesRemoved = 0;

        // sort the array of removed lines for easier processing
        Arrays.sort(removedLines);

        // traverse the lines and remove the ones specified, adding them to a temporary line
        // consecutive lines from head are removed first
        while (removedLines[linesRemoved] == lineIndex) {
            tempLine.addAllFromLineUnordered(cursorLine);
            headLine = cursorLine.getLineLink();
            cursorLine = headLine;
            lineIndex++;
            linesRemoved++;
            // if we're done, break
            if (linesRemoved == removedLines.length) {
                break;
            }
        }

        lineIndex++; // skip the next line, as it will not be removed

        // if we're not done, traverse the rest of the lines
        if (linesRemoved < removedLines.length) {
            while (cursorLine.getLineLink() != null) {
                if (lineIndex == removedLines[linesRemoved]) {
                    // if next is tail, set tail to current
                    if (cursorLine.getLineLink() == tailLine) {
                        tailLine = cursorLine;
                    }
                    tempLine.addAllFromLineUnordered(cursorLine.getLineLink());
                    cursorLine.setLineLink(cursorLine.getLineLink().getLineLink());
                    linesRemoved++;
                    // if we're done, break
                    if (linesRemoved == removedLines.length) {
                        break;
                    }
                } else {
                    cursorLine = cursorLine.getLineLink();
                }
            }
        }
        lineCount -= removedLines.length;

        while (tempLine.getLength() > 0) {
            try {
                addPerson(tempLine.removeFrontPerson());
            } catch (TakenSeatException e) {
                throw new IllegalStateException("seat conflict in tempLine", e); // safety: all people in tempLine are from lines that have already been checked for seat conflicts
            }
        }

        // reset cursor
        cursorLine = headLine;
        cursorLineNumber = 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Line currentLine = headLine;
        int lineIndex = 1;
        while (currentLine != null) {
            sb.append(String.format("Line %d: %d %s Waiting%n", lineIndex, currentLine.getLength(), currentLine.getLength() == 1 ? "Person" : "People"));
            currentLine = currentLine.getLineLink();
            lineIndex++;
        }
        return sb.toString();
    }

    public void printAllLines() {
        StringBuilder sb = new StringBuilder();
        sb.append("|  Line  |         Name         |  Seat Number  |\n");
        sb.append("+--------+----------------------+---------------+\n");

        cursorLine = headLine;
        int lineIndex = 1;
        while (cursorLine != null) {
            Person currentPerson = cursorLine.getHeadPerson();
            while (currentPerson != null) {
                sb.append("| %6d | %20s | %13d |\n".formatted(lineIndex, currentPerson.getName(), currentPerson.getSeatNumber()));
                currentPerson = currentPerson.getNextPerson();
            }
            cursorLine = cursorLine.getLineLink();
            lineIndex++;
        }
        System.out.println(sb);

        // reset cursor
        cursorLine = headLine;
        cursorLineNumber = 1;
    }
}
