package org.programmers.level3;

import java.util.PriorityQueue;

public class Solution5ByLv3 {

    /**
     * 현재 문제는 각 경기마다 숫자를 비교하여 승패가 갈라지는 심플한 문제로
     * heap 구조를 이용하여 간단하게 풀 수 있는 문제였다.
     * 두 배열을 각각 오름차순 우선순위 큐에 추가한 후
     * 첫번째 큐의 최솟값을 꺼내 두번째 큐의 최솟값과 비교 후
     * 두번째 큐의 요소가 더 크다면 승점 + 1하고 첫번째 큐의 다음 요소로
     * 순회하면 되는 문제이므로 구상 및 구현이 간단한 쉬운 문제였다.
     *
     */

    PriorityQueue<Integer> aMinHeap = new PriorityQueue<>();
    PriorityQueue<Integer> bMinHeap = new PriorityQueue<>();

    public int solution(int[] A, int[] B) {
        int result = 0;

        for(int i = 0; i < A.length; i++) {
            aMinHeap.add(A[i]);
            bMinHeap.add(B[i]);
        }

        while(!aMinHeap.isEmpty()) {
            if(bMinHeap.isEmpty()) break;
            int a = aMinHeap.poll();

            while(!bMinHeap.isEmpty()) {
                int b = bMinHeap.poll();
                if(b > a) {
                    result++;
                    break;
                }
            }
        }

        return result;
    }

}