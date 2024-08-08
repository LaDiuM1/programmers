package org.programmers.level3;

import java.util.*;
import java.util.stream.Collectors;

public class Solution3ByLv3 {

    /**
     * 이 문제는 카테고리는 DFS/BFS 탐색이나 문제를 읽어 봤을 때
     * 중복되는 네트워크를 별도의 자료구조로 저장하고 해당 네트워크에
     * 연결되어있는 노드를 추가하며 총 네트워크 갯수를 세면 된다고 생각했다.
     *
     * 처음에는 map 자료구조에 노드 번호를 키로, 값은 연결된 노드를 추가하며
     * 모든 노드에 연결된 노드들을 순회마다 업데이트 하고 value를 모두 순회하여 set
     * 자료구조로 변환, 이 사이즈를 재면 된다고 생각하였으나 해결하기 위한 의도와 다르게
     * 구현되어 구상과 가장 비슷한 알고리즘을 찾아보았다.
     * 알고리즘을 찾아보니 union find라는 경로 압축을 통한 탐색 알고리즘이 가장 의도와 비슷하였고
     * 해당 알고리즘 개념을 학습 후 문제 해결을 진행하였다.
     */

    public int solution(int n, int[][] computers) {
        int[] parents = new int[n];

        // 각 노드의 부모 위치를 현재 노드로 초기화
        for(int i = 0; i < n; i++) {
            parents[i] = i;
        }

        // 각 노드마다 연결된 네트워크를 하나의 집합(최초 루트 값)으로 병합
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(computers[i][j] == 1) {
                    if(i == j) continue;
                    union(parents, i, j);
                }
            }
        }

        // 부모 배열을 모두 경로 압축하여 배열 최종 업데이트
        for (int i = 0; i < n; i++) {
            find(parents, i);
        }

        // 중복 요소 제거 후 개수 반환 (집합의 개수, 네트워크 수)
        return Arrays.stream(parents).boxed().collect(Collectors.toSet()).size();
    }

    // 노드의 부모가 자기 자신을 가리킬 때까지 탐색(최초 루트 탐색) 후 루트 값 반환
    public int find(int[] parents, int node) {
        if(parents[node] != node) {
            parents[node] = find(parents, parents[node]);
        }

        return parents[node];
    }

    // 현재 노드와 연결 노드의 최초 루트를 찾은 후 최초 루트가 다를 시 연결 노드의 최초 루트에 현재 노드의 루트값을 적용(병합)
    // 이 과정은 서로 다른 집합을 하나의 집합으로 병합하는 과정과 동일
    public void union(int[] parents, int current, int connection) {
        int root1 = find(parents, current);
        int root2 = find(parents, connection);

        if(root1 != root2) parents[root2] = root1;
    }

}