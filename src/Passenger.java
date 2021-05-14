public class Passenger extends Thread {

//    Passengers wait for the car before boarding, naturally, and wait for the car
//    to stop before leaving. The last passenger to board signals the car and resets
//    the passenger counter.

    Car car;

    public Passenger(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            car.boardQueue.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        car.board();

        try {
            car.unboardQueue.acquire();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        car.unboard();
    }
}
