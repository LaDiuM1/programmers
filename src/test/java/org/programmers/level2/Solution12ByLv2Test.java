package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution12ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/176962]
    과제 진행하기

    과제를 받은 루는 다음과 같은 순서대로 과제를 하려고 계획을 세웠습니다.

    과제는 시작하기로 한 시각이 되면 시작합니다.
    새로운 과제를 시작할 시각이 되었을 때, 기존에 진행 중이던 과제가 있다면 진행 중이던 과제를 멈추고 새로운 과제를 시작합니다.
    진행중이던 과제를 끝냈을 때, 잠시 멈춘 과제가 있다면, 멈춰둔 과제를 이어서 진행합니다.
    만약, 과제를 끝낸 시각에 새로 시작해야 되는 과제와 잠시 멈춰둔 과제가 모두 있다면, 새로 시작해야 하는 과제부터 진행합니다.
    멈춰둔 과제가 여러 개일 경우, 가장 최근에 멈춘 과제부터 시작합니다.
    과제 계획을 담은 이차원 문자열 배열 plans가 매개변수로 주어질 때, 과제를 끝낸 순서대로 이름을 배열에 담아 return 하는 solution 함수를 완성해주세요.
    */

    @Test
    public void solution12byLv2Test1() {
        String[][] testCase1 = {{"korean", "11:40", "30"}, {"english", "12:10", "20"}, {"math", "12:30", "40"}};

        Solution12ByLv2 solution12ByLv2 = new Solution12ByLv2();

        String[] result = solution12ByLv2.solution(testCase1);

        String[] that = {"korean", "english", "math"};

        for(int i = 0; i < that.length; i++) {
            assertThat(result[i]).isEqualTo(that[i]);
        }
    }

    @Test
    public void solution12byLv2Test2() {
        String[][] testCase1 = {{"science", "12:40", "50"}, {"music", "12:20", "40"}, {"history", "14:00", "30"}, {"computer", "12:30", "100"}};

        Solution12ByLv2 solution12ByLv2 = new Solution12ByLv2();

        String[] result = solution12ByLv2.solution(testCase1);

        String[] that = {"science", "history", "computer", "music"};

        for(int i = 0; i < that.length; i++) {
            assertThat(result[i]).isEqualTo(that[i]);
        }
    }


}