package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SolutionByDay05 {

    /**
     [https://www.acmicpc.net/problem/2457]
     백준 - 공주님의 정원

     -- 문제
     개화 시기가 각각 다른 꽃이 주어졌을 때 3월 1일부터 11월 30일까지 모든 날짜가
     꽃이 필 수 있는 최소한의 꽃의 개수를 구하는 문제이다.

     -- 문제 분석
     이 문제는 전형적인 그리디 알고리즘으로 해결하는 문제이다. 정렬 조건만 잘 설정
     하면 아주 쉽게 구현할 수 있는 문제이다. 이런 문제는 알고리즘 학습 초반에 많이
     접해보았기 때문에 이 문제에서는 알고리즘 보다는 구현 관점에서 객체지향적으로
     실제 날짜를 증가시키며 문제를 해결해보았다. 제한 사항 N이 <= 100,000이므로
     시도해 볼 수 있는 방법이다.

     -- 구현
     1. 월과 일의 날짜를 다루는 클래스 선언, Comparable의 compareTo를 구현하여
        날짜를 비교하는 메서드를 오버라이딩하고 날짜를 증가시키는 메서드 구현,
        날짜를 증가시킬 때 월 별로 마지막 날짜를 HashMap에서 찾아 마지막 날짜
        조건에 따라 월과 일 변경
     2. 개화 기간 클래스 선언. 필드로 날짜 클래스를 각각 시작 날짜, 종료 날짜의
        타입으로 지정
     3. 월의 마지막 날을 3차원 배열 형태로 월별로 묶어서 저장하고 순회하여 해시맵
        초기화, Map.of로 바로 불변객체로 만드려 했으나 인자가 10개 까지로 해당
        방법 사용
     4. 입력 값들을 개화 시기 클래스로 바꾸어 리스트에 저장 후 시작 날짜 기준
        오름차순, 종료 날짜 기준 내림차순으로 정렬
     5. 현재 날짜와 종료 날짜를 개화 날짜 클래스로 초기화
     6. 시작 날짜 범위에 해당하는 꽃 중 가장 늦게 지는 꽃으로 꽃을 심음, 시작 날짜
        까지 꽃을 심을 수 없으면 바로 종료
     7. 마지막 날짜까지 반복하는 while문 선언, 현재 날짜가 심어진 꽃의 개화 기간이
        라면 다음 날짜로 이동, 개화 기간이 아니라면 오늘보다 이전에 핀 꽃들 중 가장
        늦게 지는 꽃을 선택하여 심는다, 심을 꽃이 없으면 바로 종료하며 꽃을 심었다면
        사용한 꽃의 수를 증가시키고 다음 날짜로 이동
     8. 마지막 날짜까지 정상적으로 순회하였다면 심은 꽃의 수 출력

     */

    static Map<Integer, Integer> lastDateOfMonthMap = new HashMap<>();

    private static class Flowering {
        FloweringDate startDate;
        FloweringDate endDate;

        public Flowering(int startMonth, int startDay, int endMonth, int endDay) {
            this.startDate = new FloweringDate(startMonth, startDay);
            this.endDate = new FloweringDate(endMonth, endDay);
        }
    }

    private static class FloweringDate implements Comparable<FloweringDate> {
        int month, day;

        public FloweringDate(int month, int day) {
            this.month = month;
            this.day = day;
        }

        public void incrementDay() {
            if (lastDateOfMonthMap.get(month) == day) {
                month = month == 12 ? 1 : month + 1;
                day = 1;
            } else {
                day++;
            }
        }

        @Override
        public int compareTo(FloweringDate o) {
            if (this.month == o.month) {
                return Integer.compare(this.day, o.day);
            }
            return Integer.compare(this.month, o.month);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 각 달의 마지막 날짜 초기화
        int[][][] monthInLastDates = {
                {{28}, {2}},
                {{30}, {4, 6, 9, 11}},
                {{31}, {1, 3, 5, 7, 8, 10, 12}}
        };

        for (int[][] monthOfLastDate : monthInLastDates) {
            int lastDate = monthOfLastDate[0][0];
            for (int j = 0; j < monthOfLastDate[1].length; j++) {
                int month = monthOfLastDate[1][j];
                lastDateOfMonthMap.put(month, lastDate);
            }
        }

        final int N = Integer.parseInt(br.readLine());
        List<Flowering> dateList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int startMonth = Integer.parseInt(st.nextToken());
            int startDate = Integer.parseInt(st.nextToken());
            int endMonth = Integer.parseInt(st.nextToken());
            int endDate = Integer.parseInt(st.nextToken());

            dateList.add(new Flowering(startMonth, startDate, endMonth, endDate));
        }

        // 꽃들을 시작 날짜 순으로 정렬, 시작 날짜가 같으면 종료 날짜가 늦은 순으로
        dateList.sort((o1, o2) -> {
            if (o1.startDate.compareTo(o2.startDate) == 0) {
                return o2.endDate.compareTo(o1.endDate);
            }
            return o1.startDate.compareTo(o2.startDate);
        });

        Queue<Flowering> queue = new LinkedList<>(dateList);

        FloweringDate today = new FloweringDate(3, 1); // 시작 날짜
        FloweringDate lastDate = new FloweringDate(11, 30); // 종료 날짜

        Flowering nowFlower = null; // 현재 심어진 꽃
        int usedFlower = 0; // 사용한 꽃의 수

        // 초기 꽃 선택
        // 시작 날짜 이전에 피는 꽃들 중에서 가장 늦게 지는 꽃 선택
        while (!queue.isEmpty() && queue.peek().startDate.compareTo(today) <= 0) {
            Flowering candidateFlower = queue.poll();
            if (nowFlower == null || candidateFlower.endDate.compareTo(nowFlower.endDate) > 0) {
                nowFlower = candidateFlower;
            }
        }

        // 시작 날짜에 개화할 수 없는 경우 바로 종료
        if (nowFlower == null || nowFlower.endDate.compareTo(today) <= 0) {
            System.out.println(0);
            return;
        }

        usedFlower++;

        // 날짜를 하루씩 증가시키며 꽃 심기
        while (today.compareTo(lastDate) <= 0) {
            if (nowFlower.endDate.compareTo(today) > 0) {
                // 심어진 꽃의 개화 기간
                today.incrementDay();
            } else {
                // 다음에 심을 꽃을 찾기
                Flowering nextFlower = null;
                // 오늘 이전에 피는 꽃들 중에서 가장 늦게 지는 꽃 선택
                while (!queue.isEmpty() && queue.peek().startDate.compareTo(today) <= 0) {
                    Flowering candidateFlower = queue.poll();
                    if (nextFlower == null || candidateFlower.endDate.compareTo(nextFlower.endDate) > 0) {
                        nextFlower = candidateFlower;
                    }
                }

                // 심을 꽃이 없는 경우 종료
                if (nextFlower == null || nextFlower.endDate.compareTo(today) <= 0) {
                    System.out.println(0);
                    return;
                } else {
                    nowFlower = nextFlower;
                    usedFlower++;
                }
            }
        }

        System.out.println(usedFlower);
    }
}
