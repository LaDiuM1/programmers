package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SolutionByDay19 {
    /**

     [https://www.acmicpc.net/problem/2437]
     백준 2437 - 저울

     -- 문제
     저울에 사용할 수 있는 추가 주어지며 이 추들을 이용해 잴 수 없는 최소
     무게를 구하는 문제이다.

     -- 문제 분석 및 해결
     이 문제는 복잡한 구상이 필요 없는 문제로 보인다. 문제의 요지는 가장 작
     은 합계부터 찾아가는 것인데 수열을 오름차순으로 정렬하고 배열을 하나씩
     순회하며 값을 누적시키면 가장 작은 조합의 합계들을 순차적으로 구할 수
     있다. 현재까지의 합계보다 다음 배열의 값이 더 크다면 이는 곧 잴 수
     없다는 의미이기 때문에 손쉽게 잴 수 없는 값을 구할 수 있게 된다. 이를
     아래와 같이 코드를 작성하여 문제를 해결하였다.

     1. 숫자 배열을 오름차순으로 정렬
     2. 배열을 처음부터 순회하며 다음 배열 인덱스의 값이 현재의 합계 + 1
        보다 크다면 반복문 순회 종료
     3. 구해진 합계 + 1의 값을 반환하여 해결

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        int sum = 0;
        for(int i = 0; i < n; i++) {
            if(sum < arr[i] - 1) {
                break;
            }
            sum += arr[i];
        }

        System.out.println(sum + 1);
    }

}