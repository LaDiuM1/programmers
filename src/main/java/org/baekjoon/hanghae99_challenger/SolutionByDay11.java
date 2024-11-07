package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SolutionByDay11 {
    /**

     [https://www.acmicpc.net/problem/1461]
     백준 - 도서관

     -- 문제
     이 문제는 초기 사용자의 위치와 모든 책의 위치가 0일 때 모든 책의 돌려놓아야
     할 위치와 한 번에 들 수 있는 책의 수가 주어질 때 최소 거리로 책을 돌려놓는
     값을 반환하는 문제이다. 마지막 책을 돌려놓았다면 다시 돌아오지 않아도 된다.

     -- 문제 분석 및 해결
     문제는 단순하다. 책을 모두 돌려놓았다면 돌아오지 않아도 된다는 조건이 있으니
     M의 개수만큼의 책을 가장 마지막에 돌려놓아야 한다. 그리고 나머지는 M개씩 양수
     / 음수별로 묶어 높은 순으로 처리하면 최소화된 거리가 나올 것이다. 이 구상으로
     해결할 수 있는 가장 최적화된 구조를 생각해본다면 아래와 같을 것이다.

     1. 정수 배열을 오름차순으로 정렬
     2. 왼쪽과 오른쪽 포인터 변수에 배열의 왼쪽과 오른쪽 인덱스로 초기화
     3. 내림차순 기준의 우선순위 큐를 선언하고 왼쪽과 오른쪽을 한 번씩 확인하는
        루프를 선언
     4. 루프에서는 왼쪽과 오른쪽 각각 확인 가능한 조건에 따라 가능하다면 해당 포인터
        인덱스의 요소를 절댓값으로 바꾸어 우선순위 큐에 넣고 각각 M 크기만큼 증감
     5. 모든 루프를 완료했다면 우선순위 큐의 첫 번째 요소는 요소만큼의 거리를 증가
        시키고 나머지 요소들은 요소 * 2만큼의 거리를 증가하여 최종적인 거리를 반환
        하여 해결

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int n = Integer.parseInt(st.nextToken());
        final int m = Integer.parseInt(st.nextToken());

        int[] intArr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            intArr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(intArr);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

        boolean isPossibleLeft = true;
        boolean isPossibleRight = true;
        int left = 0;
        int right = n - 1;
        while(isPossibleLeft || isPossibleRight) {
            if(isPossibleLeft) {
                if(left < n && intArr[left] < 0) {
                    pq.add(Math.abs(intArr[left]));
                    left += m;
                } else {
                    isPossibleLeft = false;
                }
            }

            if(isPossibleRight) {
                if(right >= 0 && intArr[right] > 0) {
                    pq.add(Math.abs(intArr[right]));
                    right -= m;
                } else {
                    isPossibleRight = false;
                }
            }
        }

        int count = 0;
        if(!pq.isEmpty()) count += pq.poll();

        while(!pq.isEmpty()) {
            count += pq.poll() * 2;
        }

        System.out.print(count);
    }

}