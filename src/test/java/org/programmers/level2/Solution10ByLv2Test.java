package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution10ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/181187]
    두 원 사이의 정수 쌍
    문제 설명
    x축과 y축으로 이루어진 2차원 직교 좌표계에 중심이 원점인 서로 다른 크기의 원이 두 개 주어집니다.
    반지름을 나타내는 두 정수 r1, r2가 매개변수로 주어질 때, 두 원 사이의 공간에 x좌표와 y좌표가 모두 정수인
    점의 개수를 return하도록 solution 함수를 완성해주세요.
    ※ 각 원 위의 점도 포함하여 셉니다.
    */

    @Test
    public void solution10byLv2Test1() {
        long testCase1 = 2;
        long testCase2 = 3;

        Solution10ByLv2 solution10ByLv2 = new Solution10ByLv2();

        long result = solution10ByLv2.solution(testCase1, testCase2);

        assertThat(result).isEqualTo(20);
    }

    @Test
    public void solution10byLv2Test2() {
        long testCase1 = 2;
        long testCase2 = 4;

        Solution10ByLv2 solution10ByLv2 = new Solution10ByLv2();

        long result = solution10ByLv2.solution(testCase1, testCase2);

        assertThat(result).isEqualTo(40);
    }

}