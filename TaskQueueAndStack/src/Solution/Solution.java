package Solution;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Solution {
    public static Deque<Integer> RestoreTheOriginalSequence(Stack<Integer> resultSequence) {
        Deque<Integer> sourceSequence = new LinkedList<>();
        sourceSequence.add(resultSequence.pop());
        sourceSequence.add(resultSequence.pop());
        while (!resultSequence.isEmpty()) {
            sourceSequence.add(sourceSequence.removeFirst());
            sourceSequence.add(resultSequence.pop());
        }
        return sourceSequence;
    }
}