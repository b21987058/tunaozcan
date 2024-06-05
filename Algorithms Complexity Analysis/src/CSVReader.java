import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVReader {
    public static int[] read(String filePath, int inputSize) {
        int[] columnValues = new int[250000];
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null && index < inputSize) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    try {
                        int value = Integer.parseInt(parts[6]);
                        columnValues[index++] = value;
                    } catch (NumberFormatException e) {
                        // Ignore non-integer values
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.copyOfRange(columnValues, 0, index);
    }
}
