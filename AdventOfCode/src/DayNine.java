import java.io.IOException;
import java.util.ArrayList;

public class DayNine {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayNineInput.txt");

        int size = 1501;
        int tailCount = 9;

        boolean[][] visited = new boolean[size][size];

        visited[size / 2][size / 2] = true;

        int[] head = new int[2];
        head[0] = size / 2;
        head[1] = size / 2;

        int[][] tails = new int[tailCount][2];
        for (int[] tail : tails) {
            tail[0] = size / 2;
            tail[1] = size / 2;
        }

        for (String line : fileLines) {
            for (int i = 0; i < Integer.parseInt(line.substring(2)); i++) {
                switch (line.charAt(0)) {
                    case 'R' -> head[0]++;
                    case 'L' -> head[0]--;
                    case 'U' -> head[1]++;
                    case 'D' -> head[1]--;
                }
                moveTails(head, tails, 0, tailCount);
                visited[tails[tailCount - 1][0]][tails[tailCount - 1][1]] = true;
            }
        }

        int runningTotal = 0;
        for (boolean[] booleans : visited) {
            for (boolean bool : booleans) {
                if (bool)
                    runningTotal++;
            }
        }

        System.out.println(runningTotal);
    }

    private static void moveTails(int[] head, int[][] tails, int index, int tailCount) {
        if (tailCount == index)
            return;

        if (!(Math.abs(head[0] - tails[index][0]) > 1 || Math.abs(head[1] - tails[index][1]) > 1))
            return;

        if ((Math.abs(head[1] - tails[index][1]) > Math.abs(head[0] - tails[index][0])))
            tails[index][0] = head[0];
        else if ((Math.abs(head[1] - tails[index][1]) < Math.abs(head[0] - tails[index][0])))
            tails[index][1] = head[1];


        tails[index][0] += (head[0] - tails[index][0]) / 2;
        tails[index][1] += (head[1] - tails[index][1]) / 2;
        moveTails(tails[index], tails, index + 1, tailCount);
    }

    private static void printLayout(int[] head, int[][] tails, int size) {
        System.out.println("Debug:");
        char[][] testArray = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                testArray[i][j] = '.';
        }

        testArray[head[0]][head[1]] = 'H';

        int iterator = 1;
        for (int[] tail : tails) {
            if (testArray[tail[0]][tail[1]] == '.')
                testArray[tail[0]][tail[1]] = String.valueOf(iterator++).charAt(0);
        }


        char[] tempArray = new char[size];
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++)
                tempArray[j] = testArray[j][i];
            System.out.println(new String(tempArray));
        }
    }
}