import java.util.Random;

public class Main {
    public static void main(String[] args) {
        PersonDataManager pdm = new PersonDataManager();
        pdm.buildFromFile("/home/cark/Downloads/biostats.csv");
        pdm.printTable();
    }
}