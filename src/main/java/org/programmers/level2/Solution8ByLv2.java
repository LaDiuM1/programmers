package org.programmers.level2;

import java.util.*;

public class Solution8ByLv2 {

    /**
     *
     * 문제의 지문을 읽었을 때 난이도가 크게 높지 않은 문제로 보인다.
     * 배열을 반복하여 석유를 발견 시(미탐색 영역) BFS로 석유 크기를 계산하고, 탐색했던 열에
     * 석유 크기를 더해 이를 maxCount와 비교하여 더 큰 값을 반환하면 되는 것으로 보인다.
     *
     */

    public static class Node {
        int row, col;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    int numRows, numCols, maxCount = 0;
    boolean[][] visited;
    int[] reserves;
    int[][] vectors = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} }; // 시계방향 탐색
    int[][] land;

    public int solution(int[][] land) {
        numRows = land.length;
        numCols = land[0].length;
        this.land = land;
        this.reserves = new int[numCols];
        visited = new boolean[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                if(!visited[i][j] && land[i][j] == 1) {
                    bfs(i, j);
                }
            }
        }
        return maxCount;
    }

    public void bfs(int row, int col) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(row, col));
        visited[row][col] = true;
        int count = 0;

        Set<Integer> reserveArea = new HashSet<>();
        reserveArea.add(col);

        while(!queue.isEmpty()) {
            Node node = queue.poll();
            count++;

            for (int i = 0; i < 4; i++) {
                int nextRow = node.row + vectors[i][0];
                int nextCol = node.col + vectors[i][1];

                if (checkValid(nextRow, nextCol)) {
                    reserveArea.add(nextCol);
                    visited[nextRow][nextCol] = true;
                    queue.add(new Node(nextRow, nextCol));
                }
            }
        }

        for (Integer x : reserveArea) {
            reserves[x] += count;
            maxCount = Math.max(maxCount, reserves[x]);
        }
    }

    public boolean checkValid(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && !visited[row][col] && land[row][col] == 1;
    }

}