import java.io.IOException;
import java.util.List;

public class DayTwo {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayTwoInput.txt");

        int totalScore = 0;

        for (String line : fileLines) {
//            if (line.matches("(A Y)|(B Z)|(C X)")) {
//                totalScore += 6;
//            } else if (line.matches("(A X)|(B Y)|(C Z)")) {
//                totalScore += 3;
//            }
//
//            if (line.charAt(2) == 'X')
//                totalScore++;
//            else if (line.charAt(2) == 'Y')
//                totalScore += 2;
//            else
//                totalScore += 3;
            if (line.charAt(2) == 'X') {
                if (line.charAt(0) == 'A')
                    totalScore += 3;
                else if (line.charAt(0) == 'B')
                    totalScore += 1;
                else
                    totalScore += 2;
            }
            else if (line.charAt(2) == 'Z') {
                if (line.charAt(0) == 'A')
                    totalScore += 2 + 6;
                else if (line.charAt(0) == 'B')
                    totalScore += 3 + 6;
                else
                    totalScore += 1 + 6;
            }
            else {
                if (line.charAt(0) == 'A')
                    totalScore += 1 + 3;
                else if (line.charAt(0) == 'B')
                    totalScore += 2 + 3;
                else
                    totalScore += 3 + 3;
            }

        }

        System.out.println(totalScore);
    }
}
