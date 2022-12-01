import java.io.IOException;
import java.util.List;

public class DayOne {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayOneTest.txt");

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
        //Running the check again in case a largest value is at the end of the file
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
        System.out.println(mostCalories);
        System.out.println(secondMostCalories);
        System.out.println(thirdMostCalories);
        System.out.println(mostCalories + secondMostCalories + thirdMostCalories);
    }
}
