package org.programmers.level2;

import java.util.*;

public class Solution4ByLv2 {

    // {
    //      "S O O O L",
    //      "X X X X O",
    //      "O O O O O",
    //      "O X X X X",
    //      "O O O O E"
    //  }
    //  result 16

    static String[][] maze;

    public class Node {
        int x;
        int y;
        List<int[]> history = new ArrayList<>();
        Node(int x, int y) { this.x = x; this.y = y; }
    }

    // 상하좌우 확인용 벡터
    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};

    public int solution(String[] maps) {
        maze = new String[maps.length][maps[0].length()];
        int[] startNode = new int[1];
        int[] leverNode = new int[1];
        int[] exitNode = new int[1];

        for(int i = 0; i < maps.length; i++) {
            String[] nodes = maps[i].split("");
            for(int j = 0; j < nodes.length; j++) {
                String node = nodes[j];
                if(node.equals("S")) startNode = new int[]{i, j};
                if(node.equals("L")) leverNode = new int[]{i, j};
                if(node.equals("E")) exitNode = new int[]{i, j};
                maze[i][j] = node;
            }
        }
        System.out.println("maze = " + Arrays.deepToString(maze));
        System.out.println("startNode = " + Arrays.toString(startNode));
        System.out.println("startNode = " + Arrays.toString(leverNode));
        System.out.println("startNode = " + Arrays.toString(exitNode));

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startNode[0], startNode[1]));



        return 1;
    }

    static List<int[]> bfs(String goal, String[] maps, Queue<Node> queue) {
        Node current = queue.poll();
        if(maze[current.x][current.y].equals(goal)) return current.history;

        return null;

    }
//
//    static void initAdjList(int numVertices) {
//        for(int i = 0; i < numVertices; i++) adjMaps[i] = new LinkedHashMap<>();
//    }
//
//    static void checkMoveTop(int row, int col, int index) {
//        if(row == 0) return;
//        int adjRow = row - 1;
//        String character = maze[adjRow].split("")[col];
//        if(!character.equals("X")) {
//            adjMaps[index].put(adjRow * maze[0].length() + col, character);
//        }
//    }
//
//    static void checkMoveRight(int row, int col, int index) {
//        if(col == maze[0].length() - 1) return;
//        int adjCol = col + 1;
//        String character = maze[row].split("")[adjCol];
//        if(!character.equals("X")) {
//            adjMaps[index].put(row * maze[0].length() + adjCol, character);
//        }
//    }
//
//    static void checkMoveBottom(int row, int col, int index) {
//        if(row == maze.length - 1) return;
//        int adjRow = row + 1;
//        String character = maze[adjRow].split("")[col];
//        if(!character.equals("X")) {
//            adjMaps[index].put(adjRow * maze[0].length() + col, character);
//        }
//    }
//
//    static void checkMoveLeft(int row, int col, int index) {
//        if(col == 0) return;
//        int adjCol = col - 1;
//        String character = maze[row].split("")[adjCol];
//        if(!character.equals("X")) {
//            adjMaps[index].put(row * maze[0].length() + adjCol, character);
//        }
//    }

}