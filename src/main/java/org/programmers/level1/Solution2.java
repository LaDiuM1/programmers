package org.programmers.level1;

import java.util.HashMap;
import java.util.Map;

public class Solution2 {
    public int solution(String[] friends, String[] gifts) {

        /*
        [2024 KAKAO WINTER INTERNSHIP 가장 많이 받은 선물]

        문제 설명
            선물을 직접 전하기 힘들 때 카카오톡 선물하기 기능을 이용해 축하 선물을 보낼 수 있습니다.
            당신의 친구들이 이번 달까지 선물을 주고받은 기록을 바탕으로 다음 달에 누가 선물을 많이 받을지 예측하려고 합니다.

            두 사람이 선물을 주고받은 기록이 있다면, 이번 달까지 두 사람 사이에 더 많은 선물을 준 사람이 다음 달에 선물을 하나 받습니다.
            예를 들어 A가 B에게 선물을 5번 줬고, B가 A에게 선물을 3번 줬다면 다음 달엔 A가 B에게 선물을 하나 받습니다.
            두 사람이 선물을 주고받은 기록이 하나도 없거나 주고받은 수가 같다면, 선물 지수가 더 큰 사람이 선물 지수가 더 작은 사람에게 선물을 하나 받습니다.
            선물 지수는 이번 달까지 자신이 친구들에게 준 선물의 수에서 받은 선물의 수를 뺀 값입니다.
            예를 들어 A가 친구들에게 준 선물이 3개고 받은 선물이 10개라면 A의 선물 지수는 -7입니다. B가 친구들에게 준 선물이 3개고 받은 선물이 2개라면 B의 선물 지수는 1입니다.
            만약 A와 B가 선물을 주고받은 적이 없거나 정확히 같은 수로 선물을 주고받았다면, 다음 달엔 B가 A에게 선물을 하나 받습니다.
            만약 두 사람의 선물 지수도 같다면 다음 달에 선물을 주고받지 않습니다.
            위에서 설명한 규칙대로 다음 달에 선물을 주고받을 때, 당신은 선물을 가장 많이 받을 친구가 받을 선물의 수를 알고 싶습니다.

            친구들의 이름을 담은 1차원 문자열 배열 friends 이번 달까지 친구들이 주고받은 선물 기록을 담은 1차원 문자열 배열 gifts가 매개변수로 주어집니다.
            이때, 다음달에 가장 많은 선물을 받는 친구가 받을 선물의 수를 return 하도록 solution 함수를 완성해 주세요.
         */

        Map<String, Integer> presentCountMap = new HashMap<>();
        Map<String, Integer> presentPointMap = new HashMap<>();
        Map<String, Map<String, Integer>> giftCountKeyMap = new HashMap<>();

        for (String gift : gifts) {
            String[] giftArr = gift.split(" ");
            String giverName = giftArr[0];
            String takerName = giftArr[1];


            if (giftCountKeyMap.containsKey(giverName)) {
                Map<String, Integer> takerMap = giftCountKeyMap.get(giverName);
                takerMap.put(takerName, takerMap.getOrDefault(takerName, 0) + 1);
                giftCountKeyMap.put(giverName, takerMap);
            }
            else {
                Map<String, Integer> initialTakerMap = new HashMap<>();
                initialTakerMap.put(takerName, 1);
                giftCountKeyMap.put(giverName, initialTakerMap);
            }

            presentPointMap.put(giverName, presentPointMap.getOrDefault(giverName, 0) + 1);
            presentPointMap.put(takerName, presentPointMap.getOrDefault(takerName, 0) - 1);
        }

        for (int i = 0; i < friends.length; i++) {
            for (int j = 0; j < friends.length; j++) {
                if (i == j) continue;
                String giver = friends[i];
                String taker = friends[j];

                int give = 0;
                int take = 0;

                if(giftCountKeyMap.containsKey(giver)) give = giftCountKeyMap.get(giver).getOrDefault(taker, 0);
                if(giftCountKeyMap.containsKey(taker)) take = giftCountKeyMap.get(taker).getOrDefault(giver, 0);

                if (give > take) {
                    presentCountMap.put(giver, presentCountMap.getOrDefault(giver, 0) + 1);
                } else if (give == take) {
                    int giverPresentPoint = presentPointMap.getOrDefault(giver, 0);
                    int takerPresentPoint = presentPointMap.getOrDefault(taker, 0);

                    if (giverPresentPoint > takerPresentPoint) {
                        presentCountMap.put(giver, presentCountMap.getOrDefault(giver, 0) + 1);
                    }
                }
            }
        }

        int result = 0;

        for (int value : presentCountMap.values()) {
            if (value > result) result = value;
        }

        return result;
    }
}
