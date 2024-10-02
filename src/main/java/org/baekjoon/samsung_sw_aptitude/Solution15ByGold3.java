package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution15ByGold3 {

    /**
     [https://www.acmicpc.net/problem/15684]
     삼성 SW 역량 테스트 기출 문제
     사다리 조작

     이 문제는 일반적인 사다리 게임에서 주어진 초기 조건에서 사다리를
     3개 이하로 추가하여 시작 번호와 도작번호가 모두 동일하도록 만드는
     문제이다.

     -- 구상
     추가되는 사다리의 조건에 따른 조합을 확인하는 문제이므로 DFS를 이
     용하여 추가할 수 있는 사다리 조합을 모두 구하고 제한 사항이
     (2 ≤ N ≤ 10, 1 ≤ H ≤ 30, 0 ≤ M ≤ (N-1)×H)으로
     9(N - 1) * 30 = 270, 3개 선택 시 270 * 269 * 268 / 3! =
     3,244,140로 모든 조합을 단순하게 확인하면 시간 초과가 발생할
     여지가 높으므로 백트래킹 및 BFS처럼 최초 정답 발견시 바로 메서드
     를 모두 중지하여야 한다.

     -- 1차 시도 실패, 원인 파악 및 해결
     구현은 처음에는 HashSet을 이용하여 사다리 위치를 문자열로 변환,
     contain으로 상수 시간에 사다리 존재 여부를 확인하려 하였으나,
     제출 시 일부 테스트에서 시간 초과가 발생하여 원인 파악에 들어
     갔으며, 사다리 존재 여부를 확인하는 과정에서 각 좌표를 매개로
     문자열의 새 객체를 변환하는 과정이 불필요한 메모리와 추가적인
     시간을 소모하는 것으로 판단된다. 그러하여 사다리 존재 여부를
     2차원 boolean 배열로 확인하도록 코드를 수정하였으며, 시간
     안에 테스트를 통과하여 문제를 해결하였다.

     */


    static int countMinimumAddLine = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[] firstLine = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int N = firstLine[0];
        int M = firstLine[1];
        int H = firstLine[2];

        boolean[][] visited = new boolean[H + 2][N + 2];

        for(int i = 0; i < M; i++) {
            int[] nowLine = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            visited[nowLine[0]][nowLine[1]] = true;
        }

        dfs(0, 1, 1, N, H, visited);
        System.out.print(countMinimumAddLine == Integer.MAX_VALUE ? -1 : countMinimumAddLine);

    }

    private static void dfs(int countAddLine, int startX, int startY, int N, int H, boolean[][] visited) {
        if(countAddLine >= countMinimumAddLine) {
            return;
        }

        if(isAnswer(N, H, visited)) {
            countMinimumAddLine = countAddLine;
            return;
        }

        if(countAddLine >= 3) {
            return;
        }

        for(int i = startX; i <= N; i++) {
            startY = i != startX ? 1 : startY;
            for(int j = startY; j <= H; j++) {
                if(!checkExistingLine(j, i, visited) && countAddLine < countMinimumAddLine - 1) {
                    visited[j][i] = true;
                    dfs(countAddLine + 1, i, j + 1, N, H, visited);
                    visited[j][i] = false;
                }
            }
        }
    }

    private static boolean isAnswer(int N, int H, boolean[][] visited) {
        for(int i = 1; i <= N; i++) {
            int nowLine = i;
            for(int j = 1; j <= H; j++) {
                if(visited[j][nowLine]) {
                    nowLine++;
                } else if(visited[j][nowLine - 1]) {
                    nowLine--;
                }
            }
            if(nowLine != i) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkExistingLine(int j, int i, boolean[][] visited) {
        return visited[j][i] || visited[j][i - 1] || visited[j][i + 1];
    }
}