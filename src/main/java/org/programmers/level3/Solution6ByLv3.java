package org.programmers.level3;

import java.util.Arrays;

public class Solution6ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/42884]
     * 탐욕법(Greedy)
     * 단속카메라
     *
     * level2에서 많이 풀어본 유형으로 사이트 내에서 바로 풀 수 있는 문제였다.
     * 카테고리가 탐욕법이라고 되어있는데 다른 사람들의 풀이도 비슷한 걸로 봐서는
     * 이런 형식으로 문제 해결하는 것을 탐욕법이라고 부르나보다.
     *
     */

    public int solution(int[][] routes) {
        int result = 1;

        Arrays.sort(routes, (o1, o2) ->
                Integer.compare(o1[1], o2[1])
        );

        int nowEndPoint = routes[0][1];
        for(int i = 1; i < routes.length; i++) {
            if(routes[i][0] > nowEndPoint) {
                result++;
                nowEndPoint = routes[i][1];
            }
        }

        return result;
    }

}