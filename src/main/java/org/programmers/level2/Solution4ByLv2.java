package org.programmers.level2;

import java.util.*;

public class Solution4ByLv2 {

    public static class Node {
        int y, x, distance;

        Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static String[][] maze;
    static boolean[][] visitedNodes;

    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};

    public int solution(String[] maps) {
        int xLength = maps[0].length();
        int yLength = maps.length;

        maze = new String[yLength][xLength];
        visitedNodes = new boolean[yLength][xLength];

        int[] startNode = new int[2];
        int[] leverNode = new int[2];

        for (int i = 0; i < maps.length; i++) {
            String[] nodes = maps[i].split("");
            for (int j = 0; j < nodes.length; j++) {
                String node = nodes[j];
                if (node.equals("S")) startNode = new int[]{i, j};
                if (node.equals("L")) leverNode = new int[]{i, j};
                maze[i][j] = node;
            }
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startNode[0], startNode[1]));

        int leverDistance = getHistoryBfs("L", queue);
        if(leverDistance == -1) return -1;

        queue = new LinkedList<>();
        queue.add(new Node(leverNode[0], leverNode[1]));
        visitedNodes = new boolean[yLength][xLength];

        int leverToExitDistance = getHistoryBfs("E", queue);
        if(leverToExitDistance == -1) return -1;

        return leverDistance + leverToExitDistance;
    }

    static int getHistoryBfs(String goal, Queue<Node> queue) {
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int currentDistance = current.distance;
            visitedNodes[current.y][current.x] = true;
            if (getNodeType(current.y, current.x).equals(goal)) return current.distance;

            for (int i = 0; i < 4; i++) {
                checkNextNode(queue, current, dCol[i], dRow[i], currentDistance + 1);
            }
        }
        return -1;
    }

    static String getNodeType(int y, int x) {
        return maze[y][x];
    }

    static void checkNextNode(Queue<Node> queue, Node node, int dCol, int dRow, int newDistance) {
        int x = node.x + dCol;
        int y = node.y + dRow;
        if(x < 0 || x > maze[0].length -1 || y < 0 || y > maze.length - 1 || visitedNodes[y][x]) return;

        visitedNodes[y][x] = true;
        Node nextNode = new Node(y, x);
        if (!getNodeType(y, x).equals("X")) {
            nextNode.distance = newDistance;
            queue.add(nextNode);
        }
    }

}