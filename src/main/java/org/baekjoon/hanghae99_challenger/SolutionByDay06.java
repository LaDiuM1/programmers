package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SolutionByDay06 {

    /**
     [https://www.acmicpc.net/problem/2458]
     백준 - 키 순서

     -- 문제
     1:1로 키를 비교한 결과가 주어졌을 때 자신의 키가 총 인원에서 몇 번째인지 아는
     학생의 수를 출력하는 문제이다.

     -- 문제 분석 및 해결
     이 문제는 예전에 알고리즘 학습 초반에 백준에서 비슷한 문제를 경험한 적이 있었다.
     그때는 그래프 탐색 개념을 몰랐기 때문에 복잡한 정렬을 구현하여 모든 요소의 랭킹을
     산출한 후 자신의 위치를 확정할 수 있는지 판단하는 복잡하고 비효율적인 방법으로
     해결했었다. 하지만 오랜만에 비슷한 이 문제를 접하고 드는 생각은 그래프 탐색으로
     정점에 도달할 수 있는지를 확인 후 자신으로 도달한 정점의 특정 조건만 정의하면
     쉽게 풀리겠다는 생각을 하였다. 예전에는 사고력이 강점이라고 생각하여서 어떤 문제
     를 만나더라도 구현으로 해결할 수 있다는 생각을 하였지만 지금은 생각이 다르다.
     알고리즘과 자료구조의 배경지식 깊이는 그 어떤 문제를 만나더라도 최적의 가능성을
     가장 빠르게 찾게 해주는 도구라는 느낌이 지금은 강하게 든다. 다른 얘기가 길어졌
     지만 이 문제 해결 방법을 자세히 구상해보자면 시작 정점 기준으로 모든 정점의
     방문 여부를 확인 후 (플로이드-워셜) 각 정점에서 자신으로 도달 하거나 (자신
     보다 키가 작은 경우) 자신에서 다른 정점으로 도달한 경우(자신보다 키가 큰 경우)
     의 숫자를 카운트하여 두 경우의 합이 정점의 개수 -1 (자신 제외)와 같을 때 자신
     의 랭킹을 확정할 수 있을 것이다. 해당 방법을 플로이드 워셜을 통해 간단하게
     정점 방문 여부를 확인 후 정점만큼 순회하며 조건을 비교하여 문제를 해결하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int N = Integer.parseInt(st.nextToken());
        final int M = Integer.parseInt(st.nextToken());

        int[][] graph = new int[N + 1][N + 1];
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            graph[start][end] = 1;
        }

        for(int k = 1; k <= N; k++) {
            for(int i = 1; i <= N; i++) {
                for(int j = 1; j <= N; j++) {
                    if(graph[i][k] == 1 && graph[k][j] == 1) {
                        graph[i][j] = 1;
                    }
                }
            }
        }

        int answer = 0;
        for(int i = 1; i <= N; i++) {
            int greaterCount = 0;
            int lesserCount = 0;

            for(int j = 1; j <= N; j++) {
                if(graph[i][j] == 1) greaterCount++;
                if(graph[j][i] == 1) lesserCount++;
            }

            if(greaterCount + lesserCount == N - 1) {
                answer++;
            }
        }

        System.out.print(answer);
    }
}
