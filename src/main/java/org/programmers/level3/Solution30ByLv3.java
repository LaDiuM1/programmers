package org.programmers.level3;

import java.util.*;

public class Solution30ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/118669]
     2022 KAKAO TECH INTERNSHIP
     등산코스 정하기

     이 문제는 그래프가 주어지며 출발점에서 특정 지점(산봉우리)를 찍고 다시 출발점
     으로 돌아올 때 각 단선 간 거리의 최대값이 가장 낮게 나오는 산봉우리를 반환하는
     문제이다.

     -- 문제 분석
     전형적인 다익스트라 알고리즘 문제이며 다익스트라 문제를 거의 접하지 못했기 때
     문에 좋은 기회로 생각하고 신중하게 문제 해결 방법을 구상하였다. 문제의 제한사
     항을 봤을 때 출발점이 정점의 개수와 같은 경우도 있기 때문에 단순히 다익스트라
     메서드마다 출발점을 넣고 탐색하면 다익스트라 알고리즘의 장점을 전혀 살리지 못
     한다. 그렇기 때문에 최적화가 필요하며 해결 방법은 금방 떠올릴 수 있었다. 탐
     색을 시작할 때 출발 정점을 모두 넣고 시작하면 될 것으로 보였다. 여러 출발지
     에서 특정 정점을 방문하여도 결국 최단거리로 갱신되기 때문에 최단거리의 출발점
     의 가중치만 남을 것이고 산봉우리들을 방문하는 시점에서는 결국 가장 낮은 가중
     치로 값이 갱신되기 때문에 출발점을 모두 넣고 탐색하는 방향으로 가닥을 잡았다.
     다익스트라에 완벽히 적응하기 위해서 각 코드 구현에 주석을 비교적 상세하게 달
     아보자.

     -- 구현
     1. 그래프를 선언 후 이웃 노드 간 정보를 추가한다. 같은 산봉우리를 두 번
        방문할 수 없고 이웃 노드 탐색 시 출발지인 경우 의미가 없기 때문에 출발
        노드가 산봉우리인 경우와 도착 노드가 출발지인 경우는 제외하고 그래프를
        그린다.
     2. 가중치 배열을 정점 크기 + 1만큼 모든 요소를 최댓값으로 초기화, 그리고
        우선순위 큐를 선언하고 우선순위 큐에 출발 정점을 모두 추가한다.
     3. 다익스트라 메서드를 선언 후 탐색 여부 확인 배열을 선언한다. 이후 우선
        순위 큐를 순회하며 탐색을 진행. 현재 정점이 탐색되지 않았다면 이웃 노
        드들을 불러온 뒤 순회하며 각 순회에서 이웃 노드의 거리와 현재 노드의
        가중치 중 큰 값으로 비교한다. 비교는 가중치 배열의 이웃 노드 정점 위
        치와 비교하며 더 작은 경우에만 탐색을 진행하고 작은 경우 가중치 배열의
        정점 위치의 가중치를 갱신하고 탐색 큐를 추가하여 탐색에 추가, 모든 큐
        를 순회하여 모든 정점까지의 경로 중 가장 작은 간선 간 거리를 구한다.
     4. 산봉우리 배열을 정렬 후 역순으로 순회하여 각 순회 요소에서 가중치가
        같거나 작을 때마다 산봉우리 번호와 가중치를 갱신하여 결과적으로 가장
        작은 가중치에서 가장 낮은 산봉우리 번호를 반환하여 문제 해결.

     */

    private static class Node implements Comparable<Node> {
        int vertex;
        int distance;

        public Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        // 그래프 초기화
        List<List<Node>> graph = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 산봉우리 확인용 집합
        Set<Integer> summitSet = new HashSet<>();
        for (int summit : summits) {
            summitSet.add(summit);
        }

        // 출발점 확인용 집합
        Set<Integer> gateSet = new HashSet<>();
        for (int gate : gates) {
            gateSet.add(gate);
        }

        // 그래프에 이웃 노드 정보 추가
        for (int[] path : paths) {
            int vertex1 = path[0];
            int vertex2 = path[1];
            // 이웃 정점이 출발지가 아닌 경우와 시작 정점이 산봉우리가 아닌 경우만 추가
            if(!gateSet.contains(vertex2) && !summitSet.contains(vertex1)) {
                graph.get(vertex1).add(new Node(vertex2, path[2]));
            }
            if(!gateSet.contains(vertex1) && !summitSet.contains(vertex2)) {
                graph.get(vertex2).add(new Node(vertex1, path[2]));
            }
        }

        // 가중치 배열 초기화
        int[] intensityArr = new int[n + 1];
        Arrays.fill(intensityArr, Integer.MAX_VALUE);

        // 다익스트라 탐색 큐
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 큐에 출발점 모두 추가
        for (int gate : gates) {
            pq.add(new Node(gate, 0));
        }

        // 출발점 노드부터 각 경로의 최단 가중치들 갱신
        dijkstra(pq, graph, n, intensityArr);

        Arrays.sort(summits);

        int[] result = {-1, Integer.MAX_VALUE}; // 산봉우리 번호, 가중치
        for(int i = summits.length - 1; i >= 0; i--) {
            int summitNo = summits[i];
            int curIntensity = intensityArr[summitNo];
            if(curIntensity <= result[1]) {
                result[0] = summitNo;
                result[1] = curIntensity;
            }
        }

        return result;
    }

    private void dijkstra(PriorityQueue<Node> pq, List<List<Node>> graph,
                          int n, int[] intensityArr) {
        // 탐색 여부 확인용 배열
        boolean[] visited = new boolean[n + 1];

        while(!pq.isEmpty()) {
            Node current = pq.poll();

            // 현재 정점이 이미 탐색됐다면 다음 탐색으로
            if(visited[current.vertex]) continue;
            visited[current.vertex] = true;

            // 이웃 노드까지 가중치중 더 작은 경로에 탐색 추가
            List<Node> neighbors = graph.get(current.vertex);
            for (Node neighbor : neighbors) {
                // 현재 가중치와 이웃노드 가중치중 큰 값 선택
                int intensity = Math.max(current.distance, neighbor.distance);
                // 큰 값이 가중치 배열 정점 위치의 값보다 작을때만 탐색
                if(intensity < intensityArr[neighbor.vertex]) {
                    intensityArr[neighbor.vertex] = intensity;
                    pq.add(new Node(neighbor.vertex, intensity));
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution30ByLv3 solution = new Solution30ByLv3();

        int n = 6;
        int[][] paths = {{1, 2, 3}, {2, 3, 5}, {2, 4, 2}, {2, 5, 4}, {3, 4, 4}, {4, 5, 3}, {4, 6, 1}, {5, 6, 1}};
        int[] gates = {1, 3};
        int[] summits = {5};
        System.out.println(Arrays.toString(solution.solution(n, paths, gates, summits)));
        // [5, 3]
    }
}