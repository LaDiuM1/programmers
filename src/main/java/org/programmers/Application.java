package org.programmers;

import java.util.*;

public class Application {

    public int solution(int n, int[][] computers) {
        int[] parent = new int[n];

        // 초기화: 각 컴퓨터를 자기 자신을 루트로 설정
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        // 네트워크 병합
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (computers[i][j] == 1) {
                    union(parent, i, j);
                }
            }
        }

        // 고유한 네트워크 수 계산
        int numberOfNetworks = 0;
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {
                numberOfNetworks++;
            }
        }

        return numberOfNetworks;
    }

    // 루트 찾기
    private int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]); // 경로 압축
        }
        return parent[node];
    }

    // 두 노드 병합
    private void union(int[] parent, int node1, int node2) {
        int root1 = find(parent, node1);
        int root2 = find(parent, node2);
        if (root1 != root2) {
            parent[root2] = root1; // 하나의 루트로 병합
        }
    }

    public static void main(String[] args) {
        Application application = new Application();

        int n = 3;
        int[][] computers = {{1, 1, 0}, {1, 1, 1}, {0, 1, 1}};
//        int n = 3;
//        int[][] computers = {{1, 1, 1, 0}, {1, 1, 0, 0}, {1, 0, 1, 0}, {0, 0, 0, 1}};

        int solution = application.solution(n, computers);
        System.out.println(solution);

    }

}