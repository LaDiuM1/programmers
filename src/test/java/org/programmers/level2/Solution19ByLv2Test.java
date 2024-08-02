package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution19ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/118667#]
    2022 KAKAO TECH INTERNSHIP  두 큐 합 같게 만들기

    문제 설명
    길이가 같은 두 개의 큐가 주어집니다. 하나의 큐를 골라 원소를 추출(pop)하고, 추출된 원소를 다른 큐에 집어넣는(insert)
    작업을 통해 각 큐의 원소 합이 같도록 만들려고 합니다. 이때 필요한 작업의 최소 횟수를 구하고자 합니다.
    한 번의 pop과 한 번의 insert를 합쳐서 작업을 1회 수행한 것으로 간주합니다.


    길이가 같은 두 개의 큐를 나타내는 정수 배열 queue1, queue2가 매개변수로 주어집니다.
    각 큐의 원소 합을 같게 만들기 위해 필요한 작업의 최소 횟수를 return 하도록 solution 함수를 완성해주세요.
    단, 어떤 방법으로도 각 큐의 원소 합을 같게 만들 수 없는 경우, -1을 return 해주세요.
    */

    @Test
    public void solution19byLv2Test1() {
        int[] testCase1 = {3, 2, 7, 2};
        int[] testCase2 = {4, 6, 5, 1};

        Solution19ByLv2 solution19ByLv2 = new Solution19ByLv2();

        int result = solution19ByLv2.solution(testCase1, testCase2);

        assertThat(result).isEqualTo(2);
    }


    @Test
    public void solution19byLv2Test2() {
        int[] testCase1 = {1, 2, 1, 2};
        int[] testCase2 = {1, 10, 1, 2};

        Solution19ByLv2 solution19ByLv2 = new Solution19ByLv2();

        int result = solution19ByLv2.solution(testCase1, testCase2);

        assertThat(result).isEqualTo(7);
    }


}