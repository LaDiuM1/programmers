package org.programmers.level2;

import java.util.*;

public class Solution13ByLv2 {

    /**
     * bfs 사용법이 익숙해지기 시작한 문제였다.
     * bfs에 대한 이해가 생기기 시작하니 구상도 빠르게 할 수 있었고
     * 한번에 오류 없이 성공한 케이스 였다.
     * 현재 위치에서 특정 방향으로 이동여부를 체크하기 위해 3차원 배열도 처음으로 사용해보았다.
     *
     */

    public static class Node {
        int x, y,
            vector, // 진행하던 방향, -1은 정지 상태
            count;

        Node (int x, int y, int vector, int count) {
            this.x = x;
            this.y = y;
            this.vector = vector;
            this.count = count;
        }
    }

    String[] board;
    Queue<Node> queue = new LinkedList<>();
    boolean[][][] visible;
    int numOfX, numOfY;

    // 방향, 위부터 시계방향 탐색
    int[] dx = { 1, 0, -1, 0 };
    int[] dy = { 0, 1, 0, -1 };


    public int solution(String[] board) {
        this.board = board;
        this.numOfX = board[0].split("").length;
        this.numOfY = board.length;
        visible = new boolean[numOfY][numOfX][4];

        int[] startPosition = new int[2];
        int[] goalPosition = new int[2];

        // 초기 위치, 목표 위치 탐색
        for (int i = 0; i < numOfY; i++) {
            String[] cols = board[i].split("");

            for(int j = 0; j < cols.length; j++) {
                if(cols[j].equals("R")) startPosition = new int[]{j, i};
                if(cols[j].equals("G")) goalPosition = new int[]{j, i};
            }
        }

        queue.add(new Node(startPosition[0], startPosition[1], -1, 0));

        return bfs(goalPosition[0], goalPosition[1]);
    }

    public int bfs(int goalX, int goalY) {

        while(!queue.isEmpty()) {
            Node nowNode = queue.poll();

            for(int i = 0; i < 4; i++) {
                int moveX = nowNode.x + dx[i];
                int moveY = nowNode.y + dy[i];

                if(checkPossibleMove(moveX, moveY) && !visible[moveY][moveX][i]) {
                    visible[moveY][moveX][i] = true;
                    Node movedNode = move(new Node(moveX, moveY, i, nowNode.count));
                    if(goalX == movedNode.x && goalY == movedNode.y) {
                        return movedNode.count;
                    }
                    queue.add(movedNode);
                }
            }
        }

        return -1;
    }

    public boolean checkPossibleMove(int x, int y) {
        return x >= 0
                && x < numOfX
                && y >= 0
                && y < numOfY
                && board[y].charAt(x) != 'D';
    }

    public Node move(Node node) {
        while(true) {
            int moveX = node.x + dx[node.vector];
            int moveY = node.y + dy[node.vector];

            if(!checkPossibleMove(moveX, moveY)) {
                node.count++;
                return node;
            }

            node.x = moveX;
            node.y = moveY;
        }
    }

}