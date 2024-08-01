package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution18ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/150368]
    2023 KAKAO BLIND RECRUITMENT 이모티콘 할인행사

    문제 설명
    카카오톡에서는 이모티콘을 무제한으로 사용할 수 있는 이모티콘 플러스 서비스 가입자 수를 늘리려고 합니다.
    이를 위해 카카오톡에서는 이모티콘 할인 행사를 하는데, 목표는 다음과 같습니다.

    이모티콘 플러스 서비스 가입자를 최대한 늘리는 것.
    이모티콘 판매액을 최대한 늘리는 것.
    1번 목표가 우선이며, 2번 목표가 그 다음입니다.

    이모티콘 할인 행사는 다음과 같은 방식으로 진행됩니다.

    n명의 카카오톡 사용자들에게 이모티콘 m개를 할인하여 판매합니다.
    이모티콘마다 할인율은 다를 수 있으며, 할인율은 10%, 20%, 30%, 40% 중 하나로 설정됩니다.
    카카오톡 사용자들은 다음과 같은 기준을 따라 이모티콘을 사거나, 이모티콘 플러스 서비스에 가입합니다.

    각 사용자들은 자신의 기준에 따라 일정 비율 이상 할인하는 이모티콘을 모두 구매합니다.
    각 사용자들은 자신의 기준에 따라 이모티콘 구매 비용의 합이 일정 가격 이상이 된다면, 이모티콘 구매를 모두 취소하고 이모티콘 플러스 서비스에 가입합니다.

    */

    @Test
    public void solution18byLv2Test1() {
        int[][] testCase1 = {{40, 10000}, {25, 10000}};
        int[] testCase2 = {7000, 9000};

        Solution18ByLv2 solution18ByLv2 = new Solution18ByLv2();

        int[] result = solution18ByLv2.solution(testCase1, testCase2);

        int[] when = {1, 5400};

        for (int i = 0; i < when.length; i++) {
            assertThat(result[i]).isEqualTo(when[i]);
        }
    }
    
    @Test
    public void solution18byLv2Test3() {
        int[][] testCase1 = {{40, 2900}, {23, 10000}, {11, 5200}, {5, 5900}, {40, 3100}, {27, 9200}, {32, 6900}};
        int[] testCase2 = {1300, 1500, 1600, 4900};

        Solution18ByLv2 solution18ByLv2 = new Solution18ByLv2();

        int[] result = solution18ByLv2.solution(testCase1, testCase2);

        int[] when = {4, 13860};

        for (int i = 0; i < when.length; i++) {
            assertThat(result[i]).isEqualTo(when[i]);
        }
    }

}