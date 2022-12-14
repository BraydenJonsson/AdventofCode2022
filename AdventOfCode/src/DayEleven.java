import java.io.IOException;
import java.util.*;

public class DayEleven {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayElevenInput.txt");


        //20 for Part 1, 10,000 for Part 2
        int rounds = 10000;

        ArrayList<Monkey> monkeys = new ArrayList<>();

        int test = 0, trueTarget = 0, falseTarget = 0, operationFactor;
        Monkey.OperationType operationType = Monkey.OperationType.NOTHING;

        String startingItems;
        int startingIndex, index;

        for (int i = 0; i < fileLines.size(); i += 7) {
            Queue<Long> items = new LinkedList<>();

            startingIndex = 0;
            startingItems = fileLines.get(i + 1).substring(18);
            while ((index = startingItems.indexOf(",", startingIndex)) != -1) {
                items.offer(Long.parseLong(startingItems.substring(startingIndex, index)));
                startingIndex = index + 2;
            }
            items.offer(Long.parseLong(startingItems.substring(startingIndex)));

            switch (fileLines.get(i + 2).charAt(23)) {
                case '+' -> operationType = Monkey.OperationType.ADDITION;
                case '-' -> operationType = Monkey.OperationType.SUBTRACTION;
                case '*' -> operationType = Monkey.OperationType.MULTIPLICATION;
                case '/' -> operationType = Monkey.OperationType.DIVISION;
            }
            try {
                operationFactor = Integer.parseInt(fileLines.get(i + 2).substring(25));
            } catch (NumberFormatException n) {
                operationType = Monkey.OperationType.SQUARE;
                operationFactor = 1;
            }

            for (int j = fileLines.get(i + 3).length() - 1; j >= 0; j--) {
                if (fileLines.get(i + 3).charAt(j) == ' ') {
                    test = Integer.parseInt(fileLines.get(i + 3).substring(j + 1));
                    break;
                }
            }

            for (int j = fileLines.get(i + 4).length() - 1; j >= 0; j--) {
                if (fileLines.get(i + 4).charAt(j) == ' ') {
                    trueTarget = Integer.parseInt(fileLines.get(i + 4).substring(j + 1));
                    break;
                }
            }

            for (int j = fileLines.get(i + 5).length() - 1; j >= 0; j--) {
                if (fileLines.get(i + 5).charAt(j) == ' ') {
                    falseTarget = Integer.parseInt(fileLines.get(i + 5).substring(j + 1));
                    break;
                }
            }

            monkeys.add(new Monkey(items, test, trueTarget, falseTarget, operationType, operationFactor));
        }

        // I 100% had to look this up. I tried literally everything, but I could not crack it.
        long supermodulo = 1;
        for (Monkey monkey : monkeys)
            supermodulo *= monkey.test;

        for (int i = 0; i < rounds; i++) {
            for (Monkey monkey : monkeys)
                monkey.throwItems(monkeys, supermodulo);
        }

        long largestItem = 0;
        long secondLargest = 0;

        for (Monkey monkey : monkeys) {
            if (largestItem < monkey.inspectionCount) {
                secondLargest = largestItem;
                largestItem = monkey.inspectionCount;
            } else if (secondLargest < monkey.inspectionCount) {
                secondLargest = monkey.inspectionCount;
            }
        }

        System.out.println(largestItem * secondLargest);
    }

    private static class Monkey {
        private final Queue<Long> items;

        public long inspectionCount;
        private final int test;
        private final int trueTarget, falseTarget;

        public enum OperationType {
            ADDITION,
            SUBTRACTION,
            MULTIPLICATION,
            DIVISION,
            SQUARE,
            NOTHING
        }

        private final OperationType operationType;

        private final int operationFactor;

        public Monkey(Queue<Long> items, int test, int trueTarget, int falseTarget, OperationType operationType, int operationFactor) {
            this.items = items;
            this.test = test;
            this.trueTarget = trueTarget;
            this.falseTarget = falseTarget;
            this.operationType = operationType;
            this.operationFactor = operationFactor;
            this.inspectionCount = 0L;
        }

        public void receive(Long item) {
            items.offer(item);
        }

        public void throwItems(ArrayList<Monkey> monkeys, long supermodulo){
            Long item;
            while (!items.isEmpty()) {
                item = items.poll();
                switch (operationType) {
                    case ADDITION -> item += operationFactor;
                    case SUBTRACTION -> item -= operationFactor;
                    case MULTIPLICATION -> item *= operationFactor;
                    case DIVISION -> item /= operationFactor;
                    case SQUARE -> item *= item;
                }

                inspectionCount++;
                //Part 1
//                item /= 3;

                //Part 2
                item = item % supermodulo;

                if (item % test == 0)
                    monkeys.get(trueTarget).receive(item);
                else
                    monkeys.get(falseTarget).receive(item);
            }
        }

        @Override
        public String toString() {
            return "Inspection Count: " + inspectionCount + ", Items: " + items.toString() + ", Test: Divisible by " + test + ", if True target " + trueTarget + ", else target " + falseTarget + ".";
        }
    }
}
