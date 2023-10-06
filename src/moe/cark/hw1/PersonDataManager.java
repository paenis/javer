package moe.cark.hw1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class PersonDataManager {
    private static Person[] people; // size based on file

    /**
     * Reads data from a file and builds an array of Person objects.
     *
     * @param location the file path of the data file
     * @throws IllegalArgumentException if the file contains invalid data
     * @throws RuntimeException         if an error occurs while reading the file
     */
    // should either return or not be static?
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
        } catch (IOException e) {
            throw new RuntimeException("error reading file", e);
        }

    }

    /**
     * Adds a new Person object to the existing array of people.
     *
     * @param newPerson the Person object to add
     * @throws PersonAlreadyExistsException if the person already exists in the array
     */
    public static void addPerson(Person newPerson) throws PersonAlreadyExistsException {
        for (Person person : people) {
            if (person.equals(newPerson)) throw new PersonAlreadyExistsException(); // maybe
        }

        // resize and add to array
        people = new Person[people.length + 1];
        System.arraycopy(people, 0, people, 0, people.length - 1);
        people[people.length - 1] = newPerson;
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
            if (person.name.equals(name)) return person;
        }
        throw new PersonDoesNotExistException("person does not exist: %s".formatted(name));
    }

    /**
     * Removes a Person object with the specified name from the array of people.
     *
     * @param name the name of the person to remove
     * @throws PersonDoesNotExistException if the person does not exist in the array
     */
    public static void removePerson(String name) throws PersonDoesNotExistException {
        for (int i = 0; i < people.length; i++) {
            if (people[i].name.equals(name)) {
                // copy all elements except the one to remove
                Person[] tempPeople = new Person[people.length - 1];
                System.arraycopy(people, 0, tempPeople, 0, i);
                System.arraycopy(people, i + 1, tempPeople, i, people.length - i - 1);
                people = tempPeople;
                return;
            }
        }
        throw new PersonDoesNotExistException("person does not exist: %s".formatted(name));
    }

    public static void printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name\t|\tGender\t|\tAge\t|\tHeight\t|\tWeight\n= = = = = = = = = = = = = = = = = = = = = = = = = =\n");
        for (Person person : people) {
            sb.append(person.toString()).append("\n");
        }
        System.out.println(sb);
    }


}
