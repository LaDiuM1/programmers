package org.baekjoon.silver.fibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution1BySilver5 {

    /**
     [https://www.acmicpc.net/problem/10826]
     피보나치 수 4

     이 문제는 피보나치 수를 구하는 문제이며 상대적으로 큰 값
     n <= 10000까지를 구하는 문제이다.

     -- 구상
     피보나치는 dp를 이용하면 O(N) 시간에 풀 수 있는 문제로
     보인다. BigInteger 클래스와 dp를 이용하면 쉽게 해결
     가능한 문제로 보이나 이번에는 String 클래스의 특징과
     char 배열을 이용하여 BigInteger 클래스를 사용하지
     않고 비교적 효율적으로 풀어보았다.

     -- 구현
     2차원 char 배열을 선언 후 n-1 + n-2 위치 계산 시 n-1 + 1
     크기의 배열을 선언 후 각 자리수를 더하여 배열에 저장한 뒤
     첫번째 인덱스가 0일 경우 -1 크기의 배열을 생성하여 두번째
     인덱스부터 배열 복사하여 dp에 저장, 최종적으로 dp[n] 위치
     의 char 배열을 출력하여 문제 해결


     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int n = Integer.parseInt(br.readLine());

        char[][] dp = new char[10001][];
        dp[0] = new char[]{'0'};
        dp[1] = new char[]{'1'};

        for(int i = 2; i < n + 1; i++) {
            char[] operate = new char[dp[i - 1].length + 1];
            int dp1Index = dp[i - 1].length - 1;
            int dp2Index = dp[i - 2].length - 1;

            for(int j = operate.length - 1; j > 0; j--) {
                int operated = operate[j] == 0 ? operate[j] : operate[j] - '0';

                if(dp1Index >= 0) {
                    operated += dp[i - 1][dp1Index--] - '0';
                }

                if(dp2Index >= 0) {
                    operated += dp[i - 2][dp2Index--] - '0';
                }

                operate[j] = (char) (operated % 10 + '0');
                operate[j - 1] = (char) (operated / 10 + '0');
            }

            if(operate[0] == '0') {
                operate = Arrays.copyOfRange(operate, 1, operate.length);
            }

            dp[i] = operate;
        }

        System.out.print(dp[n]);
    }
}