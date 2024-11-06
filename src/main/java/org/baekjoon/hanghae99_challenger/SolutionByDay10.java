package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SolutionByDay10 {
    /**

     [https://www.acmicpc.net/problem/1253]
     백준 - 좋다

     -- 문제
     이 문제는 중복을 허용하는 수열에서 어떤 항이 다른 두 항의 합과 같은 개수를
     반환하는 문제이다.

     -- 문제 분석 및 해결
     문제를 접하고 처음에 드는 생각은 슬라이딩 윈도우였고, 두 수의 합은 배열을
     오름차순으로 정렬 후 인덱스 0번을 왼쪽, 인덱스 i - 1을 오른쪽으로 준 후
     자신보다 클 때는 오른쪽 인덱스를 감소시키고, 작을 때는 왼쪽 인덱스를 늘리
     면 슬라이딩 윈도우 개념으로 문제를 풀 수 있기 때문에 해당 방식으로 인덱스
     세 번째 요소부터 순회하여 해결을 시도하였다.
     하지만 테스트를 실패하여 코드를 살펴보았으며 논리상 틀릴 수가 없기 때문에
     코드의 문제가 아니라는 결론을 내렸고 문제의 지문을 천천히 살펴보니 수의 개
     수 (1 ≤ N ≤ 2,000)  (|Ai| ≤ 1,000,000,000, Ai는 정수) 이 부분을 자
     세히 읽지 않고 수는 1부터 시작한다고 생각하여 해당 방법으로 풀이를 시도하여
     테스트를 통과하지 못했다는 걸 알게 되었다. 이제 0부터 숫자가 시작하는 걸 알
     았으니 모든 요소를 검사해야 하기 때문에 right를 i - 1이 아닌 n - 1로 설정
     하고 left나 right가 i와 같을 때의 조건을 추가하고 문제 해결을 시도하여 테
     스트를 통과하였다. 테스트 통과 후 이는 슬라이딩 윈도우가 아닌 투 포인터의
     개념으로 알고리즘이 변경되어 해결되었다는 걸 알게 되었다.

     -- 배운점
     최근에 어려운 문제를 자주 접하여 해결하였기 때문에 상대적으로 문제를 보자마자
     해결 구상이 떠오른 이 문제에서 지문을 정확하게 읽지 않고 곧바로 코드 작성에
     들어간 것이 패착이었다, 코드 수정은 미비하였으나 개념상으로 풀이 알고리즘이
     변경되는 대 수술을 한 것이나 다름없기 때문에 오히려 쉬운 문제일수록 지문을
     대략적으로 읽는 것이 리스크가 크다는 걸 알 수 있게 되었다. 다음은 쉬워 보
     이는 문제라도 한번 구상대로 해결 가능한지 지문과 교차 검증하는 시간을 가지
     는 것이 좋아 보인다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int N = Integer.parseInt(br.readLine());
        int[] intArr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            intArr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(intArr);

        int count = 0;
        for(int i = 0; i < N; i++) {
            int leftIndex = 0;
            int rightIndex = N - 1;
            int current = intArr[i];

            while(leftIndex < rightIndex) {
                int sum = intArr[leftIndex] + intArr[rightIndex];

                if(current == sum) {
                    if(leftIndex == i) {
                        leftIndex++;
                    } else if (rightIndex == i) {
                        rightIndex--;
                    } else {
                        count++;
                        break;
                    }
                } else if (sum > current) {
                    rightIndex--;
                } else {
                    leftIndex++;
                }
            }
        }

        System.out.print(count);
    }

}