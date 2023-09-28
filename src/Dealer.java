public class Dealer extends Player {
    //define a deck of cards
    Deck<BlackJackCard> deck;

    public Dealer() {
        super();
        deck = new Deck<>(BlackJackCard.class);
    }

    public void shuffle() {
        //shuffle the deck
        deck.shuffle();
    }

    public Card deal() {
        return deck.numCardsLeft() > 0 ? deck.nextCard() : null;
    }

    public int numCardsLeftInDeck() {
        return deck.numCardsLeft();
    }

    public boolean hit() {
        return getHandValue() < 17;
    }
}








