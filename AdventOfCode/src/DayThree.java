import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayThree {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayThreeInput.txt");

        ArrayList<Character> characters = new ArrayList<>(fileLines.size());

        boolean check = true;

        //Part 1
//        String firstHalf, secondHalf;
//        for (String line:fileLines) {
//            firstHalf = line.substring(0, (line.length() / 2));
//            secondHalf = line.substring((line.length() / 2));
//            for (int i = 0; i < firstHalf.length(); i++) {
//                for (int j = 0; j < secondHalf.length(); j++) {
//                    if (firstHalf.charAt(i) == secondHalf.charAt(j)) {
//                        characters.add(firstHalf.charAt(i));
//                        check = false;
//                        break;
//                    }
//                }
//                if (!check)
//                    break;
//            }
//            check = true;
//        }

        //Part 2
        String line1, line2, line3;
        for (int i = 0; i < fileLines.size(); i += 3) {
            line1 = fileLines.get(i);
            line2 = fileLines.get(i + 1);
            line3 = fileLines.get(i + 2);
            for (int j = 0; j < line1.length(); j++) {
                for (int k = 0; k < line2.length(); k++) {
                    for (int l = 0; l < line3.length(); l++) {
                        if ((line1.charAt(j) == line2.charAt(k)) && (line2.charAt(k) == line3.charAt(l))) {
                            characters.add(line1.charAt(j));
                            check = false;
                            break;
                        }
                    }
                    if (!check)
                        break;
                }
                if (!check)
                    break;
            }
            check = true;
        }
        
        //Both Solutions
        int runningTotal = 0;
        int temporary;

        for (Character item:characters) {
            temporary = item;
            if (temporary < 95)
                runningTotal += temporary - 65 + 27;
            else
                runningTotal += temporary - 97 + 1;

        }

        System.out.println(runningTotal);
    }
}
