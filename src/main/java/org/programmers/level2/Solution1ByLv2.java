package org.programmers.level2;

import java.util.Arrays;
import java.util.Comparator;

public class Solution1ByLv2 {

    public int solution(int[][] targets) {
        Arrays.sort(targets, Comparator.comparingInt(a -> a[1]));

        int count = 0;
        int lastIntercepted = Integer.MIN_VALUE;

        for (int[] target : targets) {
            if (target[0] > lastIntercepted) {
                lastIntercepted = target[1] - 1;
                count++;
            }
        }

        return count;
    }
}
