// For week 2
// sestoft@itu.dk * 2015-10-29
package exercises02;

public class readerwriter {
    private int readers = 0;
    private boolean writer = false;
    private boolean waitingWriter = false;

    public static void main(String[] args) {
        readerwriter monitor = new readerwriter();
        new Thread(() -> {
            try {
                monitor.readLock();
                System.out.println("Reader starts reading");
                Thread.sleep(500);
                System.out.println("Reader stops reading");
                monitor.readUnlock();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
        new Thread(() -> {
            try {
                monitor.writerLock();
                System.out.println("Writer starts writing");
                Thread.sleep(1000);
                System.out.println("Writer stops writing");
                monitor.writeUnlock();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                monitor.readLock();
                System.out.println("Reader2 starts reading");
                Thread.sleep(500);
                System.out.println("Reader2 stops reading");
                monitor.readUnlock();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public synchronized void readLock() {
        try {
            while (writer || waitingWriter) {
                wait();
            }
            readers++;
        } catch (InterruptedException e) {
        }

    }

    public synchronized void readUnlock() {
        readers--;
        if (readers == 0) {
            notifyAll();
        }
    }

    public synchronized void writerLock() {
        try {
            waitingWriter = true;
            while (writer || readers > 0) {
                wait();
            }
            writer = true;
            waitingWriter = false;
        } catch (InterruptedException e) {
        }
    }

    public synchronized void writeUnlock() {
        writer = false;
        notifyAll();
    }

}
