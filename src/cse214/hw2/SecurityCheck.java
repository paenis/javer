package cse214.hw2;

import java.util.Arrays;

public class SecurityCheck {
    Line headLine;
    Line tailLine;
    Line cursorLine;
    int lineCount;

    public SecurityCheck() {
        Line line = new Line();
        headLine = line;
        tailLine = line;
        cursorLine = line;
        lineCount = 1;
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
        Line currentLine = headLine;
        int[] lineLengths = new int[lineCount];
        for (int i = 0; currentLine != null; currentLine = currentLine.getLineLink(), i++) {
            lineLengths[i] = currentLine.length;
            if (currentLine.seatTaken(seatNumber)) {
                throw new TakenSeatException("seat %d is already taken".formatted(seatNumber));
            }
        }

        // find the first line with the least amount of people
        int minLineLength = Arrays.stream(lineLengths).min().orElseThrow(); // safety: there will always be at least one line
        currentLine = headLine;
        while (currentLine.length != minLineLength) {
            currentLine = currentLine.getLineLink();
        }

        // add the person to the line
        currentLine.addPerson(new Person(name, seatNumber));
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

    public Person removeNextAttendee() throws AllLinesEmptyException {
        // todo
        return null;
    }

    public void addNewLines(int newLines) throws InvalidLineCountException {
        // todo
    }

    /**
     * Removes the specified lines, maintaining constraints.
     *
     * @param removedLines an array of lines to be removed
     * @throws LineDoesNotExistException if a line does not exist in the list
     * @throws SingleLineRemovalException if all lines are being removed
     */
    public void removeLines(int[] removedLines) throws LineDoesNotExistException, SingleLineRemovalException {
        // assert that the lines exist
        if (Arrays.stream(removedLines).anyMatch(line -> (line > lineCount || line < 1))) {
            throw new LineDoesNotExistException("line does not exist: %d".formatted(Arrays.stream(removedLines).filter(line -> line > lineCount).findFirst().orElseThrow()));
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
        Line currentLine = headLine;
        Line previousLine = null;
        int lineIndex = 1;
        int linesRemoved = 0;

        // sort the array of removed lines for easier processing
        Arrays.sort(removedLines);

        // traverse the lines and remove the ones specified, adding them to a temporary line
        while (currentLine != null) {
            if (lineIndex == removedLines[linesRemoved]) {
                // if current is head, advance head
                if (currentLine == headLine) {
                    headLine = currentLine.getLineLink();
                }
                // if current is not head, remove in place
                else {
                    previousLine.setLineLink(currentLine.getLineLink());
                }
                tempLine.addAllFromLineUnordered(currentLine);
                linesRemoved++;
                if (linesRemoved == removedLines.length) {
                    break;
                }
            }
            previousLine = currentLine;
            currentLine = currentLine.getLineLink();
            lineIndex++;
        }

        while (tempLine.length > 0) {
            try {
                addPerson(tempLine.removeFrontPerson());
            } catch (TakenSeatException e) {
                // safety: all people in tempLine are from lines that have already been checked for seat conflicts
                e.printStackTrace();
                throw new IllegalStateException("seat conflict in tempLine", e);
            }
        }
    }
}
