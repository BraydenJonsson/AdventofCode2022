import java.io.IOException;
import java.util.*;

public class DayTen {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayTenInput.txt");

        int line = 0, cycle = 0, register = 1, runningTotal = 0;

        boolean middleOfAdd = false;

        StringBuilder crt = new StringBuilder(275);

        while (line < fileLines.size()) {
            cycle++;

            if (cycle == 20 || ((cycle - 20) % 40 == 0 && cycle <= 220))
                runningTotal += register * cycle;

            if (Math.abs(((cycle - 1) % 40) - register) <= 1)
                crt.append('#');
            else
                crt.append('.');
            if (cycle % 40 == 0)
                crt.append('\n');

            if (fileLines.get(line).startsWith("addx ")) {
                if (!middleOfAdd) {
                    middleOfAdd = true;
                }
                else {
                    register += Integer.parseInt(fileLines.get(line).substring(5));
                    middleOfAdd = false;
                }
            }


            if (!middleOfAdd)
                line++;
        }
        System.out.println(runningTotal);
        System.out.println(crt);
    }
}
