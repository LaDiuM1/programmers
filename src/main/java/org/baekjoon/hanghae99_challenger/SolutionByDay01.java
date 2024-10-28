package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay01 {

    /**
     [https://www.acmicpc.net/problem/11403]
     경로 찾기

     1일차라 약간의 긴장감을 가지고 접한 문제이며 문제는 백준과 프로그래머스의
     문제가 출제된다고 한다. 시간 체크 기능도 있어 푸는데 얼마나 걸렸는지도
     정확하게 체크가 가능하여 나름 도움되는 기능도 있는 것으로 보인다.

     -- 문제
     가중치 없는 방향 그래프가 주어지며 각 정점에서 다른 정점까지 양수의 경로가
     존재하는지 찾는 문제이다.

     -- 문제 분석
     처음에는 양수의 경로라고 표현하는 것과 입력 값이 배열 형태로 특이하게 주어
     졌기 때문에 생소한 느낌이 들었으나 결국 i 정점의 경로에 j가 포함되어 있는
     지 확인하는 문제로 보인다. 이는 단방향의 그래프를 그리고 각 정점에서 경로
     를 탐색하여 탐색 여부를 확인하면 풀 수 있을 것으로 보인다. 상대적으로 간단
     한 문제이므로 입력과 출력 부분에서 시간 최적화를 한번 진행해보았다. 최적화는
     BufferedReader, BufferedWriter, StringTokenizer, StringBuilder
     를 사용하여 입력과 출력을 스트림 형태로 처리하고 문자열 조작 시 소모되는
     불변 객체의 생성 비용을 최소화하여 최적화를 진행하였으며 이런 최적화는
     알고리즘보다는 자바 개발자로서의 경험이 도움이 되었다.

    -- 구현
     1. 그래프를 그릴 List<List<Integer>> 자료구조를 초기화 후 그래프를 그린다.
     2. 행과 열의 크기가 N인 visited 배열을 선언한다.
     3. 0부터 N까지 정점을 순회하며 각 시작 정점의 이웃 정점들을 큐에 넣고 BFS로
        탐색하며 방문한 정점을 [i][j] 위치에 갱신하고 모두 순회 후 정답을 반환한다.

     -- 문제 평가
     1일차 문제라서 단순한 그래프 탐색 문제가 주어진 것으로 보인다. 챌린저 난이도는
     복합 알고리즘을 사용한 문제가 출제된다고 하였으니 긴장을 늦추지 말자.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        final int N = Integer.parseInt(br.readLine());
        int[][] visited = new int[N][N];

        List<List<Integer>> graph = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                if(st.nextToken().equals("1")) {
                    graph.get(i).add(j);
                }
            }
        }

        for(int i = 0; i < N; i++) {
            List<Integer> startVertexes = graph.get(i);
            Queue<Integer> queue = new LinkedList<>(startVertexes);

            while(!queue.isEmpty()) {
                int curVertex = queue.poll();
                if(visited[i][curVertex] == 1) continue;
                visited[i][curVertex] = 1;

                List<Integer> neighbors = graph.get(curVertex);
                queue.addAll(neighbors);
            }
        }

        for (int[] rows : visited) {
            for (int row : rows) {
                sb.append(row).append(' ');
            }
            sb.append('\n');
        }

        bw.write(sb.toString());
        bw.close();
    }

}