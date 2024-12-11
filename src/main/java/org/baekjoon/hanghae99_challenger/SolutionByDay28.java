package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SolutionByDay28 {
    /**

     [https://www.acmicpc.net/problem/11722]
     백준 11722 - 가장 긴 감소하는 부분 수열

     -- 문제
     이 문제는 수열에 대해 가장 긴 감소하는 부분 수열을 구하는 문제이다.

     -- 문제 분석
     얼마 전에 풀었던 줄 세우기 문제가 증가하는 부분 수열을 구하는 문제였다면
     이 문제는 정확히 반대로 감소하는 부분 수열의 가장 긴 길이를 구하는 문제
     이다. 사실 점화식 조건만 반대로 하면 되기 때문에 이 문제를 출제한 의도
     를 크게 모르겠으나 얼마 전에 풀었던 dp 알고리즘에서 점화식 조건만 수정
     하여 해결하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1);

        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int maxLength = 0;
        for(int i = n - 1; i >= 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if(arr[j] > arr[i]) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
            maxLength = Math.max(dp[i], maxLength);
        }

        System.out.print(maxLength);
    }

}
