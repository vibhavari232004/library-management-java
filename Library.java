import java.util.*;

public class Library {
    private Map<Integer, Book> books;
    private Map<Integer, User> users;
    private Map<Integer, Integer> issuedBookToUser; // bookId -> userId
    private int nextBookId;
    private int nextUserId;

    public Library() {
        books = new HashMap<>();
        users = new HashMap<>();
        issuedBookToUser = new HashMap<>();
        nextBookId = 1;
        nextUserId = 1;
    }

    // Add a book (auto id)
    public Book addBook(String title, String author) {
        Book b = new Book(nextBookId++, title, author);
        books.put(b.getId(), b);
        return b;
    }

    // Add user (auto id)
    public User addUser(String name) {
        User u = new User(nextUserId++, name);
        users.put(u.getId(), u);
        return u;
    }

    public boolean issueBook(int bookId, int userId) {
        Book b = books.get(bookId);
        User u = users.get(userId);

        if (b == null) {
            System.out.println("No book with id " + bookId);
            return false;
        }
        if (u == null) {
            System.out.println("No user with id " + userId);
            return false;
        }
        if (b.isIssued()) {
            System.out.println("Book already issued.");
            return false;
        }
        if (!u.canIssueMore()) {
            System.out.println("User has reached max issued books.");
            return false;
        }

        b.setIssued(true);
        u.addIssuedBook(bookId);
        issuedBookToUser.put(bookId, userId);
        System.out.println("Issued book " + bookId + " to user " + userId);
        return true;
    }

    public boolean returnBook(int bookId, int userId) {
        Book b = books.get(bookId);
        User u = users.get(userId);

        if (b == null || u == null) {
            System.out.println("Invalid bookId or userId.");
            return false;
        }

        Integer assignedUser = issuedBookToUser.get(bookId);
        if (assignedUser == null || assignedUser != userId) {
            System.out.println("This book is not issued to this user.");
            return false;
        }

        b.setIssued(false);
        u.removeIssuedBook(bookId);
        issuedBookToUser.remove(bookId);
        System.out.println("Book " + bookId + " returned by user " + userId);
        return true;
    }

    public List<Book> listAllBooks() {
        List<Book> list = new ArrayList<>(books.values());
        list.sort(Comparator.comparingInt(Book::getId));
        return list;
    }

    public List<Book> listAvailableBooks() {
        List<Book> list = new ArrayList<>();
        for (Book b : books.values()) if (!b.isIssued()) list.add(b);
        list.sort(Comparator.comparingInt(Book::getId));
        return list;
    }

    public List<Book> searchByTitleOrAuthor(String term) {
        String t = term.toLowerCase();
        List<Book> list = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(t) || b.getAuthor().toLowerCase().contains(t)) {
                list.add(b);
            }
        }
        list.sort(Comparator.comparingInt(Book::getId));
        return list;
    }

    public List<Book> getUserIssuedBooks(int userId) {
        User u = users.get(userId);
        List<Book> result = new ArrayList<>();
        if (u == null) return result;
        for (int bid : u.getIssuedBookIds()) {
            Book b = books.get(bid);
            if (b != null) result.add(b);
        }
        result.sort(Comparator.comparingInt(Book::getId));
        return result;
    }

    public User getUser(int userId) { return users.get(userId); }
    public Book getBook(int bookId) { return books.get(bookId); }

    // Seed sample data (optional)
    public void seedSampleData() {
        addBook("The Alchemist", "Paulo Coelho");
        addBook("Clean Code", "Robert C. Martin");
        addBook("Head First Java", "Kathy Sierra");
        addBook("Introduction to Algorithms", "Cormen");
        addUser("Anushka");
        addUser("Riva");
        addUser("Vibhavari");
    }
}
