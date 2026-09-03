import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    private final int loanId;
    private final int bookId;
    private final int memberId;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private boolean active;

    public static final int BORROW_PERIOD_DAYS = 14;
    public static final double FINE_PER_DAY = 5.0;

    public Loan(int loanId, int bookId, int memberId, LocalDate issueDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(BORROW_PERIOD_DAYS);
        this.returnDate = null;
        this.active = true;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isActive() {
        return active;
    }

    public long getOverdueDays() {
        LocalDate reference = active ? LocalDate.now() : returnDate;
        long extra = ChronoUnit.DAYS.between(dueDate, reference);
        return Math.max(0, extra);
    }

    public double getFine() {
        return getOverdueDays() * FINE_PER_DAY;
    }

    public void close(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.active = false;
    }

    @Override
    public String toString() {
        String status = active ? "Active" : "Completed";
        String returned = returnDate != null ? returnDate.toString() : "Not returned";
        long overdue = getOverdueDays();
        String fineInfo = overdue > 0
                ? String.format("Overdue by %d day(s) | Fine: Rs.%.2f", overdue, getFine())
                : "No fine";
        return String.format(
                "  [Loan #%d]  Book ID: %d  Member ID: %d  | Issued: %s  Due: %s  Returned: %s  | %s  | %s",
                loanId, bookId, memberId, issueDate, dueDate, returned, status, fineInfo);
    }
}
