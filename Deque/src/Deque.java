public class Deque {
    private int[] arr;
    private int front;
    private int rear;
    private int size;
    private boolean[] check;

    public Deque(int size) {
        arr = new int[size];
        check = new boolean[size];
        front = 0;
        rear = -1;
        this.size = arr.length - 1;

    }

    public void addFront(int value) {
        if (isFull()) {
            arrayExpansion();
        }
        if (front == 0 && arr[front] != 0 && isEmpty(size - 1)) {
            front = size;
            arr[front] = value;
            check[front] = true;
            return;
        }
        if (front != 0) front--;
        if (isEmpty(front)) {
            arr[front] = value;
            check[front] = true;
        }
    }

    public void addRear(int value) {
        if (isFull()) {
            arrayExpansion();
        }

        if (rear == 0 && isEmpty(0)) {
            arr[rear] = value;
            check[rear] = true;
            return;
        }
        rear++;
        if (rear > size - 1 && isEmpty(0)) {
            rear = 0;
        }
        if (isEmpty(rear)) {
            arr[rear] = value;
            check[rear] = true;
            return;
        }
        if (rear == 0 && !isEmpty(0)) {
            rear++;
            if (isEmpty(rear)) {
                arr[rear] = value;
                check[rear] = true;
            }
        }
    }

    public void deleteFront() {
        if (!isEmpty(front)) {
            arr[front] = 0;
            check[front] = false;
            if (front != size) {
                front++;
            } else front = 0;
        }
    }

    public void deleteRear() {
        if (rear == 0 && !isEmpty(rear)) {
            arr[rear] = 0;
            check[rear] = false;
            if (!isEmpty(size)) {
                rear = size;
                return;
            }
        }
        if (!isEmpty(rear)) {
            arr[rear] = 0;
            check[rear] = false;
            rear--;
        }
        if (rear == -1) {
            rear = size;
        }
    }

    private boolean isEmpty(int x) {
        return x >= 0 && !check[x];
    }

    private boolean isFull() {
        for (boolean value : check) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    private void arrayExpansion() {
        size = size * 2;
        int[] arr1 = new int[size];
        System.arraycopy(arr, 0, arr1, 0, arr.length);
        int tmp = arr.length - front;
        int tmp1 = front;
        arr = new int[size];
        check = new boolean[size];
        if (front == 0) {
            System.arraycopy(arr1, 0, arr, 0, arr1.length);
            for (int i = 0; i <= rear; i++) {
                check[i] = true;
            }
        } else {
            front = arr.length - tmp;
            for (int i = front; i <= arr.length; i++) {
                if (i == arr.length) {
                    i = 0;
                    tmp1 = 0;
                }
                arr[i] = arr1[tmp1];
                check[i] = true;
                if (tmp1 == rear) break;
                if (rear == -1 && tmp1 == 0) break;
                tmp1++;
            }
        }
    }
}
