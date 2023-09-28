public class Card {
    public static final String[] FACES = {"ZERO", "ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};

    private String suit;
    private int face;

    //constructors
    public Card() {
        this(0, ""); // weird
    }

    public Card(int face, String suit) {
        this.face = face;
        this.suit = suit;
    }


    // modifiers
    public void setFace(int face) {
        this.face = face;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    //accessors
    public int getFace() {
        return face;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return face;
    }

    public boolean equals(Object other) {
        return other instanceof Card && ((Card) other).face == face && ((Card) other).suit.equals(suit);
    }

    //toString
    public String toString() {
        // relies on any inheriting classes to override getValue
        return String.format("%s of %s | value = %d", FACES[face], suit, getValue());
    }
}