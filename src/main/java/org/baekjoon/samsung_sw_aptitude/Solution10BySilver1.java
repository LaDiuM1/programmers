package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution10BySilver1 {
    /**

     [https://www.acmicpc.net/problem/14888]
     삼성 SW 역량 테스트 기출 문제
     연산자 끼워넣기

     기초적인 순열 완전탐색 문제로 dfs로 간단하게 해결하였다.

     */

    static int maximumValue = Integer.MIN_VALUE;
    static int minimumValue = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        br.readLine();
        int[] intArr = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] operator = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        // 덧셈, 뺄셈, 곱셈, 나눗셈

        dfs(intArr[0], 1, intArr, operator);
        System.out.println(maximumValue);
        System.out.print(minimumValue);

    }

    private static void dfs(int nowSum, int index, int[] intArr, int[] operator) {
        if(index >= intArr.length || Arrays.stream(operator).allMatch(i -> i == 0)) {
            maximumValue = Math.max(nowSum, maximumValue);
            minimumValue = Math.min(nowSum, minimumValue);
            return;
        }

        for(int i = 0; i < operator.length; i++) {
            if(operator[i] == 0) continue;
            operator[i]--;
            switch (i) {
                case 0 : {
                    dfs(nowSum + intArr[index], index + 1, intArr, operator);
                    break;
                }
                case 1 : {
                    dfs(nowSum - intArr[index], index + 1, intArr, operator);
                    break;
                }
                case 2 : {
                    dfs(nowSum * intArr[index], index + 1, intArr, operator);
                    break;
                }
                case 3 : {
                    dfs(nowSum / intArr[index], index + 1, intArr, operator);
                }
            }
            operator[i]++;
        }
    }

}