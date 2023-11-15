package cse214.hw3;

/**
 * Represents the condition of a book.
 * <p>
 * The conditions are: {@code NEW}, {@code GOOD}, {@code BAD}, and {@code REPLACE}.
 */
public enum Condition implements Comparable<Condition> { // comparable is implicitly implemented
    NEW, GOOD, BAD, REPLACE;
}
