package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SolutionByDay20 {
    /**

     [https://www.acmicpc.net/problem/2169]
     백준 2169 - 로봇 조종하기

     -- 문제
     가중치가 있는 2차원 배열에서 0,0 위치에서 출발하여 아래, 왼쪽, 오른쪽
     으로만 이동 가능할 때 가장 오른쪽 아래(n-1, m-1)까지 이동 시 이동 경
     로의 가중치 합이 가장 큰 값을 찾는 문제이다

     -- 문제 분석
     1차적으로 가장 직관적인 완전 탐색으로 풀 수 있는지 입력 조건을 확인해 보면
     배열의 크기인 n과 m이 최대 1000까지이다. 이 크기에서 경로의 수는 대략
     1998! 정도의 근사치이므로 완전 탐색은 사용할 수 없다.

     그러면 완전 탐색을 사용하지 못하는 경우 이런 문제에서는 가장 효율적인 방법
     이 동적 계획법일 것이다. 하지만 이동 경우의 수가 2가지가 아니므로 단순한
     DP로는 풀 수 없고 약간의 변형을 거쳐야 할 것으로 보인다.

     DP를 변형하기 위해서는 문제를 최대한 단순화를 해야 한다. 일단 로봇이 이동할
     수 있는 경로는 왼쪽, 오른쪽, 아래이다. 그리고 이미 탐색한 경로는 다시 이동
     할 수 없다는 조건이 붙어있다. 그렇다면 로봇이 이동 가능한 조건을 단순화시켜
     본다면 아래와 같을 것이다.

     1. 로봇의 좌우 이동은 단방향이다.
     2. 로봇이 아래로 내려가면 다시 올라갈 수 없고 좌우 이동이 초기화된다.

     이 조건을 dp로 확인한다면 아래와 같을 것이다.

     1. 첫 줄에서는 각 위치를 선택했던 경우의 수는 윗 줄이 없으므로 오른쪽으로 이동
        했을 경우 밖에 없다. 케이스가 1가지이므로 오른쪽으로 이동하며 왼쪽의 값들을
        누적한다.
     2. 두 번째 줄부터는 각 위치를 선택했던 경우의 수는 왼쪽, 위, 오른쪽이다. 오른
        쪽과 왼쪽은 단방향이기 때문에 두 번 확인해야 하고 각 방향의 경로에는 위에서
        내려온 경로도 포함되어 있다. 이를 dp로 확인하려면 아래와 같다.

        2.1 좌우 방향으로 이동하는 각각 dp에 진행 방향으로 이동하며 진행 방향 반대쪽
           값과 위쪽의 값 중 큰 값으로 현재 가중치에 더하여 갱신한다.
        2.2 구해진 두 개의 방향 dp 중 각 위치에서 더 큰 값으로 원본 dp의 값을 갱신
            한다.

     3. 위의 조건으로 모두 확인하며 마지막까지 갱신하면 가장 마지막에 있는 값의 가중치가
        가장 큰 값이 된다.

     조건과 논리과 완성되었으니 코드를 작성하면 된다.

     - 코드 해설
     dp 배열 생성 후 아래 코드에서 문제의 입력 값으로 초기화하고 초기화 시 첫 줄의 값은
     오른쪽으로 누적한다.

     그 다음 줄부터 순회하여 오른쪽과 왼쪽으로 각각 이동하며 각 방향과 위 중 더 큰 수로
     각 방향 dp로 갱신하고 구해진 각 방향 dp중 동일 위치에서 더 큰 값으로 원본 dp를 갱
     신한다.

     모두 순회 후 n - 1, m - 1위치의 dp 값을 반환하여 문제를 해결한다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] dp = new int[n][m];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++) {
                dp[i][j] = Integer.parseInt(st.nextToken());
                // 첫번째 줄은 왼쪽 값을 누적
                if(i == 0 && j > 0) {
                    dp[i][j] += dp[i][j - 1];
                }
            }
        }

        for (int i = 1; i < n; i++) {
            int[] dpLeftTop = new int[m];
            int[] dpRightTop = new int[m];

            // 가장 왼쪽 시작 경로는 바로 위를 거쳐야함
            dpLeftTop[0] = dp[i][0] + dp[i - 1][0];
            for (int j = 1; j < m; j++) {
                int left = dpLeftTop[j - 1];
                int top = dp[i - 1][j];
                dpLeftTop[j] =  dp[i][j] + Math.max(left, top);
            }

            // 가장 오른쪽 시작 경로는 바로 위를 거쳐야함
            dpRightTop[m - 1] = dp[i][m - 1] + dp[i - 1][m - 1];
            for (int j = m - 2; j >= 0; j--) {
                int right = dpRightTop[j + 1];
                int top = dp[i - 1][j];
                dpRightTop[j] = dp[i][j] + Math.max(right, top);
            }

            for (int j = 0; j < m; j++) {
                dp[i][j] = Math.max(dpLeftTop[j], dpRightTop[j]);
            }
        }

        System.out.println(dp[n - 1][m - 1]);
    }

}