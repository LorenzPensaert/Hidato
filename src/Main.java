import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    private static int[][] board;
    private static int[] given, start;
    private static int current;

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int _numTasks = Integer.parseInt(br.readLine());
            for(int t = 0; t < _numTasks; t++) {
                current = t + 1;

                int _numRows = Integer.parseInt(br.readLine().split(" ")[1]);
                String[] input = new String[_numRows];
                for(int r = 0; r < _numRows; r++) {
                    input[r] = br.readLine();
                }

                setup(input);
                start = findLowestLocation();
                solve(start[0], start[1], given[0], 0);
                printBoard();

            }
        } catch (Exception ex) {
            System.err.println(ex);
        }


    }

    private static void setup(String[] input) {
        String[][] puzzle = new String[input.length][];
        for (int i = 0; i < input.length; i++)
            puzzle[i] = input[i].split(" ");

        int nCols = puzzle[0].length;
        int nRows = puzzle.length;

        List<Integer> list = new ArrayList<>(nRows * nCols);

        board = new int[nRows][nCols];
        for (int[] row : board)
            for (int c = 0; c < nCols; c++)
                row[c] = -1;

        for (int r = 0; r < nRows; r++) {
            String[] row = puzzle[r];
            for (int c = 0; c < nCols; c++) {
                String cell = row[c];
                switch (cell) {
                    case ".":
                        board[r][c] = 0;
                        break;
                    case "x":
                        break;
                    default:
                        int val = Integer.parseInt(cell);
                        board[r][c] = val;
                        list.add(val);
                }
            }
        }
        Collections.sort(list);
        given = new int[list.size()];
        for (int i = 0; i < given.length; i++)
            given[i] = list.get(i);
    }

    private static boolean solve(int r, int c, int n, int next) {
        if(!(r >= 0 && r < board.length && c >= 0 && c < board[0].length))
            return false;

        // Dit mag geen end statement zijn, maar wat dan wel?
        if (n > given[given.length - 1])
            return true;

        if (board[r][c] != 0 && board[r][c] != n)
            return false;

        if (board[r][c] == 0 && given[next] == n)
            return false;

        int back = board[r][c];
        if (back == n)
            next++;

        board[r][c] = n;
        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if (!(i == 0 && j == 0) && solve(r + i, c + j, n + 1, next))
                    return true;

        board[r][c] = back;
        return false;
    }

    private static int[] findLowestLocation () {
        int r = -1, c = -1, lowest = 10000000;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(lowest > board[i][j] && board[i][j] > 0) {
                    lowest = board[i][j];
                    r = i;
                    c = j;
                }
            }
        }

        return new int[] {r, c};
    }

    private static void printBoard() {
        for (int[] row : board) {
            System.out.print(current);
            for (int c : row) {
                if (c == -1)
                    System.out.print(" x");
                else
                    System.out.printf(c > 0 ? " %1d" : " .", c);
            }
            System.out.println();
        }
    }
}
