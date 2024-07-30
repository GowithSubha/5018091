public class main {
    public static void main(String[] args) {
        LibraryManagementSystem libraryManagementSystem = new LibraryManagementSystem();
        libraryManagementSystem.addBook(new Book(1, "Higher Engineering Mathematics", "B. S. Grewal"));
        libraryManagementSystem.addBook(new Book(2, "Software Engineering", "Rajiv Mall"));
        libraryManagementSystem.addBook(new Book(3, "Mathematics", "R. S. Agarwal"));

        libraryManagementSystem.displayBooks();
        System.out.println("Linear Search");
        System.out.println(libraryManagementSystem.linearSearchTitle("Software Engineering"));
        System.out.println("Binary Search");
        System.out.println(libraryManagementSystem.binarySearchTitle("Software Engineering"));

    }

}
