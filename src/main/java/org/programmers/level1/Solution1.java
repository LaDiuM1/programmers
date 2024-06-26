package org.programmers.level1;

public class Solution1 {

    /*
     * [PCCE 기출문제] 9번 / 이웃한 칸
     * <p>
     * 각 칸마다 색이 칠해진 2차원 격자 보드판이 있습니다.
     * 그중 한 칸을 골랐을 때, 위, 아래, 왼쪽, 오른쪽 칸 중 같은 색깔로 칠해진 칸의 개수를 구하려고 합니다.
     * 보드의 각 칸에 칠해진 색깔 이름이 담긴 이차원 문자열 리스트 board와
     * 고른 칸의 위치를 나타내는 두 정수 h, w가 주어질 때
     * board[h][w]와 이웃한 칸들 중 같은 색으로 칠해져 있는 칸의 개수를 return 하도록 solution 함수를 완성해 주세요.
     *
     */

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
