import java.util.ArrayList;

public class Library {
    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Book> printedBooks = new ArrayList<>();
    public static ArrayList<Book> handwrittenBooks = new ArrayList<>();
    public static ArrayList<Book> readBooks = new ArrayList<>();
    public static ArrayList<Book> borrowedBooks = new ArrayList<>();
    public static ArrayList<Member> members = new ArrayList<>();
    public static ArrayList<Member> students = new ArrayList<>();
    public static ArrayList<Member> academics = new ArrayList<>();

    public static void getTheHistory() {
        for (Member member : members) {
            if (member.getType().equals("S")) {
                students.add(member);
            } else {
                academics.add(member);
            }
        }
        for (Book book : books) {
            if (book.getType().equals("P")) {
                printedBooks.add(book);
            } else {
                handwrittenBooks.add(book);
            }
        }
        System.out.println("History of library:\n");
        System.out.println("Number of students: " + students.size());
        for (Member student : students) {
            System.out.printf("Student [id: %d]%n", student.getId());
        }
        System.out.println("\nNumber of academics: " + academics.size());
        for (Member academic : academics) {
            System.out.printf("Academic [id: %d]%n", academic.getId());
        }
        System.out.println("\nNumber of printed books: " + printedBooks.size());
        for (Book printed : printedBooks) {
            System.out.printf("Printed [id: %d]%n", printed.getId());
        }
        System.out.println("\nNumber of handwritten books: " + handwrittenBooks.size());
        for (Book handwritten : handwrittenBooks) {
            System.out.printf("Handwritten [id: %d]%n", handwritten.getId());
        }
        System.out.println("\nNumber of borrowed books: " + borrowedBooks.size());
        for (Book borrowed : borrowedBooks) {
            System.out.printf("The book [%d] was borrowed by member [%d] at %s%n", borrowed.getId(), borrowed.getBorrowedBy(), borrowed.getBorrowedTime());
        }
        System.out.println("\nNumber of books read in library: " + readBooks.size());
        for (Book read : readBooks) {
            System.out.printf("The book [%d] was read in library by member [%d] at %s%n", read.getId(), read.getReadBy(), read.getReadTime());
        }
    }
    public static boolean idChecker(String bookId,String memberId){
        int bookID = Integer.parseInt(bookId);
        int memberID = Integer.parseInt(memberId);
        int memberSize = members.size();
        int bookSize = books.size();
        if(memberID>memberSize||bookID>bookSize){
            System.out.println("Invalid ID!");
            return false;
        }
        else {
            return true;
        }
    }
}
