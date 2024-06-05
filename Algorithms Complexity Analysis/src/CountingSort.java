public class CountingSort {
    public static int[] countingSort(int[] input) {
        int k = max(input);
        int[] count = new int[k + 1];
        int[] output = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            count[input[i]]++;
        }
        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }
        for (int i = input.length - 1; i >= 0; i--) {
            output[count[input[i]] - 1] = input[i];
            count[input[i]]--;
        }
        return output;


    }

    private static int max(int[] input){
        int max = input[0];
        for (int i = 1; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            }
        }

        return max;
    }
}
