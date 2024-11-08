package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SolutionByDay12 {
    /**

     [https://school.programmers.co.kr/learn/courses/30/lessons/258711]
     프로그래머스 - 도넛과 막대 모양

     -- 문제 분석 및 해결
     무려 10일 전에 해결한 문제가 이 플랫폼에서 나왔다. 이 문제를 풀었을 때 레벨 2의
     문제답지 않은 일반화 조건이 꽤 어려운 문제라고 느꼈는데 이 스터디 플랫폼에서도
     챌린저 문제로 나온 것을 보면 꽤 수준이 높은 문제라고 판단한 것으로 보인다.
     해결한 지 얼마 되지 않은 문제였기 때문에 초기화 후 다시 풀었어도 거의 유사한 코드
     가 나왔으며 차이점이라고 하면 출발 노드 판별 시 도착 노드 집합과 그래프맵을 바로
     비교하여 아주 약간 더 최적화가 이루어졌다고 할 수 있다. 실제로 측정 시 높은 메
     모리 소모 구간에서 약 5%정도의 메모리 감소와 속도 향상이 보였다. 크게 유의미한
     수치가 아닐지라도 아주 높은 수준의 문제에서는 이 정도의 최적화에도 통과 실패가
     갈리지 않을까 상상해본다.

     */

    public int[] solution(int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<Integer> existEntryNodes = new HashSet<>();
        for(int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            existEntryNodes.add(end);

            if(graph.containsKey(start)) {
                graph.get(start).add(end);
            } else {
                graph.put(start, new LinkedList<>(Arrays.asList(end)));
            }
        }

        int[] result = {-1, 0, 0, 0}; // 출발 노드, 도넛, 막대, 8자 모양 개수

        for(Integer nodeNo : graph.keySet()) {
            if(!existEntryNodes.contains(nodeNo) && graph.get(nodeNo).size() > 1) {
                result[0] = nodeNo;
                break;
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        for(Integer startNode : graph.get(result[0])) {
            queue.add(startNode);
        }

        Set<Integer> visited = new HashSet<>();

        while(!queue.isEmpty()) {
            int current = queue.poll();

            // 이웃 간선이 없음 (막대 모양)
            if(!graph.containsKey(current)) {
                result[2]++;
                continue;
            }

            List<Integer> neighbors = graph.get(current);

            // 이웃 간선이 2 이상 (8자 모양)
            if(neighbors.size() >= 2) {
                result[3]++;
                continue;
            }

            // 위 조건을 만족하지 않고 이미 탐색한 노드로 돌아옴(도넛 모양)
            if(!visited.add(current)) {
                result[1]++;
                continue;
            }

            for(Integer neighbor : neighbors) {
                queue.add(neighbor);
            }
        }

        return result;
    }

}