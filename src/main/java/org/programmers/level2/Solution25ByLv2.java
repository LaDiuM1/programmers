package org.programmers.level2;

import java.util.HashMap;
import java.util.Map;

public class Solution25ByLv2 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/60057]
     * 2020 KAKAO BLIND RECRUITMENT
     * 문자열 압축
     *
     * 이번에는 카카오 코딩테스트에서 정답률이 낮은 문제라고 들어 흥미가 동해 문제를
     * 풀어보았다.
     *
     * 문제를 살펴보면 반복되는 문자열을 압축하여 반복되는 숫자는 문자 앞에 표기하여
     * 압축한 결과중 가장 짧은 길이를 반환하는 문제이다.
     *
     * -- 구상 및 구현
     * 문자열을 특정 길이로 잘라서 압축한다는 것은 최대 문자열 절반까지 자를 수 있다는
     * 의미이므로 1부터 n / 2까지 각 슬라이스 구간마다 연속되는 집합의 갯수를 찾으면
     * 되겠다는 생각이 들었다, 그렇다면 HashMap으로 집합을 관리하고 연속되는 집합이
     * 나올 때마다 value에 값을 하나씩 추가하여 반복되는 집합의 수를 관리하여 최종적
     * 으로 map을 순회하여 기존 길이에서 계산식에 맞게 값을 변경해나가면 슬라이스 마다
     * 길이를 구할 수 있을 것이고, 모든 슬라이스를 순회하여 가장 작은 값을 구하여 문제
     * 를 해결하였다.
     *
     */

    public int solution(String s) {
        int n = s.length();
        int minimumLength = n;

        for(int i = 1; i <= n / 2; i++) {
            Map<String, Integer> map = new HashMap<>();
            int index = 0;

            for(int j = i; j <= n - i; j += i) {
                String prev = s.substring(j - i, j + i - i);
                String now = s.substring(j, j + i);

                if (now.equals(prev)) {
                    String key = index + "," + now;
                    map.put(key, map.getOrDefault(key, 1) + 1);
                } else {
                    index++;
                }
            }

            if(map.isEmpty()) continue;

            int nowLength = n;
            for(Map.Entry<String, Integer> entry : map.entrySet()) {
                int value = entry.getValue();
                int strLen = entry.getKey().split(",")[1].length();
                int minusLen = strLen * value;
                int plusLen = strLen + String.valueOf(value).length();

                nowLength += plusLen;
                nowLength -= minusLen;
            }

            minimumLength = Math.min(nowLength, minimumLength);
        }

        return minimumLength;
    }

    public static void main(String[] args) {
        Solution25ByLv2 application = new Solution25ByLv2();

        String s1 = "aabbaccc";
        String s2 = "ababcdcdababcdcd";
        String s3 = "abcabcdede";
        String s4 = "abcabcabcabcdededededede";
        String s5 = "xababcdcdababcdcd";

        System.out.println(application.solution(s1));
        System.out.println(application.solution(s2));
        System.out.println(application.solution(s3));
        System.out.println(application.solution(s4));
        System.out.println(application.solution(s5));
    }

}