package org.programmers.level3;

import java.util.*;

public class Solution10ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/64062]
     * 2019 카카오 개발자 겨울 인턴십
     * 징검다리 건너기
     *
     * 기본적으로 문제 자체는 슬라이딩 윈도우로 해결해야 하는 문제로 보였다.
     * 하지만 이전에 풀었던 슬라이딩 윈도우보다 좀 더 특이한 형태의 문제로
     * 가장 최적의 해결 방법을 구상해봤을 때 k 갯수만큼의 건널 수 있는 영역이 존재하고
     * 연속된 k 길이만큼 값이 0이라면 건널 수 없다는 뜻으로 이해했다.
     * 그렇다면 모든 배열 길이중 k영역(슬라이스)마다 가장 큰 값이 건널 수 있는
     * 사람의 수와 동일할 것이고 모든 슬라이스 영역 중 가장 적은 사람이 건널 수 있는
     * 영역이 곧 건널 수 있는 최대 인원일 것이다.
     *
     * 처음 구현은 우선순위 큐에 요소를 추가하며 영역별로 최대값을 비교하여 가장 작은 최대값을
     * 구하는 방법으로 구현하였다. 하지만 remove(요소)를 사용하여 윈도우를 벗어난 요소를 제거하는 것이
     * 비효율적인 구조였고, 값을 추가할 때마다 추가되는 값보다 작은 값들은 모두 제거하여 반복의 효율을
     * 높여주는 코드로 수정하였다. 수정된 코드로 테스트를 해보니 가장 마지막 효율성 테스트에서 시간 초과가
     * 나왔는데, 아무래도 최악의 경우 시간 초과가 발생 하는것으로 보여 대대적인 구상을 수정하였다.
     * 정통적인 슬라이딩 윈도우에 큐의 개념을 추가하여 양방향 요소 추가 제거가 가능한 deque를 구상하였고
     * deque 뒤에 값을 추가하며 추가하려는 값보다 작거나 같은 값들을 모두 제거하고, 윈도우 영역을 벗어난
     * 인덱스도 제거한 후 현재 요소를 추가하는 식으로 코드를 재구성하여 문제를 해결하였다.
     *
     */

    public int solution(int[] stones, int k) {
        Deque<Integer> deque = new LinkedList<>();
        int max = Integer.MAX_VALUE;

        for(int i = 0; i < stones.length; i++) {
            if(!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            while(!deque.isEmpty() && stones[deque.peekLast()] <= stones[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if(i >= k - 1) {
                max = Math.min(stones[deque.peekFirst()], max);
            }
        }

        return max;
    }

    /*
    * 1차 시도
    * public int solution(int[] stones, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        int max = -1;

        for(int i = 0; i < k; i++) {
            queue.add(stones[i]);
            max = Math.max(stones[i], max);
        }

        for(int i = k; i < stones.length; i++) {
            int now = stones[i];
            queue.removeIf(value -> value < now);

            queue.remove(stones[i - k]);
            queue.add(stones[i]);

            max = Math.min(queue.peek(), max);
        }

        return max;
    }
    * */

}