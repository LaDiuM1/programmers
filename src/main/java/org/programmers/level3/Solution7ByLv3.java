package org.programmers.level3;

import java.util.*;
import java.util.stream.Collectors;

public class Solution7ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/42884]
     * 해시
     * 베스트앨범
     *
     * 문제 자체가 음악 스트리밍 사이트의 장르 / 장르 내 음악별 인기순 정렬이라 구현 자체는 간단하였으나
     * 정렬 과정이 많고 그룹별 정렬을 한다는 점에서 사람마다 구현이 천차만별일 수 있겠다는 생각이 들었다.
     * 일단 나는 장르별 순서 유지를 위하여 LinkedHashMap으로 key에 장르로 그룹핑 및 value에 우선순위 큐를 사용하여
     * 음악 재생 내림차순으로 정렬하는 방법을 구상하였다.
     * 요소를 모두 LinkedHashMap에 추가한 후 LinkedHashMap을 장르 내의 노래 재생 횟수의 합으로 내림차순 정렬 후 결과를 반환하였다.
     * 자동 정렬되는 TreeMap도 고려하였으나 현재 문제에서는 마지막에 1회만 정렬되면 되므로 TreeMap은 사용하지 않았다.
     *
     */

    public int[] solution(String[] genres, int[] plays) {
        LinkedHashMap<String, PriorityQueue<int[]>> chartMap = new LinkedHashMap<>();

        for(int i = 0; i < genres.length; i++) {
            PriorityQueue<int[]> queue = chartMap.getOrDefault(genres[i], new PriorityQueue<>(
                    (o1, o2) -> o1[1] == o2[1] ? Integer.compare(o1[0], o2[0]) : Integer.compare(o2[1], o1[1])
            ));

            queue.add(new int[]{i, plays[i]});

            chartMap.put(genres[i], queue);
        }

        LinkedHashMap<String, PriorityQueue<int[]>> sortedChartMap = chartMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().stream().mapToInt(p -> p[1]).sum(),
                        entry1.getValue().stream().mapToInt(p -> p[1]).sum()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));

        List<Integer> resultList = new ArrayList<>();

        for(PriorityQueue<int[]> queue : sortedChartMap.values()) {
            for(int i = 0; i < 2; i++) {
                if(queue.isEmpty()) break;
                resultList.add(queue.poll()[0]);
            }
        }

        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }

}