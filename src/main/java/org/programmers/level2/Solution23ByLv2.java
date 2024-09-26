package org.programmers.level2;

import org.programmers.Application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution23ByLv2 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/340212]
     * [PCCP 기출문제] 2번 / 퍼즐 게임 챌린지
     *
     * 최근 백준 문제를 풀다가 백준에 어느 정도 적응되어 숨돌리기로 프로그래머스 문제를
     * 풀어보았다, 나는 많은 문제를 풀어보며 이분 탐색이 필요한 경우가 거의 없어 이분
     * 탐색을 적용해서 해결한 경우는 거의 없었는데 이 문제가 이분 탐색을 정석처럼 적용
     * 할 기회가 된 문제라고 생각한다.
     *
     * 문제의 요지는 퍼즐을 제한 시간 안에 풀기 위한 최소한의 필요 레벨을 찾는 것으로
     * 값의 범위가 크기 때문에 단순히 레벨을 1씩 증가시키면 매우 시간이 오래 걸릴 것
     * 으로 예상되어 첫번째 시도는 레벨을 2배씩 증분하여 목표까지 도달하고 해당 목표
     * 레벨에서 값을 1씩 다운 시키는 방법으로 간단하게 풀려고 하였으나 일부 구간에서
     * 시간초과가 발생하였다. 그래서 이런 문제에는 이분 탐색이 최적화되어 있다는 걸
     * 이용하여 본격적으로 이분 탐색 알고리즘을 이용하여 문제를 해결하였다.
     *
     */

    public int solution(int[] diffs, int[] times, long limit) {
        int low = 1;
        int high = 1000000000;

        while(low < high) {
            int mid = (low + high) / 2;

            if(!valid(mid, diffs, times, limit)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private boolean valid(int level, int[] diffs, int[] times, long limit) {
        long prevTime = 0, sumTime = 0;

        for(int i = 0; i < diffs.length; i++) {
            long diff = level - diffs[i];

            if(diff >= 0) {
                sumTime += times[i];
                prevTime = times[i];
            } else {
                long nowTime = (-diff * (times[i] + prevTime)) + times[i];
                sumTime += nowTime;
                prevTime = times[i];
            }

            if(sumTime > limit) return true;
        }

        return false;
    }

    public static void main(String[] args) {
        Solution23ByLv2 application = new Solution23ByLv2();

        int[] diffs2 = {1, 328, 467, 209, 54};
        int[] times2 = {2, 7, 1, 4, 3};
        long limit2 = 1723;

        int solution = application.solution(diffs2, times2, limit2);
        System.out.println(solution);
    }

}