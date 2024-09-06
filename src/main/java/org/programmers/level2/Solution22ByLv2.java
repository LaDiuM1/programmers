package org.programmers.level2;

import org.programmers.Application;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solution22ByLv2 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/17677]
     * 2018 KAKAO BLIND RECRUITMENT
     * [1차] 뉴스 클러스터링
     *
     * 이 문제는 자바에서 다중집합 구현을 학습하기 위해 자카드 유사도 계산 문제를 선택하였으며
     * 각 집합에 대한 빈도를 Map에서 각 집합의 출현 빈도로 비교하는 것으로 다중집합에 대한
     * 합집합, 교집합을 구하여 문제를 해결하였다.
     *
     */

    public int solution(String str1, String str2) {
        Map<String, Integer> unionMultipleSet = new HashMap<>();
        Map<String, Integer> multipleSet1 = new HashMap<>();
        Map<String, Integer> multipleSet2 = new HashMap<>();

        addSet(unionMultipleSet, multipleSet1, str1);
        addSet(unionMultipleSet, multipleSet2, str2);

        int union = 0;
        for (String key : unionMultipleSet.keySet()) {
            union += Math.max(multipleSet1.getOrDefault(key, 0), multipleSet2.getOrDefault(key, 0));
        }

        int interCount = 0;
        for (String key : unionMultipleSet.keySet()) {
            if(multipleSet1.containsKey(key) && multipleSet2.containsKey(key)) {
                interCount += Math.min(multipleSet1.get(key), multipleSet2.get(key));
            }
        }

        return union == 0 ? 65536 : (int) ((double) interCount / union * 65536);
    }

    private void addSet(Map<String, Integer> set1, Map<String, Integer> set2, String str) {
        Arrays.stream(str.split(" ")).forEach(p -> {
            Pattern pattern = Pattern.compile("[^a-zA-Z]");
            Matcher matcher = pattern.matcher(p);
            String regex = matcher.replaceAll(" ");

            for(int i = 0; i < regex.length() - 1; i++) {
                String strSet = regex.substring(i, i + 2).toUpperCase().trim();
                if(strSet.length() >= 2 ){
                    set1.put(strSet, set1.getOrDefault(strSet, 0) + 1);
                    set2.put(strSet, set2.getOrDefault(strSet, 0) + 1);
                }
            }
        });
    }

    public static void main(String[] args) {
        Solution22ByLv2 application = new Solution22ByLv2();
        String str1 = "FRANCE";
        String str2 = "french";
        String str3 = "handshake";
        String str4 = "shake hands";
        String str5 = "aa1+aa2";
        String str6 = "AAAA12";
        String str7 = "E=M*C^2";
        String str8 = "e=m*c^2";
        int result1 = application.solution(str1, str2);
        int result2 = application.solution(str3, str4);
        int result3 = application.solution(str5, str6);
        int result4 = application.solution(str7, str8);

        System.out.println("result1 = " + result1 + " : " + (result1 == 16384));
        System.out.println("result2 = " + result2 + " : " + (result2 == 65536));
        System.out.println("result3 = " + result3 + " : " + (result3 == 43690));
        System.out.println("result4 = " + result4 + " : " + (result4 == 65536));

    }

}