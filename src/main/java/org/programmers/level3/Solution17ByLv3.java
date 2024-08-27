package org.programmers.level3;

public class Solution17ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/92344]
     * 2022 KAKAO BLIND RECRUITMENT
     * 파괴되지 않은 건물
     *
     *  이 문제는 주어진 2차원 배열의 동일한 데이터를 효율적으로 업데이트를 해야 하는 문제로
     *  제한사항이 행, 열 크기가 1,000, 업데이트 횟수가 250,000회로 최악의 경우 1,000 x 1,000 x 250,000
     *  = 250,000,000,000번의 순회가 발생한다. 그렇다면 최적화를 위해서는 25만회의 연산에서 효율적으로 업데이트 후
     *  원본 배열과의 비교 1번만 수행하는 것이 효율적일 것이다.
     *  이를 위해서 주어진 행과 열에서는 동일한 데이터가 들어온다는 입력 조건이 있으니 행과 열의 독립적인 구간에 데이터를
     *  각 행과 열의 시작, 종료지점만 연산하는 4회의 연산가지고도 계산이 가능할 것이다. 이를 위해서 배열의 차이만큼만
     *  저장해두는 알고리즘을 이용하여 문제를 해결하였다.
     *
     *  구현
     *  배열의 열과 행의 크기가 +1인 복사본을 만든다.
     *  각 skill의 행과 열 시작점에 계산값을 더해준다.
     *  동일한 행에서 열이 끝나고 난 이후 +1 지점에 계산값을 뺀다.
     *  시작열에서 행이 끝나는 지점 +1에 계산값을 뺀다.
     *  종료열과 종료행의 각 +1 지점에 계산값을 더한다.
     *  이후 skills를 모두 순회하여 복사본 배열을 업데이트 진행,
     *  모두 업데이트 했다면 각 행을 순회하여 현재 열의 값을 이전 열의 값으로 누적한다.
     *  그렇다면 열의 시작지점부터 종료지점까지는 계산값으로 더해질 것이고 종료 + 1 지점에서는
     *  계산값을 뺏으니 원상복구 될 것이다.
     *  이후 각 열을 순회하여 현재 행의 값을 이전 행의 값으로 누적해준다.
     *  이렇게 되면 각 열의 시작지점의 계산값으로 동일한 열의 값이 더해질 것이며 마지막 종료 + 1 지점에서
     *  계산값을 빼주었으니 값이 원상복구 될 것이다.
     *  마지막으로 원본 배열에서 복사 배열의 동일한 위치에서 값을 빼주면 될 것이다.
     *
     *  이렇게 구현하면 각 업데이트는 상수 시간에 계산이 될 것이며 전체 배열의 계산도
     *  O(N*M)의 시간안에 끝날 것이라고 생각한다.
     */

    public int solution(int[][] board, int[][] skills) {
        int[][] degree = new int[board.length + 1][board[0].length + 1];

        for(int[] skill : skills) {
            int amount = skill[0] == 1 ? -skill[5] : skill[5];

            int rowStart = skill[1];
            int rowEnd = skill[3];
            int colStart = skill[2];
            int colEnd = skill[4];

            degree[rowStart][colStart] += amount;
            degree[rowStart][colEnd + 1] -= amount;
            degree[rowEnd + 1][colStart] -= amount;
            degree[rowEnd + 1][colEnd + 1] += amount;
        }

        for(int i = 0; i < degree.length; i++) {
            for(int j = 1; j < degree[0].length; j++) {
                degree[i][j] += degree[i][j - 1];
            }
        }

        for(int i = 0; i < degree[0].length; i++) {
            for(int j = 1; j < degree.length; j++) {
                degree[j][i] += degree[j - 1][i];
            }
        }

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                board[i][j] += degree[i][j];
            }
        }

        int count = 0;
        for(int[] row : board) {
            for(int col : row) {
                if(col > 0) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution17ByLv3 application = new Solution17ByLv3();
        int[][] board = {{5,5,5,5,5},{5,5,5,5,5},{5,5,5,5,5},{5,5,5,5,5}};
        int[][] skill = {{1,0,0,3,4,4},{1,2,0,2,3,2},{2,1,0,3,1,2},{1,0,1,3,3,1}};

        // 0 ~ 3
        // 0 ~ 4
        // 0 1 2 3 4
        // 0 1 2 3


        int result = application.solution(board, skill);
        System.out.println("result = " + result + ", valid : " + (result == 10));
    }

}