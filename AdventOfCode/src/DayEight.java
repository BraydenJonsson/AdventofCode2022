import java.io.IOException;
import java.util.ArrayList;

public class DayEight {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayEightInput.txt");

        int[][] trees = new int[fileLines.size()][fileLines.get(0).length()];

        // Part 1
//        boolean[][] visible = new boolean[fileLines.size()][fileLines.get(0).length()];
//        for (int lineIndex = 0; lineIndex < fileLines.size(); lineIndex++) {
//            for (int stringIndex = 0; stringIndex < fileLines.get(0).length(); stringIndex++) {
//                if (lineIndex == 0 || stringIndex == 0 || lineIndex == fileLines.size() - 1 || stringIndex == fileLines.get(0).length() - 1)
//                    visible[lineIndex][stringIndex] = true;
//                trees[lineIndex][stringIndex] = Integer.parseInt(fileLines.get(lineIndex).substring(stringIndex, stringIndex + 1));
//            }
//        }
//
//        int tallestTree = 0;
//
//        for (int lineIndex = 0; lineIndex < visible.length; lineIndex++) {
//            for (int stringIndex = 1; stringIndex < visible[lineIndex].length; stringIndex++) {
//                tallestTree = Math.max(tallestTree, trees[lineIndex][stringIndex - 1]);
//                if (trees[lineIndex][stringIndex] > tallestTree)
//                    visible[lineIndex][stringIndex] = true;
//            }
//            tallestTree = 0;
//        }
//
//        for (int lineIndex = 0; lineIndex < visible.length; lineIndex++) {
//            for (int stringIndex = visible[lineIndex].length - 2; stringIndex >= 0; stringIndex--) {
//                tallestTree = Math.max(tallestTree, trees[lineIndex][stringIndex + 1]);
//                if (trees[lineIndex][stringIndex] > tallestTree)
//                    visible[lineIndex][stringIndex] = true;
//            }
//            tallestTree = 0;
//        }
//
//        for (int stringIndex = 0; stringIndex < visible[0].length; stringIndex++) {
//            for (int lineIndex = 1; lineIndex < visible.length; lineIndex++) {
//                tallestTree = Math.max(tallestTree, trees[lineIndex - 1][stringIndex]);
//                if (trees[lineIndex][stringIndex] > tallestTree)
//                    visible[lineIndex][stringIndex] = true;
//            }
//            tallestTree = 0;
//        }
//
//        for (int stringIndex = 0; stringIndex < visible[0].length; stringIndex++) {
//            for (int lineIndex = visible.length - 2; lineIndex >= 0; lineIndex--) {
//                tallestTree = Math.max(tallestTree, trees[lineIndex + 1][stringIndex]);
//                if (trees[lineIndex][stringIndex] > tallestTree)
//                    visible[lineIndex][stringIndex] = true;
//            }
//            tallestTree = 0;
//        }
//
//        int runningTotal = 0;
//
//        for (boolean[] boolList:visible) {
//            for (boolean bool:boolList) {
//                if (bool)
//                    runningTotal++;
//            }
//        }
//
//        System.out.println(runningTotal);

        for (int lineIndex = 0; lineIndex < trees.length; lineIndex++) {
            for (int stringIndex = 0; stringIndex < trees[0].length; stringIndex++) {
                trees[lineIndex][stringIndex] = Integer.parseInt(fileLines.get(lineIndex).substring(stringIndex, stringIndex + 1));
            }
        }

        int highestScenicScore = 0;
        int runningTotal = 0;
        int tallestTree = 0;
        int candidateScenicScore = 1;
        for (int lineIndex = 0; lineIndex < trees.length; lineIndex++) {
            for (int stringIndex = 0; stringIndex < trees[0].length; stringIndex++) {

                if (stringIndex == 0 || lineIndex == 0 || stringIndex == trees[0].length - 1 || lineIndex == trees.length - 1)
                    candidateScenicScore = 0;

                for (int i = stringIndex + 1; i < trees[0].length; i++) {
                    tallestTree = Math.max(tallestTree, trees[lineIndex][i]);
                    if (trees[lineIndex][stringIndex] <= tallestTree) {
                        runningTotal += i - stringIndex;
                        break;
                    }
                }
                if (runningTotal == 0)
                    runningTotal = trees[0].length - stringIndex - 1;
                candidateScenicScore *= runningTotal;
                runningTotal = 0;
                tallestTree = 0;

                for (int i = stringIndex - 1; i >= 0; i--) {
                    tallestTree = Math.max(tallestTree, trees[lineIndex][i]);
                    if (trees[lineIndex][stringIndex] <= tallestTree) {
                        runningTotal += stringIndex - i;
                        break;
                    }
                }
                if (runningTotal == 0)
                    runningTotal = stringIndex;
                candidateScenicScore *= runningTotal;
                runningTotal = 0;
                tallestTree = 0;

                for (int i = lineIndex + 1; i < trees.length; i++) {
                    tallestTree = Math.max(tallestTree, trees[i][stringIndex]);
                    if (trees[lineIndex][stringIndex] <= tallestTree) {
                        runningTotal += i - lineIndex;
                        break;
                    }
                }
                if (runningTotal == 0)
                    runningTotal = trees.length - lineIndex - 1;
                candidateScenicScore *= runningTotal;
                runningTotal = 0;
                tallestTree = 0;

                for (int i = lineIndex - 1; i >= 0; i--) {
                    tallestTree = Math.max(tallestTree, trees[i][stringIndex]);
                    if (trees[lineIndex][stringIndex] <= tallestTree) {
                        runningTotal += lineIndex - i;
                        break;
                    }
                }
                if (runningTotal == 0)
                    runningTotal = lineIndex;
                candidateScenicScore *= runningTotal;
                runningTotal = 0;
                tallestTree = 0;

                if (candidateScenicScore > highestScenicScore)
                    highestScenicScore = candidateScenicScore;
                candidateScenicScore = 1;
            }
        }

        System.out.println(highestScenicScore);
    }
}