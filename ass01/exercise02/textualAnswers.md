# Exercise 1

## 1.1  (Mandatory)

To run code: `gradle -PmainClass=exercises02.readerwriter  run`

Code
```
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
            waitingWriter = false;
            writer = true;
        } catch (InterruptedException e) {
        }
    }

    public synchronized void writeUnlock() {
        try {
            writer = false;
            notifyAll();
        } catch (Exception e) {
        }
    }

}

``` 

## 1.2  (Mandatory)
Our solution is fair towards the writer because when the writer wants to write it sets the waiting writer boolean to true preventing any reader from starting to read. 


## 1.3 (Mandatory) 
We have four conditions one in each of the locks and unlocks. Wait() telling a threat not to start and notify all to tell others 




# Exercise 2
## 2.1 (Mandatory)
We opserve that it  sets mi to 42 and then waits for the thread forever.

It is possible this happens as the mi thread is never updated because mi is not volitile so it never reads the update 

## 2.2
We implimented this in:
```
public synchronized void set(int value) {
        this.value = value;
    }

    public synchronized int get() {
        return value;
    }
```
The thread always terminates because we ensure visibility with what is in the implicit locks

## 2.3

This would work if you only have one thread  because  if the set sunction is run in a different threat it is not neccesarily set in main memory and only in L1 or L2 

## 2.4
We  are again ensuring visability and thus t would always terminate but stil vulnurable to mutex issues

# Exercise 3

## 3.1  (Mandatory)

Output from multiple runs:
```
Sum is 1903354,000000 and should be 2000000,000000
Sum is 1898422,000000 and should be 2000000,000000
Sum is 1890361,000000 and should be 2000000,000000
Sum is 1894739,000000 and should be 2000000,000000
```

## 3.2 
The race condition happens because the 2 locks lock addInstance() locks the object m, while addStatic() is locking the class Mystery Class. So because these are different things being locked, t1 and t2 can run at the same time, meaning that they can access and update sum at the same time.

## 3.3

The new Mystery Class now has one lock, that keeps track on both methods, resulting in access to the shared variable sum is synchronized.

New Mystery Class:
```
class Mystery {
    private static double sum = 0;
    private static final Object lock = new Object();


    public static void addStatic(double x) {
        synchronized (lock) {
            sum += x;
        }
    }

    public void addInstance(double x) {
        synchronized (lock) {
            sum += x;
        }
    }

    public static double sum() {
        synchronized (lock) {
            return sum;
        }
    }
}
```

## 3.4
Removing the synchronized modifier from sum does not break the mutual exclusion, as the two other methods are syncronized 


