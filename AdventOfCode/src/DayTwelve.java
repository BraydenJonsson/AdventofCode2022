import java.io.IOException;
import java.util.*;

public class DayTwelve {
    public static void main(String[] args) throws IOException {
        ArrayList<String> fileLines = CommonFunctions.readFileLines("AdventOfCode/TextFiles/DayTwelveInput.txt");

        HashMap<Character, Integer> translator = new HashMap<>(28);
        char letter = 'a';
        for (int i = 0; i < 26; i++)
            translator.put(letter++, i);

        translator.put('S', 0);
        translator.put('E', 25);

        Node[][] map = new Node[fileLines.size()][fileLines.get(0).length()];
        Boolean[][] visited = new Boolean[fileLines.size()][fileLines.get(0).length()];

        wipeVisited(visited);

        ArrayList<Pair<Integer>> startCoords = new ArrayList<>();
        Pair<Integer> endCoord = new Pair<>();

        for (int i = 0; i < fileLines.size(); i++) {
            for (int j = 0; j < fileLines.get(i).length(); j++) {
                map[i][j] = new Node<>(translator.get(fileLines.get(i).charAt(j)), new Pair<>(i, j));
                //Remove the second half of the or statement for Part 1
                if (fileLines.get(i).charAt(j) == 'S' || fileLines.get(i).charAt(j) == 'a') {
                    startCoords.add(new Pair<>(i, j));
                } else if (fileLines.get(i).charAt(j) == 'E') {
                    endCoord.itemOne = i;
                    endCoord.itemTwo = j;
                }
            }
        }

        int lowestValue = Integer.MAX_VALUE;
        for (int i = 0; i < startCoords.size(); i++) {
            Pair<Integer> startCoord = startCoords.get(i);
            Node<Integer, Pair<Integer>> tempNode = getValueFromPair(map, startCoord);
            wipeVisited(visited);
            visited[startCoord.itemOne][startCoord.itemTwo] = true;
            Queue<Node<Integer, Pair<Integer>>> q = new LinkedList<>();
            q.offer(tempNode);
            Pair<Integer>[] pairs = new Pair[4];

            boolean check = false;

            while (!q.isEmpty()) {
                tempNode = q.poll();

                Pair<Integer> tempPair = tempNode.location;
                pairs[0] = new Pair<>(tempPair.itemOne + 1, tempPair.itemTwo);
                pairs[1] = new Pair<>(tempPair.itemOne - 1, tempPair.itemTwo);
                pairs[2] = new Pair<>(tempPair.itemOne, tempPair.itemTwo + 1);
                pairs[3] = new Pair<>(tempPair.itemOne, tempPair.itemTwo - 1);

                for (Pair<Integer> pair : pairs) {
                    if (pair.itemOne < 0 || pair.itemOne >= map.length || pair.itemTwo < 0 || pair.itemTwo >= map[0].length)
                        continue;
                    else if (getValueFromPair(visited, pair))
                        continue;
                    else if ((int) getValueFromPair(map, pair).data - tempNode.data > 1)
                        continue;
                    if (pair.equals(endCoord)) {
                        getValueFromPair(map, endCoord).cameFrom = tempNode;
                        check = true;
                        break;
                    } else {
                        visited[pair.itemOne][pair.itemTwo] = true;
                        getValueFromPair(map, pair).cameFrom = tempNode;
                        q.offer(getValueFromPair(map, pair));
                    }
                }
            }


            if (check) {
                int runningTotal = 0;
                tempNode = getValueFromPair(map, endCoord);

                while (true) {
                    if (tempNode.location.equals(startCoord))
                        break;
                    runningTotal++;
                    tempNode = tempNode.cameFrom;
                }

                lowestValue = Math.min(lowestValue, runningTotal);
            }
        }

        System.out.println(lowestValue);
    }

    private static void wipeVisited(Boolean[][] visited) {
        for (Boolean[] arr : visited) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = false;
            }
        }
    }

    private static <T> T getValueFromPair(T[][] array, Pair<Integer> pair) {
        return array[pair.itemOne][pair.itemTwo];
    }

    private static class Node<T, K> {
        public T data;
        public Node<T, K> cameFrom;

        public K location;

        public Node(T data, K location) {
            this.cameFrom = null;
            this.location = location;
            this.data = data;
        }

        @Override
        public String toString() {
            if (cameFrom == null)
                return "Location: " + location.toString() + ", Data: " + data.toString() + ", CameFrom: Nothing";
            return "Location: " + location.toString() + ", Data: " + data.toString() + ", CameFrom: " + cameFrom.toString(false);
        }
        public String toString(boolean recursive) {
            if (recursive)
                try {
                    return "Location: " + location.toString() + ", Data: " + data.toString() + ", CameFrom: " + cameFrom.toString(true);
                } catch (NullPointerException e) {
                    return toString();
                }
            else {
                return location.toString();
            }
        }
    }

    private static class Pair<T> {
        public T itemOne, itemTwo;
        public Pair(T itemOne, T itemTwo) {
            this.itemOne = itemOne;
            this.itemTwo = itemTwo;
        }

        public Pair() {
            this.itemOne = null;
            this.itemTwo = null;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            Pair<T> test;
            try {
                test = (Pair<T>) o;
            } catch (ClassCastException e) {
                return false;
            }

            return test.itemOne.equals(this.itemOne) && test.itemTwo.equals(this.itemTwo);
        }

        @Override
        public String toString() {
            return "(" + itemOne.toString() + "," + itemTwo.toString() + ")";
        }

        public Object[] toArray() {
            return new Object[] {itemOne, itemTwo};
        }
    }
}
