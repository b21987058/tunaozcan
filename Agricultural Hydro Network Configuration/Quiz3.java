import java.util.*;
import java.io.*;

public class Quiz3 {
    static class Edge implements Comparable<Edge> {
        int src, dest;
        double cost;

        Edge(int src, int dest, double cost) {
            this.src = src;
            this.dest = dest;
            this.cost = cost;
        }

        public int compareTo(Edge that) {
            return Double.compare(this.cost, that.cost);
        }
    }

    static class uni {
        int[] leader;

        uni(int size) {
            leader = new int[size];
            for (int i = 0; i < size; i++) {
                leader[i] = i;
            }
        }

        int find(int node) {
            while (node != leader[node]) {
                leader[node] = leader[leader[node]]; // path compression
                node = leader[node];
            }
            return node;
        }

        boolean union(int node1, int node2) {
            int root1 = find(node1);
            int root2 = find(node2);
            boolean united = false;

            switch (root1 == root2 ? 0 : 1) {
                case 0: // roots are the same
                    united = false;
                    break;
                case 1: // roots are different
                    leader[root1] = root2;
                    united = true;
                    break;
            }
            return united;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(args[0]));
        int testCases = scanner.nextInt();
        while (testCases-- > 0) {
            int links = scanner.nextInt();
            int points = scanner.nextInt();
            int[][] coordinates = new int[points][2];
            int i = 0;
            while (i < points) {
                coordinates[i][0] = scanner.nextInt();
                coordinates[i][1] = scanner.nextInt();
                i++;
            }

            PriorityQueue<Edge> edgeQueue = new PriorityQueue<>();
            i = 0;
            while (i < points) {
                int j = i + 1;
                while (j < points) {
                    double distance = Math.hypot(coordinates[i][0] - coordinates[j][0], coordinates[i][1] - coordinates[j][1]);
                    edgeQueue.add(new Edge(i, j, distance));
                    j++;
                }
                i++;
            }

            uni uni = new uni(points);
            double longestEdge = 0;
            int components = points;

            while (!edgeQueue.isEmpty() && components > links) {
                Edge current = edgeQueue.poll();
                if (uni.union(current.src, current.dest)) {
                    longestEdge = current.cost;
                    components--;
                }
            }

            System.out.printf("%.2f\n", longestEdge);
        }
        scanner.close();
    }
}
