package cse214.hw2;

public class Line {
    Person headPerson; // "front", lowest seat number
    Person tailPerson; // "back", highest seat number
    int length;
    Line lineLink;

    public Line() {
        headPerson = null;
        tailPerson = null;
        length = 0;
        lineLink = null;
    }

    /**
     * Adds a person to the list of attendees.
     *
     * @param attendee the Person object representing the attendee to be added
     */
    public void addPerson(Person attendee) {
        // empty line
        if (headPerson == null) {
            headPerson = attendee;
            tailPerson = attendee;
        }
        // otherwise insert in ascending order from head (after person with <= seat)
        else {
            Person currentPerson = headPerson;
            Person previousPerson = null;
            // traverse
            while (currentPerson != null && currentPerson.getSeatNumber() < attendee.getSeatNumber()) {
                previousPerson = currentPerson;
                currentPerson = currentPerson.getNextPerson();
            }
            // insert at head if seat number is less than all others
            if (previousPerson == null) {
                attendee.setNextPerson(headPerson);
                headPerson = attendee;
            }
            // insert at tail if seat number is greater than all others
            else if (currentPerson == null) {
                previousPerson.setNextPerson(attendee);
                tailPerson = attendee;
            }
            // insert in middle otherwise
            else {
                previousPerson.setNextPerson(attendee);
                attendee.setNextPerson(currentPerson);
            }
        }
        length++;
    }

    /**
     * Removes the front person from the list of attendees.
     *
     * @return the Person object representing the removed attendee
     */
    public Person removeFrontPerson() {
        Person removedPerson = headPerson;
        headPerson = headPerson.getNextPerson();
        length--;
        if (length == 0) {
            tailPerson = null;
        }
        return removedPerson;
    }

    /**
     * Checks if a seat is taken by any attendee in the list.
     *
     * @param seatNumber the seat number to check
     * @return true if the seat is taken, false otherwise
     */
    public boolean seatTaken(int seatNumber) {
        Person currentPerson = headPerson;
        while (currentPerson != null) {
            if (currentPerson.getSeatNumber() == seatNumber) {
                return true;
            }
            currentPerson = currentPerson.getNextPerson();
        }
        return false;
    }

    public void addAllFromLine(Line line) {
        Person currentPerson = line.headPerson;
        while (currentPerson != null) {
            addPerson(currentPerson);
            currentPerson = currentPerson.getNextPerson();
            length++;
        }
    }

    public void addAllFromLineUnordered(Line line) {
        Person currentPerson = line.headPerson;
        while (currentPerson != null) {
            tailPerson.setNextPerson(currentPerson);
            tailPerson = currentPerson;
            currentPerson = currentPerson.getNextPerson();
            length++;
        }
    }


    public Line getLineLink() {
        return lineLink;
    }

    public void setLineLink(Line lineLink) {
        this.lineLink = lineLink;
    }
}
