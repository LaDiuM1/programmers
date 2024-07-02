package org.programmers.level1;

public class Solution1ByLv1 {

    public int solution(String[][] board, int h, int w) {

        String selectedColor = board[h][w];
        int boardWidth = board[0].length;
        int boardHeight = board.length;

        int count = 0;

        if(h > 0 && selectedColor.equals(board[h - 1][w])) count++; // 위
        if(w < boardWidth -1 && selectedColor.equals(board[h][w + 1])) count++; // 오른쪽
        if(h < boardHeight -1 && selectedColor.equals(board[h + 1][w])) count++; // 아래
        if(w > 0  && selectedColor.equals(board[h][w - 1])) count++; // 왼쪽

        return count;
    }
}
