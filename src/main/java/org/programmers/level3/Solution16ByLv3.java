package org.programmers.level3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution16ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/72413]
     * 2021 KAKAO BLIND RECRUITMENT
     * 합승 택시 요금
     *
     * 이 문제는 기본적으로 그래프와 간선에 대한 최단거리를 구하는 문제인데 처음에는
     * BFS를 이용하여 노드간 최단거리를 구하려고 하였으나 그래프간 최단거리 구함에 있어
     * 좀 더 효율적인 알고리즘이 있을 것으로 보여 그래프 최단 거리 알고리즘을 검색하였으며
     * 다익스트라 알고리즘이 내가 원하는 문제 해결에 적합한 것으로 보여 다익스트라 알고리즘을
     * 학습 후 문제 해결을 진행하였다, 기본적으로 다익스트라 알고리즘은 현재 노드 위치를 시작점으로
     * 현재 노드 기준 인접 노드까지의 거리를 계산하여 계산 할 때마다 최단거리로 갱신하여 현재 위치
     * 기준에서 모든 노드들의 최단 거리를 구하는 알고리즘이다. 최단 거리를 구했으니 남은건 문제 해결이다.
     *
     * 지문은 시작 노드부터 별개의 두 노드까지의 거리의 합계중 가장 작은 합계를 구하는 것이다.
     * 그렇다면 출발지와 도착지 A,B까지의 최단거리의 합계를 구하면 된다.
     *
     * 모든 노드를 순회하는 반복문을 선언, 출발지, 도착지 A, 도착지 B와 현재 순회중인 노드 번호까지의
     * 최단거리 합계 중 가장 작은 합계를 저장하는 변수 minimumCost에 갱신한다면 정답을 도출할 수 있을 것이다.
     *
     */

    private static class Node implements Comparable<Node> {
        int index, distance;

        public Node(int index, int distance) {
            this.index = index;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    List<List<Node>> graph;
    int numNodes;

    public int solution(int n, int s, int a, int b, int[][] fares) {
        init(n, fares);

        int[] distancesA = dijkstra(a - 1);
        int[] distancesB = dijkstra(b - 1);
        int[] distancesS = dijkstra(s - 1);

        int minimumCost = Integer.MAX_VALUE;
        for(int i = 0; i < n; i++) {
            minimumCost = Math.min(distancesS[i] + distancesA[i] + distancesB[i], minimumCost);
        }

        return minimumCost;
    }

    private int[] dijkstra(int start) {
        int[] distances = new int[numNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(new Node(start, 0));

        while(!queue.isEmpty()) {
            Node current = queue.poll();
            int currentIndex = current.index;
            int currentDistance = current.distance;

            if(currentDistance > distances[currentIndex]) continue;

            for(Node neighbor : graph.get(currentIndex)) {
                int newDistance = currentDistance + neighbor.distance;
                if(newDistance < distances[neighbor.index]) {
                    distances[neighbor.index] = newDistance;
                    queue.add(new Node(neighbor.index, newDistance));
                }
            }
        }

        return distances;
    }

    private void init(int numNodes, int[][] fares) {
        this.numNodes = numNodes;
        graph = new ArrayList<>();
        for(int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
        }

        for(int[] fare : fares) {
            int from = fare[0] - 1;
            int to = fare[1] - 1;
            int distance = fare[2];
            graph.get(from).add(new Node(to, distance));
            graph.get(to).add(new Node(from, distance));
        }
    }

}