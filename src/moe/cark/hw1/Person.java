package moe.cark.hw1;

/**
 * todo
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

    // ctor in csv order
    public Person(String name, String gender, int age, double height, double weight) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "moe.cark.hw1.Person{age=%d, height=%s, weight=%s, name='%s', gender='%s'}".formatted(age, height, weight, name, gender);
    }
}
