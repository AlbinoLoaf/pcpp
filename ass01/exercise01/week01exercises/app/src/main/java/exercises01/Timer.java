package exercises01;

public class Timer {
    public static void main(String[] args) {
        long start, spent = 0;
        start = System.nanoTime();
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        spent = System.nanoTime() - start;

        System.out.println("Sum: " + sum);
        System.out.println("Time spent (nanoseconds): " + spent);
    }
}
