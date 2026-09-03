public class Book {
    private final int id;
    private final String title;
    private final String author;
    private boolean available;

    public Book(int id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public Book(int id, String title, String author) {
        this(id, title, author, true);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        String status = available ? "Available" : "Issued";
        return String.format("  [ID: %d]  %-30s  by %-20s  (%s)", id, title, author, status);
    }
}
