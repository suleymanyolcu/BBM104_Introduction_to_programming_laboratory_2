import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book extends Library {
    private static int lastAssignedId;
    private int id;
    private String type;
    private LocalDate borrowedTime;
    private LocalDate readTime;
    private LocalDate deadline;
    private int borrowedBy;
    private int readBy;
    boolean status = true;
    boolean isExtended;

    public Book(String type) {
        setType(type);
        lastAssignedId++;
        setId(lastAssignedId);
        String temp = type.equals("P") ? "Printed" : "Handwritten";
        System.out.printf("Created new book: %s [id: %d]%n", temp, id);
    }

    public void borrowBook(String bookID, String memberID, LocalDate time) {
        int bookIndex = Integer.parseInt(bookID) - 1;
        int memberIndex = Integer.parseInt(memberID) - 1;
        if (status && type.equals("P")) {
            if (members.get(memberIndex).getType().equals("A") && members.get(memberIndex).getBorrowedBooks() < 4 || members.get(memberIndex).getType().equals("S") && members.get(memberIndex).getBorrowedBooks() < 2) {
                borrowedTime = time;
                status = false;
                deadline = (members.get(memberIndex).getType().equals("S")) ? borrowedTime.plusWeeks(1) : borrowedTime.plusWeeks(2);
                members.get(memberIndex).setBorrowedBooks(members.get(memberIndex).getBorrowedBooks() + 1);
                System.out.printf("The book [%s] was borrowed by member [%s] at %s%n", bookID, memberID, time);
                borrowedBooks.add(books.get(bookIndex));
                borrowedBy = memberIndex + 1;
            } else {
                System.out.println("You have exceeded the borrowing limit!");
            }
        } else {
            System.out.println("You cannot borrow this book!");
        }
    }

    public void extendBook(String bookID, String memberID, LocalDate time) {
        int memberIndex = Integer.parseInt(memberID) - 1;
        if (!isExtended) {
            deadline = (members.get(memberIndex).getType().equals("S")) ? deadline.plusWeeks(1) : deadline.plusWeeks(2);
            isExtended = true;
            System.out.printf("The deadline of book [%s] was extended by member [%s] at %s%n", bookID, memberID, time);
            System.out.printf("New deadline of book [%s] is %s%n", bookID, deadline);
        } else {
            System.out.println("You cannot extend the deadline!");
        }
    }

    public void readInLibrary(String bookID, String memberID, LocalDate time) {
        int bookIndex = Integer.parseInt(bookID) - 1;
        int memberIndex = Integer.parseInt(memberID) - 1;
        if (status) {
            if (members.get(memberIndex).getType().equals("S") && type.equals("H")) {
                System.out.println("Students can not read handwritten books!");
            } else {
                status = false;
                System.out.printf("The book [%s] was read in library by member [%s] at %s%n", bookID, memberID, time);
                readTime = time;
                readBooks.add(books.get(bookIndex));
                readBy = memberIndex + 1;
            }
        } else {
            System.out.println("You can not read this book!");
        }
    }

    public void returnBook(String bookID, String memberID, LocalDate time) {
        int bookIndex = Integer.parseInt(bookID) - 1;
        if (!status) {
            status = true;
            int fee;
            if (deadline != null && time.isAfter(deadline)) {
                long between = ChronoUnit.DAYS.between(deadline, time);
                fee = Integer.parseInt(String.valueOf(between));
            } else {
                fee = 0;
            }
            System.out.printf("The book [%s] was returned by member [%s] at %s Fee: %d%n", bookID, memberID, time, fee);
            borrowedBooks.remove(books.get(bookIndex));
            readBooks.remove(books.get(bookIndex));
        } else {
            System.out.println("You can not return this book!");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorrowedBy() {
        return borrowedBy;
    }

    public LocalDate getBorrowedTime() {
        return borrowedTime;
    }

    public LocalDate getReadTime() {
        return readTime;
    }

    public int getReadBy() {
        return readBy;
    }
}


