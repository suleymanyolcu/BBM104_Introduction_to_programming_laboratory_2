/**
 * The Main class is responsible for starting the Smart Home application and redirecting the standard output to a file.
 * The Smart Home application is started by calling the SmartHomeController method and passing in a configuration file path as an argument.
 * The standard output is redirected to a file specified by the second command-line argument.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
    /**
     * The main method is the entry point of the application. It takes in two command-line arguments:
     * 1. The path to the configuration file for the Smart Home application
     * 2. The path to the file where the output should be redirected
     * The method creates a new PrintStream object with the given output file path and sets it as the standard output stream.
     * It then calls the SmartHomeController method of the SmartHome class, passing in the configuration file path as an argument.
     * Finally, it closes the PrintStream object.
     *
     * @param args The command-line arguments passed to the application.
     * @throws FileNotFoundException if the output file cannot be created or opened.
     */
    public static void main(String[] args) throws FileNotFoundException {
        File outputFile = new File(args[1]);
        PrintStream printStream = new PrintStream(outputFile);
        System.setOut(printStream);
        SmartHome.SmartHomeController(args[0]);
        printStream.close();
    }
}