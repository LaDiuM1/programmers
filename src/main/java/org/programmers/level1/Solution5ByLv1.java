package org.programmers.level1;

import org.programmers.Application;

import java.util.*;

public class Solution5ByLv1 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/118666]
     * 2022 KAKAO TECH INTERNSHIP
     * 성격 유형 검사하기
     *
     * 구현 기초 강화를 위한 기본 문제 풀이 진행
     */

    public String solution(String[] survey, int[] choices) {
        String[][] table = {
                {"R", "T"},
                {"C", "F"},
                {"J", "M"},
                {"A", "N"}
        };
        Map<String, Integer> pointMap = new HashMap<>();

        for(String[] items : table) {
            for(String item : items) {
                pointMap.put(item, 0);
            }
        }

        for(int i = 0; i < survey.length; i++) {
            int choice = choices[i];
            String a = String.valueOf(survey[i].charAt(0));
            String b = String.valueOf(survey[i].charAt(1));

            if(choice > 4) {
                pointMap.put(b, pointMap.get(b) + choice - 4);
            } else if (choice < 4){
                pointMap.put(a, pointMap.get(a) + 4 - choice);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(String[] item : table) {
            String a = item[0];
            String b = item[1];
            int pointA = pointMap.get(a);
            int pointB = pointMap.get(b);

            if(pointA > pointB) {
                sb.append(a);
            } else if (pointB > pointA) {
                sb.append(b);
            } else {
                sb.append(a);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Solution5ByLv1 application = new Solution5ByLv1();
        String[] survey = {"AN", "CF", "MJ", "RT", "NA"};
        int[] choices = {5, 3, 2, 7, 5};


        // "TCMA"
        String result = application.solution(survey, choices);
        // ["mumu", "kai", "mine", "soe", "poe"]

        System.out.println("result = " + result);
    }
}
