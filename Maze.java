import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.io.FileNotFoundException;

public class Maze {
    static char[][] globalMaze = null;

    public static void main(String[] args) {
        Scanner s = null;
        char[][] maze = new char[20][20];

        // read input
        try {
            s = new Scanner(new File("input.txt"));

            for (int i = 0; i < 20; i++) {
                String line = s.nextLine();
                for (int j = 0; j < 20; j++) {
                    maze[i][j] = line.charAt(j);
                }
            }

            globalMaze = shallowCopy(maze);

            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        // get user input
        s = new Scanner(System.in);

        String ans = "";
        do {
            // get user input
            int startRow = -1, startCol = -1;
            while (startRow < 0 || startRow > 19) {
                System.out.print("Enter row: ");
                startRow = s.nextInt();

                if (startRow < 0 || startRow > 19)
                    System.out.println("That row is out of bounds. Please try again.");
            }
            while (startCol < 0 || startCol > 19) {
                System.out.print("Enter col: ");
                startCol = s.nextInt();

                if (startCol < 0 || startCol > 19)
                    System.out.println("That column is out of bounds. Please try again.");
            }

            // search for exit path
            boolean pathFound = findPath(maze, startRow, startCol);

            System.out.print("\nResult:");
            if (pathFound)
                System.out.println(" I am free!");
            else
                System.out.println(" Help, I am trapped!");

            printMaze(maze);

            // prompt user to continue
            System.out.print("\nDo you want to continue? (yes/no): ");
            ans = s.next();

            // reset maze
            maze = shallowCopy(globalMaze);
        } while (ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y"));

        System.out.println("Thank you!");
    }

    public static boolean findPath(char maze[][], int startRow, int startCol) {
        if (findPath(maze, new boolean[maze.length][maze.length], startRow, startCol))
            return true;
        return false;
    }

    public static boolean findPath(char maze[][], boolean[][] visited, int row, int col) {
        if (maze[row][col] == 'E')
            return true;
        if (visited[row][col])
            return false;

        maze[row][col] = '+';
        visited[row][col] = true;

        Stack<int[]> stack = new Stack<>();

        // if valid square -> check square
        if (row - 1 >= 0 && maze[row - 1][col] != '1') // check up
            stack.push(new int[] { row - 1, col });
        if (row + 1 < maze.length && maze[row + 1][col] != '1') // check down
            stack.push(new int[] { row + 1, col });
        if (col - 1 >= 0 && maze[row][col - 1] != '1') // check left
            stack.push(new int[] { row, col - 1 });
        if (col + 1 < maze.length && maze[row][col + 1] != '1') // check right
            stack.push(new int[] { row, col + 1 });

        // check up, down, left, right while possible
        while (!stack.empty()) {
            int[] square = stack.pop();

            int newRow = square[0];
            int newCol = square[1];

            if (findPath(maze, visited, newRow, newCol))
                return true;
        }

        return false;
    }

    public static char[][] shallowCopy(char[][] array) {
        char[][] copy = new char[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                copy[i][j] = array[i][j];
            }
        }
        return copy;
    }

    public static void printMaze(char[][] maze) {
        System.out.print("   ");
        for (int i = 0; i < maze[0].length; i++) {
            System.out.print(String.format("%3d", i));
        }
        System.out.println();
        for (int i = 0; i < maze.length; i++) {
            System.out.print(String.format("%3d", i));
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(String.format("%3c", maze[i][j]));
            }
            System.out.println();
        }
    }
}