package Solution;

import java.util.Deque;
import java.util.Stack;

 class Solution {

     public static Deque<Integer> RestoreTheOriginalSequence(Stack<Integer> resultSequence, Deque<Integer> sourceSequence) {
        sourceSequence.addLast(resultSequence.pop());
        sourceSequence.addLast(resultSequence.pop());
        while (!resultSequence.isEmpty()) {
            sourceSequence.addLast(sourceSequence.removeFirst());
            sourceSequence.addLast(resultSequence.pop());
        }
        return sourceSequence;
    }
 }