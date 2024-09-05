package org.programmers.level3;

import org.programmers.Application;

import java.util.LinkedList;
import java.util.Queue;

public class Solution21ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/150365]
     * 2023 KAKAO BLIND RECRUITMENT
     * 미로 탈출 명령어
     *
     * 이 문제는 시작 위치에서 목표지점까지 k 횟수에 맞추어 이동하며 이동 경로를 명령어 ['d', 'l', 'r', 'u']로
     * 목표지점까지 도착했을 때 경로를 반환하는 문제이며 동일 경로 중 사전 순으로 가장 빠른 경로를 반환하면 되는 문제이다.
     * 맨 처음 떠오른 접근 방법은 bfs로 탐색을 진행하고 탐색 방향을 사전 순으로 빠른순 ['d', 'l', 'r', 'u']으로 탐색 후
     * 목표 지점에 도달 시 정해진 횟수로 도달하면 경로를 반환, 아직 남은 거리가 있다면 남은 거리의 나머지가 짝수일 때만
     * 추가로 이동하도록 구상을 하였고 해당 방법으로 구현을 진행하였다.
     * 구현 완료 후 제출을 진행하니 몇몇 문제에서 시간 초과가 발생하여 제한 사항을 다시 살펴보니 k(지정 횟수)가 무려 2500회로
     * 목표 지점에 도달 후 이동 가능 여부를 확인하는 건 매우 많은 시도가 발생할 수 있었다.
     * 그래서 이동 가능 여부를 초기와, 이동 순간순간마다 체크해야 된다고 생각하여 이동 가능 여부를 확인하는 논리를 구상하였다,
     * 요점은 2차원 좌표에서 현재 좌표와 목표 좌표까지 이동 시 거리가 반드시 짝수 또는 홀수, 둘 중 하나라는 것이다.
     * 그렇다면 현재 좌표로 이동 가능한 거리가 짝수인지 홀수인지 확인하고 지정 목표 횟수의 홀, 짝 여부와 비교하면 될 것으로 보였다.
     * 그래서 현재 좌표와 목표 좌표의 각 y축과 x축을 서로 뺀 값의 절대값을 구하고, 각 축을 더한 값이 짝수, 홀수인지 판단하고 이를 k와 비교하여
     * 각 시행마다 조건을 확인하여 조건이 맞지 않을 시 다음 시행으로 넘어가는 방법으로 최적화를 진행하였다.
     * 해당 조건을 코드에 적용 후 제출했을 때 시간은 크게 단축되었으나 여전히 몇몇 케이스에서 시간 초과가 발생하였다.
     * 그래서 추가적인 최적화가 필요하다 판단하고 추가적인 최적화를 구상하였다,
     * 두 번째로 떠오른 방법은 특정 위치에 도달 시 이동 거리가 같은 경로들은 중복 경로로 판단 가능하다는 아이디어가 떠올랐다.
     * 그래서 현재 문제는 재방문이 가능한 문제이나, 중복 이동 방지를 위해 2차원 배열의 방문 여부와, 이동 거리를 추가한 3차원 배열을 선언하여
     * 각 방문시마다 특정 좌표에 방문 거리를 저장하고, 이동 시 이동할 좌표에 이동 거리가 동일할 시 방문을 하지 않는 코드를 추가하여 최적화를
     * 추가로 진행하였으며, 2차 최적화된 코드를 제출하여 모든 테스트를 정상적으로 통과 완료하였다.
     *
     * 이 문제는 레벨3 문제 중 현재까지 가장 어려운 난이도의 문제였으며 문제를 푸는데 2시간이 넘게 걸린, 개인적으로 아주 어려운 문제였다.
     * dfs나 다른 풀이 방법도 있을 것이나 bfs를 통한 구현이 불필요한 연산이 가장 적을 것이라 생각하였고, bfs에서도 여러 최적화를
     * 거쳐야 제한 시간을 지킬 수 있는 상당히 난이도 높은 문제였다, 문제를 얼핏 보면 흔한 문제처럼 보이나 여러 최적화가 필요한
     * 어렵지만 재밌는 문제로 비슷한 문제들을 또 도전하고 싶다는 생각이 드는 도전 욕구를 불러오는 재밌는 문제였다.
     *
     */

    private static class Node {
        int x, y, distance;
        String path;

        public Node(int x, int y, int distance, String path) {
            this.x = x;
            this.y = y;
            this.distance = distance;
            this.path = path;
        }
    }

    int[][] vector = {
            {1, 0},
            {0, -1},
            {0, 1},
            {-1, 0}
    };

    char[] commend = {'d', 'l', 'r', 'u'};
    boolean[][][] visited;

    public String solution(int n, int m, int y, int x, int r, int c, int k) {
        visited = new boolean[n][m][k + 1];
        // 동일한 위치에서 동일 횟수면 방문 필요없으므로 방문 여부 확인 배열 선언

        // 도달 가능 여부 체크
        int minDistance = Math.abs(r - y) + Math.abs(c - x);
        if (minDistance > k || (k - minDistance) % 2 != 0) {
            return "impossible";
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(x - 1, y - 1, 0, ""));
        return bfs(queue, n, m, r - 1, c - 1, k);
    }

    private String bfs(Queue<Node> queue, int n, int m, int r, int c, int k) {
        while(!queue.isEmpty()) {
            Node now = queue.poll();

            if (k == now.distance && now.y == r && now.x == c) {
                return now.path;
            }

            for(int i = 0; i < vector.length; i++) {
                int nextX = now.x + vector[i][1];
                int nextY = now.y + vector[i][0];
                int nextDistance = now.distance + 1;
                String newPath = now.path + commend[i];

                // 좌표가 범위 안에 있고, 방문하지 않았으며, 남은 이동 횟수가 충분한지 확인
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n
                        && !visited[nextY][nextX][nextDistance]
                        && Math.abs(nextX - c) + Math.abs(nextY - r) <= k - nextDistance) {

                    visited[nextY][nextX][nextDistance] = true;
                    queue.add(new Node(nextX, nextY, nextDistance, newPath));
                }
            }
        }

        return "impossible";
    }

    public static void main(String[] args) {
        Application application = new Application();
        int n = 3;
        int m = 4;
        int x = 2;
        int y = 3;
        int r = 3;
        int c = 1;
        int k = 5;


        //n	m	x	y	r	c	k	result
        //3	4	2	3	3	1	5	"dllrl"
        //2	2	1	1	2	2	2	"dr"
        //3	3	1	2	3	3	4	"impossible"

        String result = application.solution(n, m, x, y, r, c, k);
        // dllrl

        System.out.println("result = " + result);
    }

}