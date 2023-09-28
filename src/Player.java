import java.util.*;

public class Player {
    private final ArrayList<Card> hand;
    private int winCount;

    public Player() {
        this(0);
    }

    public Player(int score) {
        hand = new ArrayList<>();
        winCount = score;
    }

    public void addCardToHand(Card temp) {
        hand.add(temp);
    }

    public void resetHand() {
        hand.clear();
    }

    public void setWinCount(int numwins) {
        winCount = numwins;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getHandSize() {
        return hand.size();
    }

    public int getHandValue() {
        return hand.stream().mapToInt(Card::getValue).sum();
    }

    public boolean hit() {
        return getHandValue() < 17 /* && new Scanner(System.in).next().toLowerCase().charAt(0) == 'y'*/;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("hand = [");
        for (Card card : hand) {
            sb.append(card).append("\n ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        sb.append(" - ").append(getHandValue());
        return sb.toString();
    }
}