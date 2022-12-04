import java.io.IOException;
import java.util.List;

public class DayFour {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayFourInput.txt");

        int pair1Start = 0, pair1End = 0, pair2Start = 0, pair2End = 0;
        int counter, savedIndex = 0;
        boolean oneContainsTwo, twoContainsOne;
        int runningTotal = 0;
        for(String line:fileLines) {
            counter = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '-' || line.charAt(i) == ',') {
                    switch (counter) {
                        case 0 -> {
                            pair1Start = Integer.parseInt(line.substring(0, i));
                            savedIndex = i + 1;
                            counter++;
                        }
                        case 1 -> {
                            pair1End = Integer.parseInt(line.substring(savedIndex, i));
                            savedIndex = i + 1;
                            counter++;
                        }
                        case 2 -> {
                            pair2Start = Integer.parseInt(line.substring(savedIndex, i));
                            savedIndex = i + 1;
                            counter++;
                        }
                    }
                } else if (counter == 3) {
                    pair2End = Integer.parseInt(line.substring(savedIndex));
                    break;
                }
            }

            //Part 1
//            oneContainsTwo = (pair1Start <= pair2Start) && (pair1End >= pair2End);
//            twoContainsOne = (pair2Start <= pair1Start) && (pair2End >= pair1End);

            //Part 2
            oneContainsTwo = ((pair1Start <= pair2Start) && (pair1End >= pair2Start)) || ((pair1Start <= pair2End) && (pair1End >= pair2End));
            twoContainsOne = ((pair2Start <= pair1Start) && (pair2End >= pair1Start)) || ((pair2Start <= pair1End) && (pair2End >= pair1End));

            if (oneContainsTwo || twoContainsOne)
                runningTotal++;
        }

        System.out.println(runningTotal);
    }
}
