import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("/Users/nikmenshikov/Documents/учеба/java_projects/algorithms/TaskSort/src/test3.txt"));
        int size = sc.nextInt();
        Integer[] arr = new Integer[size];
        for (int i = 0; i < arr.length; i++) {
            int value = sc.nextInt();
            arr[i] = value;
        }
        boolean[] cantChange = new boolean[size];
        for (int i = 0; i < cantChange.length; i++) {
            int value = sc.nextInt();
            if (value == 1) {
                cantChange[i] = true;
            }
        }
        Solution.modifyInsertionSort(arr, cantChange);
        for (int value : arr) {
            System.out.print(value + " ");
        }
    }
}
