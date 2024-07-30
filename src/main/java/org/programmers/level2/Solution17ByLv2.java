package org.programmers.level2;

import java.util.Stack;

public class Solution17ByLv2 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/150369#]
     *  2023 KAKAO BLIND RECRUITMENT 택배 배달과 수거하기
     *
     * 나는 이런 현실세계의 요소를 코테로 풀 때가 가장 재밌는 것 같다.
     * 이전 문제에서 스택을 본격적으로 사용해서 그런지 이 문제도 스택을 활용하여 문제를 해결하였고
     * 이번에도 프로그래머스에서만 코드를 작성하였다.
     *
     * 아래는 실제 단계별 구상을 적어가면서 해결했던 과정이다.
     *
     * 1차적 구상
     * 먼저 배달부터 하는 게 유리한가?
     * -> cap의 최대 수량은 정해져 있으므로 배달 후 수거하는 것이 유리해 보임.
     *
     * 먼 집부터 배달하는게 유리한가?
     * -> 배달과 수거가 동시에 이루어져야 하기 때문에 최대치를 항상 채울 수 있는
     * 먼 집부터 배달하는 게 유리해 보임.
     *
     * 완전탐색 알고리즘으로 해결 가능한지 구상.
     * -> 모든 경우의 수를 고려하면 10만 x 10만으로 비효율적
     *
     * 그렇다면,
     * 끝의 집부터 더하여 최대 배달 수량을 맞추고 가면서 배달,
     * 이후 오면서 최대 수량에서 수거를 차감해가면서 오면?
     * -> 스택을 사용하는 것이 좋아보인다.
     *
     * 구현 구상
     * Stack<인덱스, 수량> 배달과 픽업 두개 선언
     *
     * 두 요소를 같이 순회(길이는 동일)하면서 스택 초기화
     *
     * 배달 스택에서 첫번째 요소에 cap 수량만큼 차감,
     * 0이되면 최대 이동 거리 갱신,
     *
     * 이후 픽업 스택에서도 똑같이 차감 진행
     * 이후 최대 이동거리 * 2 를 결과 변수에 누적
     *
     * 두 stack이 빌 때까지 반복 후 결과 반환
     *
     */

    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long result = 0;
        Stack<int[]> deleveryStack = new Stack<>();
        Stack<int[]> pickupStack = new Stack<>();

        for(int i = 0; i < n; i++) {
            if(deliveries[i] > 0) deleveryStack.push(new int[]{i + 1, deliveries[i]});
            if(pickups[i] > 0) pickupStack.push(new int[]{i + 1, pickups[i]});
        }

        while(!deleveryStack.isEmpty() || !pickupStack.isEmpty()) {
            int nowQuantity = cap;
            long maximumDistance = 0;

            while(nowQuantity > 0) {
                if(deleveryStack.isEmpty()) break;

                int[] delivery = deleveryStack.peek();

                int calQuantity = Math.max(nowQuantity - delivery[1], 0);
                int calDelivery = Math.max(delivery[1] - nowQuantity, 0);

                nowQuantity = calQuantity;
                delivery[1] = calDelivery;

                maximumDistance = Math.max(maximumDistance, delivery[0]);

                if(delivery[1] == 0) deleveryStack.pop();
            }

            nowQuantity = cap;
            while(nowQuantity > 0) {
                if(pickupStack.isEmpty()) break;

                int[] pickup = pickupStack.peek();

                int calQuantity = Math.max(nowQuantity - pickup[1], 0);
                int calPickup = Math.max(pickup[1] - nowQuantity, 0);

                nowQuantity = calQuantity;
                pickup[1] = calPickup;

                maximumDistance = Math.max(maximumDistance, pickup[0]);

                if(pickup[1] == 0) pickupStack.pop();
            }

            result += maximumDistance * 2;
        }

        return result;
    }

}