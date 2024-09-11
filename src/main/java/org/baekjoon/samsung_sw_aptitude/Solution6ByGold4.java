package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution6ByGold4 {

    /**
     [https://www.acmicpc.net/problem/14500]
     삼성 SW 역량 테스트 기출 문제
     테트로미노

     이 문제는 값이 적힌 2차원 배열에서 테트리스의 모양의 합 중 가장 큰 값을 찾는 문제이다.
     문제가 조금 재밌는데, 테트리스의 블록을 사용한 줄 알았으나 테트로미노라는 수학적 도형의
     모양을 테트로미노라고 하며, 테트리스는 테트로미노를 기반으로 만든 게임이었다는 것을 알게
     되어 조금 흥미로운 지식이 하나 늘어난 기분이다.

     문제에서는 테트로미노 모양을 회전하고 반전시켜도 된다고 쓰여 있었지만, 잠깐 구상을 해봐도
     5가지 모양을 회전시키며 배열에서 해당 모양에 일치하는 값을 합하는 건 아주 비효율적인 코드가
     될 것으로 보인다. 테트로미노는 누군가 규칙 없이 정한 모양이 아니라 수학적인 규칙이 적용된
     모양이다. 규칙을 보도록 하자. 규칙은 아래와 같다.

     1. 정사각형은 서로 겹치면 안 된다.
     2. 도형은 모두 연결되어 있어야 한다.
     3. 정사각형의 변끼리 연결되어 있어야 한다. 즉, 꼭짓점과 꼭짓점만 맞닿아 있으면 안 된다.
     4. 정사각형 4개를 이어 붙인 폴리오미노는 테트로미노라고 하며, 5가지가 있다.

     위의 지문으로 프로그래밍적 사고를 해본다면 모든 방향으로 연결되어 뻗어 나가는 depth 4의 배열
     좌표값들의 집합이라고 볼 수 있을 것 같다.

     그렇다면 문제 해결은 간단해진다. 배열의 특정 위치에서 시작하여 이동할 수 있는 경로로 4번의
     이동을 반복하여 경로의 합이 가장 큰 값을 저장하는 dfs를 작성하고, 2차원 배열을 순회하며
     시작 위치를 dfs로 넘겨 가장 큰 값을 계속 갱신하며 최종적으로 가장 큰 값을 찾으면 된다. 한
     가지 예외가 있다면, 상기 지문을 dfs로 구현했을 때 백트래킹 방식으로 탐색을 진행하기 때문에
     삼각 모양과 같은 경로와 엇나간 모양은 탐색하지 않는다는 점이다. dfs에 추가 조건을 적용하여
     삼각 모양을 탐색할지, 별도의 삼각 모양 탐색 배열을 생성할지 고민하였는데, 삼각 모양 탐색 배열을
     별도로 정의한 후 삼각형 모양을 찾는 것이 성능상 리스크가 크지 않고 dfs 탐색 로직이 지나치게
     복잡해지지 않는다고 판단하여 삼각 모양 탐색 메서드를 별도로 작성하였다. 삼각 모양 탐색 배열은
     기존의 탐색 방향에 삼각 모양을 탐색할 방향들에 대한 탐색 순서를 정의하였으며, 기준 좌표에서
     3개의 좌표를 4방향으로 탐색하기 때문에 1회의 순회마다 최악의 경우에도 12회의 순회가 발생하기
     때문에 현재 제한 조건 n, m <= 500에서는 속도적으로 큰 문제가 없다고 생각하여 이렇게 구현하였다.

     */

    static int maximumValue = Integer.MIN_VALUE;
    static int[][] vector = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우 탐색 배열
    static int[][] vectorTriangle = {{3, 1, 2}, {1, 2, 0}, {2, 0, 3}, {0, 3, 1}}; // 삼각모양 탐색
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line1 = br.readLine().split(" ");
        int n = Integer.parseInt(line1[0]);
        int m = Integer.parseInt(line1[1]);

        int[][] board = new int[n][m];
        visited = new boolean[n][m];
        for(int i = 0; i < n; i++) {
            board[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }


        for(int x = 0; x < n; x++) {
            for(int y = 0; y < m; y++) {
                visited[x][y] = true;
                dfs(x, y, board[x][y], 1, board); // 합계값, 탐색 깊이, 보드
                getTriangleSumValue(x, y, board[x][y], board);
                visited[x][y] = false;
            }
        }

        System.out.println(maximumValue);
    }

    private static void dfs(int x, int y, int nowSumValue, int depth, int[][] board) {
        if(depth == 4) {
            maximumValue = Math.max(nowSumValue, maximumValue);
            return;
        }

        for (int[] vectorInfo : vector) {
            int nextX = x + vectorInfo[0];
            int nextY = y + vectorInfo[1];

            if (isPossibleExplore(nextX, nextY, board) && !visited[nextX][nextY]) {
                visited[nextX][nextY] = true;
                dfs(nextX, nextY, nowSumValue + board[nextX][nextY], depth + 1, board);
                visited[nextX][nextY] = false;
            }
        }
    }

    private static boolean isPossibleExplore(int x, int y, int[][] board) {
        return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
    }

    private static void getTriangleSumValue(int x, int y, int sumValue, int[][] board) {
        for (int[] triangleExplore : vectorTriangle) {
            int nowSumValue = sumValue;
            int count = 0;
            for (int vectorInfo : triangleExplore) {
                int exploreX = x + vector[vectorInfo][0];
                int exploreY = y + vector[vectorInfo][1];

                if (!isPossibleExplore(exploreX, exploreY, board)) break;

                nowSumValue += board[exploreX][exploreY];
                count++;
            }
            if (count == 3) maximumValue = Math.max(nowSumValue, maximumValue);
        }
    }

}