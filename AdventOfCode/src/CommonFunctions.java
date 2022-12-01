import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonFunctions {
    public static List<String> readFileLines(String fileName) throws IOException {
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
