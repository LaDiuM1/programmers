package org.programmers.level2;

import java.util.*;

public class Solution26ByLv2 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/258711]
     2024 KAKAO WINTER INTERNSHIP
     도넛과 막대 그래프

     이 문제는 그래프에서 생성 정점부터 시작하여 독립적인 모양들의 그래프가 있으며
     도넛 모양, 막대 모양, 8자 모양 그래프의 개수와 생성 정점의 번호를 찾는 문제
     이다.

     -- 문제 분석
     문제의 처음 접근은 각 모양을 분류하는 방법부터 찾아보는 것이었다. 각 모양에
     대한 조건이 명시되어 있으나 간선이 100만 개가 넘어가는 문제이기 때문에 완전
     탐색으로 모든 조건 일치 여부를 찾는 것보다 모양 분류 조건을 단순화할 수 있는
     지 한번 생각해 보았다. 지문에서는 각 모양별 특징이 그림으로 그려져 있었는데
     잘 살펴보면 8자 모양과 막대 모양은 독립적인 특징을 가지고 있었고 도넛 모양만
     다른 모양과 겹치는 특징을 가지고 있었다. 모양별 특징으로는 8자 모양은 특정
     정점에서 이웃 정점이 2개 이상인 모양이고, 막대 모양은 마지막 정점에서 이웃
     정점이 없는 모양이다. 도넛 모양은 시작 정점으로 회귀하는 모양이기 때문에
     3개의 모양은 if-else의 관계로 모양이 형성되어 있었다. 모양 분류를 단순화
     하였으니 생성 정점을 특정하기 위해 특징을 살펴보았다. 생성 정점은 시작 지점
     이기 때문에 생성 정점으로의 간선은 없다. 하지만 난해한 점이 막대 모양에도
     시작 정점은 도착 간선이 없기 때문에 막대 모양 중간에 생성 정점이 연결되어
     있는 경우 생성 정점을 독립적으로 분리할 수 없는 문제가 발생하였다. 한참을
     들여보다 둘을 구분하는 독립적인 특징이 지문에서는 없었기 때문에 제한 사항을
     살펴보니 모양은 반드시 두 개 이상 존재한다는 내용이 명시되어 있었다. 아마
     생성 정점을 분리하기 위한 배려인 것으로 보이며 이는 곧 생성 정점은 도착
     간선이 없고 이웃 간선이 두 개 이상인 정점으로만 특정되기 때문에 생성 정점
     을 찾는게 가능하다. 해결에 필요한 모든 조건을 확인하였으니 코드를 작성하자.

     -- 구현
     1. 모든 정점 집합에서 도착 간선이 있는 정점들의 차집합을 구한다.
     2. 도착 간선이 없는 생성 정점 집합 후보에서 이웃 정점이 2개 이상인 정점을
        찾아 생성 정점으로 특정한다.
     3. 생성 정점의 이웃 정점들을 리스트로 호출하고 (모양의 시작 정점들) 순회한다.
     4. 시작 정점을 큐로 탐색하여 이웃 정점이 없는 경우는 막대 모양, 이웃 정점이
        두 개 이상인 경우 8자 모양, 이웃 정점에 시작 정점이 있는 경우를 도넛 모양
        으로 조건을 주어 BFS로 그래프를 탐색한다. 그래프 탐색 시 HashSet 구조로
        탐색 여부를 판단하면서 순회하며 시작 모양 정점들을 모두 순회 시 결과를
        반환한다.

     */

    public int[] solution(int[][] edges) {
        Map<Integer, List<Integer>> graphMap = new HashMap<>();
        Set<Integer> vertexSet = new HashSet<>();   // 정점 집합
        Set<Integer> arrivalVertexSet = new HashSet<>(); // 생성 정점 확인용 도착 정점 집합
        // 그래프 그리기, 정점 집합과 도착 정점 집합에 정점들 추가
        for (int[] edge : edges) {
            int departure = edge[0];
            int arrival = edge[1];
            vertexSet.add(departure);
            vertexSet.add(arrival);
            arrivalVertexSet.add(arrival);

            if(!graphMap.containsKey(departure)) {
                ArrayList<Integer> newList = new ArrayList<>();
                newList.add(arrival);
                graphMap.put(departure, newList);
            } else {
                graphMap.get(departure).add(arrival);
            }
        }

        int[] result = new int[4]; // 생성 번호, 도넛 모양, 막대 모양, 8자 모양
        // 정점 집합에서 도착 집합의 차집합 추출
        vertexSet.removeAll(arrivalVertexSet);

        // 차집합(도착 간선이 없는 정점들에서) 출발 간선이 2개 이상인 경우(입력 값의 조건) 생성 정점
        for (int vertexNo : vertexSet) {
            if(graphMap.get(vertexNo).size() >= 2) {
                result[0] = vertexNo;
                break;
            }
        }

        // 생성 번호의 이웃 정점 호출 (모양의 출발 정점들)
        List<Integer> exploreList = graphMap.get(result[0]);
        Set<Integer> visited = new HashSet<>();

        // 특정 모양의 출발 정점들부터 탐색하여 각 모양들의 조건들을 확인
        nextStartVertex :
        for (int startNo : exploreList) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startNo);

            while(!queue.isEmpty()) {
                int curVertex = queue.poll();

                if(!visited.add(curVertex)) continue;

                if(!graphMap.containsKey(curVertex)) { // 이웃 정점이 없는 경우 막대 모양
                    result[2]++;
                    continue nextStartVertex;
                }

                List<Integer> neighbors = graphMap.get(curVertex);

                if(neighbors.size() > 1) { // 이웃 정점이 두 개 이상인 경우 8자 모양
                    result[3]++;
                    continue nextStartVertex;
                }

                for (int neighbor : neighbors) { // 이웃 정점이 시작 정점일 시 도넛 모양
                    if(neighbor == startNo) {
                        result[1]++;
                        continue nextStartVertex;
                    }
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution26ByLv2 solution = new Solution26ByLv2();

        List<int[][]> cases = new ArrayList<>(
                Arrays.asList(
                        new int[][]{{2, 3}, {4, 3}, {1, 1}, {2, 1}},
                        new int[][]{{4, 11}, {1, 12}, {8, 3}, {12, 7}, {4, 2}, {7, 11}, {4, 8}, {9, 6}, {10, 11}, {6, 10}, {3, 5}, {11, 1}, {5, 3}, {11, 9}, {3, 8}}
                )
        );

        for (int[][] aCase : cases) {
            System.out.println(Arrays.toString(solution.solution(aCase)));
        }

        /*
         * [2, 1, 1, 0]
         * [4, 0, 1, 2]
         * */
    }
}