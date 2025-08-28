# EXERCISE 1.1

MANDATORY

## 1. 

Output: Count is 19125501 and should be 20000000

## 2. 

### Output:
Count is 200 and should be 200

### Answer:
The longer the code runs, the more likely a race condition is to occur, so it's not guarantied that it's always 200.

## 3. 

All three types of writing the incrementation compiles to the same form of byte code, meaning the computer understands them as exactly the same

## 4.

We have integrated ReetrantLock by importing: java.util.concurrent.locks.ReentrantLock;

in the increment() function, we find a critical section, because we handle shared memory. To solve this we use a lock to for fill mutual exclusion as we now control the interleavings of the threads. And after we have done the not atomic one-liner count = count + 1;, we can release the lock again. Doing this assures that we don't end in a race condition.
### code:
```
public void increment() {
            reentrantLock.lock();
            count = count + 1;
            reentrantLock.unlock();
        }
``` 
## 5.

As stated previously, the critical section is this line of code: count = count + 1;

So locking around the for loop of inside each for loop would make the for a longer code section. So being precise with finding the critical section ensures the least amount of lines of code.

CHALLENGING:

6.

7.

8.

# EXERCISE 1.2

MANDATORY

## 1.

Running the code: gradle -PmainClass=exercises01.Printer_Exercise run


Code:
```
package exercises01;

class Printer {
    public void print() {
        System.out.print("-");
        try {
            Thread.sleep(50);
        } catch (InterruptedException exn) {
            Thread.currentThread().interrupt();
        }
        System.out.print("|");
    }
}

public class Printer_Exercise {
    public static void main(String[] args) {
        Printer p = new Printer();
        
        Thread t1 = new Thread(() -> {
            while (true) {
                p.print();
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                p.print();
            }
        });

        t1.start();
        t2.start();
    }
}
``` 
## 2.

Let's say that T1, prints "-", as its the first thing the print method does, then goes to sleep, while it sleeps T2 starts print, printing "-", now the output is "--".

## 3. 

Running the code: gradle -PmainClass=exercises01.Printer_Exercise run

Code:
```
package exercises01;

import java.util.concurrent.locks.ReentrantLock;

class Printer {

    ReentrantLock reentrantLock = new ReentrantLock();
    public void print() {
        reentrantLock.lock();
        System.out.print("-");
        try {
            Thread.sleep(50);
        } catch (InterruptedException exn) {
            Thread.currentThread().interrupt();
        }
        System.out.print("|");
        reentrantLock.unlock();
    }
}

public class Printer_Exercise {
    public static void main(String[] args) {
        Printer p = new Printer();

        // Thread 1
        Thread t1 = new Thread(() -> {
            while (true) {
                p.print();
            }
        });

        // Thread 2
        Thread t2 = new Thread(() -> {
            while (true) {
                p.print();
            }
        });

        t1.start();
        t2.start();
    }
}
```

CHALLENGING

4.




# EXERCISE 1.3 

## 1. 

Runnning the code:

gradle -PmainClass=exercises01.CounterThreads2Covid run

Code:
```
package exercises01;

import java.util.concurrent.locks.*;

public class CounterThreads2Covid {

    long counter = 0;
    final long PEOPLE = 10_000;
    final long MAX_PEOPLE_COVID = 15_000;
    ReentrantLock lock = new ReentrantLock();

    public CounterThreads2Covid() {
        try {
            Turnstile turnstile1 = new Turnstile();
            Turnstile turnstile2 = new Turnstile();

            turnstile1.start();
            turnstile2.start();
            turnstile1.join();
            turnstile2.join();

            System.out.println(counter + " people entered");
        } catch (InterruptedException e) {
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CounterThreads2Covid();
    }

    public class Turnstile extends Thread {
        public void run() {
            for (int i = 0; i < PEOPLE; i++) {
                try {
                    lock.lock();
                    if (counter < MAX_PEOPLE_COVID)
                        counter++;
                } finally {
                    lock.unlock();
                }

            }
        }
    }
}
```

## 2. 

The threads will never let more than 15000 people in because there is an if statement inside the lock with the counter. Some will be rejected ie the for loop will run but not increase the counter.

# EXERISE 1.4
Goetz motivations for concureny
- **Resource utilization**. Programs sometimes have to wait for external operations such as input or output, and while waiting can do no useful work. It is more  efﬁcient to use that wait time to let another program run.
- **Fairness** Multiple users and programs may have equal claims on the machine’s resources. It is preferable to let them share the computer via ﬁner-grained time slicing than to let one program run to completion and then start another.
- **Convenience** It is often easier or more desirable to write several programs that each perform a single task and have them coordinate with each other as necessary than to write a single program that performs all the tasks.

Kristen Nygaard
- **Inherent** User interfaces and other kinds of input/output.
- **Exploitation** Hardware capable of simultaneously executing multiple streams of statements. A special (but important) case is communication and coordination of independent computers on the internet.
- **Hidden** Enabling several programs to share some resources in a manner where each can act as if they had sole ownership.

## 1.
Shared computation server; the goetz fiarness includes a server where everyones programm eventually gets to run, it can be said that this is included in hidden but it is not explicitly included in Kristens notes. 

Shared hosting server, where each developer thinks they have sole ownership over a given cumpyter but in reality it is a vm and they have different cores or the like. (hidden the illusion of sole ownership) 

## 2.
### inherent 
1. websites 
2. Lifts
3. Wifi
### Exploitation 
1. HPC at itu
2. gpu training of NN models
3. Operating systems
### Hidden
1. Servers
2. VMs 
3. Databases 

# EXERCISE 1.5 
## Mariu
### 1. 
Ubuntu - native 
### 2. 
- OS:ATM - Ubuntu 
- 14 cores
- Cache 24 MB - Intel’s Hybrid Cache Architecture
- 32 gb ram

### 3. 
Running the code: gradle -PmainClass=exercises01.Timer run
