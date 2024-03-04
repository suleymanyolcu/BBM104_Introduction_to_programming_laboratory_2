import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * A class that redirects console output to a file.
 */
public class FileOutput {
    private static PrintStream printStream;

    /**
     * Creates a new instance of the FileOutput class that redirects console output to a file.
     * @param fileName The name of the file to write the output to.
     * @throws FileNotFoundException If the file cannot be created or opened for writing.
     */
    public static void writeToFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        printStream = new PrintStream(file);
        System.setOut(printStream);
    }

    /**
     * Closes the file and restores the original console output.
     */
    public static void close() {
        printStream.close();
    }
}
