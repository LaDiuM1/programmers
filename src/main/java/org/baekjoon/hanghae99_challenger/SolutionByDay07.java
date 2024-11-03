package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay07 {
    /**
     [https://www.acmicpc.net/problem/1240]
     백준 - 노드사이의 거리

     -- 문제
     N개의 노드로 이루어진 트리가 주어질 때 두 노드가 주어지면 노드 간 거리를 출력하는
     문제이다.

     -- 문제 분석 및 해결
     트리 구조라면 두 노드로 향하는 경로가 유일할 것이다. 그렇다면 시작 노드에서 출발하여
     목표 노드로 향하는 누적 거리를 반환하면 되는 간단한 문제이다. 새로운 카테고리의 출제
     시작이라 간단한 문제부터 나온 것인지는 알 수 없으나 기초적인 문제이므로 구현이 간단한
     BFS로 탐색을 진행하여 해결하였다.

     */

    private static class Node {
        int no, dist;

        public Node(int no, int dist) {
            this.no = no;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int N = Integer.parseInt(st.nextToken());
        final int M = Integer.parseInt(st.nextToken());

        List<List<Node>> graph = new ArrayList<>();
        for(int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            graph.get(start).add(new Node(end, dist));
            graph.get(end).add(new Node(start, dist));
        }

        for(int k = 0; k < M; k++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int target = Integer.parseInt(st.nextToken());

            int moveDist = bfs(start, target, graph, N);
            sb.append(moveDist).append('\n');
        }

        bw.write(sb.toString());
        bw.close();
    }

    private static int bfs(int start, int target, List<List<Node>> graph, int N) {
        boolean[] visited = new boolean[N + 1];
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(start, 0));

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();

            if(visited[curNode.no]) continue;
            visited[curNode.no] = true;

            for (Node neighbor : graph.get(curNode.no)) {
                int sumDist = curNode.dist + neighbor.dist;
                if(neighbor.no == target) return sumDist;
                queue.offer(new Node(neighbor.no, sumDist));
            }
        }

        return 0;
    }
}
