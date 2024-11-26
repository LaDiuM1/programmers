package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SolutionByDay22 {

    /**

     [https://www.acmicpc.net/problem/1446]
     백준 1446 - 지름길

     -- 문제
     고속도로에 지름길이 있다. 도착 지점까지의 가장 짧은 거리를 찾는 문제이다.

     -- 문제 분석
     이 문제는 현재 알고리즘 지식에서는 풀 수 있는 옵션이 존재하는 문제로 보인다.
     도착지부터 시작점까지의 가장 짧은 선택의 경로를 갱신하는 dp, 시작 지점부터
     도착지까지의 모든 경로를 그래프로 그리고 다익스트라로 푸는 방법. 이 문제에서
     는 두 알고리즘 모두 효율성이 우수해 보이며 다익스트라로 푼다면 가장 정석적인
     형태의 코드가 나올 것으로 보여 조금 더 학습에 도움이 될 것 같은 dp 변형을
     이용해 풀이를 진행해 보았다.

     dp 알고리즘에서 이 문제의 적용은 결국 지름길을 이용했을 때와 이용하지 않았을
     경우의 비교이다. 하지만 동일 지점에 지름길이 여러 개 존재할 수 있는 문제이니
     동일 지점에서의 비교도 추가해야 한다. 이를 흐름으로 나타낸다면 아래와 같다.

     1. 시작 지점을 key로 가지는 지름길 리스트 맵을 초기화.
     2. dp 배열을 총거리보다 + 1만큼 크기로 선언 후 역순으로 순회를 진행.
     3. 순회에서 현재 위치에 시작 지점이 map에 있다면 맵에 저장된 지름길 리스트를
        호출하고 각 지름길의 끝나는 위치의 dp와 거리를 더한 값이 가장 작은 값을
        찾은 후 이전의 dp 요소와 비교하여 더 작은 값으로 현재 dp에 저장
     4. dp의 처음 인덱스까지 순회 후 최종적으로 갱신된 최단거리 반환으로 문제 해결

     */


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int length = Integer.parseInt(st.nextToken());

        int[] dp = new int[length + 1];
        // 시작 지점 key, 동일 시작 지점의 지름길 리스트맵[종료 지점, 거리]
        Map<Integer, List<int[]>> anotherRoutesMap = new HashMap<>();

        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());

            if(!anotherRoutesMap.containsKey(start)) {
                List<int[]> anotherRoutes = new ArrayList<>(List.of(new int[]{end, dist}));
                anotherRoutesMap.put(start, anotherRoutes);
            } else {
                anotherRoutesMap.get(start).add(new int[]{end, dist});
            }
        }

        for(int i = length - 1; i >= 0; i--) {
            if(anotherRoutesMap.containsKey(i)) {
                List<int[]> anotherRoutes = anotherRoutesMap.get(i);
                int minDist = Integer.MAX_VALUE;
                for (int[] anotherRoute : anotherRoutes) {
                    int end = anotherRoute[0];
                    int dist = anotherRoute[1];
                    if(end <= length) {
                        minDist = Math.min(dp[end] + dist, minDist);
                    }
                }
                dp[i] = Math.min(minDist, dp[i + 1] + 1);
            } else {
                dp[i] = dp[i + 1] + 1;
            }
        }

        System.out.print(dp[0]);
    }
}
