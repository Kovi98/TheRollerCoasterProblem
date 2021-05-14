public class Main {

    public static void main(String[] args) throws InterruptedException {
        int numberOfSeats = 4;
        int numberOfPassengers = numberOfSeats * 5;
//        int numberOfPassengers = 5;

        Passenger[] passengers = new Passenger[numberOfPassengers];

        Car car = new Car(numberOfSeats, numberOfPassengers);

        car.start();

        for (int i = 0; i < numberOfPassengers; i++) {
            passengers[i] = new Passenger(car);
            passengers[i].start();
        }

        for (int i = 0; i < numberOfPassengers; i++) {
            passengers[i].join();
        }
        car.interrupt();
        System.out.println("All threads have ended.");
    }
}
