import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Integer> issuedBookIds;
    private int maxBooks = 3; // एका user कडे जास्तीत जास्त किती books

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.issuedBookIds = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public List<Integer> getIssuedBookIds() { return issuedBookIds; }

    public boolean canIssueMore() {
        return issuedBookIds.size() < maxBooks;
    }

    public void addIssuedBook(int bookId) {
        issuedBookIds.add(bookId);
    }

    public boolean removeIssuedBook(int bookId) {
        return issuedBookIds.remove(Integer.valueOf(bookId));
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (Issued: " + issuedBookIds.size() + ")";
    }
}
