import java.util.ArrayList;
import java.util.List;

public class LibraryManagementSystem {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public List<Book> linearSearchTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getBookTitle().equals(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> binarySearchTitle(String title) {
        List<Book> result = new ArrayList<>();
        int left = 0;
        int right = books.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (books.get(mid).getBookTitle().equals(title)) {
                result.add(books.get(mid));
                int i = mid - 1;
                while (i >= 0 && books.get(i).getBookTitle().equals(title)) {
                    result.add(books.get(i));
                    i--;
                }
                i = mid + 1;
                while (i < books.size() && books.get(i).getBookTitle().equals(title)) {
                    result.add(books.get(i));
                    i++;
                }
                break;
            } else if (books.get(mid).getBookTitle().compareTo(title) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

}
