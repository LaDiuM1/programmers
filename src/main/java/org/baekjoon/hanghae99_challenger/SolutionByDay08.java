package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay08 {
    /**
     [https://www.acmicpc.net/problem/4485]
     녹색 옷 입은 애가 젤다지?

     -- 문제
     2차원 배열에 정수들이 적혀있으며 배열의 0, 0 위치부터 n-1, n-1 위치까지 상, 하,
     좌, 우로 한 칸씩 이동할 수 있을 때 누적 합이 가장 작은 값을 반환하는 문제이다.

     -- 문제 분석 및 해결
     이 문제는 N의 범위가 중요해보여 확인해보니 N이 125로 완전탐색은 불가능한 수준의
     크기이므로 첫 접근은 DP 알고리즘으로 접근하였다. 하지만 DP는 두 가지 선택 중 최선
     의 선택을 누적해 나갔을 때 가장 효율적인 알고리즘이나 이 문제는 4방향으로 이동 가능
     하므로 DP로 풀기 위해서는 상하좌우 값을 확인하며 최소비용을 계속 누적해 나가야 한다.
     이미 이 시점에서 DP 알고리즘의 장점은 전혀 없어지고 구상하면서도 이미 다익스트라로
     구하는 최단거리 문제라는 것을 금방 알 수 있었다. 그렇다면 다익스트라로 풀기 위해 배열
     의 각 인접 관계를 그래프화 한다면 자신의 주변에 있는 인덱스 번호가 정점일 것이고 인덱
     스의 값이 이동 거리일 것이다. 그렇다면 배열을 순회하여 i * n + j의 값을 정점 번호,
     주변의 정점 번호를 기준으로 양방향으로 그래프를 그린 후 다익스트라로 정점 0부터 출발
     하여 최단거리를 구한 후 V(n * n) -1 정점 번호의 최단 거리를 반환하면 해결 될 것이다.

     */

    private static class Node implements Comparable<Node> {
        int no, dist;

        public Node(int no, int dist) {
            this.no = no;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.dist, o.dist);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int printNo = 1;
        while(true) {
            int n = Integer.parseInt(br.readLine());
            if(n == 0) break;

            int[][] board = new int[n][n];
            for(int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j = 0; j < n; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            final int V = n * n;
            List<List<Node>> graph = new ArrayList<>();
            for(int i = 0; i <= V; i++) {
                graph.add(new ArrayList<>());
            }

            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(i + 1 < n) {
                        graph.get(i * n + j).add(new Node((i + 1) * n + j, board[i + 1][j]));
                        graph.get((i + 1) * n + j).add(new Node(i * n + j, board[i][j]));
                    }
                    if(j + 1 < n) {
                        graph.get(i * n + j).add(new Node(i * n + j + 1, board[i][j + 1]));
                        graph.get(i * n + j + 1).add(new Node(i * n + j, board[i][j]));
                    }
                }
            }

            int[] dist = new int[V];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[0] = board[0][0];
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(0, board[0][0]));

            while(!pq.isEmpty()) {
                Node current = pq.poll();

                for(Node neighbor : graph.get(current.no)) {
                    int sumDist = current.dist + neighbor.dist;
                    if(sumDist < dist[neighbor.no]) {
                        dist[neighbor.no] = sumDist;
                        pq.add(new Node(neighbor.no, sumDist));
                    }
                }
            }

            sb.append(String.format("Problem %d: ", printNo++));
            sb.append(dist[V - 1]);
            sb.append('\n');
        }

        bw.write(sb.toString());
        bw.close();
    }

}
