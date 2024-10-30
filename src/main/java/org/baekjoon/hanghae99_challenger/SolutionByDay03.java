package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SolutionByDay03 {

    /**
     [https://www.acmicpc.net/problem/2660]
     회장뽑기

     -- 문제
     그래프 탐색 문제이며 현재 정점에서 가장 먼 거리의 정점의 값을 구하는 문제이다.

     -- 문제 분석 및 해결
     최근 이런 유형의 문제는 전부 다익스트라로 구현했으므로 이 문제에서는 플로이드-워셜
     알고리즘을 이용하여 해결해 보았다. 플로이드 워셜은 복잡한 코드 작성 없이 3중 for문
     을 이용하여 모든 출발지, 도착지에 대한 경유지를 확인하므로 코드 작성에는 큰 무리가
     없었다. 각 정점에 대한 최단 거리를 구하고 각 정점마다 가장 먼 거리를 구하여 우선
     순위 큐로 가장 작은 중복 요소들을 판별하고 반환하여 문제를 해결하였다. 챌린저 코스
     로 신청하였지만 아직 초반 단계라 기초 정도의 문제가 나오는 것으로 느끼며 지문 읽기
     부터 코드 작성 완료까지 거의 20분도 걸리지 않은 간단한 문제였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        final int N = Integer.parseInt(br.readLine());
        final int INF = 999999;
        int[][] graph = new int[N][N];
        for(int i = 0; i < N; i++) {
            Arrays.fill(graph[i], INF);
            graph[i][i] = 1;
        }

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()) - 1;
            int end = Integer.parseInt(st.nextToken()) - 1;

            if(start < 0 && end < 0) break;
            graph[start][end] = 1;
            graph[end][start] = 1;
        }

        for(int k = 0; k < N; k++) {
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(graph[i][k] + graph[k][j] < graph[i][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                    }
                }
            }
        }

        PriorityQueue<int[]> indexAndWeightPq = new PriorityQueue<>((o1, o2) -> {
            if(o1[1] == o2[1]) {
                return Integer.compare(o1[0], o2[0]);
            }
            return Integer.compare(o1[1], o2[1]);
        });

        for (int i = 0; i < N; i++) {
            int curMaximum = -1;
            for(int j = 0; j < N; j++) {
                if(graph[i][j] == INF) continue;
                curMaximum = Math.max(graph[i][j], curMaximum);
            }
            if(!indexAndWeightPq.isEmpty() && indexAndWeightPq.peek()[1] > curMaximum) {
                indexAndWeightPq.clear();
            }
            if(indexAndWeightPq.isEmpty() || indexAndWeightPq.peek()[1] >= curMaximum) {
                indexAndWeightPq.add(new int[]{i + 1, curMaximum});
            }
        }

        sb.append(indexAndWeightPq.peek()[1]).append(' ').append(indexAndWeightPq.size()).append('\n');
        while(!indexAndWeightPq.isEmpty()) {
            sb.append(indexAndWeightPq.poll()[0]).append(' ');
        }

        System.out.print(sb);
    }

}