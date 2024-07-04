package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution3ByLv2Test {

    /*
        [PCCP 기출문제] 3번 / 아날로그 시계

        시침, 분침, 초침이 있는 아날로그시계가 있습니다.
        시계의 시침은 12시간마다, 분침은 60분마다, 초침
        은 60초마다 시계를 한 바퀴 돕니다. 따라서 시침,
        분침, 초침이 움직이는 속도는 일정하며 각각 다릅니다.
        이 시계에는 초침이 시침/분침과 겹칠 때마다 알람이 울리는 기능이 있습니다.
        당신은 특정 시간 동안 알람이 울린 횟수를 알고 싶습니다.
    */

    @Test
    public void solution3byLv2Test1() {
        Solution3ByLv2 solution3ByLv2 = new Solution3ByLv2();

        int testParam1 = 0;
        int testParam2 = 5;
        int testParam3 = 30;
        int testParam4 = 0;
        int testParam5 = 7;
        int testParam6 = 0;

        int result = solution3ByLv2.solution(testParam1, testParam2, testParam3, testParam4, testParam5, testParam6);

        assertThat(result).isEqualTo(2);
    }

    @Test
    public void solution3byLv2Test2() {
        Solution3ByLv2 solution3ByLv2 = new Solution3ByLv2();

        int testParam1 = 12;
        int testParam2 = 0;
        int testParam3 = 0;
        int testParam4 = 12;
        int testParam5 = 0;
        int testParam6 = 30;

        int result = solution3ByLv2.solution(testParam1, testParam2, testParam3, testParam4, testParam5, testParam6);

        assertThat(result).isEqualTo(1);
    }
    @Test
    public void solution3byLv2Test3() {
        Solution3ByLv2 solution3ByLv2 = new Solution3ByLv2();

        int testParam1 = 0;
        int testParam2 = 0;
        int testParam3 = 0;
        int testParam4 = 23;
        int testParam5 = 59;
        int testParam6 = 59;

        int result = solution3ByLv2.solution(testParam1, testParam2, testParam3, testParam4, testParam5, testParam6);

        assertThat(result).isEqualTo(2852);
    }

    @Test
    public void solution3byLv2Test4() {
        Solution3ByLv2 solution3ByLv2 = new Solution3ByLv2();

        int testParam1 = 1;
        int testParam2 = 59;
        int testParam3 = 59;
        int testParam4 = 2;
        int testParam5 = 0;
        int testParam6 = 1;

        int result = solution3ByLv2.solution(testParam1, testParam2, testParam3, testParam4, testParam5, testParam6);

        assertThat(result).isEqualTo(1);
    }


}