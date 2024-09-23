package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Solution8ByGold4 {

    /**
     [https://www.acmicpc.net/problem/14502]
     삼성 SW 역량 테스트 기출 문제
     연구소

     이 문제는 2차원 배열 지도에서 벽을 세워 바이러스가 퍼져 나가는 것을 막는 문제이며,
     초기 지도 상태에서 추가로 벽을 3개 세운 후 바이러스가 퍼진 다음, 안전 구역이 가장
     많은 값을 반환하는 문제이다.

     문제의 지문에서는 반드시 벽을 3개 세워야 하며, 빈 곳이 최소 3개 이상 주어진다고 하였다.
     그렇다면 벽을 세울 수 있는 위치에 벽 3개를 세우는 모든 경우의 수에서 바이러스를 퍼뜨린
     후, 안전 구역 개수가 가장 많은 값을 세어서 반환하면 될 것이다.

     -- 구현
     DFS를 선언하여 벽을 세울 수 있는 위치에 벽을 세운 후 depth 증가, 이후 depth가 3(벽이
     3개)에 도달 시 바이러스를 퍼뜨리는 BFS를 호출, BFS에서는 배열을 복사하여(배열 크기가 최대 8x8로
     비용 소모가 크지 않다고 판단) 초기 바이러스 위치를 queue에 추가한 후, 벽에 막히기 전까지 모든
     방향으로 바이러스를 퍼뜨리는 로직 작성 (복사된 배열 상태 변경), 만약 바이러스를 퍼뜨리다가
     전역 변수에 저장된 이전의 바이러스 수를 초과하면 백트래킹하는 개념으로 탐색 즉시 종료.
     BFS를 모두 완료한 후, 안전 구역 개수를 배열에서 센 후 전역 변수의 안전 구역 최대값과 확인하여
     안전 구역이 더 많을 시 현재의 안전 구역을 저장하고 바이러스 개수도 동시에 저장.
     최종적으로 DFS로 모든 경우의 수를 순회하여 최종적으로 가장 높은 안전 구역 변수값 반환.

     -- 최종평
     이 문제를 해결하며 가장 크게 느낀 점은, 이제 BFS와 DFS가 어느 정도 익숙해졌다는 것이다.
     지문을 읽고, 해결을 구상하고, 구현 후 테스트 - 통과까지 일사천리로 진행되었으며, 한 번의 막힘
     없이 구현이 매끄럽게 이루어졌다는 점에서 큰 보람을 느꼈다. 더 복잡한 DFS, BFS 활용 문제에도
     도전하고 싶어진다.

     */

    static int maximumSafetyArea = -1;
    static int miniMumInspectionArea = Integer.MAX_VALUE;
    static int[][] vector = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splitNowLine = br.readLine().split(" ");
        int n = Integer.parseInt(splitNowLine[0]);
        int m = Integer.parseInt(splitNowLine[1]);

        int[][] laboratoryMap = new int[n][m];
        boolean[][] visited = new boolean[n][m];

        for(int i = 0; i < n; i++) {
            splitNowLine = br.readLine().split(" ");
            for(int j = 0; j < splitNowLine.length; j++) {
                laboratoryMap[i][j] = Integer.parseInt(splitNowLine[j]);
                if(laboratoryMap[i][j] != 0) visited[i][j] = true;
            }
        }

        dfs(0, laboratoryMap, visited);

        System.out.print(maximumSafetyArea);
    }

    private static void dfs(int depth, int[][] laboratoryMap, boolean[][] visited) {
        if(depth == 3) {
            infection(laboratoryMap);
            return;
        }

        for(int i = 0; i < laboratoryMap.length; i++) {
            for(int j = 0; j < laboratoryMap[0].length; j++) {
                if(!visited[i][j]) {
                    visited[i][j] = true;
                    laboratoryMap[i][j] = 1;
                    dfs(depth + 1, laboratoryMap, visited);
                    visited[i][j] = false;
                    laboratoryMap[i][j] = 0;
                }
            }
        }

    }

    private static void infection(int[][] laboratoryMap) {
        Queue<int[]> virus = new LinkedList<>();
        int safetyArea = 0;
        int countInspection = 0;

        int x = laboratoryMap.length;
        int y = laboratoryMap[0].length;

        int[][] copyMap = new int[x][y];

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                copyMap[i][j] = laboratoryMap[i][j];
                if(copyMap[i][j] == 2) {
                    countInspection++;
                    virus.add(new int[]{i, j});
                }
            }
        }

        int numOfInspected = bfs(virus, copyMap, countInspection);
        if(numOfInspected == -1) {
            return;
        }

        countInspection += numOfInspected;

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                if(copyMap[i][j] == 0) safetyArea++;
            }
        }

        if(safetyArea > maximumSafetyArea) {
            miniMumInspectionArea = countInspection;
            maximumSafetyArea = safetyArea;
        }
    }

    private static int bfs(Queue<int[]> virus, int[][] copyMap, int countInspection) {
        while(!virus.isEmpty()) {
            int[] now = virus.poll();

            for(int i = 0; i < vector.length; i++) {
                int nextX = now[0] + vector[i][0];
                int nextY = now[1] + vector[i][1];

                if(isPossibleInspection(nextX, nextY, copyMap)) {
                    copyMap[nextX][nextY] = 2;
                    if(++countInspection > miniMumInspectionArea) return -1;
                    virus.add(new int[]{nextX, nextY});
                }
            }
        }

        return countInspection;
    }

    private static boolean isPossibleInspection(int x, int y, int[][] copyMap) {
        return x >= 0 && x < copyMap.length && y >= 0 && y < copyMap[0].length && copyMap[x][y] == 0;
    }

}