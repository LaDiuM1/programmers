package org.programmers.level3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solution13ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/17678]
     * 2018 KAKAO BLIND RECRUITMENT
     * [1차] 셔틀버스
     *
     *  이 문제는 버스를 타기 위한 가장 마지막 시간을 구하는 것인데
     *  문제 지문이 현실 세계의 요소를 정확하게 표현하고 있어 복잡한
     *  알고리즘을 사용하는 대신 기존에 습득한 자료구조를 이용하여
     *  실제 일어나는 흐름에 따라 코드를 작성해보았다.
     *
     *  구현은 기본적으로 모든 시간을 분 단위로 변환하여 처리하였으며
     *  크루의 도착 시간을 첫 버스의 도착시간인 9시로 초기화 후
     *  버스 운행 횟수마다 버스 stack을 생성 후 그 안에 탑승한 인원을
     *  추가한 stack을 추가하여 실제 버스마다 어떤 인원들이 타고 있는지로
     *  구분하여 구현하였으며 결과 반환은 마지막 버스에 탄 사람들과 비교하여
     *  가장 마지막에 탈 수 있는 시간을 구하여 반환하여 해결하였다.
     *
     *  이 문제는 좀 더 수학적으로 효율적인 알고리즘이 있겠지만 현실 세계의 요소를
     *  반영한 문제에는 현실과 같은 흐름으로 작성하는 것을 나는 좋아하기 때문에
     *  위 처럼 프로그래머스에서 직접 코드를 작성하여 문제를 해결하였다.
     *
     */

    public String solution(int n, int t, int m, String[] timetable) {
        int arriveBusMinute = 9 * 60;
        Stack<Stack<Integer>> busStack = new Stack<>();
        Queue<Integer> timeQueue = new LinkedList<>();

        Arrays.sort(timetable, (o1, o2) -> {
            int o1Time = Integer.parseInt(o1.split(":")[0]) * 60
                    + Integer.parseInt(o1.split(":")[1]);
            int o2Time = Integer.parseInt(o2.split(":")[0]) * 60
                    + Integer.parseInt(o2.split(":")[1]);
            return Integer.compare(o1Time, o2Time);
        });

        for(String time : timetable) {
            int formatMinute = Integer.parseInt(time.split(":")[0]) * 60
                    + Integer.parseInt(time.split(":")[1]);
            timeQueue.add(formatMinute);
        }

        for(int i = 0; i < n; i++) {
            Stack<Integer> bus = new Stack<>();
            for(int j = 0; j < m; j++) {
                if(timeQueue.isEmpty()) break;
                if(timeQueue.peek() <= arriveBusMinute) bus.push(timeQueue.poll());
            }
            busStack.push(bus);
            if(i + 1 != n) arriveBusMinute += t;
        }

        if(!busStack.isEmpty() && busStack.peek().size() == m) {
            Stack<Integer> busInPeopleTimes = busStack.pop();
            int lastWaitingTime = busInPeopleTimes.peek();

            while(!busInPeopleTimes.isEmpty() && lastWaitingTime <= busInPeopleTimes.peek()) {
                arriveBusMinute =  busInPeopleTimes.pop() - 1;
            }
        }

        return String.format("%02d",arriveBusMinute / 60) + ":" +
                String.format("%02d", (arriveBusMinute % 60));
    }

}