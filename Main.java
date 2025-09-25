import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        library.seedSampleData();
        System.out.println("Welcome to Library Management System (Console)");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt();
            switch (choice) {
                case 1: addBook(); break;
                case 2: addUser(); break;
                case 3: listAllBooks(); break;
                case 4: listAvailableBooks(); break;
                case 5: searchBooks(); break;
                case 6: issueBook(); break;
                case 7: returnBook(); break;
                case 8: viewUserBooks(); break;
                case 9: listAllUsers(); break;
                case 0:
                    running = false;
                    System.out.println("Exiting. Good luck!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Book");
        System.out.println("2. Add User");
        System.out.println("3. List All Books");
        System.out.println("4. List Available Books");
        System.out.println("5. Search Books by title/author");
        System.out.println("6. Issue Book");
        System.out.println("7. Return Book");
        System.out.println("8. View User's Issued Books");
        System.out.println("9. List All Users");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static int readInt() {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (Exception e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = sc.nextLine().trim();
        System.out.print("Enter book author: ");
        String author = sc.nextLine().trim();
        Book b = library.addBook(title, author);
        System.out.println("Added: " + b);
    }

    private static void addUser() {
        System.out.print("Enter user name: ");
        String name = sc.nextLine().trim();
        User u = library.addUser(name);
        System.out.println("Added user: " + u);
    }

    private static void listAllBooks() {
        List<Book> list = library.listAllBooks();
        System.out.println("\nAll Books:");
        for (Book b : list) System.out.println(b);
    }

    private static void listAvailableBooks() {
        List<Book> list = library.listAvailableBooks();
        System.out.println("\nAvailable Books:");
        for (Book b : list) System.out.println(b);
    }

    private static void searchBooks() {
        System.out.print("Enter search term: ");
        String term = sc.nextLine().trim();
        List<Book> list = library.searchByTitleOrAuthor(term);
        System.out.println("\nSearch results:");
        for (Book b : list) System.out.println(b);
    }

    private static void issueBook() {
        System.out.print("Enter book id to issue: ");
        int bookId = readInt();
        System.out.print("Enter user id: ");
        int userId = readInt();
        library.issueBook(bookId, userId);
    }

    private static void returnBook() {
        System.out.print("Enter book id to return: ");
        int bookId = readInt();
        System.out.print("Enter user id: ");
        int userId = readInt();
        library.returnBook(bookId, userId);
    }

    private static void viewUserBooks() {
        System.out.print("Enter user id: ");
        int userId = readInt();
        User u = library.getUser(userId);
        if (u == null) {
            System.out.println("No user with id " + userId);
            return;
        }
        System.out.println("User: " + u);
        List<Book> books = library.getUserIssuedBooks(userId);
        if (books.isEmpty()) System.out.println("No books issued.");
        else {
            System.out.println("Issued Books:");
            for (Book b : books) System.out.println(b);
        }
    }

    private static void listAllUsers() {
        System.out.println("\nAll Users:");
        // naive way: print ids 1..nextUserId-1 (since we don't expose map)
        int i = 1;
        while (true) {
            User u = library.getUser(i);
            if (u == null) {
                // try find next until gap of 3 empties - but simple approach: break when i > 100 maybe
                // For simplicity, we'll print existing users by iterating small range
                i++;
                if (i > 100) break;
                continue;
            }
            System.out.println(u);
            i++;
            if (i > 100) break;
        }
    }
}

