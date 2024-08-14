package org.programmers.level3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution4ByLv3Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/42898]
    DP
    등굣길

    계속되는 폭우로 일부 지역이 물에 잠겼습니다.
    물에 잠기지 않은 지역을 통해 학교를 가려고 합니다. 집에서 학교까지 가는 길은 m x n 크기의 격자모양으로 나타낼 수 있습니다.

    가장 왼쪽 위, 즉 집이 있는 곳의 좌표는 (1, 1)로 나타내고 가장 오른쪽 아래, 즉 학교가 있는 곳의 좌표는 (m, n)으로 나타냅니다.

    격자의 크기 m, n과 물이 잠긴 지역의 좌표를 담은 2차원 배열 puddles이 매개변수로 주어집니다.
    오른쪽과 아래쪽으로만 움직여 집에서 학교까지 갈 수 있는 최단경로의 개수를 1,000,000,007로
    나눈 나머지를 return 하도록 solution 함수를 작성해주세요.

    제한사항
    격자의 크기 m, n은 1 이상 100 이하인 자연수입니다.
    m과 n이 모두 1인 경우는 입력으로 주어지지 않습니다.
    물에 잠긴 지역은 0개 이상 10개 이하입니다.
    집과 학교가 물에 잠긴 경우는 입력으로 주어지지 않습니다.
     */

    @Test
    public void solution4byLv3Test1() {
        Solution4ByLv3 solution4ByLv3 = new Solution4ByLv3();

        int testCase1 = 4;
        int testCase2 = 3;
        int[][] testCase3 = {{2,2}};

        int result = solution4ByLv3.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(4);
    }

    @Test
    public void solution4byLv3Test2() {
        Solution4ByLv3 solution4ByLv3 = new Solution4ByLv3();

        int testCase1 = 5;
        int testCase2 = 4;
        int[][] testCase3 = {{2,2}, {4,3}, {3,4}};

        int result = solution4ByLv3.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(3);
    }

    @Test
    public void solution4byLv3Test3() {
        Solution4ByLv3 solution4ByLv3 = new Solution4ByLv3();

        int testCase1 = 5;
        int testCase2 = 4;
        int[][] testCase3 = {{2,2}, {4,3}, {4,4}, {5,3}};

        int result = solution4ByLv3.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(0);
    }

}