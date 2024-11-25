package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class SolutionByDay21 {

    /**

     [https://www.acmicpc.net/problem/11657]
     백준 11657 - 타임머신

     -- 문제
     음수 가중치가 존재하는 단방향 그래프에서 시작점에서 출발하여 다른 모든 노드
     까지의 최단거리를 구하고 다른 노드에 도달할 수 없거나 무한한 음수 사이클을
     발견하는 문제이다.

     -- 문제 분석
     이 문제는 얼마 전에 풀었던 웜홀 문제와 로직이 거의 동일한 문제이다. 다른 점
     이 있다면, 웜홀 문제는 모든 노드에서 출발할 수 있는 경우가 기준이고 이 문제
     는 시작 노드가 정해져 있다는 점이다.

     일단 무한 음수 사이클이 존재하는 음수 가중치 그래프이기 때문에 웜홀과 마찬가지
     로 벨만-포드 알고리즘을 이용해서 풀어야 할 것이다. 벨만-포드로 v-1회 반복으로
     최단거리를 구하고 더 반복해서 최단거리가 갱신된다면 무한 음수 사이클이 존재한다
     는 의미이므로 해당 알고리즘으로 문제를 풀어야 한다.

     알고리즘이 정의 됐으니 코드 흐름을 작성해보자. 아래는 코드의 흐름이다.

     1. 입력 값으로 단뱡향 그래프를 그리고 벨만 포드 알고리즘 작성.
     2. 벨만 포드에서는 가중치 배열을 최댓값으로 초기화, 시작 노드를 가중치 0으로
        설정하고 v - 1회만큼 이웃 노드의 최단 거리 갱신
     3. v - 1회 이전에 더 이상 갱신되지 않는 경우는 최적화를 위해 바로 정답을 반환.
     4. v - 1회 이후 갱신되는 최단거리가 있다면 무한 음수 사이클 존재함으로 반환
     5. 무한 음수 사이클이 있는 경우 -1로 정답을 반환하고 아닌 경우 가중치 배열 2번부터
        순회하여 해당 노드까지의 최단 거리를 정답에 누적, 만약 순회 요소의 가중치가 INF
        초기 값이라면 -1을 추가 후 모두 순회하여 정답 반환

     -- 문제 발견
     코드의 흐름대로 실제 코드를 작성하고 결과를 제출하였으나 출력 초과가 발생하였다.
     일단 출력 초과라는 용어상 여러 해석이 가능하기 때문에 용어의 의미 해석보다는 코드의
     문제를 분석해보았다.

     코드를 보았을 때 이론상 흐름의 문제는 없었다. 그러면 아까 본 출력 초과의 관점에서
     한 번 살펴본다면 단순히 생각했을 때 값의 범위가 -10,000 ~ 10,000에 v가 500이라면
     시작점부터 도착까지 누적 거리의 최대 범위는 -4,990,000 ~ 4,990,000 정도일 것이다.
     처음에도 이런 생각으로 가중치 배열을 int 형으로 선언했으나 이 문제는 음수의 사이클이
     존재할 수 있다는 점을 간과하였다. 만약 시작 지점에서 출발하여 다시 시작 지점으로 돌아
     오는 사이클이 존재한다면 최단 거리를 1회 갱신할 때마다 -4,990,000만큼 값이 계속 감소
     한다는 얘기가 된다. 이는 벨만 포드에서 수행하는 v - 1회인 499회를 반복한다면 값은
     -4,990,000 * 499 = -2,490,010,000까지 값이 감소할 수 있다는 얘기가 된다. 이는
     int형의 최소 범위인 약 -21억을 하회하는 값으로 언더플로우가 발생할 수 있기 때문에
     출력 초과의 원인으로 지목 가능하다.

     하여 자료형을 long으로 변경하여 제출하였으며 정상적으로 테스트를 통과하였다.

     -- 회고
     이 문제는 문제로 주어진 값의 범위와 사용되는 알고리즘의 최대 연산 횟수를 정밀하게 계산
     하여야 언더플로우 여부를 알 수 있는 문제이다. 나 같은 경우 보통 문제를 풀 때 값의 범위를
     간략하게 확인하고 풀이 알고리즘 구상에 집중하는데 이 문제의 경우 이 점이 문제가 되었다.
     이번은 시간적 여유가 있고 경험해 본 문제라 금방 해결할 수 있었지만 풀이 조건이 대단히 복
     잡한 경우 이런 풀이 과정은 문제가 될 수 있다. 이번에 이런 유형을 경험해 보았으니 다음에
     문제를 풀 때는 알고리즘 구상 완료 후 오랜 시간이 걸리지 않는다면 한번 더 입력 범위에 대해
     고민해 볼 필요를 느끼게 해 준 문제였다.

     */
    public static class Node {
        int no, weight;

        public Node(int no, int weight) {
            this.no = no;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int v = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Node>> graph = new ArrayList<>();
        for(int i = 0; i <= v; i++) {
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph.get(start).add(new Node(end, weight));
        }

        long[] result = bellmanFord(1, v, graph);
        if(result == null) {
            sb.append(-1);
        } else {
            for(int i = 2; i <= v; i++) {
                if(result[i] == Long.MAX_VALUE) {
                    sb.append(-1);
                } else {
                    sb.append(result[i]);
                }
                if(i < v) {
                    sb.append('\n');
                }
            }
        }

        bw.write(sb.toString());
        bw.close();
    }

    private static long[] bellmanFord(int startNo, int v, List<List<Node>> graph) {
        long[] weights = new long[v + 1];
        Arrays.fill(weights, Long.MAX_VALUE);
        weights[startNo] = 0;

        for(int i = 0; i < v - 1; i++) {
            boolean flag = false;
            for(int curNo = 1; curNo <= v; curNo++) {
                for (Node neighbor : graph.get(curNo)) {
                    if (weights[curNo] != Long.MAX_VALUE && weights[curNo] + neighbor.weight < weights[neighbor.no]) {
                        weights[neighbor.no] = weights[curNo] + neighbor.weight;
                        flag = true;
                    }
                }
            }

            if(!flag) return weights;
        }

        for(int curNo = 1; curNo <= v; curNo++) {
            for (Node neighbor : graph.get(curNo)) {
                if (weights[curNo] != Long.MAX_VALUE && weights[curNo] + neighbor.weight < weights[neighbor.no]) {
                    return null;
                }
            }
        }

        return weights;
    }
}