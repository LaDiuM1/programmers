package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class SolutionByDay13 {
    /**

     [https://www.acmicpc.net/problem/2665]
     백준 2665 - 미로만들기

     -- 문제
     이 문제는 0, 0 좌표가 시작점, n-1, n-1이 도착점인 미로에서 벽에 막힌 상황에서
     탈출이 불가능할 때 벽을 제거하여 탈출할 수 있는 최소의 벽 제거 개수를 찾는 문제
     이다.

     -- 문제 분석
     제거할 최소의 벽의 개수를 찾는 문제는 완전 탐색 알고리즘이 많으므로 제한 사항을
     확인 시 n이 최대 50으로 2개 항을 제외한 조합의 수가 C(48,1~48)만큼 나오므로
     이는 완전탐색으로 풀 수 없는 문제라는 결론이 나온다. 그렇다면 어떻게 해야 최소
     한의 제거할 벽의 개수를 구할지 구상하던 중 이 문제는 본질적으로 벽에 막혔을 때
     되돌아가는 것이 아닌 어떤 경우에도 목적지에 도달할 수 있다는 전제가 깔려 있다는
     것을 깨닫게 되었다. 그렇다면 목적지까지 이동 중 최소한의 벽을 만나는 이동 경로를
     찾으면 된다. 이동 경로가 떠오른 시점이서 곧바로 그래프가 연상되었으며 그래프는 곧
     가중치가 존재하기 때문에 벽을 만나는 지점을 가중치 1로 주고 목적 노드까지 도달할
     때 최단거리를 구하면 되는 문제이다. 결론적으로 그래프를 그리고 다익스트라로 목적지
     v - 1 위치의 정점의 최단 거리가 곧 정답일 것이다.

     -- 구현
     1. 입력 값의 인접 배열 형태의 그래프를 List 형태의 그래프로 그린다.
     2. 다익스트라 알고리즘을 구현 후 시작 노드 번호 0점을 기준으로 최단거리를 탐색한다.
     3. 가중치 배열의 마지막 위치의 최단거리 값을 반환하여 해결한다.

     -- 느낀점
     이 문제는 예전의 BFS, DFS를 처음 배웠을 때 완전탐색만 이용해서 해결하려고 시도하였다면
     해결을 위한 아주 복잡한 변형을 찾는데 상당한 시간이 소비되었을 것이다. 하지만 머릿속에
     여러 알고리즘이 정착하기 시작한 지금은 문제의 핵심만 파악한다면 학습된 알고리즘의 종류가
     다양할수록 해결을 위한 매칭률이 높아지는 것 아닐까라는 생각이 든다. 매칭률이 높다는 것은
     해결을 위한 변형이 적다는 의미이고 이는 빠르고 효율적인 문제 해결을 위한 기반이 될 것이다.
     여러 다양한 알고리즘에 대한 지식이 생긴다면 이는 프로그래밍적으로 난해한 문제를 해결하는데도
     분명 도움이 될 것이라고 생각한다.

     */

    private static class Node implements Comparable<Node> {
        int no, weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int v = n * n;

        int[][] vectors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우
        int[][] adjMatrix = new int[n][n];
        for(int i = 0 ; i < n; i++) {
            String nowLine = br.readLine();
            for(int j = 0; j < n; j++) {
                adjMatrix[i][j] = nowLine.charAt(j) - '0';
            }
        }

        List<List<Node>> graph = new ArrayList<>();
        for(int i = 0; i < v; i++) {
            graph.add(new ArrayList<>());
        }

        // 이웃 노드 번호 및 가중치 추가
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int curNodeNo = i * n + j;
                for (int[] vector : vectors) {
                    int nextX = i + vector[0];
                    int nextY = j + vector[1];

                    if(nextX >= 0 && nextX < n && nextY >= 0 && nextY < n) {
                        graph.get(curNodeNo).add(new Node(nextX * n + nextY, (adjMatrix[nextX][nextY] + 1) % 2));
                    }
                }
            }
        }

        int[] weights = new int[v];
        Arrays.fill(weights, Integer.MAX_VALUE);
        weights[0] = 0;

        dijkstra(0, graph, weights);
        System.out.print(weights[v - 1]);

    }

    private static void dijkstra(int startNo, List<List<Node>> graph, int[] weights) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(startNo, 0));

        while(!pq.isEmpty()) {
            Node current = pq.poll();

            for(Node neighbor : graph.get(current.no)) {
                int sumWeight = current.weight + neighbor.weight;
                if(sumWeight < weights[neighbor.no]) {
                    weights[neighbor.no] = sumWeight;
                    pq.add(new Node(neighbor.no, sumWeight));
                }
            }
        }
    }

}