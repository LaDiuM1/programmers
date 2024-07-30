package org.programmers.level2;

public class Solution14ByLv2 {

    /**
     * 문제를 처음 접했을 때 얼핏 보면 DFS로 모든 경우의 수를 확인하면서
     * 규칙 여부를 체크해야하나 싶었지만 지문을 읽어봤을 때 배열의 크기가 3X3으로 고정이고,
     * 아래의 규칙을 읽어봤을 때 규칙을 위반하는 케이스가 명확하다고 생각하였다.
     *
     * 규칙 1. "O"를 표시할 차례인데 "X"를 표시하거나 반대로 "X"를 표시할 차례인데 "O"를 표시한다.
     * 규칙 2. 선공이나 후공이 승리해서 게임이 종료되었음에도 그 게임을 진행한다.
     *
     * 규칙을 한번 분해해보면.
     * 1. "O"를 표시할 차례인데 "X"를 표시하거나 반대로 "X"를 표시할 차례인데 "O"를 표시한다.
     *      -> X의 돌의 개수는 O보다 많아서는 안 된다.
     *      -> O와 X의 돌의 개수 차이는 1을 초과할 수 없다.
     * 2. 선공이나 후공이 승리해서 게임이 종료되었음에도 그 게임을 진행한다.
     *      -> O(선공)이 승리했을 때 돌의 개수는 O가 X보다 하나 많아야 함.
     *      -> X(후공)이 승리했을 때 돌의 개수는 같아야 함.
     *      -> O와 X의 승리 횟수는 동시에 1이 될 수 없다.
     *
     * 그렇다면 구현해야할 것은 명확하다. 선, 후공의 돌의 개수와 승리 횟수를 각각 세어
     * 위 규칙을 준수하는지만 체크하면 될 것이다.
     *
     * 돌의 개수를 세는 것은 명확하지만 승리 횟수를 세는 것은 효율성과 속도로 나뉘어 진다.
     * 1. 각 돌의 위치 기준으로 BFS로 탐색하며 중복되지 않는 조건 일치 여부를 확인.
     * 2. 승리 조건 배열을 초기화 하고 시작하여 해당 배열 순회
     *
     * 1번의 경우 board의 배열 크기가 증가할 수록 경우의 수가 기하 급수적으로 증가하니
     * 해당 방법을 사용하는 것이 적절할 것이다. 하지만 속도는 상대적으로 매우 느릴 것이다.
     *
     * 2번의 경우 모든 승리 배열을 직접 선언해줘야 하는 단점이 있다.
     * 하지만 board의 크기가 작을 경우 승리할 경우의 수가 한정적으로 오히려 BFS로 구현하는 것보다
     * 더 빠르게 배열을 만들 수 있을 것이며 항상 O(1)의 속도를 보장하기 때문에 아주 빠르게 탐색이 가능하다.
     *
     * 현재 문제의 경우 보드 크기가 3X3 고정이고 승리할 경우의수를 직관적으로 알 수 있기 때문에 2번의 방법으로
     * 선, 후공의 각각 승리 횟수를 카운트하여 문제를 해결하였다.
     */

    int[][][] winCases = {
            { {0, 0}, {0, 1}, {0, 2} },
            { {1, 0}, {1, 1}, {1, 2} },
            { {2, 0}, {2, 1}, {2, 2} },
            { {0, 0}, {1, 0}, {2, 0} },
            { {0, 1}, {1, 1}, {2, 1} },
            { {0, 2}, {1, 2}, {2, 2} },
            { {0, 0}, {1, 1}, {2, 2} },
            { {0, 2}, {1, 1}, {2, 0} }
    };

    String[] board;

    int winCountO = 0;
    int winCountX = 0;
    int numOfO = 0;
    int numOfX = 0;

    public int solution(String[] board) {
        this.board = board;

        for(String cols : board) {
            for(int j = 0; j < cols.length(); j++) {
                if(cols.charAt(j) == 'O') numOfO++;
                if(cols.charAt(j) == 'X') numOfX++;
            }
        }

        // O의 돌 개수가 X보다 작거나, O와 X의 돌 개수 차이가 2이상이면 규칙 위반
        if(numOfO < numOfX || Math.abs(numOfO - numOfX) >= 2) {
            return 0;
        }

        checkWinCount();

        // 선, 후공의 승리 횟수가 둘 다 1이면 규칙 위반
        if(winCountO == 1 && winCountX == 1) {
            return 0;
        }

        // O가 승리한 경우 O의 돌 개수는 X보다 하나 많아야 함.
        if(winCountO == 1 && winCountX == 0) {
            if(numOfO != numOfX + 1) return 0;
        }

        // X가 승리한 경우 O와 X의 돌 개수는 서로 같아야 함.
        if(winCountX == 1 && winCountO == 0) {
            if(numOfO != numOfX) return 0;
        }

        return 1;
    }

    public void checkWinCount() {

        for (int[][] winCase : winCases) {
            int oCount = 0;
            int xCount = 0;

            for(int[] position : winCase) {
                switch(board[position[0]].charAt(position[1])) {
                    case 'O' : { oCount++; break; }
                    case 'X' : { xCount++; }
                }
            }

            if(oCount == 3) winCountO++;
            if(xCount == 3) winCountX++;
        }
    }

}