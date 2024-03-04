import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Main {
    public static String[] inputs;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) throws FileNotFoundException {
        inputs = FileInput.readFile(args[0], true, true);
        FileOutput.writeToFile(args[1]);
        for (String input : Objects.requireNonNull(inputs)) {
            String[] line = input.split("\t");
            String command = line[0];
            switch (command) {
                case "addBook":
                    if(Library.books.size()<999999){
                        Library.books.add(new Book(line[1]));
                    }
                    break;
                case "addMember":
                    if(Library.members.size()<999999){
                        Library.members.add(new Member(line[1]));
                    }
                    break;
                case "borrowBook":
                    if(Library.idChecker(line[1],line[2])){
                        Library.books.get(Integer.parseInt(line[1]) - 1).borrowBook(line[1], line[2], LocalDate.parse(line[3], formatter));
                    }
                    break;
                case "extendBook":
                    if(Library.idChecker(line[1],line[2])) {
                        Library.books.get(Integer.parseInt(line[1]) - 1).extendBook(line[1], line[2], LocalDate.parse(line[3], formatter));
                    }
                    break;
                case "readInLibrary":
                    if(Library.idChecker(line[1],line[2])) {
                        Library.books.get(Integer.parseInt(line[1]) - 1).readInLibrary(line[1], line[2], LocalDate.parse(line[3], formatter));
                    }
                    break;
                case "returnBook":
                    if(Library.idChecker(line[1],line[2])) {
                        Library.books.get(Integer.parseInt(line[1]) - 1).returnBook(line[1], line[2], LocalDate.parse(line[3], formatter));
                    }
                    break;
                case "getTheHistory":
                    Library.getTheHistory();
                    break;
                default:
                    System.out.println("Erroneous command!");
                    break;
            }
        }
        FileOutput.close();
    }
}