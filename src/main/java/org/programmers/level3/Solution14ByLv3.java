package org.programmers.level3;

import java.util.*;

public class Solution14ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/77486]
     * 2021 Dev-Matching: 웹 백엔드 개발자(상반기)
     * 다단계 칫솔 판매
     *
     * 지문 그대로 다단계를 구현하면 되는 문제로, 개인적으로 간단한 문제였다.
     * HashMap으로 참조인을 판별하고 이익의 10%를 분배하여 0이 될 때까지 반복하는 재귀함수를 만들고
     * 각 사원마다의 누적액도 별도의 HashMap을 선언하여 각 사원마다의 집계액을 산출하여 반환하는 간단한 구현으로 해결하였다.
     */

    Map<String, String> referMap = new HashMap<>();
    Map<String, Integer> pointMap = new HashMap<>();

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] result = new int[enroll.length];

        for(int i = 0; i < enroll.length; i++) {
            pointMap.put(enroll[i], 0);
            if(!referral[i].equals("-")) {
                referMap.put(enroll[i], referral[i]);
            }
        }

        for(int i = 0; i < seller.length; i++) {
            recursive(seller[i], amount[i] * 100);
        }

        for(int i = 0; i < enroll.length; i++) {
            result[i] = pointMap.get(enroll[i]);
        }

        return result;
    }

    private void recursive(String seller, int amount) {
        if(amount == 0) return;

        if(!referMap.containsKey(seller)) {
            pointMap.put(seller, pointMap.get(seller) + amount - amount / 10);
        } else {
            pointMap.put(seller, pointMap.get(seller) + amount - amount / 10);
            recursive(referMap.get(seller), amount / 10);
        }
    }

}