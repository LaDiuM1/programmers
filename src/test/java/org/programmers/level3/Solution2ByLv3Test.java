package org.programmers.level3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution2ByLv3Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/43105]
    동적계획법(Dynamic Programming)
    정수 삼각형


    문제 설명
    위와 같은 삼각형의 꼭대기에서 바닥까지 이어지는 경로 중, 거쳐간 숫자의 합이 가장 큰 경우를 찾아보려고 합니다.
    아래 칸으로 이동할 때는 대각선 방향으로 한 칸 오른쪽 또는 왼쪽으로만 이동 가능합니다.
    예를 들어 3에서는 그 아래칸의 8 또는 1로만 이동이 가능합니다.

    삼각형의 정보가 담긴 배열 triangle이 매개변수로 주어질 때, 거쳐간 숫자의 최댓값을 return 하도록 solution 함수를 완성하세요.

    제한사항
    삼각형의 높이는 1 이상 500 이하입니다.
    삼각형을 이루고 있는 숫자는 0 이상 9,999 이하의 정수입니다.
     */

    @Test
    public void solution2byLv3Test1() {
        Solution2ByLv3 solution2ByLv3 = new Solution2ByLv3();

        int[][] testCase1 = {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}};

        int result = solution2ByLv3.solution(testCase1);

        assertThat(result).isEqualTo(30);
    }

}