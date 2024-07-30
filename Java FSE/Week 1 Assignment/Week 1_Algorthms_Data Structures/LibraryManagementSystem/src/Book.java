public class Book {
    private int bookId;
    private String Title;
    private String Author;

    public Book(int bookId, String Title, String Author) {
        this.bookId = bookId;
        this.Title = Title;
        this.Author = Author;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return Title;
    }

    public String getBookAuthor() {
        return Author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                '}';
    }

}
