package exercises01;

public class Timer {
    public static void main(String[] args) {
        long start, spent = 0;

        // Start the timer
        start = System.nanoTime();

        // Code you are measuring: sum numbers 1 to 100
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }

        // Stop the timer
        spent += System.nanoTime() - start;

        System.out.println("Sum: " + sum);
        System.out.println("Time spent (nanoseconds): " + spent);
    }
}
