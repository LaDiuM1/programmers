package org.programmers.level1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    public void solution2Test() {
        Solution2 solution2 = new Solution2();

        String[] testCase1 = {"muzi", "ryan", "frodo", "neo"};
        String[] testCase2 = {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"};

        int result = solution2.solution(testCase1, testCase2);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void solution3Test() {
        Solution3 solution3 = new Solution3();

        int[][] testCase = { {4, 5}, {4, 8}, {10, 14}, {11, 13}, {5, 12}, {3, 7}, {1, 4} };

        int result = solution3.solution(testCase);

        assertThat(result).isEqualTo(3);
    }
}