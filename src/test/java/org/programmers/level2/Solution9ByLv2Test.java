package org.programmers.level2;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution9ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/142085]
    디펜스 게임

        준호는 요즘 디펜스 게임에 푹 빠져 있습니다.
        디펜스 게임은 준호가 보유한 병사 n명으로 연속되는 적의 공격을 순서대로 막는 게임입니다.
        디펜스 게임은 다음과 같은 규칙으로 진행됩니다.

        준호는 처음에 병사 n명을 가지고 있습니다.
        매 라운드마다 enemy[i]마리의 적이 등장합니다.
        남은 병사 중 enemy[i]명 만큼 소모하여 enemy[i]마리의 적을 막을 수 있습니다.
        예를 들어 남은 병사가 7명이고, 적의 수가 2마리인 경우, 현재 라운드를 막으면 7 - 2 = 5명의 병사가 남습니다.
        남은 병사의 수보다 현재 라운드의 적의 수가 더 많으면 게임이 종료됩니다.
        게임에는 무적권이라는 스킬이 있으며, 무적권을 사용하면 병사의 소모없이 한 라운드의 공격을 막을 수 있습니다.
        무적권은 최대 k번 사용할 수 있습니다.
        준호는 무적권을 적절한 시기에 사용하여 최대한 많은 라운드를 진행하고 싶습니다.

        준호가 처음 가지고 있는 병사의 수 n, 사용 가능한 무적권의 횟수 k, 매 라운드마다 공격해오는 적의 수가 순서대로 담긴 정수 배열
        enemy가  매개변수로 주어집니다. 준호가 몇 라운드까지 막을 수 있는지 return 하도록 solution 함수를 완성해주세요.
        */

    @Test
    public void solution9byLv2Test1() {
        Solution9ByLv2 solution9ByLv2 = new Solution9ByLv2();

        int testCase1 = 7;
        int testCase2 = 3;
        int[] testCase3 = {	4, 2, 4, 5, 3, 3, 1 };

        int result = solution9ByLv2.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(5);

    }

    @Test
    public void solution9byLv2Test2() {
        Solution9ByLv2 solution9ByLv2 = new Solution9ByLv2();

        int testCase1 = 2;
        int testCase2 = 4;
        int[] testCase3 = {	3, 3, 3, 3 };

        int result = solution9ByLv2.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(4);
    }

    @Test
    public void solution9byLv2Test3() {
        Solution9ByLv2 solution9ByLv2 = new Solution9ByLv2();

        int testCase1 = 10;
        int testCase2 = 2;
        int[] testCase3 = { 5, 7, 6, 8 };

        int result = solution9ByLv2.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(3);
    }

    @Test
    public void solution9byLv2Test4() {
        Solution9ByLv2 solution9ByLv2 = new Solution9ByLv2();

        int testCase1 = 10;
        int testCase2 = 0;
        int[] testCase3 = { 1, 2, 3, 4, 5 };

        int result = solution9ByLv2.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(4);
    }

    @Test
    public void solution9byLv2Test5() {
        Solution9ByLv2 solution9ByLv2 = new Solution9ByLv2();

        int testCase1 = 10;
        int testCase2 = 1;
        int[] testCase3 = { 11, 1, 1, 1 };

        int result = solution9ByLv2.solution(testCase1, testCase2, testCase3);

        assertThat(result).isEqualTo(4);
    }

    @Test
    public void solution9byLv2ComplexTest() {
        Solution9ByLv2 solution9ByLv2 = new Solution9ByLv2();

        int testCase1 = 1000000;
        int testCase2 = 10;
        int[] testCase3 = {
                100000, 200000, 150000, 300000, 250000, 100000, 200000, 150000,
                300000, 250000, 500000, 600000, 700000, 800000, 900000, 1000000,
                950000, 850000, 750000, 650000, 550000, 450000, 350000, 250000,
                150000, 50000, 100000, 150000, 200000, 250000, 300000, 350000,
                400000, 450000, 500000, 550000, 600000, 650000, 700000, 750000,
                800000, 850000, 900000, 950000, 1000000, 950000, 900000, 850000,
                800000, 750000, 700000, 650000, 600000, 550000, 500000, 450000,
                400000, 350000, 300000, 250000, 200000, 150000, 100000, 50000
        };

        int result = solution9ByLv2.solution(testCase1, testCase2, testCase3);

        int expectedValue = 32;

        assertThat(result).isEqualTo(expectedValue);
    }




}