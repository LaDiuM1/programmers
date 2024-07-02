package org.programmers.level1;

import java.util.HashMap;
import java.util.Map;

public class Solution2ByLv1 {
    public int solution(String[] friends, String[] gifts) {

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
