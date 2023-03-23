public class Deque<T> {
    private T[] arr;
    private int front;
    private int rear;
    private int size;

    public Deque(int size) {
        arr = (T[]) new Object[size];
        front = 0;
        rear = -1;
        this.size = arr.length - 1;

    }

    public void addFront(T value) {
        if (isFull()) {
            arrayExpansion();
        }
        if (front == 0 && arr[front] != null && isEmpty(size - 1)) {
            front = size;
            arr[front] = value;
            return;
        }
        if (front != 0) front--;
        if (isEmpty(front)) {
            arr[front] = value;
        }
    }

    public void addRear(T value) {
        if (isFull()) {
            arrayExpansion();
        }

        if (rear == 0 && isEmpty(0)) {
            arr[rear] = value;
            return;
        }
        rear++;
        if (rear > size && isEmpty(0)) {
            rear = 0;
        }
        if (isEmpty(rear)) {
            arr[rear] = value;
            return;
        }
        if (rear == 0 && !isEmpty(0)) {
            rear++;
            if (isEmpty(rear)) {
                arr[rear] = value;
            }
        }
    }

    public void deleteFront() {
        if (!isEmpty(front)) {
            arr[front] = null;
            if (front != size) {
                front++;
            } else front = 0;
        }
    }

    public void deleteRear() {
        if (rear == -1) {
            rear++;
            arr[rear] = null;
            rear = size;
            return;
        }
        if (rear == 0 && !isEmpty(rear)) {
            arr[rear] = null;
            if (!isEmpty(size)) {
                rear = size;
                return;
            }
        }
        if (!isEmpty(rear)) {
            arr[rear] = null;
            rear--;
        }
        if (rear == -1) {
            rear = size;
        }
    }

    private boolean isEmpty(int x) {
        return x >= 0 && arr[x] == null;
    }

    private boolean isFull() {
        for (T value : arr) {
            if (value == null) {
                return false;
            }
        }
        return true;
    }

    private void arrayExpansion() {
        size = size * 2;
        T[] arr1 = (T[]) new Object[size];
        System.arraycopy(arr, 0, arr1, 0, arr.length);
        int tmp = arr.length - front;
        int tmp1 = front;
        arr = (T[]) new Object[size];
        size -= 1;
        if (front == 0) {
            System.arraycopy(arr1, 0, arr, 0, arr1.length);
        } else {
            front = arr.length - tmp;
            for (int i = front; i <= arr.length; i++) {
                if (i == arr.length) {
                    i = 0;
                    tmp1 = 0;
                }
                arr[i] = arr1[tmp1];
                if (tmp1 == rear) break;
                if (rear == -1 && tmp1 == 0) break;
                tmp1++;
            }
        }
    }
}
