import java.util.*;
import java.io.*;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        // TODO: Use the first command line argument (args[0]) as the file to read the input from
        String inputFile = args[0];
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String[] firstLine = reader.readLine().split(" ");
        int M = Integer.parseInt(firstLine[0]); // uzay mekiginin kilo cinsinden kapasitesi
        int n = Integer.parseInt(firstLine[1]); // resourceların sayısı
        int[] resources = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray(); //  resource masslerin arrayi
        reader.close();
        // TODO: Your code goes here
        int[][] L = new int[M + 1][n + 1];
        // Initialize the L matrix
        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0)
                    L[i][j] = 1;
                else
                    L[i][j] = 0;
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int m = 0; m <= M; m++) {
                if (resources[i - 1] > m)
                    L[m][i] = L[m][i - 1];
                else
                    L[m][i] = Math.max(L[m][i - 1], L[m - resources[i - 1]][i - 1]);
            }
        }
        int maxMass = 0;
        for (int m = 0; m <= M; m++) {
            if (L[m][n] == 1)
                maxMass = m;
        }
        // TODO: Print the solution to STDOUT
        System.out.println(maxMass);
        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.print(L[i][j]);
            }
            System.out.println();
        }
        String outputFile = "output.txt"; //hocamlar emin olamadım, bir de output dosyasına bastırayım dedim
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(String.valueOf(maxMass));
        writer.newLine();
        for (int i = 0; i <= M; i++) {
            for (int j = 0; j <= n; j++) {
                writer.write(String.valueOf(L[i][j]));
            }
            writer.newLine();
        }
        writer.close();
    }
}
