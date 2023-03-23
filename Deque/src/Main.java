public class Main {
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.deleteRear();
        dq.addFront(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.addFront(5);
        dq.addRear(1);
        dq.addRear(2);
        dq.deleteFront();
        dq.addRear(7);
        dq.deleteFront();
        dq.addRear(7);
        dq.deleteFront();
        dq.addRear(7);
        dq.deleteFront();
        dq.addRear(7);
        dq.deleteFront();
        dq.addRear(7);
        dq.deleteFront();
        dq.deleteRear();
    }
}