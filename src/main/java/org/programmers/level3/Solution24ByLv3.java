package org.programmers.level3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solution24ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/250134]
     PCCP 기출문제
     [PCCP 기출문제] 4번 / 수레 움직이기

     이 문제는 전형적인 최단거리 이동 문제이지만 다른 점이 있다면
     두 개의 노드가 동시에 이동하며 어느 하나가 목표해 도달하면 그
     자리에 멈추고 나머지 노드도 목표 위치에 도달하는 최단 거리를
     찾는다는 점이다.

     -- 구상 및 구현
     두 개의 노드가 동시에 여러 방향으로 움직여야 하는 경우지만 이
     경우는 방향 이동을 2중 for문으로 선언하여 각 루트별로 두 개의
     노드가 이동할 수 있는 모든 방향을 확인하는 것으로 간단하게 해결
     하였다, 두 개의 노드가 동시에 이동하므로 변수가 많지만 문제에서
     규칙을 아래와 같이 명확하게 정의하였으므로 규칙과 정확히 일치하는
     조건을 확인하여 BFS로 간단하게 문제를 해결하였다.

     */

    public class Node {
        int count;
        RearCar redCar;
        RearCar blueCar;

        public Node(RearCar redCar, RearCar blueCar, int count) {
            this.redCar = redCar;
            this.blueCar = blueCar;
            this.count = count;
        }
    }

    public class RearCar {
        int x;
        int y;
        boolean[][] visited;
        boolean isGoal = false;

        public RearCar(int x, int y, boolean[][] visited) {
            this.x = x;
            this.y = y;
            this.visited = visited;
        }
    }

    public int solution(int[][] maze) {
        int[][] vector = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int n = maze.length;
        int m = maze[0].length;
        int[] redPosition = new int[2];
        int[] bluePosition = new int[2];

        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                if(maze[i][j] == 1) {
                    redPosition[0] = i;
                    redPosition[1] = j;
                } else if(maze[i][j] == 2) {
                    bluePosition[0] = i;
                    bluePosition[1] = j;
                }
            }
        }

        RearCar redCar = new RearCar(redPosition[0], redPosition[1], new boolean[n][m]);
        RearCar blueCar = new RearCar(bluePosition[0], bluePosition[1], new boolean[n][m]);
        redCar.visited[redCar.x][redCar.y] = true;
        blueCar.visited[blueCar.x][blueCar.y] = true;

        Node node = new Node(redCar, blueCar, 0);

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);

        return bfs(queue, maze, vector);
    }

    public int bfs(Queue<Node> queue, int[][] maze, int[][] vector) {
        int n = maze.length;
        int m = maze[0].length;

        while(!queue.isEmpty()) {
            Node nowNode = queue.poll();
            RearCar red = nowNode.redCar;
            RearCar blue = nowNode.blueCar;

            if(!red.isGoal && maze[red.x][red.y] == 3) {
                red.isGoal = true;
            }

            if(!blue.isGoal && maze[blue.x][blue.y] == 4) {
                blue.isGoal = true;
            }

            if(red.isGoal && blue.isGoal) return nowNode.count;

            for(int i = 0; i < vector.length; i++) {
                for(int j = 0; j < vector.length; j++) {
                    int redX = red.x + vector[i][0];
                    int redY = red.y + vector[i][1];
                    int blueX = blue.x + vector[j][0];
                    int blueY = blue.y + vector[j][1];

                    if(red.isGoal) {
                        redX = red.x;
                        redY = red.y;
                    }
                    if(blue.isGoal) {
                        blueX = blue.x;
                        blueY = blue.y;
                    }

                    if ((red.isGoal || isPossibleMove(redX, redY, maze, red.visited))
                            && (blue.isGoal || isPossibleMove(blueX, blueY, maze, blue.visited))
                            && !(redX == blue.x && redY == blue.y && blueX == red.x && blueY == red.y)
                            && !(redX == blueX && redY == blueY)) {

                        boolean[][] newRedVisited = copyVisited(red, n, m);
                        newRedVisited[redX][redY] = true;
                        RearCar newRed = new RearCar(redX, redY, newRedVisited);

                        boolean[][] newBlueVisited = copyVisited(blue, n, m);
                        newBlueVisited[blueX][blueY] = true;
                        RearCar newBlue = new RearCar(blueX, blueY, newBlueVisited);

                        Node newNode = new Node(newRed, newBlue, nowNode.count + 1);
                        queue.add(newNode);
                    }
                }
            }
        }

        return 0;
    }

    private static boolean[][] copyVisited(RearCar blue, int n, int m) {
        boolean[][] newVisited = new boolean[n][m];

        for(int k = 0; k < n; k++) {
            newVisited[k] = Arrays.copyOf(blue.visited[k], m);
        }
        return newVisited;
    }

    public boolean isPossibleMove(int x, int y, int[][] maze, boolean[][] visited) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length
                && maze[x][y] != 5 && !visited[x][y];
    }

    public static void main(String[] args) {
        Solution24ByLv3 solution = new Solution24ByLv3();

        int[][] maze = {{1, 0, 2}, {0, 0, 0}, {5, 0 ,5}, {4, 0, 3}};

        int result = solution.solution(maze);
        System.out.println("result = " + result);
    }



}