package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution20ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/92342]
    2022 KAKAO BLIND RECRUITMENT
        양궁대회

        문제 설명
        카카오배 양궁대회가 열렸습니다.
        라이언은 저번 카카오배 양궁대회 우승자이고 이번 대회에도 결승전까지 올라왔습니다. 결승전 상대는 어피치입니다.
        카카오배 양궁대회 운영위원회는 한 선수의 연속 우승보다는 다양한 선수들이 양궁대회에서 우승하기를 원합니다.
        따라서, 양궁대회 운영위원회는 결승전 규칙을 전 대회 우승자인 라이언에게 불리하게 다음과 같이 정했습니다.

        어피치가 화살 n발을 다 쏜 후에 라이언이 화살 n발을 쏩니다.
        점수를 계산합니다.
        과녁판은 아래 사진처럼 생겼으며 가장 작은 원의 과녁 점수는 10점이고 가장 큰 원의 바깥쪽은 과녁 점수가 0점입니다.
        만약, k(k는 1~10사이의 자연수)점을 어피치가 a발을 맞혔고 라이언이 b발을 맞혔을 경우 더 많은 화살을 k점에 맞힌 선수가 k 점을 가져갑니다.
        단, a = b일 경우는 어피치가 k점을 가져갑니다. k점을 여러 발 맞혀도 k점 보다 많은 점수를 가져가는 게 아니고
        k점만 가져가는 것을 유의하세요. 또한 a = b = 0 인 경우, 즉, 라이언과 어피치 모두 k점에 단 하나의 화살도 맞히지 못한 경우는
        어느 누구도 k점을 가져가지 않습니다.
        예를 들어, 어피치가 10점을 2발 맞혔고 라이언도 10점을 2발 맞혔을 경우 어피치가 10점을 가져갑니다.
        다른 예로, 어피치가 10점을 0발 맞혔고 라이언이 10점을 2발 맞혔을 경우 라이언이 10점을 가져갑니다.
        모든 과녁 점수에 대하여 각 선수의 최종 점수를 계산합니다.
        최종 점수가 더 높은 선수를 우승자로 결정합니다. 단, 최종 점수가 같을 경우 어피치를 우승자로 결정합니다.
    */

    @Test
    public void solution20byLv2Test1() {
        int testCase1 = 5;
        int[] testCase2 = {2,1,1,1,0,0,0,0,0,0,0};

        Solution20ByLv2 solution20ByLv2 = new Solution20ByLv2();

        int[] result = solution20ByLv2.solution(testCase1, testCase2);

        int[] then = {0,2,2,0,1,0,0,0,0,0,0};

        for(int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }

    }

    @Test
    public void solution20byLv2Test2() {
        int testCase1 = 1;
        int[] testCase2 = {1,0,0,0,0,0,0,0,0,0,0};

        Solution20ByLv2 solution20ByLv2 = new Solution20ByLv2();

        int[] result = solution20ByLv2.solution(testCase1, testCase2);

        int[] then = {-1};

        for(int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }

    @Test
    public void solution20byLv2Test3() {
        int testCase1 = 9;
        int[] testCase2 = {0,0,1,2,0,1,1,1,1,1,1};

        Solution20ByLv2 solution20ByLv2 = new Solution20ByLv2();

        int[] result = solution20ByLv2.solution(testCase1, testCase2);

        int[] then = {1,1,2,0,1,2,2,0,0,0,0};

        for(int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }

    @Test
    public void solution20byLv2Test4() {
        int testCase1 = 10;
        int[] testCase2 = {0,0,0,0,0,0,0,0,3,4,3};

        Solution20ByLv2 solution20ByLv2 = new Solution20ByLv2();

        int[] result = solution20ByLv2.solution(testCase1, testCase2);

        int[] then = {1,1,1,1,1,1,1,1,0,0,2};

        for(int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }


}