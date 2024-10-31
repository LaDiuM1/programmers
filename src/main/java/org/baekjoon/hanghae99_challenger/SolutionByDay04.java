package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class SolutionByDay04 {
    /**
     [https://www.acmicpc.net/problem/1865]
     웜홀

     -- 문제
     그래프에서 최단거리를 탐색하는 문제이며 음의 가중치도 가진 중복 경로가 존재하는
     탐색 문제이다.

     -- 문제 분석
     기존에 접한 그래프 최단 경로 탐색 문제는 다익스트라와 플로이드 워셜 알고리즘으로
     모두 해결이 가능하였으나 이 문제는 분석을 진행할수록 복잡한 문제에 직면한 문제
     였다. 첫 번째로 중복 경로가 있는 것이 문제가 되었는데, 음의 가중치를 가진 간선
     이 존재하기 때문에 특정 경로에서는 더 짧은 경로라도 다른 간선에서 그다음 간선
     이 음의 가중치를 가진다면 최단거리를 특정할 수 없는 문제가 발생한다. 그래서
     DFS로 각 정점마다 모든 이웃 정점을 확인하는 방법을 구상해 보았는데 곧 치명적인
     문제에 직면하였다. 바로 정점들이 무한하게 순환하는 구조가 존재할 때 간선의 합이
     음수라면 해당 순환을 이루는 간선들이 무한한 음수로 끝 없이 갱신되는 문제가 발생
     발생할 수 있다는 것이다. 이 문제를 해결하려면 최단거리를 갱신하기 위해 필요한
     횟수만큼 최단 거리를 갱신해야 한다. 거기에 더해 그래프에서 무한 음수 순환 구조가
     존재하는지 정확히 필요한 만큼만 효율적으로 순회해서 확인해야 한다. 이는 이 문제의
     권장 시간인 한 시간 반 이내에 구상 및 해결까지 완료하기 힘든 구조적 복잡함이었다.
     그래서 이 문제는 구현 능력을 보는 게 아닌 특정 알고리즘을 타겟으로 나온 문제일 것
     이기 때문에 문제 구상을 멈추고 해결 알고리즘을 검색해 보았다. 검색해 보니 이 문제
     는 벨만-포드라는 알고리즘으로 해결해야 하는 문제인 것으로 보이며 벨만 포드 알고리즘
     은 음의 가중치를 가질 때 각 시작 정점별로 V - 1회만큼 반복하여 모든 이웃 정점의
     최단 거리를 갱신하고 이후 반복할 때 갱신되는 최단 거리는 무한 음수 사이클이라고
     부르며 특정할 수 있다는 것이다. 무한 순환 구조를 가장 최소의 비용으로 판별할 수
     있는 방법이 필요했는데 정확히 이 알고리즘이 그러했으며 V - 1회라는 힌트를 얻었으니
     나머지는 고민 과정 중 떠오른 방법들로 해결하면 될 것으로 보인다.

     -- 구현
     1. 그래프를 선언한다. 이 문제는 N과 M의 제한 조건이 상당히 높기 때문에 Map보다는 메모리
        효율성을 위해 ArrayList를 선언한다.
     2. 정점 번호와 가중치를 가진 클래스를 선언하고 일반 간선은 양방향, 웜홀 간선은 단방향으로
        거리를 음수로 저장하여 그래프를 그린다.
     3. 모든 정점을 시작점으로 벨만 포드 알고리즘으로 문제의 조건을 확인한다. 벨만 포드에서는
        시작 정점을 0, 나머지 경로를 최댓값으로 초기화하고 V - 1 횟수만큼 반복하여 모든 이웃
        정점을 탐색하여 최단 거리를 갱신하고 효율성을 위해 반복 시 갱신이 없을 때에는 바로 순회
        를 종료한다, 그리고 시작 정점이 음수이거나 정점들을 한번 더 순회하여 최단거리 갱신이
        발생하면 문제 true을 반환한다.
     4. 각 시작 정점 순회 시 벨말 포드 메서드의 반환값이 true인 경우 출력문을 StringBuilder에
        쓰고 바로 다음 케이스로 이동한다. 모든 정점을 순회할 시 조건 불일치에 해당하는 출력문을
        쓴다.
     5. 모든 테스트 케이스를 순회한 후 StringBuilder에 쓰여진 값을 한번에 출력하여 문제를 해결
        한다.

     -- 총평
     3일차까지는 해결이 쉽게 떠오르는 문제만 나왔지만 이번 문제는 그래프 탐색에서의 이해를 또 한번
     넓혀주는 좋은 문제였다. 음의 가중치가 존재할 것이라는 건 예상할 수 있었지만 그로인해 파생되는
     여러 문제들을 접할 수 있는 경험이 되는 좋은 문제였다.

     */

    static final int INF = 99999999;
    private static class Node {
        int no;
        int dist;

        public Node(int no, int dist) {
            this.no = no;
            this.dist = dist;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        final int numOfCase = Integer.parseInt(br.readLine());
        nextCase :
        for(int i = 0; i < numOfCase; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            final int V = Integer.parseInt(st.nextToken());
            final int M = Integer.parseInt(st.nextToken());
            final int W = Integer.parseInt(st.nextToken());

            List<List<Node>> graph = new ArrayList<>();
            for(int j = 0; j <= V; j++) {
                graph.add(new ArrayList<>());
            }
            // 일반 간선 추가
            for(int j = 0; j < M; j++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int dist = Integer.parseInt(st.nextToken());

                graph.get(start).add(new Node(end, dist));
                graph.get(end).add(new Node(start, dist));
            }
            // 웜홀 간선 추가, 웜홀은 방향이 있으며 음수 가중치를 가짐
            for(int j = 0; j < W; j++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int dist = Integer.parseInt(st.nextToken());

                graph.get(start).add(new Node(end, -dist));
            }

            /* 그래프 탐색, 모든 정점에 대해 벨만-포드로 음수 사이클이 존재하는지 여부와
               시작점에 음수 간선이 존재하는지 같이 확인 */
            int[] dist = new int[V + 1];
            for (int startNo = 1; startNo <= V; startNo++) {
                if (bellmanFord(startNo, graph, V, dist)) {
                    // 조건 만족 시 YES 출력 후 다음 케이스로
                    sb.append("YES\n");
                    continue nextCase;
                }
            }
            // 조건을 만족하는 정점이 없음
            sb.append("NO\n");
        }

        bw.write(sb.toString());
        bw.close();
    }

    private static boolean bellmanFord(int startNo, List<List<Node>> graph, int V, int[] dist) {
        // 시작 정점을 제외한 최단 거리 배열을 최댓값으로 초기화
        Arrays.fill(dist, INF);
        dist[startNo] = 0;

        // V - 1회 반복 (이웃 정점에 대한 완전 탐색으로 최단 거리 갱신)
        for(int i = 0; i < V - 1; i++) {
            // 각 반복마다 갱신 여부 확인용 플래그
            boolean isUpdated = false;
            for(int curNo = 1; curNo <= V; curNo++) {
                for (Node neighbor : graph.get(curNo)) {
                    if(dist[curNo] != INF && dist[curNo] + neighbor.dist < dist[neighbor.no]) {
                        dist[neighbor.no] = dist[curNo] + neighbor.dist;
                        isUpdated = true;
                    }
                }
            }
            // 최단거리를 모두 구했을 시 루프 정지
            if(!isUpdated) break;
        }

        // 시작점이 음수면 바로 결과 반환
        if(dist[startNo] < 0) return true;

        // V - 1회 이후 값이 갱신되면 무한 음수 사이클 존재
        for(int curNo = 1; curNo <= V; curNo++) {
            for (Node neighbor : graph.get(curNo)) {
                if(dist[curNo] != INF && dist[curNo] + neighbor.dist < dist[neighbor.no]) {
                    return true;
                }
            }
        }

        return false;
    }
}