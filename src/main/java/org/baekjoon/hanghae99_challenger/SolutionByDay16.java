package org.baekjoon.hanghae99_challenger;

import java.util.*;

public class SolutionByDay16 {
    /**

     [https://school.programmers.co.kr/learn/courses/30/lessons/214288]
     2023 현대모비스 알고리즘 경진대회 예선
     상담원 인원

     -- 문제
     이 문제는 상담 종류와 멘토가 있으며 멘토마다 특정 유형의 상담 종류만 상담 가능하다.
     상담 요청은 동일한 유형의 상담인 경우 먼저 상담을 요청한 순서대로 처리하며 각 상담
     은 상담 시간이 정해져 있다. 상담 시간 중 요청된 상담은 상담 종료 시까지 대기해야
     한다. 모든 상담 유형은 1가지 이상 멘토에게 분배되어야 하며 총 대기 시간이 최소가
     되는 멘토의 상담 유형 조합을 찾아 최소 대기 시간을 반환하는 문제이다.

     -- 문제 분석
     문제를 봤을 때 상담 종류별로 멘토들에게 적절하게 분배하는 것이므로 이는 중복 조합을
     찾는 문제로 보인다. 중복 조합을 찾는 것은 제한 사항에 따라 수행 시간이 지수적으로
     증가하기 때문에 제한사항부터 확인해 보았다. 제한 사항은 k가 5까지, n이 20까지이고
     모든 상담 종류는 1가지 이상 선택해야 한다는 조건이 있기 때문에 k = 5, n = n - k
     이므로 H(15, 5)가 최대의 조합의 수라고 할 수 있다 H(15, 5)는 C(n + k−1, k)이기
     때문에 대략 1만 정도의 조합의 수가 나오므로 완전 탐색으로 충분히 풀 수 있는 수행 시간
     으로 보인다. 이제 완전 탐색으로 풀 수 있다는 걸 알았으니 DFS 탐색으로 중복 조합을
     구하고 구해진 조합에서 상담을 처리하여 최소의 상담 시간을 구하는 코드를 작성하면 될
     것으로 보인다.

     -- 구현
     1. 상담 중복 조합은 상담 유형별의 멘토의 수와 일치하므로 상담 유형별로 분배할 수를
        저장할 int 배열을 선언하고 기본값은 1로 초기화.
     2. DFS 메서드를 선언 후 시작 번호, 선택 종류의 수, 선택 항의 개수를 받아 상담 유형
        개수 배열에 증감하여 중복 조합의 수를 탐색
     3. 선택 항의 개수를 탐색마다 -1하여 0이 될 시 대기 시간을 구하는 코드 작성
     4. 대기 시간은 상담 요청 시 현재 대기에서 가장 빠른 상담을 불러오기 위해 우선순위 큐
        의 리스트를 사용 후 상담 개수만큼 리스트 초기화.
     5. 이후 상담 요청을 순회하여 상담 종류마다 조합으로 구한 개수에 맞춰 큐에 우선 추가,
        이후 가장 짧은 대기시간의 상담부터 호출하며 대기시간을 누적한다, 만약 대기시간이
        현재까지의 최소 대기시간보다 같거나 커지면 백트래킹하고 아니라면 상담을 모두 처리한
        후 현재 대기시간을 최소 대기시간으로 갱신하고 완전 탐색으로 구해진 최소 대기시간을
        반환하여 해결한다.

     */

    static int minimumWaiting = Integer.MAX_VALUE;

    public int solution(int k, int n, int[][] reqs) {
        int[] counselCounts = new int[k + 1];
        Arrays.fill(counselCounts, 1);

        dfs(1, k, n - k, counselCounts, reqs);
        return minimumWaiting;
    }

    private void dfs(int start, int k, int n, int[] counselCounts, int[][] reqs) {
        if(n == 0) {
            int curWaiting = 0;
            List<PriorityQueue<Integer>> counselQueues = new ArrayList<>();
            for(int i = 0; i <= k; i++) {
                counselQueues.add(new PriorityQueue<>(Comparator.comparingInt(o -> o)));
            }

            for (int[] req : reqs) {
                int reqTime = req[0];
                int counselLength = req[1];
                int type = req[2];

                if (counselQueues.get(type).size() < counselCounts[type]) {
                    counselQueues.get(type).add(reqTime + counselLength);
                    continue;
                }

                if (counselQueues.get(type).peek() <= reqTime) {
                    counselQueues.get(type).poll();
                    counselQueues.get(type).add(reqTime + counselLength);
                } else {
                    curWaiting += counselQueues.get(type).peek() - reqTime;
                    if (curWaiting >= minimumWaiting) return;
                    counselQueues.get(type).add(counselQueues.get(type).poll() + counselLength);
                }
            }

            minimumWaiting = curWaiting;
            return;
        }

        for(int i = start; i <= k; i++) {
            counselCounts[i]++;
            dfs(i, k, n - 1, counselCounts, reqs);
            counselCounts[i]--;
        }
    }

}