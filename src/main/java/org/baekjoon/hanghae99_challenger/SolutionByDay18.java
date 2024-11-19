package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay18 {
    /**

     [https://www.acmicpc.net/problem/17182]
     백준 17182 - 우주 탐사선

     -- 문제
     이 문제는 우주선의 시작 행성 위치, 행성 간 거리 가중치가 주어졌을 때 모든
     행성을 탐사하기 위해 필요한 최소 시간을 구하는 문제이다.

     -- 문제 분석
     이런 유형의 문제는 제한 조건 크기가 가장 중요하기 때문에 정점 개수 N의
     최대 입력 조건을 확인했을 때 N이 최대 10까지로 거의 모든 유형의 알고
     리즘으로 풀 수 있는 그래프 크기이기 때문에 가장 직관적인 방법으로 문제
     를 풀어보았다.

     -- 해결 방법
     플로이드 워셜로 모든 정점에서 시작하는 최단거리를 구하고 완전 탐색으로
     시작점부터 갈 수 있는 모든 경로를 구하여 최소거리를 구해 반환하여 해결
     하였다.

     */

    static int minTotalDist = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());

        int[][] adjGraph = new int[n][n];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                adjGraph[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // 플로이드-워셜로 모든 간선에 최단거리 산출
        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(adjGraph[i][k] + adjGraph[k][j] < adjGraph[i][j]) {
                        adjGraph[i][j] = adjGraph[i][k] + adjGraph[k][j];
                    }
                }
            }
        }

        // dfs로 시작점부터 갈 수 있는 모든 경로에 대해 완전탐색으로 최단거리 산출
        boolean[] visited = new boolean[n];
        dfs(start, 0, 0, n, adjGraph, visited);

        System.out.print(minTotalDist);
    }

    private static void dfs(int start, int curDist, int depth, int n, int[][] adjGraph, boolean[] visited) {
        if(depth == n) {
            minTotalDist = curDist;
            return;
        }

        for(int i = 0; i < n; i++) {
            if(!visited[i] && curDist + adjGraph[start][i] < minTotalDist) {
                visited[i] = true;
                dfs(i, curDist + adjGraph[start][i], depth + 1, n, adjGraph, visited);
                visited[i] = false;
            }
        }
    }

}