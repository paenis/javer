package moe.cark.hw1;


/**
 * The Person class represents a person with attributes such as age, height, weight, name, and gender.
 */
public class Person {
    int age;
    double height;
    double weight;

    String name;
    String gender;

    public Person() {
        // todo
    }

    /**
     * Constructs a new Person object with the specified attributes.
     *
     * @param name   the name of the person
     * @param gender the gender of the person
     * @param age    the age of the person
     * @param height the height of the person
     * @param weight the weight of the person
     */ // ctor in csv order
    public Person(String name, String gender, int age, double height, double weight) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Returns a string representation of the Person object in tabular format.
     */
    @Override
    public String toString() {
        return "    %s    |    %d    |    %s    |   %d feet %d inches%s  |    %d pounds    ".formatted(name, age, gender, (int) (height / 12.0), (int) (height % 12.0), ((int) (height % 12.0) >= 10 ? "" : " "), (int) weight);
    }
}
