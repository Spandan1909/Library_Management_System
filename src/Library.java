import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Library {
    private final HashMap<Integer, Book> books = new HashMap<>();
    private final HashMap<Integer, Member> members = new HashMap<>();
    private final ArrayList<Loan> loans = new ArrayList<>();
    private int nextLoanId = 1;

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException(
                    "A book with ID " + book.getId() + " already exists.");
        }
        books.put(book.getId(), book);
    }

    public void removeBook(int bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID " + bookId + ".");
        }
        if (hasActiveLoanForBook(bookId)) {
            throw new IllegalStateException(
                    "Cannot remove book " + bookId + " -- it has an active loan.");
        }
        books.remove(bookId);
    }

    public void registerMember(Member member) {
        if (members.containsKey(member.getId())) {
            throw new IllegalArgumentException(
                    "A member with ID " + member.getId() + " already exists.");
        }
        members.put(member.getId(), member);
    }

    public void removeMember(int memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("No member found with ID " + memberId + ".");
        }
        if (hasActiveLoanForMember(memberId)) {
            throw new IllegalStateException(
                    "Cannot remove member " + memberId + " -- they have an active loan.");
        }
        members.remove(memberId);
    }

    public Loan issueBook(int bookId, int memberId, LocalDate issueDate) {
        Book book = books.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID " + bookId + ".");
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book " + bookId + " is already issued.");
        }
        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("No member found with ID " + memberId + ".");
        }
        Loan loan = new Loan(nextLoanId++, bookId, memberId, issueDate);
        loans.add(loan);
        book.setAvailable(false);
        return loan;
    }

    public Loan returnBook(int bookId, LocalDate returnDate) {
        Loan loan = loans.stream()
                .filter(l -> l.getBookId() == bookId && l.isActive())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Book " + bookId + " is not currently issued."));
        loan.close(returnDate);
        Book book = books.get(bookId);
        if (book != null) {
            book.setAvailable(true);
        }
        return loan;
    }

    public List<Book> listBooks() {
        return books.values().stream()
                .sorted(Comparator.comparingInt(Book::getId))
                .collect(Collectors.toList());
    }

    public List<Member> listMembers() {
        return members.values().stream()
                .sorted(Comparator.comparingInt(Member::getId))
                .collect(Collectors.toList());
    }

    public List<Loan> listActiveLoans() {
        return loans.stream()
                .filter(Loan::isActive)
                .collect(Collectors.toList());
    }

    public List<Loan> listAllLoans() {
        return new ArrayList<>(loans);
    }

    public Optional<Book> findBookById(int id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> findBooksByTitle(String title) {
        return books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList());
    }

    public Optional<Member> findMemberById(int id) {
        return Optional.ofNullable(members.get(id));
    }

    public List<Member> findMembersByName(String name) {
        return members.values().stream()
                .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(Member::getName))
                .collect(Collectors.toList());
    }

    private boolean hasActiveLoanForBook(int bookId) {
        return loans.stream().anyMatch(l -> l.getBookId() == bookId && l.isActive());
    }

    private boolean hasActiveLoanForMember(int memberId) {
        return loans.stream().anyMatch(l -> l.getMemberId() == memberId && l.isActive());
    }
}
