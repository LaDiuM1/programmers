package org.programmers.level3;

import java.util.*;
import java.util.stream.Collectors;

public class Solution8ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/64064]
     * 2019 카카오 개발자 겨울 인턴십
     * 불량 사용자
     *
     * 문제 자체는 일반적인 완전 탐색이나 최종적으로 경우의 수를 체크한다는 점에서
     * 조금 다른 문제였다, DFS로 완전 탐색 후 독립적인 케이스인지 확인하기 위해
     * Set 안에 Set<String>을 추가하여 총 개수를 확인하여 문제를 해결하였다.
     *
     */

    Set<Set<String>> resultSet = new HashSet<>(); // 요소 내부들의 동일 여부 판단하여 독립적인 경우인지 확인

    public int solution(String[] user_id, String[] banned_id) {

        dfs(new HashSet<>(),user_id, banned_id, 0);

        return resultSet.size();
    }

    private void dfs(Set<String> current, String[] user_id, String[] banned_id, int index) {
        if(index == banned_id.length) {
            resultSet.add(new HashSet<>(current));
            return;
        }

        for (int i = 0; i < user_id.length; i++) {
            if(current.contains(user_id[i])) continue;

            if (checkValid(user_id[i], banned_id[index])) {
                current.add(user_id[i]);
                dfs(current, user_id, banned_id, index + 1);
                current.remove(user_id[i]);
            }
        }
    }

    private boolean checkValid(String now, String ban) {
        if(ban.length() != now.length()) return false;

        for(int i = 0; i < ban.length(); i++) {
            if(ban.charAt(i) == '*') continue;
            if(ban.charAt(i) != now.charAt(i)) return false;
        }

        return true;
    }

}