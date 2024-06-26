package org.programmers.level1;

import org.junit.jupiter.api.Test;

class Solution2Test {

    @Test
    public void solution2Test() {
        Solution2 solution2 = new Solution2();

        int result = solution2.solution(
                new String[]{"muzi", "ryan", "frodo", "neo"},
                new String[]{"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"});
    }
}