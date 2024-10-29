package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay02 {

    /**
     [https://www.acmicpc.net/problem/1389]
     케빈 베이컨의 6단계 법칙

     -- 문제
     친구 관계로 관계 그래프가 주어지며 모든 친구 관계에서 누적 거리의 합이 가장
     가까운 번호 중 가장 낮은 번호를 반환하는 문제이다.

     -- 문제 분석 및 해결
     이 문제는 플로이드 워셜 또는 다익스트라로 해결할 수 있는 문제로 보인다. 전형
     적인 최단 거리를 구하는 문제이며 상대적으로 더 익숙한 다익스트라를 이용하여
     문제를 해결하였다, 모든 정점의 가중치를 초기화 후 해당 시작 정점 기준으로
     최단 거리의 정점으로만 초기화 후 최단 거리가 구해지면 해당 시작 정점의 가중치
     의 값을 모두 더한 후 결과 배열에 인덱스와 함께 저장하였다. 모든 정점 순회 후
     결과 배열을 가중치 기준 오름차순, 값이 같을 시 정점 번호 기준으로 오름차순
     정렬하고 첫 번째 값을 반환하여 문제를 해결하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nowLine = br.readLine().split(" ");
        final int N = Integer.parseInt(nowLine[0]);
        final int M = Integer.parseInt(nowLine[1]);

        List<List<Integer>> graph = new ArrayList<>();
        int[][] weights = new int[N + 1][N + 1];
        int[][] result = new int[N][2];
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
            for(int j = 1; j <= N; j++) {
                weights[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int startVertex = Integer.parseInt(st.nextToken());
            int endVertex = Integer.parseInt(st.nextToken());

            graph.get(startVertex).add(endVertex);
            graph.get(endVertex).add(startVertex);
        }

        for(int i = 1; i <= N; i++) {
            result[i - 1][0] = i;
            dijkstra(i, weights, graph, N);
            for(int j = 1; j <= N; j++) {
                if(weights[i][j] != Integer.MAX_VALUE) {
                    result[i - 1][1] += weights[i][j];
                }
            }
        }

        Arrays.sort(result, (o1, o2) -> {
            if(o1[1] == o2[1]) {
                return Integer.compare(o1[0], o2[0]);
            } else {
                return Integer.compare(o1[1], o2[1]);
            }
        });

        System.out.print(result[0][0]);
    }

    private static void dijkstra(int start, int[][] weights, List<List<Integer>> graph, int N) {
        boolean[] visited = new boolean[N + 1];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start, 0});

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int vertexNo = current[0];
            int distance = current[1];

            if(visited[vertexNo]) continue;
            visited[vertexNo] = true;

            List<Integer> neighbors = graph.get(vertexNo);
            for (Integer neighbor : neighbors) {
                if(distance < weights[start][neighbor]) {
                    weights[start][neighbor] = distance;
                    queue.add(new int[]{neighbor, distance + 1});
                }
            }
        }
    }

}