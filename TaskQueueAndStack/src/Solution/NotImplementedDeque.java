package Solution;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public interface NotImplementedDeque<T> extends Deque<T> {
    @Override
    public default void addFirst(T t) {

    }

    @Override
    default void addLast(T t) {

    }

    @Override
    public default boolean offerFirst(T t) {
        return false;
    }

    @Override
    public default boolean offerLast(T t) {
        return false;
    }

    @Override
    public default T removeFirst() {
        return null;
    }

    @Override
    public default T removeLast() {
        return null;
    }

    @Override
    public default T pollFirst() {
        return null;
    }

    @Override
    public default T pollLast() {
        return null;
    }

    @Override
    public default T getFirst() {
        return null;
    }

    @Override
    public default T getLast() {
        return null;
    }

    @Override
    public default T peekFirst() {
        return null;
    }

    @Override
    public default T peekLast() {
        return null;
    }

    @Override
    public default boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public default boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public default boolean add(T t) {
        return false;
    }

    @Override
    public default boolean offer(T t) {
        return false;
    }

    @Override
    public default T remove() {
        return null;
    }

    @Override
    public default T poll() {
        return null;
    }

    @Override
    public default T element() {
        return null;
    }

    @Override
    public default T peek() {
        return null;
    }

    @Override
    public default boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public default void clear() {

    }

    @Override
    public default void push(T t) {

    }

    @Override
    public default T pop() {
        return null;
    }

    @Override
    public default boolean remove(Object o) {
        return false;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public default boolean contains(Object o) {
        return false;
    }

    @Override
    public default int size() {
        return 0;
    }

    @Override
    public default boolean isEmpty() {
        return false;
    }

    @Override
     default Iterator<T> iterator() {
        return null;
    }

    @Override
     default Object[] toArray() {
        return new Object[0];
    }

    @Override
     default <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
     default Iterator<T> descendingIterator() {
        return null;
    }
}
