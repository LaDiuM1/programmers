package org.programmers.level2;

import java.util.PriorityQueue;

public class Solution18ByLv2 {

    /**
     *  [https://school.programmers.co.kr/learn/courses/30/lessons/150368]
     *   2023 KAKAO BLIND RECRUITMENT 이모티콘 할인행사
     *
     *   이 문제는 모든 할인율에 대한 경우의 수를 체크하는 문제로 생각되어 DFS를 이용하여
     *   완전탐색을 진행하여 해결하였다.
     *
     */

    int[] discountTable = { 10, 20, 30, 40 };
    int[] emoticons;
    int[][] users;

    PriorityQueue<int[]> queue = new PriorityQueue<>();

    public int[] solution(int[][] users, int[] emoticons) {
        this.emoticons = emoticons;
        this.users = users;
        queue = new PriorityQueue<>(
                (o1, o2) -> o1[0] == o2[0] ? Integer.compare(o2[1], o1[1])
                        : Integer.compare(o2[0], o1[0])
        );

        int[] discounts = new int[emoticons.length];

        dfs(discounts, 0);

        return queue.peek();
    }

    public void dfs(int[] discounts, int index) {
        if (index == emoticons.length) {
            calculateResult(discounts);
            return;
        }

        for (int i = 0; i < 4; i++) {  // 10%, 20%, 30%, 40% 할인율
            discounts[index] = i;
            dfs(discounts, index + 1);
        }
    }

    public void calculateResult(int[] discounts) {
        int totalSubscribersCount = 0;
        int totalSalesAmount = 0;

        for (int[] user : users) {
            int sumSalesAmount = 0;

            for (int j = 0; j < emoticons.length; j++) {
                int discountRate = discountTable[discounts[j]];

                if (discountRate >= user[0]) {
                    int price = emoticons[j];
                    sumSalesAmount += price - (price * discountRate / 100);
                }
            }

            if (sumSalesAmount >= user[1]) {
                totalSubscribersCount++;
            } else {
                totalSalesAmount += sumSalesAmount;
            }
        }

        queue.add(new int[]{totalSubscribersCount, totalSalesAmount});
    }

}