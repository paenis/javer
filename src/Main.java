import moe.cark.hw1.PersonDataManager;

public class Main {
    public static void main(String[] args) {
        PersonDataManager pdm = new PersonDataManager();
        pdm.buildFromFile("/home/cark/Downloads/biostats.csv");
        pdm.printTable();
    }
}