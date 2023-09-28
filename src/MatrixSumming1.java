public class MatrixSumming1 {
    private static final int[][] m = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 0},
            {6, 7, 1, 2, 5},
            {6, 7, 8, 9, 0},
            {5, 4, 3, 2, 1},
    };

    public static int sum() {
        int sum = 0;
        for (int[] row : m) {
            for (int i : row) {
                sum += i;
            }
        }
        return sum;
    }
}
