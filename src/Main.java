import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String SEPARATOR = "==============================================";

    public static void main(String[] args) {
        Library library = new Library();
        seedDemoData(library);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice: ");
            System.out.println();
            switch (choice) {
                case 1 -> addBook(scanner, library);
                case 2 -> registerMember(scanner, library);
                case 3 -> listBooks(library);
                case 4 -> listMembers(library);
                case 5 -> searchBooks(scanner, library);
                case 6 -> searchMembers(scanner, library);
                case 7 -> issueBook(scanner, library);
                case 8 -> returnBook(scanner, library);
                case 9 -> listActiveLoans(library);
                case 10 -> listAllLoans(library);
                case 11 -> removeBook(scanner, library);
                case 12 -> removeMember(scanner, library);
                case 13 -> {
                    running = false;
                    System.out.println("Exiting Library Management System. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1-13.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("       LIBRARY MANAGEMENT SYSTEM");
        System.out.println(SEPARATOR);
        System.out.println("  1.  Add Book");
        System.out.println("  2.  Register Member");
        System.out.println("  3.  List Books");
        System.out.println("  4.  List Members");
        System.out.println("  5.  Search Books");
        System.out.println("  6.  Search Members");
        System.out.println("  7.  Issue Book");
        System.out.println("  8.  Return Book");
        System.out.println("  9.  List Active Loans");
        System.out.println("  10. List All Loans");
        System.out.println("  11. Remove Book");
        System.out.println("  12. Remove Member");
        System.out.println("  13. Exit");
        System.out.println(SEPARATOR);
    }

    private static void seedDemoData(Library library) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("  Loading DEMO data for quick testing...");
        System.out.println(SEPARATOR);
        try {
            library.addBook(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"));
            library.addBook(new Book(2, "1984", "George Orwell"));
            library.addBook(new Book(3, "To Kill a Mockingbird", "Harper Lee"));
            library.addBook(new Book(4, "Clean Code", "Robert C. Martin"));
            library.addBook(new Book(5, "Effective Java", "Joshua Bloch"));

            library.registerMember(new Member(101, "Alice Sharma", "alice@example.com"));
            library.registerMember(new Member(102, "Bob Verma", "bob@example.com"));
            library.registerMember(new Member(103, "Charlie Rao", "charlie@example.com"));

            library.issueBook(1, 101, LocalDate.now().minusDays(20));
            library.issueBook(3, 102, LocalDate.now().minusDays(5));

            System.out.println("  Demo books, members, and two active loans added.");
        } catch (Exception e) {
            System.out.println("  Could not load demo data: " + e.getMessage());
        }
        System.out.println(SEPARATOR);
    }

    private static void addBook(Scanner scanner, Library library) {
        int id = readInt(scanner, "Enter Book ID: ");
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();
        try {
            Book book = new Book(id, title, author);
            library.addBook(book);
            System.out.println("Book added successfully:\n" + book);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerMember(Scanner scanner, Library library) {
        int id = readInt(scanner, "Enter Member ID: ");
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        try {
            Member member = new Member(id, name, email);
            library.registerMember(member);
            System.out.println("Member registered successfully:\n" + member);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listBooks(Library library) {
        List<Book> books = library.listBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("Books in Library:");
        books.forEach(System.out::println);
    }

    private static void listMembers(Library library) {
        List<Member> members = library.listMembers();
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        System.out.println("Registered Members:");
        members.forEach(System.out::println);
    }

    private static void searchBooks(Scanner scanner, Library library) {
        System.out.println("Search by: 1) Title  2) Author  3) ID");
        int sub = readInt(scanner, "Choose search type: ");
        switch (sub) {
            case 1 -> {
                System.out.print("Enter title (or part): ");
                String title = scanner.nextLine().trim();
                List<Book> results = library.findBooksByTitle(title);
                printBookResults(results);
            }
            case 2 -> {
                System.out.print("Enter author (or part): ");
                String author = scanner.nextLine().trim();
                List<Book> results = library.findBooksByAuthor(author);
                printBookResults(results);
            }
            case 3 -> {
                int id = readInt(scanner, "Enter Book ID: ");
                Optional<Book> result = library.findBookById(id);
                if (result.isPresent()) {
                    System.out.println("Found:\n" + result.get());
                } else {
                    System.out.println("No book found with ID " + id + ".");
                }
            }
            default -> System.out.println("Invalid search type.");
        }
    }

    private static void printBookResults(List<Book> results) {
        if (results.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void searchMembers(Scanner scanner, Library library) {
        System.out.println("Search by: 1) Name  2) ID");
        int sub = readInt(scanner, "Choose search type: ");
        switch (sub) {
            case 1 -> {
                System.out.print("Enter name (or part): ");
                String name = scanner.nextLine().trim();
                List<Member> results = library.findMembersByName(name);
                if (results.isEmpty()) {
                    System.out.println("No matching members found.");
                } else {
                    results.forEach(System.out::println);
                }
            }
            case 2 -> {
                int id = readInt(scanner, "Enter Member ID: ");
                Optional<Member> result = library.findMemberById(id);
                if (result.isPresent()) {
                    System.out.println("Found:\n" + result.get());
                } else {
                    System.out.println("No member found with ID " + id + ".");
                }
            }
            default -> System.out.println("Invalid search type.");
        }
    }

    private static void issueBook(Scanner scanner, Library library) {
        int bookId = readInt(scanner, "Enter Book ID to issue: ");
        int memberId = readInt(scanner, "Enter Member ID: ");
        try {
            Loan loan = library.issueBook(bookId, memberId, LocalDate.now());
            System.out.println("Book issued successfully:\n" + loan);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook(Scanner scanner, Library library) {
        int bookId = readInt(scanner, "Enter Book ID to return: ");
        try {
            Loan loan = library.returnBook(bookId, LocalDate.now());
            System.out.println("Book returned successfully:\n" + loan);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listActiveLoans(Library library) {
        List<Loan> active = library.listActiveLoans();
        if (active.isEmpty()) {
            System.out.println("No active loans.");
            return;
        }
        System.out.println("Active Loans:");
        active.forEach(System.out::println);
    }

    private static void listAllLoans(Library library) {
        List<Loan> all = library.listAllLoans();
        if (all.isEmpty()) {
            System.out.println("No loans recorded.");
            return;
        }
        System.out.println("All Loans:");
        all.forEach(System.out::println);
    }

    private static void removeBook(Scanner scanner, Library library) {
        int bookId = readInt(scanner, "Enter Book ID to remove: ");
        try {
            library.removeBook(bookId);
            System.out.println("Book " + bookId + " removed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeMember(Scanner scanner, Library library) {
        int memberId = readInt(scanner, "Enter Member ID to remove: ");
        try {
            library.removeMember(memberId);
            System.out.println("Member " + memberId + " removed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number. Please enter an integer.");
            }
        }
    }
}
