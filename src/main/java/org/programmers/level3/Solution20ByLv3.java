package org.programmers.level3;

import java.util.LinkedList;
import java.util.Queue;

public class Solution20ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/87694]
     * 깊이/너비 우선 탐색(DFS/BFS)
     * 아이템 줍기
     *
     * 이 문제는 여러 직사각형이 겹쳐진 형태에서 가장 바깥쪽의 테두리를 따라 목적지에 도달하는 문제로
     * 맨 처음 접근은 배열에서 각 직사각형의 범위로 배열을 채우고 내부를 0으로 비워 정해진 경로를 bfs로
     * 탐색하려고 하였다. 하지만 실제 도형을 그리고 나니 문제가 생겼다, 배열 하나당 가장 작은 사각형 한개를
     * 1:1 매칭하여 도형을 그렸으나 실제 지문에 나와있는 테두리 이동은 각 사각형을 4등분한 모서리를 따라 움직임을
     * 보여 배열만 따라 이동해서는 테두리의 움직임을 구현할 수 없었다. 그렇다면 사각형을 4등분을 한 움직임을
     * 구현하기 위해 고민을 많이 하였는데, 사각형이 4분위로 움직일 수 있는 모양이 되려면 기존 배열 사이즈에서
     * 가로, 세로의 크기가 각각 2배수가 되면 정사각형이 4등분되며 이 4등분된 사각형의 바깥쪽에 1을 그려주면 그게
     * 곧 테두리 이동이 될 것이다, 문제를 해결하였으니 구현만 남았다.
     *
     * -- 구현
     * 문제의 제한사항인 1부터 50까지의 도형의 +1 사이즈인 52의 2배수인 104 사이즈의 int 2차원 배열을 선언
     * [중간에 도형의 모양을 확인하기 위해 boolean이 아닌 int로 선언]
     * 직사각형 모양의 2배수로 배열을 1로 채움
     * 그리고 직사각형 모양의 2배수의 시작지점 +1, 종료지점 -1의 영역을 0으로 채워 내부를 비워준다.
     * 이후 bfs 탐색을 시작하고 각 시작 캐릭터의 위치, 아이템의 위치를 2배수로 매개변수로 전달.
     * 목적지에 도달하면 이동 거리를 나누기 2하여 최종 이동거리를 반환하여 해결
     *
     */

    int[][] vector = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    boolean[][] visited;

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int[][] maps = new int[104][104];
        visited = new boolean[104][104];
        Queue<int[]> queue = new LinkedList<>();

        for(int[] rect : rectangle) {
            int x1 = rect[0] * 2;
            int y1 = rect[1] * 2;
            int x2 = rect[2] * 2;
            int y2 = rect[3] * 2;

            for(int y = y1; y <= y2; y++) {
                for(int x = x1; x <= x2; x++) {
                    maps[y][x] = 1;
                }
            }
        }

        for(int[] rect : rectangle) {
            int x1 = rect[0] * 2 + 1;
            int y1 = rect[1] * 2 + 1;
            int x2 = rect[2] * 2 - 1;
            int y2 = rect[3] * 2 - 1;

            for(int y = y1; y <= y2; y++) {
                for(int x = x1; x <= x2; x++) {
                    maps[y][x] = 0;
                }
            }
        }

        queue.add(new int[]{characterY * 2, characterX * 2, 0});
        return bfs(queue, maps, itemX * 2, itemY * 2);
    }

    private int bfs(Queue<int[]> queue, int[][] maps, int itemX, int itemY) {

        while (!queue.isEmpty()) {
            int[] now = queue.poll();

            if(now[0] == itemY && now[1] == itemX) {
                return now[2] / 2;
            }

            for (int i = 0; i < vector.length; i++) {
                int nextY = now[0] + vector[i][0];
                int nextX = now[1] + vector[i][1];
                if (maps[nextY][nextX] == 1 && !visited[nextY][nextX]) {
                    visited[nextY][nextX] = true;
                    queue.add(new int[]{nextY, nextX, now[2] + 1});
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Solution20ByLv3 application = new Solution20ByLv3();
        int[][] rectangle = {{1,1,7,4},{3,2,5,5},{4,3,6,9},{2,6,8,8}};
        int characterX = 1;
        int characterY = 3;
        int itemX = 7;
        int itemY = 8;

        int result = application.solution(rectangle, characterX, characterY, itemX, itemY);

        System.out.println("result = " + result + (result == 17));
    }

}