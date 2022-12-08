import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CommonFunctions {
    public static ArrayList<String> readFileLines(String fileName) throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader(fileName));

        ArrayList<String> lines = new ArrayList<>();

        String line = bufReader.readLine();
        while (line != null) {
            lines.add(line);
            line = bufReader.readLine();
        }

        bufReader.close();

        return lines;
    }
}
