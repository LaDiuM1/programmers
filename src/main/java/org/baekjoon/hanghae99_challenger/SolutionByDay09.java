package org.baekjoon.hanghae99_challenger;

import java.util.HashMap;
import java.util.Map;

public class SolutionByDay09 {

    /**

     [https://school.programmers.co.kr/learn/courses/30/lessons/77486]
     2021 Dev-Matching: 웹 백엔드 개발자(상반기)
     다단계 칫솔 판매

     -- 문제 분석 및 해결
     이 문제는 약 두 달 전에 풀어본 적 있는 문제이다. 항해 99 플랫폼은 백준과 프로그래머스를
     플랫폼으로 사용하므로 풀어본 문제도 이렇게 나오는 게 가능하다. 예전에 이 문제를 풀 때는
     최대한 단순하게 해시맵 자료구조와 재귀를 이용해 풀었는데 이번에는 코드를 초기화하고 좀
     더 실제 구현에 가까운 객체 형태로 선언하여 문제를 해결하였고 예전과 달라진 점이라면 이
     런 자료구조 형태를 예전에는 그냥 해시 구조로 인식하고 사용하였으나 현재는 트리 구조라는
     것을 명확하게 인지한 상태에서 문제를 풀었다는 점이다. 풀고 나니 결과적으로 풀이 방법은
     사실상 동일하다는 걸 코드 비교를 통해 알 수 있었다.

     */

    static final int PRICE_UNIT = 100;

    private static class Seller {
        String name;
        int totalSalePrice;

        public Seller (String name, int totalSalePrice) {
            this.name = name;
            this.totalSalePrice = totalSalePrice;
        }
    }

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        Map<String, Seller> sellerMap = new HashMap<>();
        Map<Seller, Seller> treeMap = new HashMap<>();
        int sellerCount = enroll.length;
        int saleCount = amount.length;

        // 판매자 맵 초기화
        sellerMap.put("center", new Seller("center", 0));
        for(int i = 0; i < sellerCount; i++) {
            sellerMap.put(enroll[i], new Seller(enroll[i], 0));
        }
        // 다단계 트리 구조 초기화
        for(int i = 0; i < sellerCount; i++) {
            String sellerName = referral[i].equals("-") ? "center" : referral[i];
            treeMap.put(sellerMap.get(enroll[i]), sellerMap.get(sellerName));
        }
        // 판매량을 순회하여 다단계 구조에 이익 분배
        for(int i = 0; i < saleCount; i++) {
            sale(sellerMap.get(seller[i]), amount[i] * PRICE_UNIT, treeMap);
        }
        // 판매자별 총 판매액 결과 저장
        int[] result = new int[sellerCount];
        for(int i = 0; i < sellerCount; i++) {
            result[i] = sellerMap.get(enroll[i]).totalSalePrice;
        }

        return result;
    }

    private void sale(Seller current, int salePrice, Map<Seller, Seller> treeMap) {
        if(salePrice == 0) return;

        if(!treeMap.containsKey(current)) {
            current.totalSalePrice += salePrice;
            return;
        }

        current.totalSalePrice += salePrice - (salePrice / 10);
        sale(treeMap.get(current), salePrice / 10, treeMap);
    }
}
