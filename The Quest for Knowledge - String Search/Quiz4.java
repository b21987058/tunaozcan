import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Quiz4 {
    class TrieNode {
        Map<Character, TrieNode> children;
        List<Word> words;

        TrieNode() {
            children = new HashMap<>();
            words = new ArrayList<>();
        }
    }

    class Word {
        long weight;
        String word;

        Word(long weight, String word) {
            this.weight = weight;
            this.word = word;
        }
    }

    class Trie {
        private TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        public void insert(String word, long weight) {
            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                node.children.putIfAbsent(ch, new TrieNode());
                node = node.children.get(ch);
            }
            node.words.add(new Word(weight, word));
        }

        public List<Word> search(String prefix) {
            TrieNode node = root;
            for (char ch : prefix.toCharArray()) {
                if (!node.children.containsKey(ch)) {
                    return new ArrayList<>();
                }
                node = node.children.get(ch);
            }
            return collectAllWords(node);
        }

        private List<Word> collectAllWords(TrieNode node) {
            List<Word> result = new ArrayList<>(node.words);
            for (TrieNode child : node.children.values()) {
                result.addAll(collectAllWords(child));
            }
            return result;
        }
    }
    public static void main(String[] args) throws IOException {

        // TODO: Use the first and the second command line argument (args[0] and args[1]) to read the database and the query file.
        // TODO: Your code goes here
        Quiz4 quiz4 = new Quiz4();

        // Use the first and the second command line argument (args[0] and args[1]) to read the database and the query file.
        String databaseFile = args[0];
        String queryFile = args[1];

        Trie trie = quiz4.new Trie();
        try (BufferedReader br = new BufferedReader(new FileReader(databaseFile))) {
            int N = Integer.parseInt(br.readLine().strip());
            int i = 0;
            while (i < N) {
                String[] parts = br.readLine().strip().split("\t");
                long weight = Long.parseLong(parts[0]);
                String word = parts[1].toLowerCase();
                trie.insert(word, weight);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(queryFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.strip().split("\t");
                String query = parts[0].toLowerCase();
                int limit = Integer.parseInt(parts[1]);
                System.out.println("Query received: \"" + query + "\" with limit " + limit + ". Showing results:");
                List<Word> results = trie.search(query);
                results.sort((w1, w2) -> Long.compare(w2.weight, w1.weight));

                if (limit == 0 || results.isEmpty()) {
                    System.out.println("No results.");
                } else {
                    int j = 0;
                    while (j < Math.min(limit, results.size())) {
                        Word result = results.get(j);
                        System.out.println("- " + result.weight + " " + result.word);
                        j++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Print the solution to STDOUT
    }

}
