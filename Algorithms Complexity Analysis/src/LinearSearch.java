public class LinearSearch {
    public static int linearSearch(int[] arr, int x) {
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            if (arr[i] == x) {
                return i;
            }
        }
        return -1;
    }
}
