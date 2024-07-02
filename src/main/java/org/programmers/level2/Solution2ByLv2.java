package org.programmers.level2;

import java.util.*;

public class Solution2ByLv2 {

    public int solution(int[] picks, String[] minerals) {
        int answer = 0;

        Map<Integer, List<String>> sumMineralPointsPerPickDescRank = new TreeMap<>(Comparator.reverseOrder());
        Map<String, Integer> pointTable = new HashMap<>();
        pointTable.put("diamond", 25);
        pointTable.put("iron", 5);
        pointTable.put("stone", 1);

        int sumMineralPointsPerPick = 0;
        List<String> mineralsPerPickList = new ArrayList<>();
        for(int i = 1; i <= minerals.length; i++) {
            String mineralName = minerals[i - 1];

            sumMineralPointsPerPick += pointTable.get(mineralName);
            mineralsPerPickList.add(mineralName);

            if(i % 5 == 0 || i == minerals.length) {
                sumMineralPointsPerPickDescRank.put(sumMineralPointsPerPick, mineralsPerPickList);
                sumMineralPointsPerPick = 0;
                mineralsPerPickList = new ArrayList<>();
            }
        }

        for (List<String> mineMineralList : sumMineralPointsPerPickDescRank.values()) {
            int pickPoint = 1;
            int index = -1;

            boolean soldoutFlag = true;

            for(int j = 0; j < picks.length; j++) {
                if(picks[j] == 0) continue;

                pickPoint = j == 0 ? 25 : j == 1 ? 5 : 1;
                index = j;
                soldoutFlag = false;
                break;
            }

            if(soldoutFlag) return answer;

            for(String mineMineralName : mineMineralList) {
                int mineralPoint = pointTable.get(mineMineralName);
                answer += Math.max(mineralPoint / pickPoint , 1);
            }
            picks[index]--;
        }
        return answer;
    }

}
