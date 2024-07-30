package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution16ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/154539]
    뒤에 있는 큰 수 찾기

    문제 설명
    정수로 이루어진 배열 numbers가 있습니다. 배열 의 각 원소들에 대해 자신보다 뒤에 있는 숫자 중에서 자신보다 크면서 가장 가까이 있는 수를 뒷 큰수라고 합니다.
    정수 배열 numbers가 매개변수로 주어질 때, 모든 원소에 대한 뒷 큰수들을 차례로 담은 배열을 return 하도록 solution 함수를 완성해주세요. 단, 뒷 큰수가 존재하지 않는 원소는 -1을 담습니다.

    제한사항
    4 ≤ numbers의 길이 ≤ 1,000,000
    1 ≤ numbers[i] ≤ 1,000,000
    */

    @Test
    public void solution16byLv2Test1() {
        int[] testCase1 = {4, 5, 2, 10, 8, 6, 1, 9, 3, 7};

        Solution16ByLv2 solution16ByLv2 = new Solution16ByLv2();

        int[] result = solution16ByLv2.solution(testCase1);

        int[] when = {5, 10, 10, -1, 9, 9, 9, -1, 7, -1};

        for (int i = 0; i < when.length; i++) {
            assertThat(result[i]).isEqualTo(when[i]);
        }
    }


}