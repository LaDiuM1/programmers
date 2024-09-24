package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution11BySilver1 {
    /**
     [https://www.acmicpc.net/problem/14889]
     삼성 SW 역량 테스트 기출 문제
     스타트와 링크

     이 문제는 각 인덱스와 매칭되는 인덱스별 점수 테이블이 있으며
     0부터 n - 1까지의 인덱스로 절반씩 팀을 구성하여 각 팀의 점수
     차가 가장 적은 조합을 선택하는 문제이다.

     문제는 기본적인 조합 선택 문제이며 조합 선택을 위한 DFS를 선언하여
     각 팀의 조합이 확정되었을 때 점수를 계산하여 점수차가 가장 작은 점수
     를 반환하여 해결하였다, 최적화 측면에서는 절반의 팀이 확정되면 나머지
     절반의 조합이 자동으로 정해지기 때문에 모든 조합을 선택하지 않고 절반의
     조합을 구했을 때 탐색 여부 배열로 나머지 팀을 구하여 문제를 해결하였다.

     */


    static int minimumDiff = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] scoreMatrix = new int[n][n];

        for(int i = 0; i < n; i++) {
            scoreMatrix[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        dfs(new ArrayList<>(), 0, new boolean[n], scoreMatrix, n);

        System.out.print(minimumDiff);
    }

    private static void dfs(ArrayList<Integer> teamA, int index, boolean[] visited, int[][] scoreMatrix, int n) {
        if(teamA.size() == n / 2) {
            ArrayList<Integer> teamB = new ArrayList<>();
            for(int i = 0; i < n; i++) { if(!visited[i]) teamB.add(i); }

            int scoreA = calScore(teamA, scoreMatrix);
            int scoreB = calScore(teamB, scoreMatrix);

            minimumDiff = Math.min(Math.abs(scoreA - scoreB), minimumDiff);
            return;
        }

        for(int i = index; i < n; i++) {
            if(!visited[i]) {
                visited[i] = true;
                teamA.add(i);
                dfs(teamA, i + 1, visited, scoreMatrix, n);
                visited[i] = false;
                teamA.remove(teamA.size() - 1);
            }
        }
    }

    private static int calScore(List<Integer> team, int[][] scoreMatrix) {
        int n = scoreMatrix.length;
        int score = 0;
        for(int i = 0; i < n / 2; i++) {
            for(int j = 0; j < n / 2; j++) {
                if(i == j) continue;
                score += scoreMatrix[team.get(i)][team.get(j)];
            }
        }
        return score;
    }

}