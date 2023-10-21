package cse214.hw1;


import java.util.Objects;

/**
 * Represents a person with attributes such as age, height, weight, name, and gender.
 */
public class Person {
    private int age;
    private double height;
    private double weight;
    private String name;
    private String gender;

    public Person() {
        // never used
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
     * Returns the age of the person.
     *
     * @return the age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the person.
     *
     * @param age the age to be set for the person
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the height of the person.
     *
     * @return the height of the person
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the person.
     *
     * @param height the height to set for the person
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the weight of the person.
     *
     * @return the weight of the person
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the person.
     *
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Returns the name of the person.
     *
     * @return the name of the person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the gender of the person.
     *
     * @return the gender of the person
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the person.
     *
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns a string representation of the Person object in tabular format.
     */
    @Override
    public String toString() {
        return "    %s    |    %d    |    %s    |   %d feet %d inches%s  |    %d pounds    ".formatted(name, age, gender, (int) (height / 12.0), (int) (height % 12.0), ((int) (height % 12.0) >= 10 ? "" : " "), (int) weight);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this object is the same as the o argument;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Person person) { // instanceof pattern matching java 14+
            return getAge() == person.getAge() && Double.compare(getHeight(), person.getHeight()) == 0 && Double.compare(getWeight(), person.getWeight()) == 0 && Objects.equals(getName(), person.getName()) && Objects.equals(getGender(), person.getGender());
        }
        return false;
    }
}
