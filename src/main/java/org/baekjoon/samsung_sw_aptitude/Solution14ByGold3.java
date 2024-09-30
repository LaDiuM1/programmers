package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution14ByGold3 {

    /**
     [https://www.acmicpc.net/problem/15683]
     삼성 SW 역량 테스트 기출 문제
     감시

     이 문제는 CCTV 종류별로 감시할 수 있는 방향의 개수 및 종류가 있으며,
     CCTV 위치부터 CCTV 감시 방향으로 배열의 끝 또는 벽까지가 감시 영역
     으로 모든 CCTV의 90도 단위의 각도를 결정하여 감시 불가 영역의 최소
     개수를 반환하는 문제이다.

     -- 구상
     문제는 결국 CCTV마다의 각도별 조합을 구하는 것이므로 DFS로 CCTV의
     위치별 각도의 조합을 구한 후, 조합 완성 시마다 감시 불가 영역을 구
     하여, 최종적으로 최소의 감시 불가 영역을 구하면 되는 문제이다.

     -- 구현
     문제를 잘 읽어보면 각 CCTV 종류별로 감시할 수 있는 방향 및 개수가
     다르므로 각 CCTV마다 감시할 수 있는 방향을 매핑해야 하는 것으로 보
     인다.

     1. 배열의 상하좌우 방향을 확인하는 배열과 CCTV의 종류
     별로 확인 가능한 방향의 경우의 수를 매칭한다.
     2. CCTV 위치들과 visited 배열을 선언하여 DFS로 전달.
     3. DFS에서는 인덱스 0부터 시작하여 CCTV 위치들을 순회하고
     각 위치마다 CCTV 종류별로 방향 개수를 확인하여 각 방향 개수
     만큼 반복하여 각 위치별 방향을 현재 순회 리스트에 추가하여
     DFS 전달, 반복 시 CCTV 위치와 각도별로 탐색 여부를 확인하는
     visited 배열 확인하여 중복 탐색을 방지하고 DFS 순회 후 visited
     배열 및 마지막에 추가한 CCTV 제거하여 백트래킹으로 탐색
     4. DFS에서 리스트에 추가된 CCTV 개수가 모든 CCTV 개수와 일치하면
     CCTV별 각도 조합에 해당하는 방향을 확인하여 사각지대 개수를 반환하는
     메서드 호출 후 사각지대의 최소값 갱신
     5. 사각지대 확인 메서드에서는 인자로 받은 CCTV 위치별 각도를 순회하여
     각 종류별 방향의 직진 방향으로 복사한 배열 값을 변경하여 모든 CCTV
     순회 완료 시 값이 변경되지 않은 사각 지대의 개수를 반환

     -- 총평
     이 문제는 조합을 직접 생성하여 조합을 모두 탐색하고 탐색 시 별도의 조건을
     확인하는 문제로 완전 탐색에 익숙하다면 상대적으로 난이도가 낮은 문제로 보인다.

     */


    static int minimumCountBlindSpot = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        int[][] vector = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우 방향
        int[][][] CCTVType = {
                {},
                {{0}, {1}, {2}, {3}},
                {{0, 1}, {2, 3}},
                {{0, 3}, {3, 1}, {1, 2}, {2, 0}},
                {{2, 0, 3}, {0, 3, 1}, {3, 1, 2}, {1, 2, 0}},
                {{0, 1, 2, 3}}
        }; // cctv 종류별 감시 방향

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nowLine = br.readLine().split(" ");
        final int N = Integer.parseInt(nowLine[0]);
        final int M = Integer.parseInt(nowLine[1]);

        int[][] monitoringArea = new int[N][M];
        List<int[]> cctvPositions = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            int[] rows = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            for(int j = 0; j < rows.length; j++) {
                monitoringArea[i][j] = rows[j];
                if(rows[j] >= 1 && rows[j] <= 5) cctvPositions.add(new int[]{i, j, rows[j]});
            }
        }

        boolean[][][] visited = new boolean[N][M][4];

        dfs(cctvPositions, 0, new ArrayList<>(), monitoringArea, visited, vector, CCTVType);

        System.out.print(minimumCountBlindSpot);

    }

    private static void dfs(List<int[]> cctvPositions, int index, List<int[]> now, int[][] monitoringArea, boolean[][][] visited, int[][] vector, int[][][] cctvType) {
        if(now.size() == cctvPositions.size()) {
            int result = workMonitoring(now, monitoringArea, vector, cctvType);
            minimumCountBlindSpot = Math.min(result, minimumCountBlindSpot);
            return;
        }

        for(int i = index; i < cctvPositions.size(); i++) {
            int x = cctvPositions.get(i)[0];
            int y = cctvPositions.get(i)[1];
            int type = cctvPositions.get(i)[2];

            int[][] directionsByType = cctvType[type];

            for(int j = 0; j < directionsByType.length; j++) {
                if(!visited[x][y][j]) {
                    visited[x][y][j] = true;
                    now.add(new int[]{x, y, type, j});
                    dfs(cctvPositions, i + 1, now, monitoringArea, visited, vector, cctvType);
                    visited[x][y][j] = false;
                    now.remove(now.size() - 1);
                }
            }
        }

    }

    private static int workMonitoring(List<int[]> nowCCTVs, int[][] monitoringArea, int[][] vector, int[][][] cctvType) {
        int countBlindSpot = 0;
        int numOfX = monitoringArea.length;
        int numOfY = monitoringArea[0].length;
        int[][] copyMonitoringArea = new int[numOfX][numOfY];

        for(int i = 0; i < numOfX; i++) {
            System.arraycopy(monitoringArea[i], 0, copyMonitoringArea[i], 0, numOfY);
        }

        for(int[] now : nowCCTVs) {
            int type = now[2];
            int dir = now[3];

            int[] monitoringVector = cctvType[type][dir];
            for (int j : monitoringVector) {
                int x = now[0];
                int y = now[1];

                while (true) {
                    x += vector[j][0];
                    y += vector[j][1];
                    if (x < 0 || x >= numOfX || y < 0 || y >= numOfY || copyMonitoringArea[x][y] == 6) break;

                    copyMonitoringArea[x][y] = -1;
                }
            }
        }

        for(int[] rows : copyMonitoringArea) {
            for(int col : rows) {
                if(col == 0) countBlindSpot++;
            }
        }

        return countBlindSpot;
    }
}