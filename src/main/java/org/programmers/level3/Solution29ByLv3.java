package org.programmers.level3;

import java.util.Arrays;

public class Solution29ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/138475]
     연습문제
     억억단을 외우자

     이 문제는 구구단처럼 곱셈으로 이루어진 행렬에서 각 행과 열의 길이가 1억인 경우
     등장하는 숫자의 빈도를 구해야하며 e가 주어지고 1 <= e의 요소로 이루어진 배열
     이 주어질 때 배열의 각 요소부터 e까지의 숫자중 가장 빈도수가 많이 나오고 동일
     한 빈도 수일 시 더 작은 수를 각 배열의 요소마다 구하여 정답을 반환하는 문제이다.

     -- 문제 분석
     1억 * 1억의 곱셈 행렬 표는 단순히 모두 계산하면 1억의 제곱만큼 순회가 발생하기
     때문에 제한사항을 살펴보았으며 e가 최대 500만이므로 결국 숫자 500만까지의 빈
     도수를 구하면 된다. 곱셉 표에서 특정 숫자의 출현 빈도수는 약수의 개수와 일치할
     것이기 때문에 처음 접근은 약수의 개수를 효율적으로 접근하는 방법을 구상해 보았
     다. 약수의 개수는 제곱근까지의 수만 구하면 되기 때문에 효율적이겠으나 이 경우
     에도 최적화를 한다면 O(e log e)의 시간, 그냥 1부터 e까지의 2중 포문을 선언
     하고 최대치가 e를 넘을 때마다 2차 반복문에서 break를 거는 것도 o(e log e)
     의 시간이 걸릴 것이기 때문에 단순한 방법으로 진행하였다.

     -- 구현
     1. 2차원 int 배열에 1부터 e까지의 빈도수를 저장
     2. starts 배열과 동일한 2차원 배열 선언
     3. starts 배열의 요소와 인덱스를 2차원 배열에 복사
     4. 복사된 배열을 요소의 값 기준으로 내림차순으로 정렬(내림차순 배열)
     5. right 변수를 선언 후 e를 right에 저장
     6. 내림차순 배열을 순회하며 빈도수가 저장된 배열에서 right
        부터 각 요소의 숫자까지 내려가며 빈도수가 같거나 클 때마다
        인덱스 번호와 빈도수 갱신하며 결과 배열에 저장 right를 left -1로 순회마다 갱신
     7. 슬라이딩 윈도우 개념을 사용하여 o(e log e)[빈도수] + o(e log e)[정렬] + o(e)[결과 탐색]
        결과적으로 총 o(e log e) 시간 안에 모든 결과를 구하여 문제 해결.

     */

    public int[] solution(int e, int[] starts) {
        int n = starts.length;
        int min = 1;

        int[][] map = new int[e + 1][1];
        // 빈도 수 map에 저장
        for(int i = min; i <= e; i++) {
            for(int j = min; j <= e; j++) {
                int cal = i * j;
                if(cal > e) break;
                map[cal][0]++;
            }
        }
        // 슬라이딩 윈도우에 사용할 내림차순 배열 선언 및 저장, 0:값 0:인덱스 번호
        int[][] descOrderStarts = new int[n][2];
        for(int i = 0; i < n; i++) {
            descOrderStarts[i][0] = starts[i];
            descOrderStarts[i][1] = i;
        }
        // 내림차순 정렬
        Arrays.sort(descOrderStarts, (o1, o2) -> Integer.compare(o2[0],o1[0]));

        // 내림차순 배열을 순회하여 각 순회마다 right부터 left까지 내려가며
        // 최댓값보다 크거나 같은 값을 갱신하고 인덱스 저장 후 각 순회 완료 시 right를 left로
        // 갱신하고 최댓값의 인덱스를 result 배열에 동일한 인덱스 위치에 결과 저장
        int[] result = new int[n];
        int right = e;
        int[] maximum = {0, 0}; // 등장 횟수, 인덱스
        for(int i = 0; i < n; i++) {
            int[] now = descOrderStarts[i];
            int left = now[0];
            int resultIndex = now[1];

            for(int j = right; j >= left; j--) {
                if(map[j][0] >= maximum[0]) {
                    maximum[0] = map[j][0];
                    maximum[1] = j;
                }
            }
            right = left - 1;
            result[resultIndex] = maximum[1];
        }

        return result;
    }


    public static void main(String[] args) {
        Solution29ByLv3 application = new Solution29ByLv3();

        int e = 8;
        int[] starts = {1,3,7};

        System.out.println(Arrays.toString(application.solution(e, starts)));
        // answer : [6,6,8]
    }
}