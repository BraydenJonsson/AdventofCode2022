import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class DayFive {
    public static void main(String[] args) throws IOException {
        List<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayFiveInput.txt");

        int separatorLine = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            if (fileLines.get(i).equals("")) {
                separatorLine = i;
                break;
            }
        }

        String numberLine = fileLines.get(separatorLine - 1);
        int largestNumber = 0;
        for (int i = 0; i < numberLine.length(); i++)  {
            try {
                largestNumber = Integer.parseInt(String.valueOf(numberLine.charAt(i)));
            } catch (NumberFormatException ignored) {}
        }

        Stack<String>[] stackArray = new Stack[largestNumber];
        for (int i = 0; i < largestNumber; i++)
            stackArray[i] = new Stack<>();

        String tempString;
        for (int i = separatorLine - 2; i >= 0; i--) {
            for (int j = 0; j < largestNumber; j++) {
                try {
                    tempString = fileLines.get(i).substring(j * 4, j * 4 + 3);
                } catch (StringIndexOutOfBoundsException e) {
                    break;
                }
                if (tempString.matches("\\[.\\]"))
                    stackArray[j].push(tempString);
            }
        }

        StringBuilder line;
        //Part 2
        Stack<String> tempStack = new Stack<>();
        int endIndex, count, source, target;
        for (int i = separatorLine + 1; i < fileLines.size(); i++) {
            line = new StringBuilder(fileLines.get(i));
            line.delete(0,5);

            endIndex = line.indexOf(" ");
            tempString = line.substring(0, endIndex);
            count = Integer.parseInt(tempString);
            line.delete(0, 6 + tempString.length());

            endIndex = line.indexOf(" ");
            tempString = line.substring(0, endIndex);
            source = Integer.parseInt(tempString);
            line.delete(0, 4 + tempString.length());

            target = Integer.parseInt(line.substring(0));

            source--;
            target--;

            for (int j = 0; j < count; j++) {
                //Part 1
//                stackArray[target].push(stackArray[source].pop());
                //Part 2
                tempStack.push(stackArray[source].pop());
            }
            //Part 2
            while(!tempStack.isEmpty()) {
                stackArray[target].push(tempStack.pop());
            }
        }

        StringBuilder output = new StringBuilder(largestNumber);
        for (Stack<String> stack:stackArray) {
            output.append(stack.peek().charAt(1));
        }

        System.out.println(output);
    }
}
