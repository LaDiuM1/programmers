package org.programmers.level3;

import org.programmers.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Solution18ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/152995]
     * 인사고과
     *
     * 이 문제는 특정 중복 순위 랭크를 뽑아내는 문제인데 특이하게도 특정한 조건 하에서는
     * 랭크 자체에 포함되지 않아야 하는 문제이다. 어떤 직원의 두 점수 모두 다른 직원의
     * 두 점수보다 낮은 경우에 랭크에 제외시켜야 하는 문제로 제한사항은 25회로 N제곱으로
     * 순회하여 검사하면 상당히 오래 걸리는 문제로 생각되므로 정렬과 필터를 이용하여 문제를
     * 해결하였다.
     *
     * 첫번째로는 태도 점수 내림차순, 동 순위에서는 평가 점수 오름차순으로 정렬 후
     * 필터를 이용하여 평가 점수 MAX값을 갱신하면서 현재 MAX 값보다 같거나 큰 요소만
     * 남기도록 필터링하였고 이후 합계 점수 순으로 내림차순으로 재정렬하여 중복 순위
     * 랭크 로직을 작성하여 문제를 해결하였다.
     *
     * 현재 문제에서는 stream을 이용하여 함수형 프로그래밍 개념을 조금 섞어서 구현해보았다.
     */

    private static class Employee {
        int attitudeScore, colleagueScore;

        public Employee(int attitudeScore, int colleagueScore) {
            this.attitudeScore = attitudeScore;
            this.colleagueScore = colleagueScore;
        }
    }

    public int solution(int[][] scores) {
        List<Employee> employeeScores = new ArrayList<>();
        int[] wanho = scores[0];
        int result = 1;

        for(int[] score : scores) {
            if(score[0] > wanho[0] && score[1] > wanho[1]) return -1;
            employeeScores.add(new Employee(score[0], score[1]));
        }

        employeeScores.sort(
                (o1, o2) -> o1.attitudeScore == o2.attitudeScore
                        ? Integer.compare(o1.colleagueScore, o2.colleagueScore)
                        : Integer.compare(o2.attitudeScore, o1.attitudeScore)
        );

        AtomicInteger maxColleagueScore = new AtomicInteger();
        List<Employee> filterList = employeeScores.stream().filter(employee -> {
                    if (employee.colleagueScore >= maxColleagueScore.get()) {
                        maxColleagueScore.set(Math.max(employee.colleagueScore, maxColleagueScore.get()));
                        return true;
                    }
                    return false;
                }).sorted(
                        (o1, o2) ->
                                Integer.compare(o2.attitudeScore + o2.colleagueScore, o1.attitudeScore + o1.colleagueScore))
                .collect(Collectors.toList());

        int rank = 1;
        int beforeSum = -1;
        for(int i = 0; i < filterList.size(); i++) {
            Employee employeeScore = filterList.get(i);
            int attitudeScore = employeeScore.attitudeScore;
            int colleagueScore = employeeScore.colleagueScore;
            int nowSum = attitudeScore + colleagueScore;

            if(nowSum != beforeSum) {
                rank = i + 1;
                beforeSum = nowSum;
            }

            if(wanho[0] == attitudeScore && wanho[1] == colleagueScore) {
                result = rank;
                break;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        Solution18ByLv3 application = new Solution18ByLv3();
        int[][] scores = {{2,2},{1,4},{3,2},{3,2},{2,1}};
        int[][] scores2 = {{2, 2}, {2, 2}, {2, 3}, {3, 2}};

        int result = application.solution(scores);
        int result2 = application.solution(scores2);
        System.out.println("result = " + result + (result == 4));
        System.out.println("result2 = " + result2 + (result2 == 3));
    }

}