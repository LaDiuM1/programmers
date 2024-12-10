package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SolutionByDay27 {
    /**

     [https://www.acmicpc.net/problem/1958]
     백준 1958 - LCS 3

     -- 문제
     이 문제는 세 문자열에 대한 LCS(공통 부분 수열)의 길이를 구하는 문제
     이다.

     -- 문제 분석
     이 문제는 바로 이전 문제의 두 개의 대한 LCS를 구하는 문제에서 3개에
     대한 LCS 길이를 구하는 문제로 확장된 문제이다.

     일단 이전 문제에서 2개의 LCS를 구하는 것에 개념적으로 2차원 DP의 개념
     을 사용하여 풀이하였다. 효율성을 위해 1차원 DP로만 선언하고 이전 값을
     변수에 저장하는 방식으로 사용하였으나 2차원 DP의 개념을 사용했다고 할
     수 있다.

     그렇다면 이 문제는 3개의 LCS를 구하는 문제이므로 이전 알고리즘의 개념
     을 적용하면 3차원 DP로 해결이 가능하다. 해결 논리를 아래와 같이 작성
     해 보았다.

     조건
     1. 문자열 A와 B, C가 있다고 가정할 때 공통 부분 수열이 성립하려면 세
        문자열에서 동일한 문자가 있어야 한다.
     2. 공통 부분 수열의 크기는 오른쪽으로 갈 수록 크기가 커진다.

     dp를 이용한 부분 수열 크기 갱신
     1. 세 문자열을 3차원 dp로 계산하기 위해 dp[a][b][c]의 지점에 각 문자열
        상태를 관리한다.
     2. 세 문자열의 왼쪽부터 모두 일치하는 문자가 발견되는 위치가 곧 공통 부분
        수열의 시작점이다. i,j,k로 순회한다고 했을 때 dp[i][j][k]위치가 공
        통 부분 수열 크기가 1인 지점이다.
     3. 오른쪽으로 이동하며 공통 문자가 발견되면 LCS의 크기는 이전 LCS 크기
        + 1일 것이다. 최대 선택 dp로 표현하면 아래와 같이 표현 가능하다.
        dp[i][j][k] = dp[i-1][j-1][k-1] + 1
     4. LCS 크기를 증가시킬 식은 완성되었다. 이제 문자가 일치하지 않을 때 이
        전까지의 선택 중 최댓값으로 현재 순회 위치를 갱신해야 한다. 3중 for
        문으로 순회한다고 할 때 문자가 일치하지 않으면 아래와 같은 의사 코드
        로 각 위치를 이전까지의 최대 LCS 크기로 갱신 가능할 것이다.
        dp[i][j][k] = max(dp[i-1][j][k], dp[i][j-1][k], dp[i][j][k-1])

     결론
     세 문자열에 대한 LCS는 각 위치에 대한 3차원 DP로 관리 가능하다. 아래는 그
     점화식이며 모든 문자열을 순회한다면 dp 마지막에 저장된 크기가 가장 큰 LCS
     크기가 될 것이다.

     점화식
        현재 위치 i,j,k의 문자가 동일할 때
            dp[i][j][k] = dp[i-1][j-1][k-1] + 1
        현재 위치 i,j,k의 문자가 같지 않을 때
            dp[i][j][k] = max(
                dp[i-1][j][k], dp[i][j-1][k], dp[i][j-1][k]
            )

     상기 점화식을 적용하여 코드를 작성해 보자.

     -- 회고
     이 문제는 이전 문제(LCS)에서 하나의 문자열이 추가되었기 때문에 이미 LCS를
     경험해 본 시점에서 dp의 차원 하나만 늘리면 되기 때문에 큰 어려움은 없는 문제
     였다. 하지만 문자열 길이를 n, 개수를 m 이라 할 때 이 알고리즘의 시간 복잡도는
     O(n^m)이고 메모리 사용량도 기하급수적으로 증가한다. 이를 해결하기 위한 최적화
     방법을 잠깐 생각해 보아도 상당히 복잡한 알고리즘의 구현이 필요할 것으로 보인다.
     LCS를 경험해 봤기 때문에 기회가 된다면 고급 알고리즘도 한번 구상해 보자.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strA = br.readLine();
        String strB = br.readLine();
        String strC = br.readLine();

        int aLength = strA.length();
        int bLength = strB.length();
        int cLength = strC.length();
        byte[][][] dp = new byte[aLength + 1][bLength + 1][cLength + 1];

        for(int i = 0; i < aLength; i++) {
            char a = strA.charAt(i);
            for(int j = 0; j < bLength; j++) {
                char b = strB.charAt(j);
                for(int k = 0; k < cLength; k++) {
                    char c = strC.charAt(k);
                    if(a == b && b == c) {
                        dp[i + 1][j + 1][k + 1] = (byte) (dp[i][j][k] + 1);
                    } else {
                        dp[i + 1][j + 1][k + 1] = (byte) Math.max(
                                Math.max(dp[i][j + 1][k + 1], dp[i + 1][j][k + 1]),
                                dp[i + 1][j + 1][k]
                        );
                    }
                }
            }
        }

        System.out.print(dp[aLength][bLength][cLength]);
    }

}
