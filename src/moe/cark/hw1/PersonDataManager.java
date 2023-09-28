package moe.cark.hw1;

import moe.cark.hw1.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class PersonDataManager {
    Person[] people; // size based on file

    public PersonDataManager() {
        people = new Person[0];
    }

    public PersonDataManager(Person[] people) {
        this.people = people;
    }

    // should either return or not be static?
    public /*static*/ void buildFromFile(String location) throws IllegalArgumentException {
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
                    throw new IllegalArgumentException("name contains digits: %s".formatted(data[0])); // todo nicer error

                String gender = data[1];
                if (!gender.matches("^[MF]$")) throw new IllegalArgumentException("invalid gender :)");

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
            throw new RuntimeException(e);
        }

    }

    public void addPerson(Person newPerson) throws PersonAlreadyExistsException {
        for (Person person : people) {
            if (person.equals(newPerson)) throw new PersonAlreadyExistsException(); // maybe
        }
        // todo
    }

    public void getPerson(String name) throws PersonDoesNotExistException {
        // todo
    }

    public void removePerson(String name) throws PersonDoesNotExistException {
        // todo
    }

    public void printTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name\t|\tGender\t|\tAge\t|\tHeight\t|\tWeight\n= = = = = = = = = = = = = = = = = = = = = = = = = =\n");
        for (Person person : people) {
            sb.append(person.toString()).append("\n");
        }
        System.out.println(sb);
    }


}
