package cse214.hw2;

public class Person {
    private String name;
    private int seatNumber;
    private Person nextPerson;

    public Person() {
    }

    public Person(String name, int seatNumber) {
        this.name = name;
        this.seatNumber = seatNumber;
        // nextPerson is null by default
    }


    public Person getNextPerson() {
        return nextPerson;
    }

    public void setNextPerson(Person nextPerson) {
        this.nextPerson = nextPerson;
    }


    public int getSeatNumber() {
        return seatNumber;
    }

    public String getName() {
        return name;
    }
}
