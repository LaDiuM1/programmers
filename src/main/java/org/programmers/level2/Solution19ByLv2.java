package org.programmers.level2;

import java.util.LinkedList;
import java.util.Queue;

public class Solution19ByLv2 {

    /**
     *  [https://school.programmers.co.kr/learn/courses/30/lessons/118667#]
     *   2022 KAKAO TECH INTERNSHIP  두 큐 합 같게 만들기
     *
     *   구상 자체는 1, 2번의 큐의 각 합산값 중 큰 쪽의 요소를 제거 후 작은 쪽에
     *   추가하면서 루프한다면 쉽게 해결 될 수 있는 문제였으나 합을 동일하게 만들 수 없는 경우를
     *   판단하기가 쉽지 않았다, 아주 여유롭게 반복 횟수를 설정한다면 문제 해결은 가능하나
     *   필요 횟수를 정확히 설정하지 않는다면 이는 불필요한 순회를 유발한다. 그래서 수학적 사고로
     *   필요 이동 경로의 수를 도출하는 과정이 쉽지 않았다,
     *
     *   최대 반복 횟수를 구하는 과정은 아래와 같았다.
     *   일단 두 큐가 추가, 제거되는 과정은 동일한 배열이 무한으로 순환되는 원의 형태이다.
     *   이 중 두 큐에서 상호 요소가 추가, 제거됨은 원에서 각 큐가 차지하는 지분의 변경을 의미하며
     *   한쪽 요소가 모두 변경되는 횟수는 배열 길이와 동일하다. 그렇다면 단순히 양쪽 요소가 전부 순환되는 경우는
     *   2n이고, 모두 순환 완료된 시점에서는 초기 배열과 동일하니 2n - 1일 것이다. 현재 코드에서는 한쪽 최대치에서
     *   다른쪽 최대치(근사값)로 이동해야 하는 경우를 고려하여 최대 이동 횟수를 3n으로 설정하였다. 수학적으로 따지면
     *   3n - 3 정도가 더 정확할 수 있으나 현재 제한사항 수준에서는 3회의 연산은 무시하도록 한다.
     *
     */

    public int solution(int[] arr1, int[] arr2) {
        Queue<Integer> queue1 = new LinkedList<>();
        Queue<Integer> queue2 = new LinkedList<>();

        int count = 0;
        long sumQueue1 = 0;
        long sumQueue2 = 0;

        for(int i = 0; i < arr1.length; i++) {
            sumQueue1 += arr1[i];
            queue1.add(arr1[i]);

            sumQueue2 += arr2[i];
            queue2.add(arr2[i]);
        }

        while(sumQueue1 != sumQueue2) {
            count++;

            if(count == arr1.length * 3) {
                count = -1;
                break;
            }

            if(sumQueue1 > sumQueue2 && !queue1.isEmpty()) {
                sumQueue1 -= queue1.peek();
                sumQueue2 += queue1.peek();
                queue2.add(queue1.poll());
            } else if (sumQueue2 > sumQueue1 && !queue2.isEmpty()) {
                sumQueue2 -= queue2.peek();
                sumQueue1 += queue2.peek();
                queue1.add(queue2.poll());
            }
        }

        return count;
    }

}