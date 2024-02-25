import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

// Süleyman Yolcu b2210765016
// This class represents the main entry point of the program
public class Main {
    // temp stands for temporary which I used it for storing the character that will be replaced
    public static char[][] board, firstBoard;
    public static int whiteBallRow, whiteBallCol, numRows, numCols, score;
    public static char temp;
    public static boolean gameover;

    // This method is the main entry point of the program
    public static void main(String[] args) {
        String boardFile = args[0];
        String moveFile = args[1];

        // Reads the input files
        String[] boardInputs = FileInput.readFile(boardFile, true, true);
        String[] moveInputs = FileInput.readFile(moveFile, true, true);
        String element = Objects.requireNonNull(moveInputs)[0].replaceAll("\\s", "");
        FileOutput.writeToFile("output.txt","",false,false);

        // Initializes the game board
        numRows = Objects.requireNonNull(boardInputs).length;
        numCols = boardInputs[0].split(" ").length;
        board = new char[numRows][numCols];
        score = 0;
        gameover = false;

        // Fills the game board with the values read from the input file
        for (int i = 0; i < numRows; i++) {
            String[] row = boardInputs[i].split(" ");
            for (int j = 0; j < numCols; j++) {
                board[i][j] = row[j].charAt(0);
            }
        }

        // Creates a copy of the game board to use for output at the end of the game
        firstBoard = new char[numRows][numCols];
        for (int i = 0; i < board.length; i++) {
            firstBoard[i] = board[i].clone();
        }
        char[] moves = element.toCharArray();
        ArrayList<Character> playedMoves = new ArrayList<>();
        findLocation();

        // Iterates over each move and updates the game board accordingly
        for (char c : moves) {
            moveWhiteBall(c);
            playedMoves.add(c);
            if (gameover) {
                break;
            }
        }

        // If the game is over, output the final game board, played moves, and score
        if (gameover) {
            findLocation();
            board[whiteBallRow][whiteBallCol] = 'H';
            FileOutput.writeToFile("output.txt", "Game board:", true, true);
            printBoard(firstBoard);
            FileOutput.writeToFile("output.txt", "\n" + "Your movement is:", true, true);
            String joinedString = playedMoves.stream().map(Object::toString).collect(Collectors.joining(" "));
            FileOutput.writeToFile("output.txt", joinedString, true, true);
            FileOutput.writeToFile("output.txt", "\n" + "Your output is:", true, true);
            printBoard(board);
            FileOutput.writeToFile("output.txt", "\n" + "Game Over!", true, true);
            FileOutput.writeToFile("output.txt", "Score: " + score, true, true);
        } else {
            FileOutput.writeToFile("output.txt", "Game board:", true, true);
            printBoard(firstBoard);
            FileOutput.writeToFile("output.txt", "\n" + "Your movement is:", true, true);
            FileOutput.writeToFile("output.txt", String.join(" ", moveInputs), true, true);
            FileOutput.writeToFile("output.txt", "\n" + "Your output is:", true, true);
            printBoard(board);
            FileOutput.writeToFile("output.txt", "\n" + "Score: " + score, true, true);
        }

    }

    // this method takes the way as parameter and moves the white ball accordingly
    public static void moveWhiteBall(char way) {
        switch (way) {
            case 'L':
                if (whiteBallCol == 0) {
                    temp = board[whiteBallRow][numCols - 1];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[whiteBallRow][numCols - 1] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = numCols - 1;
                    } else {
                        temp = board[whiteBallRow][whiteBallCol + 1];
                        tempCheck(temp);
                        board[whiteBallRow][whiteBallCol + 1] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = whiteBallCol + 1;
                    }
                } else {
                    temp = board[whiteBallRow][whiteBallCol - 1];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[whiteBallRow][whiteBallCol - 1] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = whiteBallCol - 1;
                    } else {
                        temp = board[whiteBallRow][(whiteBallCol + 1) % numCols];
                        tempCheck(temp);
                        board[whiteBallRow][(whiteBallCol + 1) % numCols] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = (whiteBallCol + 1) % numCols;
                    }
                }
                break;
            case 'R':
                if (whiteBallCol == numCols - 1) {
                    temp = board[whiteBallRow][0];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[whiteBallRow][0] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = 0;
                    } else {
                        temp = board[whiteBallRow][whiteBallCol - 1];
                        tempCheck(temp);
                        board[whiteBallRow][whiteBallCol - 1] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = whiteBallCol - 1;
                    }
                } else {
                    temp = board[whiteBallRow][whiteBallCol + 1];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[whiteBallRow][whiteBallCol + 1] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = whiteBallCol + 1;
                    } else {
                        temp = board[whiteBallRow][(whiteBallCol - 1 + numCols) % numCols];
                        tempCheck(temp);
                        board[whiteBallRow][(whiteBallCol - 1 + numCols) % numCols] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallCol = (whiteBallCol - 1 + numCols) % numCols;
                    }
                }
                break;
            case 'U':
                if (whiteBallRow == 0) {
                    temp = board[numRows - 1][whiteBallCol];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[numRows - 1][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = numRows - 1;
                    } else {
                        temp = board[whiteBallRow + 1][whiteBallCol];
                        tempCheck(temp);
                        board[whiteBallRow + 1][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = whiteBallRow + 1;
                    }
                } else {
                    temp = board[whiteBallRow - 1][whiteBallCol];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[whiteBallRow - 1][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = whiteBallRow - 1;
                    } else {
                        temp = board[(whiteBallRow + 1) % numRows][whiteBallCol];
                        tempCheck(temp);
                        board[(whiteBallRow + 1) % numRows][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = (whiteBallRow + 1) % numRows;
                    }
                }
                break;
            case 'D':
                if (whiteBallRow == numRows - 1) {
                    temp = board[0][whiteBallCol];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[0][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = 0;
                    } else {
                        temp = board[whiteBallRow - 1][whiteBallCol];
                        tempCheck(temp);
                        board[whiteBallRow - 1][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = whiteBallRow - 1;
                    }
                } else {
                    temp = board[whiteBallRow + 1][whiteBallCol];
                    if (temp != 'W') {
                        tempCheck(temp);
                        board[whiteBallRow + 1][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = whiteBallRow + 1;
                    } else {
                        temp = board[(whiteBallRow - 1 + numCols) % numCols][whiteBallCol];
                        tempCheck(temp);
                        board[(whiteBallRow - 1 + numRows) % numRows][whiteBallCol] = '*';
                        board[whiteBallRow][whiteBallCol] = temp;
                        whiteBallRow = (whiteBallRow - 1 + numCols) % numCols;
                    }
                }
                break;
        }
    }

    // this method checks the temporary variable and does necessary operations for the character to be replaced
    public static void tempCheck(char temporary) {
        if (temporary == 'R') {
            score = score + 10;
            temp = 'X';
        }
        if (temporary == 'Y') {
            score = score + 5;
            temp = 'X';
        }
        if (temporary == 'B') {
            score = score - 5;
            temp = 'X';
        }
        if (temporary == 'H') {
            temp = ' ';
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == '*') {
                        whiteBallRow = i;
                        whiteBallCol = j;
                    }
                }
            }
            board[whiteBallRow][whiteBallCol] = 'H';
            gameover = true;
        }
    }

    // this method takes a 2D array as parameter and prints it
    public static void printBoard(char[][] board) {
        for (char[] chars : board) {
            for (char aChar : chars) {
                FileOutput.writeToFile("output.txt", aChar + " ", true, false);
            }
            FileOutput.writeToFile("output.txt", "", true, true);
        }
    }

    // this method finds the location of the white ball and updates the corresponding variables
    public static void findLocation() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '*') {
                    whiteBallRow = i;
                    whiteBallCol = j;
                }
            }
        }
    }
}