package org.programmers.level3;

import java.util.*;

public class Solution11ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/43164]
     * 여행경로
     *
     * 보유 중인 여행 티켓으로 모든 경로를 탐색하는 문제이며 경로가 여러 개라면 문자열 순으로 가장 빠른 경로를
     * 반환하는 문제이다, 기본적으로는 dfs와 재귀를 이용하였으며 각 케이스는 스택을 이용 결과 탐색 후,
     * 우선순위 큐로 정렬을 커스텀 하여 모든 요소에 대해 가장 빠른 문자열을 반환하도록 문제를 해결하였다.
     *
     */

    PriorityQueue<Stack<String>> resultList = new PriorityQueue<>(
            (o1, o2) -> {
                Iterator<String> iter1 = o1.iterator();
                Iterator<String> iter2 = o2.iterator();

                while(iter1.hasNext() && iter2.hasNext()) {
                    int compare = iter1.next().compareTo(iter2.next());
                    if(compare != 0) return compare;
                }
                return 0;
            }
    );

    public String[] solution(String[][] tickets) {
        dfs(0,"ICN", tickets, new Stack<>(), new boolean[tickets.length]);

        return resultList.poll().toArray(new String[0]);
    }

    private void dfs(int index, String key, String[][] tickets, Stack<String> current, boolean[] visited) {
        if (current.size() == tickets.length) {
            current.add(tickets[index][1]);
            Stack<String> newStack = new Stack<>();
            newStack.addAll(current);
            resultList.add(newStack);
            return;
        }

        current.push(key);

        for(int i = 0; i < tickets.length; i++) {
            if(tickets[i][0].equals(key) && !visited[i]) {
                visited[i] = true;
                dfs(i, tickets[i][1], tickets, current, visited);
                current.pop();
                visited[i] = false;
            }
        }
    }

}