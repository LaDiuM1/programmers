package org.programmers.level1;

import org.programmers.Application;

import java.util.*;

public class Solution4ByLv1 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/178871]
     * 연습문제
     * 달리기 경주
     *
     * 정렬 및 구현 기초 강화를 위한 기본 문제 풀이 진행
     */

    public String[] solution(String[] players, String[] callings) {
        Map<String, Integer> rankMap = new HashMap<>();
        Map<Integer, String> rankIndexMap = new HashMap<>();

        for(int i = 0; i < players.length; i++) {
            rankMap.put(players[i], i);
            rankIndexMap.put(i, players[i]);
        }

        for(String catcher : callings) {
            int i = rankMap.get(catcher);
            String captured = rankIndexMap.get(i - 1);

            rankMap.put(catcher, i - 1);
            rankMap.put(captured, i);
            rankIndexMap.put(i - 1, catcher);
            rankIndexMap.put(i, captured);
        }

        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(rankMap.entrySet());
        sortedList.sort((o1, o2) -> o1.getValue() - o2.getValue());

        return sortedList.stream().map(Map.Entry::getKey).toArray(String[]::new);
    }

    public static void main(String[] args) {
        Application application = new Application();
        String[] players = {"mumu", "soe", "poe", "kai", "mine"};
        String[] callings = {"kai", "kai", "mine", "mine"};


        String[] result = application.solution(players, callings);
        // ["mumu", "kai", "mine", "soe", "poe"]

        System.out.println("result = " + Arrays.toString(result));
    }
}
