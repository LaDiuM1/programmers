package org.programmers.level2;

import java.util.PriorityQueue;

public class Solution15ByLv2 {

    /**
     * 이번 문제는 문제부터가 현실세계의 상황을 설명하고 있기 때문에
     * 구상이 매우 간단하여 프로그래머스 ide를 이용해 직접 구현해보았다.
     * 요지는 동시간에 예약 불가한 최대 객실 수를 반환하면 되므로
     * 현실 세계의 요소를 대입하여 구상을 진행하였다.
     * 시간시간과 종료시간을 정렬된 타임라인으로 만든 후
     * 객실 이용 시작 시 현재 이용 객실 수 + 1, 객실 이용 종료(청소 완료)시 - 1을 하여
     * 각 순회마다의 계산된 이용 객실 수를 최대 이용 객실 수와 비교하여 더 큰수를 저장,
     * 최대 이용 객실수를 반환하도록 구현하면 될 것이다.
     * 여기서도 PriorityQueue를 이용하였으며 PriorityQueue는 아주 범용적인 자료구조라 만족도가 높은 상태다.
     * 추후에는 PriorityQueue의 최대힙 구조를 직접 구현하도록 해보자.
     */

    public int solution(String[][] book_time) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(
                (o1, o2) -> o1[0] == o2[0] ? Integer.compare(o2[1], o1[1]) : Integer.compare(o1[0], o2[0])
        );

        for (String[] reservTimeRange : book_time) {
            int startMinutes = Integer.parseInt(reservTimeRange[0].split(":")[0]) * 60
                    + Integer.parseInt(reservTimeRange[0].split(":")[1]);

            int endMinutes = Integer.parseInt(reservTimeRange[1].split(":")[0]) * 60
                    + Integer.parseInt(reservTimeRange[1].split(":")[1]);

            queue.add(new int[]{startMinutes, 1});
            queue.add(new int[]{endMinutes + 10, -1});
        }

        int maxCount = 0;
        int count = 0;
        while (!queue.isEmpty()) {
            count += queue.poll()[1];
            maxCount = Math.max(count, maxCount);
        }

        return maxCount;
    }

}