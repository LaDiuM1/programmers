package org.programmers.level3;

public class Solution28ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/92345]
     2022 KAKAO BLIND RECRUITMENT
     사라지는 발판

     이 문제는 특정 크기의 보드에서 두 캐릭터의 위치가 주어지고 캐릭터가 이동 시
     캐릭터가 있던 위치의 발판이 사라지며 보드 안에 있는 발판이 있는 곳으로만 이동
     이 가능하고 자기 차례에서 이동할 수 없거나 캐릭터가 있는 위치에 발판이 없으면
     패배하는 게임이다. A 캐릭터부터 이동하며 각 캐릭터가 최선의 플레이(무조건 이기
     는 플레이어는 최소의 이동 경로, 무조건 지는 플레이어는 최대의 이동 경로)를 했
     을 때 두 플레이어의 이동 경로의 합을 반환하는 문제이다.

     -- 문제 분석
     이 문제는 풀이 방법부터 난해했다. 반드시 이기는 무적 수를 가진 플레이어가 있는
     경우의 수가 상대적으로 작은 제로섬 게임이고 이는 바둑이나 체스와 같이 한 수마다
     최적의 수를 구해야 하는 문제처럼 보였다. 완전 탐색으로는 결과에 해당하는 조건을
     일치시키는 것이 거의 불가능해 보이기 때문에 각 차례의 플레이어마다 최선의 수를
     찾는 알고리즘을 작성하고 이동 가능한 방향 중 최선의 수에 해당하는 방향으로 이동
     시켜야 한다. 그래서 첫 번째 시도로는 아래와 같은 방법으로 시도하였다.

     -- 첫 번째 시도
     일단 이동 가능한 방향 중 최선의 방향이 무엇인지부터 정의해 보았다. 첫 번째 플레
     이어는 A 플레이어이고 A 플레이어는 현재 위치에서 4방향으로 이동한다. 4방향 중에
     반드시 이길 수 있는 방향이 여러 개일 수도 있고 없을 수도 있다. 그래서 각 이동 할
     수 있는 방향마다 완전 탐색을 진행하여 현재 플레이어가 이겼던 수의 깊이(depth)와
     탐색 분기 수를 4방향 모두 구하여 정렬된 treeMap에 넣고 가장 높은 값과 가장 낮은
     값 중 하나만으로 진행하는 두 탐색 큐를 넣고 탐색을 시작하고 다음 플레이어도 동일
     하게 이전 플레이어의 최대 또는 최소의 반대되는 방향으로 이동하여 결과적으로 각
     플레이어마다 한쪽은 최소한의 횟수로 이기는 수를, 반대쪽은 최대한의 횟수로 버티는
     방향으로만 이동하여 두 플레이어 모두 이기는 경우, 지는 경우를 탐색하여 플레이어의
     이동 합이 가장 작았던 결과를 BFS로 찾아 문제를 해결하기 위해 시도하였다.
     결과를 제출하니 크기가 상대적으로 작은 테스트(1~6)까지는 통과하였으나 그 위는 통
     과 실패를 반복하는 결과를 보여주었다. 원인을 생각해보면 단순 탐색 깊이와 탐색 분
     기 수 가지고는 최선의 수를 구할 수 없다는 걸 깨달았다. 각 탐색마다 모든 방향을
     탐색하는 시도에서도 재귀적으로 최선의 수를 찾아야 한다는 것을 깨닫게되었고 무려
     4시간이 넘는 시간 동안 최선의 수를 찾기 위한 알고리즘을 구상하고 시도하였지만
     시간 초과가 발생하여 실패하였고 결국 최적화된 수를 찾는 새로운 알고리즘을 내가
     창조하는 것보다 기존의 정립된 알고리즘을 사용할 해야 할 때라고 인정할 수밖에
     없었다. 이는 지금까지 코딩테스트 중에 처음으로 문제를 풀다가 다른 알고리즘
     도움을 받는 문제였다.

     -- 미니맥스 알고리즘 구현
     검색해 보니 내가 원했던 방법이 정확히 미니맥스 알고리즘인 것으로 확인했다. 각 탐색
     마다 최소 승리 횟수, 최대 승리 횟수를 재귀적으로 탐색하고 탐색 경로를 반대로 거슬러
     올라가 상위 트리에서 하위 트리의 결과 중 최선의 수만을 선택하여 최종적으로 최선의
     결과를 반환하는 알고리즘이다. 혼자 구상했던 방법에서는 재귀 끝으로 이동 후 결과를
     진행 경로에 모두 누적해나가 비효율적인 탐색으로 시간 초과가 발생하였는데 미니맥스
     알고리즘은 마치 DP처럼 각 선택 중 최선의 수만을 선택하는 알고리즘으로 상대적으로
     효율적으로 느껴졌다. 물론 재귀적 탐색이므로 깊이가 깊어지면 이 방법도 기하급수적
     으로 시간 복잡도가 증가할 것이지만 내가 구상했던 방법보다는 확실히 효율적인 방법
     이다. 미니맥스 알고리즘의 개념을 알았으니 구상은 일사천리다. 코드를 모두 초기화
     하고 물 흐르듯 코드를 작성하여 문제를 해결하였다.

     */

    public int solution(int[][] board, int[] aloc, int[] bloc) {
        int[][] vectors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우 탐색
        return playGameOfMinimax(aloc[0], aloc[1], bloc[0], bloc[1], board, vectors)[1];
    }

    private int[] playGameOfMinimax(int curX, int curY, int oppX, int oppY, int[][] board, int[][] vectors) {
        int n = board.length;
        int m = board[0].length;

        if(board[curX][curY] == 0) {
            return new int[]{0, 0};
        }

        boolean isWinner = false;
        int minWin = Integer.MAX_VALUE;
        int maxLose = 0;

        for (int[] vector : vectors) {
            int nextX = curX + vector[0];
            int nextY = curY + vector[1];

            if(nextX >= 0 && nextX < n && nextY >= 0 && nextY < m && board[nextX][nextY] == 1) {
                board[curX][curY] = 0;
                int[] res = playGameOfMinimax(oppX, oppY, nextX, nextY, board, vectors);
                board[curX][curY] = 1;

                if(res[0] == 0) {
                    isWinner = true;
                    minWin = Math.min(res[1] + 1, minWin);
                } else {
                    maxLose = Math.max(res[1] + 1, maxLose);
                }
            }
        }

        if(isWinner) {
            return new int[]{1, minWin};
        } else {
            return new int[]{0, maxLose};
        }
    }


    public static void main(String[] args) {
        Solution28ByLv3 application = new Solution28ByLv3();

        int[][] board1 = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        int[] aloc1 = {1, 0};
        int[] bloc1 = {1, 2};
        System.out.println(application.solution(board1, aloc1, bloc1));
        // 5

        int[][] board2 = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        int[] aloc2 = {1, 0};
        int[] bloc2 = {1, 2};
        System.out.println(application.solution(board2, aloc2, bloc2));
        // 4

        int[][] board3 = {{1, 1, 1, 1, 1}};
        int[] aloc3 = {0, 0};
        int[] bloc3 = {0, 4};
        System.out.println(application.solution(board3, aloc3, bloc3));
        //4

        int[][] board4 = {{1}};
        int[] aloc4 = {0, 0};
        int[] bloc4 = {0, 0};
        System.out.println(application.solution(board4, aloc4, bloc4));
        // 0

        // answer : 5
    }
}