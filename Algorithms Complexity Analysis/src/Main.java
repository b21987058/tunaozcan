import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import com.opencsv.exceptions.CsvException;


class Main {
    public static void main(String args[]) throws IOException {

        // Save the char as .png and show it
       // showAndSaveChart("Sample Test", inputAxis, yAxis);

        int [] reader = CSVReader.read("TrafficFlowDataset.csv",250000);

        long[] testInsertionSortRandom = AlgorithmTester.testInsertionSortRandom(reader);
        long[] testMergeSortRandom = AlgorithmTester.testMergeSortRandom(reader);
        long[] testCountingSortRandom = AlgorithmTester.testCountingSortRandom(reader);

        long[] testInsertionSortSorted = AlgorithmTester.testInsertionSortSorted(reader);
        long[] testMergeSortSorted = AlgorithmTester.testMergeSortSorted(reader);
        long[] testCountingSortSorted = AlgorithmTester.testCountingSortSorted(reader);

        long[] testInsertionSortReverselySorted = AlgorithmTester.testInsertionSortReverselySorted(reader);
        long[] testMergeSortReverselySorted = AlgorithmTester.testMergeSortReverselySorted(reader);
        long[] testCountingSortReverselySorted = AlgorithmTester.testCountingSortReverselySorted(reader);

        long[] testLinearSearchRandom = AlgorithmTester.testLinearSearchRandom(reader);
        long[] testLinearSearchSorted = AlgorithmTester.testLinearSearchSorted(reader);
        long[] testBinarySearchSorted = AlgorithmTester.testBinarySearchSorted(reader);

        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        double[] doubleArray = longToDouble(testInsertionSortRandom);
        double[] doubleArray2 = longToDouble(testInsertionSortSorted);
        double[] doubleArray3 = longToDouble(testInsertionSortReverselySorted);

        double[] doubleArray4 = longToDouble(testMergeSortRandom);
        double[] doubleArray5 = longToDouble(testMergeSortSorted);
        double[] doubleArray6 = longToDouble(testMergeSortReverselySorted);

        double[] doubleArray7 = longToDouble(testCountingSortRandom);
        double[] doubleArray8 = longToDouble(testCountingSortSorted);
        double[] doubleArray9 = longToDouble(testCountingSortReverselySorted);

        double[] doubleArray10 = longToDouble(testLinearSearchRandom);
        double[] doubleArray11 = longToDouble(testLinearSearchSorted);
        double[] doubleArray12 = longToDouble(testBinarySearchSorted);

        double[][] randomResults = {doubleArray, doubleArray4, doubleArray7};
        double[][] sortedResults = {doubleArray2, doubleArray5, doubleArray8};
        double[][] reverselySortedResults = {doubleArray3, doubleArray6, doubleArray9};

        double[][] searchResults = {doubleArray10, doubleArray11, doubleArray12};


        Chart.showAndSaveChart("Tested with Random Data", inputSizes, randomResults,"InsertionSort", "MergeSort", "CountingSort");
        Chart.showAndSaveChart("Tested with Sorted Data", inputSizes, sortedResults,"InsertionSort", "MergeSort", "CountingSort");
        Chart.showAndSaveChart("Tested with Reversely Sorted Data", inputSizes, reverselySortedResults,"InsertionSort", "MergeSort", "CountingSort");

        Chart.showAndSaveChartSearching("Tests on Search Algorithms", inputSizes, searchResults,"LinearSearch w Random Data", "LinearSearch on Sorted Data", "BinarySearch on Sorted Data");
    }
    public static double[] longToDouble(long[] longArray) { //i used this function to convert the long data type to double
        double[] doubleArray = new double[longArray.length];
        for (int i = 0; i < longArray.length; i++) {
            doubleArray[i] = (double) longArray[i];
        }
        return doubleArray;
    }


}
