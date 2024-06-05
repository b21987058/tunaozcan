import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    // Refactored to use extractVar method
    public int getIntVar(String varName, String fileContent) {
        return extractVar(varName, fileContent, Integer::parseInt);
    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    // Refactored to use extractVar method
    public String getStringVar(String varName, String fileContent) {
        return extractVar(varName, fileContent, s -> s);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    // Refactored to use extractVar method
    public Double getDoubleVar(String varName, String fileContent) {
        return extractVar(varName, fileContent, Double::parseDouble);
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Point p = new Point(0, 0);
        // TODO: Your code goes here
        String pointStr = extractVar(varName, fileContent, s -> s);
        Pattern pat = Pattern.compile("\\(([0-9]+),\\s*([0-9]+)\\)");
        Matcher m = pat.matcher(pointStr);
        if (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            p.x = x;
            p.y = y;
            return p;
        }
        return p;

    }



    // Combined extraction method for different types
    private <T> T extractVar(String varName, String fileContent, Function<String, T> converter) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([^\\n]+)");
        Matcher m = p.matcher(fileContent);
        if (m.find()) {
            return converter.apply(m.group(1).trim());
        }
        throw new IllegalArgumentException("Variable not found: " + varName);
    }
    /**
     * Function to extract the train lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();

        // TODO: Your code goes here
        Pattern p = Pattern.compile("train_line_name\\s*=\\s*\"([^\"]+)\"\\s*train_line_stations\\s*=\\s*(\\([^\\)]+\\)(\\s*\\([^\\)]+\\))*)");
        Matcher m = p.matcher(fileContent);

        while (m.find()) {
            String lineName = m.group(1);
            String[] stationsData = m.group(2).split("\\)\\s*\\(");
            List<Station> stations = new ArrayList<>();

            for (String stationData : stationsData) {
                String[] coordinates = stationData.replaceAll("[\\(\\)]", "").split(",");
                int x = Integer.parseInt(coordinates[0].trim());
                int y = Integer.parseInt(coordinates[1].trim());
                stations.add(new Station(new Point(x, y), lineName + " Station"));
            }

            trainLines.add(new TrainLine(lineName, stations));
        }

        return trainLines;

    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {

        // TODO: Your code goes here
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContentBuilder.append(line).append("\n");
            }
            String fileContent = fileContentBuilder.toString();

            numTrainLines = getIntVar("num_train_lines", fileContent);
            startPoint = new Station(getPointVar("starting_point", fileContent), "Starting Point");
            destinationPoint = new Station(getPointVar("destination_point", fileContent), "Destination Point");
            averageTrainSpeed = getDoubleVar("average_train_speed", fileContent);
            lines = getTrainLines(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}