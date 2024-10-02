package org.programmers.level2;

import org.programmers.Application;

import java.util.HashMap;
import java.util.Map;

public class Solution24ByLv2 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/340211]
     * [PCCP 기출문제] 3번 / 충돌위험 찾기
     *
     * 이 문제는 2차원 좌표에서 특정 좌표가 적혀 있는 포인트들과, 각 로봇들의 포인트 경로가
     * 문제로 주어지며 로봇이 동시에 움직였을 때 동일한 시간대에서 특정 좌표가 겹쳐 충돌 할
     * 수 있는 위험 횟수를 반환하는 문제이다.
     *
     * 이 문제는 간단하게 구상할 수 있다, 지문은 동시에 이동할 때이지만 코드적으로는 특정
     * 시간대에서 동일한 좌표를 공유하는지 여부만 확인하면 된다. 문제는 최단 경로가 여러개
     * 일 경우였는데, 조건에서 r 좌표부터 이동으로 조건이 주어졌으니 문제 해결은 명확하다.
     * map에 String으로 로봇의 좌표와 시간을 쉼표로 구분하여 key에 넣고, 동일한 키가 입
     * 력으로 들어오면 value에 +1을 해주면 된다. 최종적으로 map을 순회하여 value가 1을
     * 초과하는 카운트를 반환하면 문제가 해결 될 것이다.
     *
     * 코드는 routes를 순회하여 첫번째 포인트를 시작 위치로 지정해주고 routes의 두번째
     * 포인트부터 순회하여 end 좌표로 지정해주면서 x와 y값이 목표에 도달할 때까지 while
     * 문으로 반복해주고 각 이동마다 시간을 증분시키며 map에 넣어주어 문제를 해결하였다.
     *
     */

    public int solution(int[][] points, int[][] routes) {
        Map<String, Integer> map = new HashMap<>();

        for(int i = 0; i < routes.length; i++) {
            int second = 0;
            int x = points[routes[i][0]-1][0];
            int y = points[routes[i][0]-1][1];
            pushMap(x, y, second, map);

            for(int j = 1; j < routes[i].length; j++) {
                int endX = points[routes[i][j]-1][0];
                int endY = points[routes[i][j]-1][1];

                while(x != endX || y != endY) {
                    if(x < endX) {
                        x++;
                    } else if (x > endX) {
                        x--;
                    } else if(y < endY) {
                        y++;
                    } else if(y > endY) {
                        y--;
                    }
                    second++;
                    pushMap(x, y, second, map);
                }
            }
        }

        return (int) map.values().stream().filter(i -> i > 1).count();
    }

    private void pushMap(int x, int y, int second, Map<String, Integer> map) {
        String now = x + "," + y + "," + second;
        map.put(now, map.getOrDefault(now, 0) + 1);
    }

    public static void main(String[] args) {
        Solution24ByLv2 application = new Solution24ByLv2();

        int[][] points = {{3, 2}, {6, 4}, {4, 7}, {1, 4}};
        int[][] routes = {{4, 2}, {1, 3}, {2, 4}};

        int[][] points2 = {{3, 2}, {6, 4}, {4, 7}, {1, 4}};
        int[][] routes2 = {{4, 2}, {1, 3}, {4, 2}, {4, 3}};

        int[][] points3 = {{2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}};
        int[][] routes3 = {{2, 3, 4, 5}, {1, 3, 4, 5}};

        int solution = application.solution(points, routes);
        int solution2 = application.solution(points2, routes2);
        int solution3 = application.solution(points3, routes3);
        System.out.println(solution);
        System.out.println(solution2);
        System.out.println(solution3);
    }

}