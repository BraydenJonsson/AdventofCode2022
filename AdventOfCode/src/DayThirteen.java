import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DayThirteen {
    public static void main(String[] args) throws IOException, ParseException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayThirteenInput.txt");
        //Part 1
        int runningTotal = 0;
        JSONParser parser = new JSONParser();
        for (int pair = 0; pair + 1 < fileLines.size(); pair += 3) {
            JSONArray leftArray = (JSONArray) parser.parse(fileLines.get(pair));
            JSONArray rightArray = (JSONArray) parser.parse(fileLines.get(pair + 1));

            if(compareLists(leftArray, rightArray) < 1)
                runningTotal += pair/3 + 1;
        }

        System.out.println(runningTotal);

        //Part 2
//        for (int i = 0; i < fileLines.size(); i++) {
//            if (fileLines.get(i).equals(""))
//                fileLines.remove(i);
//        }
//
//        JSONParser parser = new JSONParser();
//        ArrayList<JSONArray> jsonArrays = new ArrayList<>(fileLines.size());
//        for (String line:fileLines)
//            jsonArrays.add((JSONArray) parser.parse(line));
//
//        JSONArray firstDivider = (JSONArray) parser.parse("[[2]]");
//        JSONArray secondDivider = (JSONArray) parser.parse("[[6]]");
//        jsonArrays.add(firstDivider);
//        jsonArrays.add(secondDivider);
//
//        jsonArrays.sort(DayThirteen::compareLists);
//
//        System.out.println((jsonArrays.indexOf(firstDivider) + 1) * (jsonArrays.indexOf(secondDivider) + 1));
    }

    private static int compareLists(JSONArray leftArray, JSONArray rightArray) {
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < leftArray.size() && rightIndex < rightArray.size()) {
            if (leftArray.get(leftIndex).getClass() == Long.class && rightArray.get(rightIndex).getClass() == Long.class) {
                if ((Long) leftArray.get(leftIndex) - (Long)rightArray.get(rightIndex) < 0)
                    return -1;
                else if ((Long) leftArray.get(leftIndex) - (Long)rightArray.get(rightIndex) > 0)
                    return 1;
            } else if ((leftArray.get(leftIndex).getClass() == JSONArray.class && rightArray.get(rightIndex).getClass() == JSONArray.class)) {
                switch (compareSubLists((JSONArray)leftArray.get(leftIndex), (JSONArray)rightArray.get(rightIndex))) {
                    case 0 -> {return 1;}
                    case 1 -> {return -1;}
                }
            } else if ((leftArray.get(leftIndex).getClass() == JSONArray.class)) {
                JSONArray tempArray = new JSONArray();
                tempArray.add(rightArray.get(rightIndex));
                switch (compareSubLists((JSONArray)leftArray.get(leftIndex), tempArray)) {
                    case 0 -> {return 1;}
                    case 1 -> {return -1;}
                }
            } else {
                JSONArray tempArray = new JSONArray();
                tempArray.add(leftArray.get(leftIndex));
                switch (compareSubLists(tempArray, (JSONArray)rightArray.get(rightIndex))) {
                    case 0 -> {return 1;}
                    case 1 -> {return -1;}
                }
            }
            leftIndex++;
            rightIndex++;
        }
        return leftArray.size() - rightArray.size();
    }

    private static int compareSubLists(JSONArray leftArray, JSONArray rightArray) {
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < leftArray.size() && rightIndex < rightArray.size()) {
            if (leftArray.get(leftIndex).getClass() == Long.class && rightArray.get(rightIndex).getClass() == Long.class) {
                if ((Long) leftArray.get(leftIndex) - (Long)rightArray.get(rightIndex) < 0)
                    return 1;
                else if ((Long) leftArray.get(leftIndex) - (Long)rightArray.get(rightIndex) > 0)
                    return 0;
            } else if ((leftArray.get(leftIndex).getClass() == JSONArray.class && rightArray.get(rightIndex).getClass() == JSONArray.class)) {
                switch (compareSubLists((JSONArray)leftArray.get(leftIndex), (JSONArray)rightArray.get(rightIndex))) {
                    case 0 -> {return 0;}
                    case 1 -> {return 1;}
                }
            } else if ((leftArray.get(leftIndex).getClass() == JSONArray.class)) {
                JSONArray tempArray = new JSONArray();
                tempArray.add(rightArray.get(rightIndex));
                switch (compareSubLists((JSONArray)leftArray.get(leftIndex), tempArray)) {
                    case 0 -> {return 0;}
                    case 1 -> {return 1;}
                }
            } else {
                JSONArray tempArray = new JSONArray();
                tempArray.add(leftArray.get(leftIndex));
                switch (compareSubLists(tempArray, (JSONArray)rightArray.get(rightIndex))) {
                    case 0 -> {return 0;}
                    case 1 -> {return 1;}
                }
            }
            leftIndex++;
            rightIndex++;
        }
        if (leftArray.size() > rightArray.size())
            return 0;
        else if (leftArray.size() < rightArray.size())
            return 1;
        else
            return 2;
    }
}
