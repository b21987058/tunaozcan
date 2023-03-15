import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class datFileWrite {
    public static void datFileWrite(String path,String text) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(text);
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");

        }
    }
}
