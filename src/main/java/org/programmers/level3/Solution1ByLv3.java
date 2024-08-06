package org.programmers.level3;

import java.util.Collections;
import java.util.PriorityQueue;

public class Solution1ByLv3 {

    /**
     * level3부터는 본격적인 자료구조와 코딩테스트에 입문하는 단계이므로
     * 합격률이 높은 문제(자료구조 학습) 부터 해결해가는 전략을 선택하였다.
     * 아래 이중 우선순위 큐는 처음에는 deque 구조를 이용하려 하였으나 deque는
     * 삽입 정렬이 지원되지 않으므로 요소마다 삽입 시 정렬을 수동으로 해야하기 때문에 효율성이
     * 매우 떨어지는 문제가 있다. 하여 아래처럼 우선순위 큐를 두개를 사용하였다.
     * 해당 방법도 최댓값 삭제 시 반대 정렬 큐의 삭제 요소를 탐색하는 시간이 항상 n과 일치하기 때문에
     * 가장 좋은 방법으로는 보이지 않는다. 하여 추후 문제들을 해결해 나가며 다른 자료구조들을 학습 시
     * 추후 동일한 문제를 요구하는 문제를 해결할 때 학습한 자료구조들을 바탕으로 응용하여 문제 해결이 필요할 것으로 보인다.
     */

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    public int[] solution(String[] operations) {
        int[] result = new int[2]; // max, min

        for(String operationInfo : operations) {
            String type = operationInfo.split(" ")[0];
            int value = Integer.parseInt(operationInfo.split(" ")[1]);

            switch(type) {
                case "I" : { add(value); break; }
                case "D" : {
                    if (value == 1) {
                        removeMax();
                    } else if (value == -1) {
                        removeMin();
                    }
                }
            }
        }

        if(!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            result[0] = maxHeap.peek();
            result[1] = minHeap.peek();
        }

        return result;
    }

    private void add(int i) {
        minHeap.add(i);
        maxHeap.add(i);
    }

    private void removeMax() {
        if(maxHeap.isEmpty()) return;

        int i = maxHeap.poll();
        minHeap.remove(i);
    }

    private void removeMin() {
        if(minHeap.isEmpty()) return;

        int i = minHeap.poll();
        maxHeap.remove(i);
    }

}