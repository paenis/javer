public class BlackJackCard extends Card {
    //constructors
    public BlackJackCard() {
        super();
    }

    public BlackJackCard(int face, String suit) {
        super(face, suit);
    }

    public int getValue() {
        // no local face variable
        switch (getFace()) {
            case 1:
                return 11;
            case 11:
            case 12:
            case 13:
                return 10;
            default:
                return getFace();
        }
    }
}