package cse214.hw1;

import java.io.IOException;
import java.util.Scanner;

/**
 * The PersonManager class represents a manager for person data.
 * It provides functionality for importing, adding, removing, getting information,
 * printing, and saving person data.
 */
public class PersonManager {
    /**
     * This method represents the main entry point for the application.
     * It displays a menu and allows the user to select different options
     * by inputting their choice.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            // print menu
            System.out.println("\nMenu:\n" +
                               "\t(I) Import from File\n" +
                               "\t(A) Add Person\n" +
                               "\t(R) Remove Person\n" +
                               "\t(G) Get Information on Person\n" +
                               "\t(P) Print Table\n" +
                               "\t(S) Save to File\n" +
                               "\t(Q) Quit\n");

            // get option
            System.out.print("Please select an option: ");
            switch (scanner.next().toLowerCase()) { // enhanced switch statement java 14+
                case "i" -> doImport(scanner);
                case "a" -> doAdd(scanner);
                case "r" -> doRemove(scanner);
                case "g" -> doGet(scanner);
                case "p" -> doPrint();
                case "s" -> doSave(scanner);
                case "q" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. (I, A, R, G, P, S, Q)");
            }
        } while (true);
    }

    /**
     * Imports person data from a specified location.
     *
     * @param scanner The scanner object used to get user input for the location.
     */
    private static void doImport(Scanner scanner) {
        System.out.print("Please enter a location: ");
        String location = scanner.next();
        try {
            System.out.println("Loading...");
            PersonDataManager.buildFromFile(location);
            System.out.println("Person data loaded successfully!");
        } catch (RuntimeException e) {
            System.out.printf("Error while importing: %s%n", e.getMessage());
        }
    }

    /**
     * Adds a new person to the person data manager.
     *
     * @param scanner The scanner object used to get user input for the person's information.
     */
    private static void doAdd(Scanner scanner) {
        String name;
        int age;
        String gender;
        double height;
        double weight;

        try {
            System.out.print("Please enter the name of the person: ");
            name = scanner.next();
            if (name.matches(".*\\d.*")) throw new IllegalArgumentException(); // name contains digits
            System.out.print("Please enter the age: ");
            age = scanner.nextInt();
            System.out.print("Please enter the gender (M or F): ");
            gender = scanner.next();
            if (!gender.matches("^[MF]$")) throw new IllegalArgumentException(); // gender is not M or F
            System.out.print("Please enter the height (in inches): ");
            height = scanner.nextDouble();
            System.out.print("Please enter the weight (in lbs): ");
            weight = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("The input you entered is incorrect. Please try again!");
            scanner.next(); // clear scanner (just in case)
            return;
        }

        try {
            PersonDataManager.addPerson(new Person(name, gender, age, height, weight));
            System.out.printf("%s has been added to the list!%n", name);
        } catch (PersonAlreadyExistsException e) {
            System.out.printf("Error while adding person: %s%n", e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Person data not loaded. Please import from file first.");
        }
    }

    /**
     * Removes a person from the person data manager.
     *
     * @param scanner The scanner object used to get user input for the person's name.
     */
    private static void doRemove(Scanner scanner) {
        System.out.print("Please enter the name of the person to remove: ");
        String name = scanner.next();
        try {
            PersonDataManager.removePerson(name);
            System.out.printf("%s has been removed!%n", name);
        } catch (PersonDoesNotExistException e) {
            System.out.printf("Error while removing person: %s%n", e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Person data not loaded. Please import from file first.");
        }
    }

    /**
     * Retrieves information about a specific person from the person data manager.
     *
     * @param scanner The scanner object used to get user input for the person's name.
     */
    private static void doGet(Scanner scanner) {
        System.out.print("Please enter the name of the person to get: ");
        String name = scanner.next();
        try {
            Person person = PersonDataManager.getPerson(name);
            System.out.printf("%s is a %d year old %s who is %d feet and %d inches tall and weighs %d pounds.%n", person.getName(), person.getAge(), (person.getGender().equals("M") ? "male" : "female"), (int) (person.getHeight() / 12.0), (int) (person.getHeight() % 12.0), (int) person.getWeight());
        } catch (PersonDoesNotExistException e) {
            System.out.printf("Error while getting person: %s%n", e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Person data not loaded. Please import from file first.");
        }
    }

    /**
     * Prints the table of person data using the PersonDataManager.
     */
    private static void doPrint() {
        try {
            PersonDataManager.printTable();
        } catch (NullPointerException e) {
            System.out.println("Person data not loaded. Please import from file first.");
        }
    }

    /**
     * Saves the person data to a file with the specified name using the PersonDataManager.
     *
     * @param scanner the scanner object to read user input from
     */
    private static void doSave(Scanner scanner) {
        System.out.print("Please select a name for the file: ");
        String name = scanner.next();

        try {
            PersonDataManager.saveToFile(name);
            System.out.printf("A file named %s has been created!%n", name);
        } catch (NullPointerException e) {
            System.out.println("Person data not loaded. Please import from file first.");
        } catch (RuntimeException | IOException e) {
            System.out.printf("Error while saving to file: %s%n", e.getCause().getMessage());
        }
    }
}
