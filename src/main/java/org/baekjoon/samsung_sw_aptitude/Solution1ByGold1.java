package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution1ByGold1 {

    /**
     *
     [https://www.acmicpc.net/problem/13460]
     삼성 SW 역량 테스트 기출 문제
     구슬 탈출2

     프로그래머스의 레벨3 문제들은 어느정도 익숙한 패턴들이 나오기 시작하여
     레벨4로 넘어가기 전 백준의 유명한 삼섬 SW 문제도 풀어보았다.
     이 문제는 정해진 크기의 미로 형태의 보드에 두 개의 구슬이 올려져 있으며
     판을 기울여서 빨간 구슬만 구멍에 넣는 게임이다. 하지만 사람 처럼 빨간 구슬을 넣고
     바로 멈추는 것이 아닌 모든 구슬이 이동할 수 없을때까지 기울인 상태를 유지한다는 점이
     차이점이라고 할 수 있다.
     백준에서는 입력을 직접 받으므로 성능 상 좀 더 효율적인 BufferedReader 클래스를 사용하였다.
     문제 자체는 예전에 프로그래머스에서 풀어본 리코쳇 로봇과 어느 정도 유사한 점이 있어 구상은
     꽤 빠르게 할 수 있었다.

     구상
     bfs 탐색을 진행, 보드를 기울이는 방향으로 탐색을 진행하며 각 탐색은 두 구슬이 동시에
     움직이도록 로직 작성, 구슬이 이동할 때 각 방향에서 앞에 있는 구슬부터 동시에 이동시키고 이동할 때마다
     구멍에 들어가는지 조건 확인, 두 구슬의 이동이 끝난 후 각 구슬이 구멍에 들어갔는지 여부를 확인
     만약 파란 구슬이 들어갔다면 다음 탐색으로 이동, 빨간 구슬만 구멍에 들어갔다면 해당 시점에서 탐색
     종료 후 기울인 횟수 반환, 탐색 여부 체크는 각 구슬이 이동 완료 후 이동 완료 위치가 이미 탐색했는지
     여부로 판단, 이후 각 큐의 시작에서 제한사항인 횟수 10회가 넘을 시 실패처리 진행.

     상기 구상을 약간의 객체지향적인 코드로 작성하였으며 큰 무리 없이 문제를 해결 완료하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line1 = br.readLine();
        StringTokenizer st = new StringTokenizer(line1);
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] board = new char[n][m];
        for(int i = 0; i < n; i++) {
            String rows = br.readLine();
            for(int j = 0; j < m; j++) {
                board[i][j] = rows.charAt(j);
            }
        }

        int[][] vector = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 탐색 방향

        boolean[][][][] visited = new boolean[n][m][n][m];

        Queue<Position> queue = new LinkedList<>();
        int[] redPosition = new int[2];
        int[] bluePosition = new int[2];
        int[] goalPosition = new int[2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                switch (board[i][j]) {
                    case 'R': {
                        redPosition[0] = i;
                        redPosition[1] = j;
                        break;
                    }
                    case 'B': {
                        bluePosition[0] = i;
                        bluePosition[1] = j;
                        break;
                    }
                    case 'O': {
                        goalPosition[0] = i;
                        goalPosition[1] = j;
                    }
                }
            }
        }
        Node red = new Node(redPosition[1], redPosition[0]);
        Node blue = new Node(bluePosition[1], bluePosition[0]);
        Position position = new Position(red, blue, 0);
        queue.add(position);

        int result = bfs(queue, goalPosition, board, vector, visited);
        System.out.println(result);
    }

    private static int bfs(Queue<Position> queue, int[] goalPosition, char[][] board, int[][] vector, boolean[][][][] visited) {
        while(!queue.isEmpty()) {
            Position nowPosition = queue.poll();
            Node red = nowPosition.red;
            Node blue = nowPosition.blue;

            if (nowPosition.count >= 10) {
                return -1;
            }

            for (int i = 0; i < vector.length; i++) {
                Node newRed = new Node(red.x, red.y);
                Node newBlue = new Node(blue.x, blue.y);

                move(newRed, newBlue, board, i, vector, goalPosition);
                if (newBlue.isGoal) continue;
                if (newRed.isGoal) return nowPosition.count + 1;

                Position newPosition = new Position(newRed, newBlue, nowPosition.count + 1);
                if (!visited[newRed.y][newRed.x][newBlue.y][newBlue.x]) {
                    visited[newRed.y][newRed.x][newBlue.y][newBlue.x] = true;
                    queue.add(newPosition);
                }
            }
        }

        return -1;
    }

    private static void move(Node red, Node blue
            , char[][] board, int vectorIndex, int[][] vector, int[] goalPosition) {

        Node first = red;
        Node second = blue;

        switch (vectorIndex) {
            case 0 : {
                if(blue.y < red.y) {
                    first = blue;
                    second = red;
                    break;
                }
            }
            case 1 : {
                if(blue.x > red.x) {
                    first = blue;
                    second = red;
                    break;
                }
            }
            case 2 : {
                if(blue.y > red.y) {
                    first = blue;
                    second = red;
                    break;
                }
            }
            case 3 : {
                if(blue.x < red.x) {
                    first = blue;
                    second = red;
                }
            }
        }

        while(true) {
            boolean isMoveFirst = false;
            boolean isMoveSecond = false;
            if(!first.isGoal) isMoveFirst = moveOneStep(first, second, board, vectorIndex, vector, goalPosition);
            if(!second.isGoal) isMoveSecond = moveOneStep(second, first, board, vectorIndex, vector, goalPosition);
            if(!isMoveFirst && !isMoveSecond) return;
        }
    }

    private static boolean moveOneStep(Node node1, Node node2, char[][] board, int vectorIndex, int[][] vector, int[] goalPosition) {
        int x = node1.x + vector[vectorIndex][1];
        int y = node1.y + vector[vectorIndex][0];

        if(x >= 0 && x < board[0].length && y >= 0 && y < board.length
                && board[y][x] != '#' && (x != node2.x || y != node2.y || node2.isGoal)) {
            node1.x = x;
            node1.y = y;

            if (node1.x == goalPosition[1] && node1.y == goalPosition[0]) {
                node1.isGoal = true;
            }
            return true;
        }
        return false;
    }

    private static class Position {
        Node red;
        Node blue;
        int count;

        public Position(Node red, Node blue, int count) {
            this.red = red;
            this.blue = blue;
            this.count = count;
        }
    }

    private static class Node {
        int x, y;
        boolean isGoal = false;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}