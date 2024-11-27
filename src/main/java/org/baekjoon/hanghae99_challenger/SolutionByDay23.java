package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SolutionByDay23 {
    /**

     [https://www.acmicpc.net/problem/5972]
     백준 5972 - 택배 배송

     -- 문제
     음수가 없는 정수의 가중치가 있는 양방향 그래프가 주어진다. 1부터 시작하여
     n까지의 최단 거리를 구하는 문제이다.

     -- 문제 분석 및 해결
     항해 99도 막바지에 접어들었기 때문에 그동안 사용한 알고리즘의 기초 복습 느낌
     의 문제처럼 느껴진다. 왜냐하면 최단거리를 구하는 가장 기초적인 문제이기 때문이
     다. 하지만 이러한 유형의 케이스에서는 끝나갈 때 즈음 가장 높은 수준의 문제가
     나오는 경우도 흔하기 때문에 방심은 하면 안 될 것이다.

     이 문제는 최단 거리 계산에 표준에 가까운 문제이기 때문에 플루이드 워셜, 다익
     스트라 모두 사용 가능하다. 이 문제에서는 입력 값이 인접 행렬이 아닌 각각의 간
     선 형태로 주어지기 때문에 입력 값 변환이 필요 없는 다익스트라를 이용하여 해결
     하였다. 다익스트라에 표준 해결 구조를 그대로 따라 작성하였기 때문에 다른 글에
     서 해결 과정은 많이 설명하였기 때문에 여기서는 설명은 패스하도록 하겠다.

     -- 회상
     거의 15분 정도에 해결한 문제이기 때문에 시간적 여유가 있어 입력 값 초기화 부분
     을 전부 메서드로 분리하고 메인 메서드는 최소한의 코드로 작성해 보았다. 하지만
     BufferedReader의 입, 출력 메서드가 체크 예외가 필수기 때문에 메인 코드와 입력
     값 초기화 메서드에 IOException을 두 번 선언하여 그렇게 깔끔한 느낌은 들지 않는
     다. Scanner 클래스는 효율성 측면에서 고려 대상이 아니기 때문에 만약 예외 중복
     선언을 없애려면 main에서 try-catch를 사용해야 하는데 이는 코드의 간결성 측면에
     서는 손해를 보기 때문에 맘에 들지 않는다. 마음 같아서는 입력 값 초기화 부분은
     공통 클래스로 선언하여 사용하고 싶지만 알고리즘 풀이 후 다른 플랫폼을 사용하여
     테스트하는 구조상 이는 제약이 따른다. 결론적으로 입력 값 초기화 부분까지 메서드
     로 분리할 필요성은 없어 보인다.

     */

    private static class Node {
        int no, weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        List<List<Node>> graph = processInput();
        System.out.print(dijkstra(1, graph, graph.size() - 1));
    }

    private static List<List<Node>> processInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        return initGraph(n, m, br);
    }

    private static List<List<Node>> initGraph(int n, int m, BufferedReader br) throws IOException {
        List<List<Node>> graph = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph.get(start).add(new Node(end, weight));
            graph.get(end).add(new Node(start, weight));
        }
        return graph;
    }

    private static int dijkstra(int start, List<List<Node>> graph, int n) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));
        pq.add(new Node(start, 0));
        int[] weight = new int[n + 1];
        Arrays.fill(weight, Integer.MAX_VALUE);
        weight[start] = 0;

        while(!pq.isEmpty()) {
            Node current = pq.poll();

            for(Node neighbor : graph.get(current.no)) {
                int sumWeight = current.weight + neighbor.weight;
                if(sumWeight < weight[neighbor.no]) {
                    weight[neighbor.no] = sumWeight;
                    pq.add(new Node(neighbor.no, sumWeight));
                }
            }
        }

        return weight[n];
    }


}
