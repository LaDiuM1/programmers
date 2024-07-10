package org.programmers.level2;

import java.util.*;

public class Solution2ByLv2 {
    static int lowestFatigue = Integer.MAX_VALUE;
    static Map<String, Integer> pointTable = new HashMap<>();
    public int solution(int[] picks, String[] minerals) {
        pointTable.put("diamond", 25);
        pointTable.put("iron", 5);
        pointTable.put("stone", 1);

        getLowestFatigue(picks, minerals, 0, 0);
        return lowestFatigue;
    }

    static public void getLowestFatigue(int[] picks, String[] minerals, int index, int currentFatigue) {
        if(currentFatigue > lowestFatigue) return;

        if(index > minerals.length -1 || picks[0] == 0 && picks[1] == 0 && picks[2] == 0) {
            lowestFatigue = currentFatigue;
            return;
        }

        for(int i = 0; i < picks.length; i++) {
            if(picks[i] == 0) continue;

            String pickName = i == 0 ? "diamond" : i == 1 ? "iron" : "stone";
            picks[i]--;
            int originalFatigue = currentFatigue;

            for(int j = index; j < 5 + index; j++) {
                int mineralPoint = pointTable.get(minerals[j]);
                int pickPoint = pointTable.get(pickName);
                currentFatigue += Math.max(mineralPoint / pickPoint , 1);
                if(j == minerals.length -1) break;
            }

            getLowestFatigue(picks, minerals, index + 5, currentFatigue);
            currentFatigue = originalFatigue;
            picks[i]++;
        }
    }
}
