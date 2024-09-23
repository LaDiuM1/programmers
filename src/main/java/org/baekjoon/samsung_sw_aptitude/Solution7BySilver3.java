package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution7BySilver3 {

    /**
     [https://www.acmicpc.net/problem/14501]
     삼성 SW 역량 테스트 기출 문제
     퇴사

     이 문제는 배열에 날짜와 금액이 기록되어 있으며, 해당 배열을 선택 시 선택 날짜 + 기록 날짜 -1만큼의
     기간 동안 기록된 금액을 받을 수 있으며, 배열 전체에서 가장 큰 총 금액을 산출하는 문제이다.

     첫 번째 구상은 배열의 뒤로부터 순회하여 배열의 날짜 기간 중에서 가장 큰 값을 선택하여 누적하는
     방식이었다. 하지만 이 접근은 조금만 더 생각해 보더라도 해당 기간 동안 그 값이 제일 크지만, 해당
     기간 중 더 작은 값을 선택하더라도 영향을 미치는 다른 날짜를 선택하면 누적합이 더 큰 경우가 있을 수
     있기 때문에 이 접근은 포기하였다.

     두 번째 구상은 DFS를 이용하여 모든 선택에 대한 경우의 수로 최종적인 누적합이 가장 큰 값을 선택하는
     방법이었다. 이 방법은 가장 확실하며 무조건 답을 산출할 수 있는 방법이었지만, 문득 각 항목을
     선택한다는 접근에서 다른 접근 방법의 영감을 떠올리게 되었다.

     모든 경우의 수를 선택하는 것이 아닌, 만약 각 선택에서 선택한 경우와 선택하지 않은 경우 중 큰 값만
     누적한다면 최종적으로 가장 큰 값을 구할 수 있을 것이다. 이 접근은 얼마 전에 풀어보았던 DP
     알고리즘으로 정확하게 풀 수 있는 알고리즘으로 보여, DP를 이용한 해결을 진행하였다.

     -- 구현
     배열의 맨 뒤로부터 순회하여 상담 기간을 벗어난 상담은 선택에서 넘어가며 (DP의 현재의 값을 바로
     뒤의 DP 배열의 값으로 저장) 상담이 가능한 경우, DP의 현재 날짜 배열에 현재 요소를 선택한 값과
     선택하여 상담할 수 없는 기간을 뺀 바로 이후의 DP의 누적합의 합과 현재 날짜 바로 이후의 누적합을
     비교하여 더 큰 값을 현재 DP에 저장. 최종적으로 순회 완료 이후 DP 첫 번째 배열의 값을 반환.

     -- 최종평
     이 문제는 각 문제마다의 사고 깊이에 따라 더 나은 알고리즘이 있음을 강하게 시사하였고, 단순히 DFS로
     문제를 해결하였다면 알고리즘에 대한 사고방식의 고정이 생겼을 것 같다. 비교적 난이도가 낮은 문제라고
     생각하여 단순하게 접근하는 것보다는 제한 시간 안에서 최선의 알고리즘을 항상 생각하도록 해보자.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] dp = new int[n + 2];
        int[][] counsels = new int[n][2];

        for(int i = 0; i < n; i++) {
            String[] splitCounsel = br.readLine().split(" ");
            counsels[i][0] = Integer.parseInt(splitCounsel[0]);
            counsels[i][1] = Integer.parseInt(splitCounsel[1]);
        }

        for(int i = n - 1; i >= 0; i--) {
            int t = counsels[i][0];
            int p = counsels[i][1];

            if(t + i > n) {
                dp[i] = dp[i + 1];
            } else {
                dp[i] = Math.max(p + dp[i + t], dp[i + 1]);
            }
        }
        System.out.print(dp[0]);
    }

}