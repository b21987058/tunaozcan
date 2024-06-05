import java.util.Arrays;
import java.util.Random;

public class AlgorithmTester {


    public static long[] testMergeSortRandom(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Random Merge Sort Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                long startTime = System.currentTimeMillis();
                MergeSort.mergeSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testMergeSortSorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Sorted Merge Sort Test Started:");

        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                long startTime = System.currentTimeMillis();
                MergeSort.mergeSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);

        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testMergeSortReverselySorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Reversely Merge Sort Test Started:");

        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                reverseArray(subsetInput);
                long startTime = System.currentTimeMillis();
                MergeSort.mergeSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);


        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testCountingSortRandom(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Random Counting Sort Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                long startTime = System.currentTimeMillis();
                CountingSort.countingSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();
        return averageTimes;
    }
    public static long[] testCountingSortSorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Sorted Counting Sort Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                long startTime = System.currentTimeMillis();
                CountingSort.countingSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testCountingSortReverselySorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Reversely Sorted Counting Sort Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                reverseArray(subsetInput);
                long startTime = System.currentTimeMillis();
                CountingSort.countingSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);

        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testInsertionSortRandom(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Random Insertion Sort Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                long startTime = System.currentTimeMillis();
                InsertionSort.insertionSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();

        return averageTimes;
    }

    public static long[] testInsertionSortSorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Sorted Insertion Sort Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                long startTime = System.currentTimeMillis();
                InsertionSort.insertionSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testInsertionSortReverselySorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Reversely Sorted Insertion Sort Test Started:");

        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 10; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                reverseArray(subsetInput);
                long startTime = System.currentTimeMillis();
                InsertionSort.insertionSort(subsetInput);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 10;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);

        }
        System.out.println();
        return averageTimes;
    }

    public static long[] testLinearSearchRandom(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Random Linear Search Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 1000; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                long startTime = System.nanoTime();
                Random random = new Random();
                int randomNumber = random.nextInt(inputSize);
                int index = LinearSearch.linearSearch(subsetInput, subsetInput[randomNumber]);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 1000;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();

        return averageTimes;
    }

    public static long[] testLinearSearchSorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println("Sorted Linear Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 1000; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                long startTime = System.nanoTime();
                Random random = new Random();
                int randomNumber = random.nextInt(inputSize);
                int index = LinearSearch.linearSearch(subsetInput, subsetInput[randomNumber]);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 1000;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();

        return averageTimes;
    }

    public static long[] testBinarySearchSorted(int[] input) {
        int[] inputSizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        long[] averageTimes = new long[inputSizes.length];
        System.out.println(" Sorted Binary Search Test Started:");
        for (int i = 0; i < inputSizes.length; i++) {
            int inputSize = inputSizes[i];
            long totalTime = 0;

            for (int j = 0; j < 1000; j++) {
                int[] subsetInput = Arrays.copyOf(input, inputSize);
                Arrays.sort(subsetInput);
                long startTime = System.nanoTime();
                Random random = new Random();
                int randomNumber = random.nextInt(inputSize);
                int index = LinearSearch.linearSearch(subsetInput, subsetInput[randomNumber]);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                totalTime += duration;
            }

            // anlık inpput size için avgtime hesabı
            averageTimes[i] = totalTime / 1000;
            System.out.println("->For " + inputSize + " time spent average is: " + averageTimes[i]);
        }
        System.out.println();

        return averageTimes;
    }

    public static void reverseArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            // sağ ve soldaki elemanları swaplar
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;

            // Soldakini sağa ve sağdakini sola taşır
            left++;
            right--;
        }
    }
}
