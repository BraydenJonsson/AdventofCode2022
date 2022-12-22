import java.io.IOException;
import java.util.*;

public class DayTwentyOne {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayTwentyOneInput.txt");

        ArrayList<Monkey> monkeys = new ArrayList<>(fileLines.size());
        String name;
        for (String line:fileLines) {
            name = line.substring(0,4);
            try {
                Long.parseLong(line.substring(6));
                monkeys.add(new Monkey(Monkey.monkeyType.NUMBER, name));
            } catch (NumberFormatException e) {
                monkeys.add(new Monkey(Monkey.monkeyType.FUNCTION, name));
            }
        }
        Monkey tempMonkey;
        String line;
        char tempChar;
        for (int i = 0; i < fileLines.size(); i++) {
            tempMonkey = monkeys.get(i);
            line = fileLines.get(i);
            switch(tempMonkey.type) {
                case NUMBER -> tempMonkey.number = Long.parseLong(line.substring(6));
                case FUNCTION -> {
                    tempChar = line.charAt(11);
                    switch(tempChar) {
                        case '+' -> tempMonkey.operationType = Monkey.OperationType.ADDITION;
                        case '-' -> tempMonkey.operationType = Monkey.OperationType.SUBTRACTION;
                        case '*' -> tempMonkey.operationType = Monkey.OperationType.MULTIPLICATION;
                        case '/' -> tempMonkey.operationType = Monkey.OperationType.DIVISION;
                    }
                    tempMonkey.firstTarget = Monkey.findMonkey(monkeys, line.substring(6, 10));
                    tempMonkey.firstTarget.parent = tempMonkey;
                    tempMonkey.secondTarget = Monkey.findMonkey(monkeys, line.substring(13));
                    tempMonkey.secondTarget.parent = tempMonkey;
                }
            }
        }

        //Part 1
//        System.out.println(Monkey.findMonkey(monkeys, "root").solve());

        //Part 2
        Monkey.findMonkey(monkeys, "root").operationType = Monkey.OperationType.EQUALS;
        Monkey.findMonkey(monkeys, "humn").type = Monkey.monkeyType.VARIABLE;

        System.out.println(Monkey.findMonkey(monkeys, "root").solveForHumn(monkeys));
    }

    private static class Monkey {
        public enum monkeyType {
            NUMBER,
            FUNCTION,

            VARIABLE
        }
        public monkeyType type;
        public long number;
        public Monkey firstTarget, secondTarget;
        public Monkey parent;
        public enum OperationType {
            ADDITION,
            SUBTRACTION,
            MULTIPLICATION,
            DIVISION,

            EQUALS
        }
        public OperationType operationType;

        public String name;

        Monkey(monkeyType type, String name) {
            this.type = type;
            this.name = name;
            this.parent = null;
            switch(type) {
                case FUNCTION -> {firstTarget = null; secondTarget = null; operationType = null;}
                case NUMBER -> number = 0;
            }
        }

        public long solveForHumn(ArrayList<Monkey> monkeys) {
            if (operationType != OperationType.EQUALS)
                throw new IllegalCallerException();

            Monkey humn = Monkey.findMonkey(monkeys, "humn");
            Stack<Monkey> parents = humn.findParentStack();

            parents.pop();
            long goal;

            if (this.firstTarget.equals(parents.peek()))
                goal = secondTarget.solve();
            else
                goal = firstTarget.solve();

            Monkey currentMonkey;
            while (!parents.isEmpty()) {
                currentMonkey = parents.pop();
                if (currentMonkey.name.equals("humn"))
                    return goal;
                if (currentMonkey.firstTarget.equals(parents.peek()))
                    goal = currentMonkey.invert(goal, true);
                else
                    goal = currentMonkey.invert(goal, false);
            }

            throw new IllegalStateException();
        }

        private long invert(long goal, boolean first) {
            long factor;
            if (first) {
                factor = secondTarget.solve();
                switch(operationType) {
                    case ADDITION -> {return goal - factor;}
                    case SUBTRACTION -> {return goal + factor;}
                    case MULTIPLICATION -> {return goal / factor;}
                    case DIVISION -> {return goal * factor;}
                }
            }
            else {
                factor = firstTarget.solve();
                switch(operationType) {
                    case ADDITION -> {return goal - factor;}
                    case SUBTRACTION -> {return factor - goal;}
                    case MULTIPLICATION -> {return goal / factor;}
                    case DIVISION -> {return factor / goal;}
                }
            }

            throw new IllegalStateException();
        }

        public long solve() {
            switch(type) {
                case FUNCTION -> {
                    switch(operationType) {
                        case ADDITION -> {return firstTarget.solve() + secondTarget.solve();}
                        case SUBTRACTION -> {return firstTarget.solve() - secondTarget.solve();}
                        case MULTIPLICATION -> {return firstTarget.solve() * secondTarget.solve();}
                        case DIVISION -> {return firstTarget.solve() / secondTarget.solve();}
                        case EQUALS -> {
                            if (firstTarget.solve() == secondTarget.solve())
                                return 1;
                            else
                                return 0;
                        }
                    }
                }
                case NUMBER -> {return number;}
            }
            throw new IllegalStateException();
        }

        public static Monkey findMonkey(List<Monkey> monkeys, String name) {
            for (Monkey monkey:monkeys) {
                if (monkey.name.equals(name))
                    return monkey;
            }
            return null;
        }

        @Override
        public String toString() {
            switch(type) {
                case NUMBER -> {return name + ": " + number;}
                case FUNCTION -> {
                    switch(operationType) {
                        case ADDITION -> {return name + ": " + firstTarget.name + " + " + secondTarget.name;}
                        case SUBTRACTION -> {return name + ": " + firstTarget.name + " - " + secondTarget.name;}
                        case MULTIPLICATION -> {return name + ": " + firstTarget.name + " * " + secondTarget.name;}
                        case DIVISION -> {return name + ": " + firstTarget.name + " / " + secondTarget.name;}
                        case EQUALS -> {return name + ": " + firstTarget.name + " = " + secondTarget.name;}
                    }
                }
                case VARIABLE -> {return name + ": ?";}
            }
            throw new IllegalStateException();
        }

        public void printParents() {
            if (Objects.equals(parent.name, "root"))
                return;
            System.out.println(parent);
            parent.printParents();
        }

        public Stack<Monkey> findParentStack() {
            return findParentStack(new Stack<>());
        }

        private Stack<Monkey> findParentStack(Stack<Monkey> monkeyStack) {
            monkeyStack.push(this);
            if (parent == null)
                return monkeyStack;
            return parent.findParentStack(monkeyStack);
        }
    }
}
