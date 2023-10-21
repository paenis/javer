import cse214.hw1.Person;
import cse214.hw1.PersonDataManager;
import cse214.hw1.PersonDoesNotExistException;

public class Main {
    public static void main(String[] args) {
        PersonDataManager.buildFromFile("src/moe/cark/hw1/biostats.csv");

        Person p1;
        try {
            p1 = PersonDataManager.getPerson("Alex");
        } catch (PersonDoesNotExistException e) {
            throw new RuntimeException("person does not exist", e);
        }
        System.out.println(p1);

        PersonDataManager.printTable();
    }
}