import java.io.IOException;
import java.util.List;

public class DaySix {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DaySixInput.txt");

        String line = fileLines.get(0);

        boolean check;
        //4 for Part 1, 14 for Part 2
        int count = 14;

        char[] charArray = new char[count];
        for (int i = 0; i < count; i++) {
            charArray[i] = line.charAt(i);
        }

        check = true;
        for (int i = count; i < line.length(); i++) {
            for (int j = 0; j < count; j++) {
                for (int k = 0; k < count; k++) {
                    if (charArray[k] == charArray[j] && k != j) {
                        check = false;
                        break;
                    }
                }
                if (!check)
                    break;
            }
            if (check) {
                System.out.println(i);
                break;
            }
            check = true;

            for (int j = 1; j < count; j++) {
                charArray[j - 1] = charArray[j];
            }
            charArray[count - 1] = line.charAt(i);
        }

    }
}
