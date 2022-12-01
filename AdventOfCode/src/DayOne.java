import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayOne {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = readFileLines("AdventOfCode/TextFiles/DayOneInput.txt");

        int mostCalories = 0;
        int secondMostCalories = 0;
        int thirdMostCalories = 0;
        int runningTotal = 0;

        for (String line:fileLines) {
            if (line.equals("\n") || line.equals("")) {
                if (mostCalories <= runningTotal) {
                    thirdMostCalories = secondMostCalories;
                    secondMostCalories = mostCalories;
                    mostCalories = runningTotal;
                } else if (secondMostCalories <= runningTotal) {
                    thirdMostCalories = secondMostCalories;
                    secondMostCalories = runningTotal;
                } else if (thirdMostCalories <= runningTotal) {
                    thirdMostCalories = runningTotal;
                }
                runningTotal = 0;
            } else {
                runningTotal += Integer.parseInt(line);
            }
        }
        System.out.println(mostCalories);
        System.out.println(secondMostCalories);
        System.out.println(thirdMostCalories);
        System.out.println(mostCalories + secondMostCalories + thirdMostCalories);
    }

    private static List<String> readFileLines(String fileName) throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader(fileName));

        ArrayList<String> lines = new ArrayList<>();

        String line = bufReader.readLine();
        while (line != null) {
            lines.add(line);
            line = bufReader.readLine();
        }

        bufReader.close();

        return lines;
    }
}
