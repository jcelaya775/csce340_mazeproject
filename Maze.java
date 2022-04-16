import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.io.FileNotFoundException;

public class Maze {
    public static void main(String[] args) {
        Scanner s = null;
        char[][] maze = new char[20][20];

        try {
            s = new Scanner(new File("input.txt"));

            // read input
            for (int i = 0; i < 20; i++) {
                String line = s.nextLine();
                for (int j = 0; j < 20; j++) {
                    maze[i][j] = line.charAt(j);
                }
            }

            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        // get user input
        s = new Scanner(System.in);

        System.out.print("Enter row: ");
        int startRow = s.nextInt();
        System.out.print("Enter col: ");
        int startCol = s.nextInt();
        System.out.println();

        // search for exit path
        boolean pathFound = findPath(maze, startRow, startCol);
        if (pathFound)
            System.out.println("I am free!");
        else
            System.out.println("Help, I am trapped!");

        System.out.println("\nResult:");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
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
}