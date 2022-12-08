import java.io.IOException;
import java.util.ArrayList;

public class DaySeven {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DaySevenInput.txt");

        int runningTotal = 0, temp;
        // Part 2
        runningTotal = Integer.MAX_VALUE;
        temp = getTotalSpace(fileLines);
        int deleteRequirement = temp - 40_000_000;

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            if (line.equals("$ ls")) {
                temp = checkDirectory(fileLines, i);
                // Part 1
//                if (temp <= 100_000)
//                    runningTotal += temp;
                // Part 2
                if (temp >= deleteRequirement)
                    runningTotal = Math.min(temp, runningTotal);
            }
        }

        System.out.println("Too High: " + 5861103);
        System.out.println(runningTotal);
        System.out.println("Too Low: " + 4309);
    }

    private static int checkDirectory(ArrayList<String> fileLines, int index) {
        int runningTotal = 0;
        String temp;
        for (int i = index + 1; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            if (line.startsWith("$"))
                break;
            else if (line.startsWith("d")) {
                temp = "$ cd " + line.substring(4);
                runningTotal += checkDirectory(fileLines, indexOfHelper(fileLines, i, temp) + 1);
            } else {
                runningTotal += Integer.parseInt(line.substring(0, line.indexOf(' ')));
            }
        }
        return runningTotal;
    }

    private static int indexOfHelper(ArrayList<String> fileLines, int startingIndex, String temp) {
        int directoryHelper = 0;
        for (int i = startingIndex; i < fileLines.size(); i++) {
            if (fileLines.get(i).equals(temp) && directoryHelper == 0)
                return i;
            else if (fileLines.get(i).equals("$ cd .."))
                directoryHelper--;
            else if (fileLines.get(i).startsWith("$ cd"))
                directoryHelper++;
        }
        throw new IllegalStateException();
    }

    private static int getTotalSpace(ArrayList<String> fileLines) {
        int runningTotal = 0;
        for (String line:fileLines) {
            if (line.startsWith("$") || line.startsWith("d"))
                continue;
            runningTotal += Integer.parseInt(line.substring(0, line.indexOf(' ')));
        }
        return runningTotal;
    }
}