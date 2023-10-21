package cse214.hw1;

import java.io.*;
import java.util.List;

/**
 * The PersonDataManager class is responsible for reading, managing, and manipulating
 * an array of Person objects. It provides methods to build the array from a file,
 * add new Person objects, retrieve and remove existing Person objects, print a table
 * of Person objects, and save the data to a file.
 */
public class PersonDataManager {
    private static Person[] people; // size based on file

    /**
     * Reads data from a file and builds an array of Person objects.
     *
     * @param location the file path of the data file
     * @throws IllegalArgumentException if the file contains invalid data
     * @throws RuntimeException         if an error occurs while reading the file
     */
    // should either return or not be static? or entire class should be static?
    public static void buildFromFile(String location) throws IllegalArgumentException {
        try (BufferedReader reader = new BufferedReader(new FileReader(location))) {
            List<String> lines = reader.lines().skip(1).toList(); // first line is header
            Person[] tempPeople = new Person[lines.size()];
            int idx = 0;

            for (String line : lines) {
                String[] data = line.split(","); // assumes no commas in data

                // remove all quotes and whitespace
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replaceAll("\"", "").trim();
                }

                // parse - assume order is name,gender,age,height,weight
                String name = data[0];
                if (name.matches(".*\\d.*"))
                    throw new IllegalArgumentException("name contains digits: %s".formatted(data[0]));

                String gender = data[1];
                if (!gender.matches("^[MF]$"))
                    throw new IllegalArgumentException("invalid gender: %s".formatted(data[1]));

                int age;
                try {
                    age = Integer.parseInt(data[2]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid age: %s".formatted(data[2]));
                }

                double height;
                try {
                    height = Double.parseDouble(data[3]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid height: %s".formatted(data[3]));
                }

                double weight;
                try {
                    weight = Double.parseDouble(data[4]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid weight: %s".formatted(data[4]));
                }

                // checks passed, add to array
                tempPeople[idx++] = new Person(name, gender, age, height, weight);
            }

            // set people
            people = new Person[tempPeople.length];
            System.arraycopy(tempPeople, 0, people, 0, tempPeople.length);
            // reader is auto-closed
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file. Did you enter the correct path? (Try using an absolute file path)", e);
        }

    }

    /**
     * Adds a new Person object to the existing array of people.
     *
     * @param newPerson the Person object to add
     * @throws PersonAlreadyExistsException if the person already exists in the array
     */
    public static void addPerson(Person newPerson) throws PersonAlreadyExistsException {
        // get index to insert at
        int idx = 0;
        for (Person person : people) {
            if (person.equals(newPerson)) // having two people with the same name is allowed per the assignment, even though it's not a good idea
                throw new PersonAlreadyExistsException("A person with the same attributes already exists");
            if (person.getName().compareTo(newPerson.getName()) > 0) break; // name is greater, insert before
            idx++;
        }

        // resize and add to array
        Person[] tempPeople = new Person[people.length + 1];
        System.arraycopy(people, 0, tempPeople, 0, idx);
        tempPeople[idx] = newPerson;
        System.arraycopy(people, idx, tempPeople, idx + 1, people.length - idx);
        people = tempPeople;
    }

    /**
     * Retrieves a Person object with the specified name from the array of people.
     *
     * @param name the name of the person to retrieve
     * @return the Person object with the specified name
     * @throws PersonDoesNotExistException if the person does not exist in the array
     */
    public static Person getPerson(String name) throws PersonDoesNotExistException {
        for (Person person : people) {
            if (person.getName().equals(name)) return person;
        }
        throw new PersonDoesNotExistException("Requested person does not exist: %s".formatted(name));
    }

    /**
     * Removes a Person object with the specified name from the array of people.
     *
     * @param name the name of the person to remove
     * @throws PersonDoesNotExistException if the person does not exist in the array
     */
    public static void removePerson(String name) throws PersonDoesNotExistException {
        for (int i = 0; i < people.length; i++) {
            if (people[i].getName().equals(name)) {
                // copy all elements except the one to remove
                Person[] tempPeople = new Person[people.length - 1];
                System.arraycopy(people, 0, tempPeople, 0, i);
                System.arraycopy(people, i + 1, tempPeople, i, people.length - i - 1);
                people = tempPeople;
                return;
            }
        }
        throw new PersonDoesNotExistException("Requested person does not exist: %s".formatted(name));
    }

    /**
     * Prints a table of Person objects.
     * <p>
     * The table displays the Name, Age, Gender, Height, and Weight of each Person
     * object in the array of people.
     */
    public static void printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("    Name    |    Age   |  Gender |       Height        |      Weight      \n");
        sb.append("------------+----------+---------+---------------------+------------------\n");
        for (Person person : people) {
            sb.append(person.toString()).append("\n");
        }
        System.out.println(sb);
    }

    /**
     * Saves the data of Person objects to a file.
     * <p>
     * The method writes the data of each Person object in the array of people
     * to a file with the given name. The data is formatted as comma-separated values,
     * with each line representing a single Person object.
     *
     * @param name the name of the file to save the data to
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void saveToFile(String name) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            writer.write("Name,Sex,Age,\"Height (in)\",\"Weight (lbs)\"\n");
            for (Person person : people) {
                writer.write("%s,%s,%d,%.1f,%.1f\n".formatted(person.getName(), person.getGender(), person.getAge(), person.getHeight(), person.getWeight()));
            }
            // writer is auto-closed. the downside is that the file is still created even if there is an error
        }
    }
}
