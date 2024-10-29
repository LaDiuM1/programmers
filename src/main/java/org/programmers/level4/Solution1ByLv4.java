package org.programmers.level4;

import java.util.*;

public class Solution1ByLv4 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/42891]
     2019 KAKAO BLIND RECRUITMENT
     무지의 먹방 라이브

     여러 알고리즘에 익숙해지기 시작한 시점이므로 본격적으로 level4의 문제 풀이를
     시작하였다.

     이 문제는 회전하는 음식판이 있으며 각 음식마다 먹는 시간이 정해져 있고 음식은
     1초씩 먹을 수 있다. 음식을 먹기 시작한 후 k초 뒤에 먹어야 하는 음식을 반환
     하는 문제이며 모든 음식을 먹었다면 -1를 반환하면 된다.

     -- 문제 분석
     일단 효율성 테스트가 있다는 문구가 맨 처음에 보였으므로 제한 사항부터 확인
     해 보았다. 제한 사항을 확인해보면 단순한 알고리즘으로는 시간 초과가 발생할
     가능성이 높으므로 최적화된 방법으로 구상하는 것을 목표로 잡았다. 첫 번째로
     떠오르는 생각은 회전할 때마다 길이가 가변적으로 변하기 때문에 빼야 하는 요
     소를 빠르게 식별할 방법이었다. 이는 비교적 빠르게 떠올릴 수 있었는데 배열
     에서 가장 작은 시간 * 총 요소 크기가 현재 남은 k보다 크다면 뺄 수 없다는
     의미일 것이다. 곧 반대는 뺄 수 있다는 의미이므로 오름차순으로 선언된 우선
     순위 큐를 이용하여 조건을 확인해가며 하나씩 빼며 k를 차감해 나간다면 가변
     크기 계산과 조건 확인을 동시에 할 수 있을 것이다. 큐를 모두 순회했다면
     모든 요소가 k보다 작다는 의미이므로 -1을 반환하면 되며 뺄 수 없는 부분
     까지 순회하고 멈췄다면 남은 요소들을 다시 인덱스순으로 정렬 후 남은 k에서
     남은 길이를 나눈 나머지의 인덱스가 곧 정답이 될 것이다. 이 구상의 시간
     복잡도는 큐 삽입 O(N log N), 큐 탐색 O(N), 정답 인덱스 확인 O(1)로 총
     시간 복잡도는 O(N log N)이다. 구상한 대로 실수 없이 구현한다면 효율성
     테스트는 통과할 것으로 보인다. 처음 접하는 level4 문제이므로 신중하게
     주석을 달아 구현해보자.

     -- 구현
     1. 오름차순 우선순위 큐 선언 후 입력 값인 시간에 인덱스 번호를 병합하여
        우선순위 큐에 추가
     3. 우선순위 큐를 순차적으로 탐색하며 현재 가장 작은 값을 뺄 수 있는지
        확인하기 위해 현재 순회 중인 요소의 값과 큐의 크기를 곱한 값이 k
        보다 큰지 확인, 조건을 만족하지 않을 시 뺄 수 있다는 의미이므로
        탐색 최댓값 갱신 후 k에서 계산 값을 차감 후 다음 탐색으로. 탐색
        시 중복된 값은 계산에 불필요하므로 이전 탐색 값과 현재의 값이
        같다면 탐색 건너뛰기.
     3. 뺄 수 없다면(조건 만족 시) 우선순위 큐를 ArrayList로 변환 후
        인덱스 기준으로 오름차순 정렬, 정렬 후 남은 k에서 리스트의 길이
        를 나눈 나머지를 인덱스로 리스트에서 찾은 후 찾은 요소에 저장된
        인덱스 번호를 반환하여 정답 반환
     4. 만약 큐가 모두 비었다면 -1을 반환.

     */

    public int solution(int[] food_times, long k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
        // 시간순으로 오름차순 선언된 우선순위 큐 초기화
        for(int i = 0; i < food_times.length; i++) {
            pq.add(new int[]{food_times[i], i + 1});
        }
        // 이전 탐색 최댓값, 탐색 시 요소에서 차감할 변수
        int previousMax = 0;
        // 우선순위 큐를 탐색하여 뺄 수 있는지 순차적으로 확인
        while(!pq.isEmpty()) {
            int nowQueueSize = pq.size();
            int[] timeAndIndex = pq.poll();
            // 이미 뺀 시간과 동일한 시간은 뺀 후 다음 탐색으로
            if(timeAndIndex[0] == previousMax) continue;
            // 현재 값에서 이전 탐색 최댓값을 차감
            timeAndIndex[0] -= previousMax;
            // 요소를 빼기 위한 필요 크기 계산
            long requireSize = (long) timeAndIndex[0] * nowQueueSize;
            // 필요 크기가 남은 k보다 큰 경우 다시 queue에 담은 후 break
            if(requireSize > k) {
                pq.add(timeAndIndex);
                break;
            }
            // 탐색 최댓값을 갱신
            previousMax += timeAndIndex[0];
            // k에서 조건 확인 크기를 차감
            k -= requireSize;
        }
        if(!pq.isEmpty()) {
            /*  뺄 수 없는 경우 인덱스 오름차순으로 다시 정렬 후
                k % 현재 길이의 인덱스 위치의 원본 인덱스 번호 반환 */
            List<int[]> list = new ArrayList<>(pq);
            list.sort((o1, o2) -> Long.compare(o1[1], o2[1]));
            return list.get((int) (k % list.size()))[1];
        }

        // 모든 큐를 순회하는 것은 모든 누적 시간이 k보다 작다는 의미이므로 -1 반환
        return -1;
    }

    public static void main(String[] args) {
        Solution1ByLv4 solution = new Solution1ByLv4();
        // {6, 7};
        int[] food_times = {8, 7, 7, 4};
        long k = 14;
        System.out.println(solution.solution(food_times, k));
        // 1
    }
}