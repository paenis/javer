import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Integer> a = Arrays.stream(new int[10]).map(i -> new Random().nextInt(100)).boxed().toList();
        System.out.println(a);
    }
}