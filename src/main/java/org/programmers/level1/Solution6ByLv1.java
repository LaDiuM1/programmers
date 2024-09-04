package org.programmers.level1;

import org.programmers.Application;

import java.util.*;

public class Solution6ByLv1 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/92334]
     * 2022 KAKAO BLIND RECRUITMENT
     * 신고 결과 받기
     *
     * 구현 기초 강화를 위한 기본 문제 풀이 진행
     */

    public int[] solution(String[] id_list, String[] report, int k) {
        LinkedHashMap<String, Integer> userMap = new LinkedHashMap<>();
        Map<String, Set<String>> reportMap = new HashMap<>();

        for(String id : id_list) {
            userMap.put(id, 0);
            reportMap.put(id, new HashSet<>());
        }

        for(String users : report) {
            String[] splitUsers = users.split(" ");
            String reporter = splitUsers[0];
            String reported = splitUsers[1];

            reportMap.get(reported).add(reporter);
        }

        for(Map.Entry<String, Set<String>> entry : reportMap.entrySet()) {
            if(entry.getValue().size() >= k) {
                for (String reporter : entry.getValue()) {
                    userMap.put(reporter, userMap.get(reporter) + 1);
                }
            }
        }

        return userMap.values().stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        Solution6ByLv1 application = new Solution6ByLv1();
        String[] id_list = {"muzi", "frodo", "apeach", "neo"};
        String[] report = {"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"};
        int k = 2;

        int[] result = application.solution(id_list, report, k);
        // [2,1,1,0]

        System.out.println("result = " + Arrays.toString(result));
    }
}
