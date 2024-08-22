package org.programmers.level3;

public class Solution12ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/67259]
     * 2020 카카오 인턴십
     * 경주로 건설
     *
     * 문제를 처음 읽어 봤을 때 모든 경로에 대한 최소 비용을 구하는 문제라고 생각하여
     * dfs로 모든 문제를 탐색하며 백트래킹을 이용해 모든 경로를 탐색 후 도착지에 도달한
     * 총 비용에서 각 경로마다 총 비용이 가장 최소인 비용을 반환하려고 시도하였다.
     * 구현 후 테스트 시도 시 몇개의 테스트에서 시간 초과가 발생하여 최적화에 대한 구상을
     * 진행하였으며 최소 비용으로 목적지에 도달해야 한다면 결국 각 블럭마다의 비용도 최소 비용으로
     * 이동해야 함을 깨달았다, 그래서 블럭마다의 비용을 저장하는 2차원 배열을 생성하여
     * 이동 시마다 블럭의 총 비용을 갱신 후, 기존 비용보다 총 비용이 클 시 백트래킹을 하여
     * 경로 최적화를 진행하였다, 구현 후 테스트 시 아주 빠른 시간에 테스트를 완료 할 수 있었지만
     * 차후에는 BFS를 사용하는 것이 조금이라도 더 최소 횟수로 결과를 도출 할 수 있을걸로 보여
     * 이런 카테고리의 문제에는 큐와 BFS를 사용하도록 해야겠다.
     *
     */

    public static class Root {
        int x = 0;
        int y = 0;
        int nowVector = -1;
        int totalCost = 0;
        boolean[][] visited;

        public Root(boolean[][] visited){
            this.visited = visited;
        }
    }

    int[][] board;
    int[][] costMatrix;
    int[][] vector = { {0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 오른쪽부터 시계방향
    int minimumCost = Integer.MAX_VALUE;

    public int solution(int[][] board) {
        this.board = board;
        this.costMatrix = new int[board.length][board[0].length];
        boolean[][] visited = new boolean[board.length][board[0].length];
        visited[0][0] = true;
        dfs(new Root(visited));

        return minimumCost;
    }

    public void dfs(Root root) {
        if(root.totalCost >= minimumCost || (costMatrix[root.y][root.x] > 0 && root.totalCost > costMatrix[root.y][root.x])) return;
        if(root.x == board[0].length -1 && root.y == board.length -1) {
            minimumCost = root.totalCost;
            return;
        }

        int originalCost = root.totalCost;
        int originalX = root.x;
        int originalY = root.y;
        int originalVector = root.nowVector;
        costMatrix[root.y][root.x] = root.totalCost;

        for(int i = 0; i < vector.length; i++) {
            if(checkPossibleMove(vector[i], root.x, root.y)
                    && !root.visited[root.y + vector[i][0]][root.x + vector[i][1]]) {
                root.visited[root.y + vector[i][0]][root.x + vector[i][1]] = true;
                root.totalCost += root.nowVector != -1 && root.nowVector != i ? 600 : 100;
                root.x += vector[i][1];
                root.y += vector[i][0];
                root.nowVector = i;
                dfs(root);
                root.visited[root.y][root.x] = false;
                root.totalCost = originalCost;
                root.x = originalX;
                root.y = originalY;
                root.nowVector = originalVector;
            }
        }
    }

    private boolean checkPossibleMove(int[] vector, int x, int y) {
        x = x + vector[1];
        y = y + vector[0];

        return x >= 0 && x < board[0].length && y >= 0
                && y < board.length && board[y][x] == 0;
    }

}