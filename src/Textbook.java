public class Textbook extends Book {

    private int edition;

    public Textbook(String bookTitle, double bookPrice, int bookEdition) {
        super(bookTitle, bookPrice);
        edition = bookEdition;
    }

    public int getEdition() {
        return edition;
    }

    public boolean canSubstituteFor(Textbook other) {
        return this.getTitle().equals(other.getTitle()) && this.getEdition() >= other.getEdition();
    }

    public String getBookInfo() {
        return super.getBookInfo() + "-" + edition;
    }
}
