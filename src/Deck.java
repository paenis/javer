import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

public class Deck<T extends Card> {
    public static final int NUMFACES = 13;
    public static final int NUMSUITS = 4;
    public static final int NUMCARDS = 52;

    public static final String[] SUITS = {"CLUBS", "SPADES", "DIAMONDS", "HEARTS"};

    private int topCardIndex;
    private final ArrayList<T> stackOfCards;

    public Deck(Class<? extends T> impl) {
        //initialize data - stackOfCards - topCardIndex
        Constructor<? extends T> ctor;
        try {
            ctor = impl.getConstructor(int.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        stackOfCards = new ArrayList<>();
        topCardIndex = NUMCARDS - 1;

        //loop through suits
        for (String suit : SUITS) {
            //loop through faces
            for (int face = 1; face <= NUMFACES; face++) {
                //add in a new card
                try {
                    stackOfCards.add(ctor.newInstance(face, suit));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    //modifiers
    public void shuffle() {
        //shuffle the deck
        //reset variables as needed
        Collections.shuffle(stackOfCards);
        topCardIndex = NUMCARDS - 1;
    }

    //accessors
    public int size() {
        return stackOfCards.size();
    }

    public int numCardsLeft() {
        return topCardIndex + 1;
    }

    public Card nextCard() {
        return stackOfCards.get(topCardIndex--);
    }

    public String toString() {
        return stackOfCards + "   topCardIndex = " + topCardIndex;
    }
}