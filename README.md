# Library Management System

A menu-driven, console-based **Library Management System** written in **Java 17**. It demonstrates core Object-Oriented Programming principles and the Java Collections Framework, making it suitable as a college DSA/OOP project.

---

## Description

This application lets a librarian manage books, members, and book loans entirely from the console. It tracks which books are available or issued, records issue and return dates, and automatically calculates overdue fines. No external database or libraries are required — all data lives in memory for the duration of the session.

---

## Features

- **Book Management**
  - Add a book
  - Remove a book (blocked if it has an active loan)
  - List all books with availability status
  - Search books by title, author, or ID

- **Member Management**
  - Register a member
  - Remove a member (blocked if they have an active loan)
  - List all members
  - Search members by name or ID

- **Issue / Return**
  - Issue an available book to a registered member
  - Prevent issuing already-issued books
  - Return a book and close its loan
  - Automatic 14-day borrowing period
  - Overdue fine of **₹5 per day** beyond the due date

- **Loan Management**
  - List active loans
  - List all loans (active + completed)
  - Display issue date, due date, return date, and fine info

- **Robust Input Handling**
  - Duplicate book/member IDs rejected
  - Invalid numeric input re-prompted (no crashes)
  - Issuing nonexistent books/members rejected
  - Returning books not currently issued rejected
  - Removing books/members with active loans blocked

- **Demo Data**
  - On startup, a small set of demo books, members, and two active loans is loaded so the app can be tested immediately.

---

## Technologies

- Java 17+ (Standard Edition)
- No external libraries
- No external database (in-memory only)

---

## Project Structure

```
Library-Management-System/
├── src/
│   ├── Book.java       # Book entity (id, title, author, availability)
│   ├── Member.java     # Member entity (id, name, email)
│   ├── Loan.java       # Loan entity (issue date, due date, fine logic)
│   ├── Library.java    # Core service: collections + business logic
│   └── Main.java       # Console menu + user interaction
├── README.md
└── .gitignore
```

---

## OOP Concepts Demonstrated

| Concept | Where |
|---|---|
| Classes & Objects | `Book`, `Member`, `Loan`, `Library`, `Main` |
| Encapsulation | Private fields with public getters/setters in all entity classes |
| Constructors | Parameterized constructors in `Book`, `Member`, `Loan` |
| Composition | `Library` is composed of `HashMap`s of `Book`/`Member` and an `ArrayList` of `Loan` |
| Exception Handling | `IllegalArgumentException` / `IllegalStateException` with meaningful messages |
| Separation of Responsibilities | `Library` holds logic; `Main` handles I/O; entities hold data |

---

## Data Structures Used

| Structure | Purpose |
|---|---|
| `HashMap<Integer, Book>` | Fast lookup of books by ID |
| `HashMap<Integer, Member>` | Fast lookup of members by ID |
| `ArrayList<Loan>` | Ordered collection of all loan records |

---

## Java Concepts Demonstrated

- `HashMap` and `ArrayList` from the Collections Framework
- `Comparator` for sorting books/members
- **Streams** (`filter`, `sorted`, `collect`) for querying
- **Lambdas** in stream pipelines and `forEach` output
- `Optional` for safe lookup results
- `LocalDate` and `ChronoUnit` for date math
- `Collections` utilities
- `switch` expression with arrow (`->`) syntax
- Enhanced `for` / `forEach` iteration

---

## How to Compile

From the project root:

```bash
javac -d out src/*.java
```

This compiles all `.java` files in `src/` and places `.class` files in `out/`.

---

## How to Run

```bash
java -cp out Main
```

---

## Example Menu

```
==============================================
       LIBRARY MANAGEMENT SYSTEM
==============================================
  1.  Add Book
  2.  Register Member
  3.  List Books
  4.  List Members
  5.  Search Books
  6.  Search Members
  7.  Issue Book
  8.  Return Book
  9.  List Active Loans
  10. List All Loans
  11. Remove Book
  12. Remove Member
  13. Exit
==============================================
```

---

## Example Workflow

1. Start the app — demo books, members, and two active loans are loaded.
2. Choose **3. List Books** to see all books and their availability.
3. Choose **7. Issue Book**, enter Book ID `2` and Member ID `103`.
4. Choose **9. List Active Loans** — the new loan appears alongside the demo loans.
5. Choose **8. Return Book**, enter Book ID `1` (issued 20 days ago in demo data).
   - The loan closes and a fine of ₹30 is shown (6 overdue days × ₹5).
6. Choose **10. List All Loans** to see both active and completed loans.
7. Choose **13. Exit** to quit.

---

## Future Improvements

- Persist data to a file or database between sessions
- Add member login / authentication
- Support multiple copies of the same book
- Add book categories / genres and filter by them
- Generate fine payment receipts
- Add a GUI using JavaFX or Swing
- Export loan history to CSV

---

## Author

**Spandan1909** — [GitHub](https://github.com/Spandan1909)

## License

This project is free to use for educational purposes.
