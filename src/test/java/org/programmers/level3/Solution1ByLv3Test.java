package org.programmers.level3;

import org.junit.jupiter.api.Test;
import org.programmers.level2.Solution9ByLv2;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution1ByLv3Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/42628]
    이중 우선순위 큐

    문제 설명
    이중 우선순위 큐는 다음 연산을 할 수 있는 자료구조를 말합니다.

    명령어	수신 탑(높이)
    I 숫자	큐에 주어진 숫자를 삽입합니다.
    D 1	큐에서 최댓값을 삭제합니다.
    D -1	큐에서 최솟값을 삭제합니다.
    이중 우선순위 큐가 할 연산 operations가 매개변수로 주어질 때, 모든 연산을 처리한 후 큐가 비어있으면 [0,0] 비어있지 않으면 [최댓값, 최솟값]을 return 하도록 solution 함수를 구현해주세요.

    제한사항
    operations는 길이가 1 이상 1,000,000 이하인 문자열 배열입니다.
    operations의 원소는 큐가 수행할 연산을 나타냅니다.
    원소는 “명령어 데이터” 형식으로 주어집니다.- 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제합니다.
    빈 큐에 데이터를 삭제하라는 연산이 주어질 경우, 해당 연산은 무시합니다.
        */

    @Test
    public void solution1byLv3Test1() {
        Solution1ByLv3 solution1ByLv3 = new Solution1ByLv3();

        String[] testCase1 = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};

        int[] result = solution1ByLv3.solution(testCase1);

        int[] then = {0,0};

        for (int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }

    @Test
    public void solution1byLv3Test2() {
        Solution1ByLv3 solution1ByLv3 = new Solution1ByLv3();

        String[] testCase1 = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};

        int[] result = solution1ByLv3.solution(testCase1);

        int[] then = {333, -45};

        for (int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }


}