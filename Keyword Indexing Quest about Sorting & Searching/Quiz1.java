import java.util.*;
import java.io.*;

public class Quiz1 {
    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            return;
        }

        String inputFile = args[0];
        List<String> ignoreWords = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        boolean readTitles = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("...")) {
                    readTitles = true;
                    continue;
                }
                if (readTitles) {
                    titles.add(line);
                } else {
                    ignoreWords.add(line.toLowerCase());
                }
            }
        }

        List<String> index = generateIndex(ignoreWords, titles);
        for (String entry : index) {
            System.out.println(entry);
        }
    }

    private static List<String> generateIndex(List<String> ignoreWords, List<String> titles) {
        List<String> index = new ArrayList<>();
        Map<String, List<String>> keywordMap = new TreeMap<>();

        for (String title : titles) {
            String[] words = title.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i].toLowerCase();
                if (!ignoreWords.contains(word)) {
                    String indexedTitle = toIndexedTitle(words, i);
                    keywordMap.computeIfAbsent(word, k -> new ArrayList<>()).add(indexedTitle);
                }
            }
        }

        keywordMap.forEach((key, value) -> index.addAll(value));
        return index;
    }

    private static String toIndexedTitle(String[] words, int index) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) builder.append(" ");
            if (i == index) {
                builder.append(words[i].toUpperCase());
            } else {
                builder.append(words[i].toLowerCase());
            }
        }
        return builder.toString();
    }
}

