import java.util.concurrent.Semaphore;

public class Car extends Thread{

//    When the car arrives, it signals C passengers, then waits for the last one to
//    signal allAboard. After it departs, it allows C passengers to disembark, then
//    waits for allAshore.

    private final int numberOfSeats;
    private int currentPassengers = 0;
    private int waitingPassengers;

    private Semaphore mutex = new Semaphore(1); // mutual exclusion
    private Semaphore mutex2 = new Semaphore(1);
    private int boarders = 0;
    private int unboarders = 0;
    public Semaphore boardQueue = new Semaphore(0);
    public Semaphore unboardQueue = new Semaphore(0);
    private Semaphore allAboard = new Semaphore(0);
    private Semaphore allAshore = new Semaphore(0);

    public Car(int numberOfSeats, int totalPassengers) {
        this.numberOfSeats = numberOfSeats;
        this.waitingPassengers = totalPassengers;
    }

    @Override
    public void run() {
        System.out.println("The roller coaster is now open! There are " + numberOfSeats + " available seats.\n");

        while (true) {
            // load
            boardQueue.release(numberOfSeats);
            System.out.print("Boarding: ");
            try {
                allAboard.acquire();
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            // run
            waitingPassengers -= currentPassengers;
            System.out.println("Running.");

            // unload
            System.out.print("Unboarding: ");
            unboardQueue.release(numberOfSeats);
            try {
                allAshore.acquire();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("There are " + waitingPassengers + " passengers left.\n");
            if (waitingPassengers == 0) break;
        }
    }

    public void board() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.print(" （˶′◡‵˶） ");
        boarders++;

        if (boarders == waitingPassengers || boarders == numberOfSeats) {
            allAboard.release();
            currentPassengers = boarders;
            boarders = 0;
            System.out.println();
        }

        mutex.release();
    }

    public void unboard() {
        try {
            mutex2.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.print(" ヽ(＾Д＾)ﾉ ");
        unboarders++;

        if (unboarders == currentPassengers) {
            allAshore.release();
            currentPassengers = 0;
            unboarders = 0;
            System.out.println();
        }

        mutex2.release();
    }
}
