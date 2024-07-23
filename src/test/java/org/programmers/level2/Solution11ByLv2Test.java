package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution11ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/178870]
    연속된 부분 수열의 합

    문제 설명
    비내림차순으로 정렬된 수열이 주어질 때, 다음 조건을 만족하는 부분 수열을 찾으려고 합니다.

    기존 수열에서 임의의 두 인덱스의 원소와 그 사이의 원소를 모두 포함하는 부분 수열이어야 합니다.
    부분 수열의 합은 k입니다.
    합이 k인 부분 수열이 여러 개인 경우 길이가 짧은 수열을 찾습니다.
    길이가 짧은 수열이 여러 개인 경우 앞쪽(시작 인덱스가 작은)에 나오는 수열을 찾습니다.
    수열을 나타내는 정수 배열 sequence와 부분 수열의 합을 나타내는 정수 k가 매개변수로 주어질 때,
    위 조건을 만족하는 부분 수열의 시작 인덱스와 마지막 인덱스를 배열에 담아 return 하는 solution 함수를 완성해주세요.
    이때 수열의 인덱스는 0부터 시작합니다.
    */

    @Test
    public void solution11byLv2Test1() {
        int[] testCase1 = {1, 2, 3, 4, 5};
        int testCase2 = 7;

        Solution11ByLv2 solution11ByLv2 = new Solution11ByLv2();

        int[] result = solution11ByLv2.solution(testCase1, testCase2);

        int[] that = {2, 3};

        for(int i = 0; i < that.length; i++) {
            assertThat(result[i]).isEqualTo(that[i]);
        }
    }

    @Test
    public void solution11byLv2Test2() {
        int[] testCase1 = {1, 1, 1, 2, 3, 4, 5};
        int testCase2 = 5;

        Solution11ByLv2 solution11ByLv2 = new Solution11ByLv2();

        int[] result = solution11ByLv2.solution(testCase1, testCase2);

        int[] that = {6, 6};

        for(int i = 0; i < that.length; i++) {
            assertThat(result[i]).isEqualTo(that[i]);
        }
    }

    @Test
    public void solution11byLv2Test3() {
        int[] testCase1 = {2, 2, 2, 2, 2};
        int testCase2 = 6;

        Solution11ByLv2 solution11ByLv2 = new Solution11ByLv2();

        int[] result = solution11ByLv2.solution(testCase1, testCase2);

        int[] that = {0, 2};

        for(int i = 0; i < that.length; i++) {
            assertThat(result[i]).isEqualTo(that[i]);
        }
    }

}