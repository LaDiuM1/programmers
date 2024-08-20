package org.programmers.level3;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution9ByLv3Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/67258]
    2020 카카오 인턴십
    보석 쇼핑

    개발자 출신으로 세계 최고의 갑부가 된 어피치는 스트레스를 받을 때면
    이를 풀기 위해 오프라인 매장에 쇼핑을 하러 가곤 합니다.
    어피치는 쇼핑을 할 때면 매장 진열대의 특정 범위의 물건들을
    모두 싹쓸이 구매하는 습관이 있습니다.
    어느 날 스트레스를 풀기 위해 보석 매장에 쇼핑을 하러 간
    어피치는 이전처럼 진열대의 특정 범위의 보석을 모두 구매하되
    특별히 아래 목적을 달성하고 싶었습니다.
    진열된 모든 종류의 보석을 적어도 1개 이상 포함하는 가장 짧은 구간을 찾아서 구매

    [제한사항]
    gems 배열의 크기는 1 이상 100,000 이하입니다.
    gems 배열의 각 원소는 진열대에 나열된 보석을 나타냅니다.
    gems 배열에는 1번 진열대부터 진열대 번호 순서대로 보석이름이 차례대로 저장되어 있습니다.
    gems 배열의 각 원소는 길이가 1 이상 10 이하인 알파벳 대문자로만 구성된 문자열입니다.
     */

    @Test
    public void solution9byLv3Test1() {
        Solution9ByLv3 solution9ByLv3 = new Solution9ByLv3();

        String[] testCase1 = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"};

        int[] result = solution9ByLv3.solution(testCase1);

        int[] then = {3, 7};

        for(int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }

    @Test
    public void solution9byLv3Test2() {
        Solution9ByLv3 solution9ByLv3 = new Solution9ByLv3();

        String[] testCase1 = {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA", "RUBY", "DIA", "EMERALD", "SAPPHIRE"};

        int[] result = solution9ByLv3.solution(testCase1);

        int[] then = {6, 9};
        System.out.println("Arrays.toString(result) = " + Arrays.toString(result));

        for(int i = 0; i < then.length; i++) {
            assertThat(result[i]).isEqualTo(then[i]);
        }
    }



}