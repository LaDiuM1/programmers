package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution2ByGold1 {

    /**
     [https://www.acmicpc.net/problem/12100]
     삼성 SW 역량 테스트 기출 문제
     2048 (Easy)

     이 문제는 배열의 모든 값이 한 방향으로 이동하는 게임으로
     값이 같으면 두 값을 합하고, 이동 방향에서 앞에 있는 값을 먼저 합치는 게임이다.
     제한사항으로는 각 이동마다 한번 합쳐진 값은 더 합칠 수 없는 제한사항이 존재하며
     최대 5회 이내에서의 최댓값을 찾는 문제이다.

     문제를 보고 모든 경우의 수를 테스트해야 하니 dfs부터 떠올랐으며 중복 순열 문제이기 때문에
     depth가 조금만 높아져도 시행 횟수가 극적으로 올라가므로 현재 문제에서는 depth가 최대 5이니
     4(방향)의 5제곱(depth) = 1024로 모든 경우의 수를 테스트하기 적절해보여 dfs를 사용하여
     문제를 해결하였다.

     구상은 각 숫자가 앞에서부터 이동하며 한번 병합되면 해당 이동에서는 다시 병합되지 않는다는 조건을
     확인하여 방향에 따른 앞 배열부터 순회하기 위해 방향 배열을 조금 커스텀하는 방법으로 구현하였다.
     vector를 2차원 배열로 선언하여 순회 시작지점, 순회 종료지점, 증감 구분, 가로/세로 구분으로 선언하여
     각 방향마다 가로 순방향, 역방향, 세로 순방향, 역방향으로 순회하기 위한 2차원 배열을 선언하였으며
     각 이동마다의 병합 상태를 관리하기 위해 Unit 클래스를 선언하여 내부에 값과 병합여부를 필드로 선언하였다.
     이후 Unit 클래스 2차원 배열을 선언하여 dfs로 순회, move 메서드에서 현재 순회의 값과 뒤의 값을 비교하여
     조건에 따라 현재 위치로 바꾸거나 병합하는 메서드를 작성하여 문제를 해결하였다.

     */

    static int[][] vector; // 상하좌우
    static int maximumValue = 0;

    public static void main(String[] args) throws IOException {
        String input = """
                3
                2 2 2
                4 4 4
                8 8 8
                """;


        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Unit[][] board = new Unit[n][n];
        vector = new int[][]{
                {0, n - 2, 1, 0},
                {n - 1, 1, -1, 0},
                {0, n - 2, 1, 1},
                {n - 1, 1, -1, 1}
        }; // 0 : 시작, 1 : 종료, 2 : 증감식, 3 : 0 세로, 1 가로a

        for (int i = 0; i < n; i++) {
            String[] rows = br.readLine().split(" ");
            for (int j = 0; j < rows.length; j++) {
                int value = Integer.parseInt(rows[j]);
                if (value != 0) {
                    board[i][j] = new Unit(value);
                    maximumValue = Math.max(value, maximumValue);
                }
            }
        }

        dfs(board, 0);

        System.out.println(maximumValue);
    }

    private static void dfs(Unit[][] board, int depth) {
        if (depth == 5) {
            return;
        }

        for (int i = 0; i < vector.length; i++) {
            Unit[][] newBoard = deepCopy(board);

            if (move(i, newBoard)) {
                dfs(newBoard, depth + 1);
            }
        }
    }

    private static boolean move(int vectorIndex, Unit[][] board) {
        int moveCount = 0;
        boolean isHorizon = vector[vectorIndex][3] == 1;
        int start = vector[vectorIndex][0];
        int end = vector[vectorIndex][1];
        int delta = vector[vectorIndex][2];

        if (isHorizon) {
            for (int i = 0; i < board.length; i++) {
                for (int k = 0; k < board[i].length - 1; k++) {
                    for (int j = start; delta == 1 ? j <= end : j >= end; j += delta) {
                        Unit currentUnit = board[i][j];
                        Unit neighborUnit = board[i][j + delta];
                        if (neighborUnit == null) continue;

                        if (currentUnit == null) {
                            board[i][j] = neighborUnit;
                            board[i][j + delta] = null;
                        } else if (!neighborUnit.isFusion && !currentUnit.isFusion
                                && currentUnit.value == neighborUnit.value) {
                            currentUnit.value += neighborUnit.value;
                            currentUnit.isFusion = true;
                            board[i][j + delta] = null;
                        } else {
                            continue;
                        }
                        moveCount++;
                    }
                }
            }
        } else {
            for (int i = 0; i < board[0].length; i++) {
                for (int k = 0; k < board.length - 1; k++) {
                    for (int j = start; delta == 1 ? j <= end : j >= end; j += delta) {
                        Unit currentUnit = board[j][i];
                        Unit neighborUnit = board[j + delta][i];
                        if (neighborUnit == null) continue;

                        if (currentUnit == null) {
                            board[j][i] = neighborUnit;
                            board[j + delta][i] = null;
                        } else if (!neighborUnit.isFusion && !currentUnit.isFusion
                                && currentUnit.value == neighborUnit.value) {
                            currentUnit.value += neighborUnit.value;
                            currentUnit.isFusion = true;
                            board[j + delta][i] = null;
                        } else {
                            continue;
                        }
                        moveCount++;
                    }
                }
            }

        }

        if (moveCount == 0) {
            return false;
        } else {
            for (Unit[] rows : board) {
                for (Unit row : rows) {
                    if (row != null) {
                        maximumValue = Math.max(row.value, maximumValue);
                        row.isFusion = false;
                    }
                }
            }
        }

        return true;
    }

    private static Unit[][] deepCopy(Unit[][] original) {
        Unit[][] copyBoard = new Unit[original.length][original[0].length];
        for (int y = 0; y < original.length; y++) {
            for (int x = 0; x < original.length; x++) {
                if (original[y][x] != null) {
                    copyBoard[y][x] = new Unit(original[y][x].value);
                }
            }
        }
        return copyBoard;
    }

    private static class Unit {
        int value;
        boolean isFusion;

        public Unit(int value) {
            this.value = value;
        }
    }

}