import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CommonFunctions {
    public static ArrayList<String> readFileLines(String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        final BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null)
            list.add(line);

        in.close();

        return list;
    }
    public static ArrayList<Long> primeNumberFinder(int n) {
        // Create a boolean array "prime[0..n]" and initialize
        // all entries it as true. A value in prime[i] will
        // finally be false if i is Not a prime, else true.
        ArrayList<Long> output = new ArrayList<>();
        boolean[] prime = new boolean[n + 1];
        for (int i = 0; i <= n; i++)
            prime[i] = true;

        for (int p = 2; p * p <= n; p++) {
            // If prime[p] is not changed, then it is a prime
            if (prime[p]) {
                // Update all multiples of p
                for (int i = p * p; i <= n; i += p)
                    prime[i] = false;
            }
        }

        // Print all prime numbers
        for (int i = 2; i <= n; i++) {
            if (prime[i])
                output.add((long) i);
        }
        return output;
    }


}
