public class Member extends Library {
    private static int lastAssignedId;
    private int id;
    private String type;

    private int borrowedBooks;
    public Member(String type){
        setType(type);
        lastAssignedId++;
        setId(lastAssignedId);
        String temp = getType().equals("S") ? "Student":"Academic";
        System.out.printf("Created new member: %s [id: %d]%n",temp,id);
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
    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
