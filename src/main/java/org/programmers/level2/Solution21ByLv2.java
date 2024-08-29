package org.programmers.level2;

import org.programmers.Application;

import java.util.*;
import java.util.stream.Collectors;

public class Solution21ByLv2 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/72411]
     * 2021 KAKAO BLIND RECRUITMENT
     * 메뉴 리뉴얼
     *
     * 카카오에서 많은 사람이 어렵다고 하는 문제를 한번 풀어보았다.
     * 문제를 분석해보면 조합의 수가 2 이상, 그리고 주어진 조합의 수가 지문과 일치하여야 하고
     * 케이스별로 많이 나온 조합별로 동일 최고 랭크의 배열을 문자열의 오름차순으로 반환하는 문제이다.
     * 최근에는 레벨3 위주로 풀었어서 레벨2 문제를 접하니 확실히 사용하는 자료구조가 단순하다는 느낌을
     * 받았다, 문제 풀이는 각 주문을 순회하여 문자열 오름차순으로 정렬 후 dfs 탐색을 진행한 후 모든
     * 조합의 수를 구하여 Map에 담아 key로 그룹화한 후 value로 카운트하여 각 조합마다의 출현 수를 카운트하였다.
     * 이후 course의 조합의 수 조건들을 set에 담아 contains로 확인하며 curse 조합의 수 개수에 해당하는
     * 길이 2이상의 문자열들만 결과에 반환 후 문자열 기준으로 오름차순 정렬하여 해결하였다.
     *
     */

    public String[] solution(String[] orders, int[] course) {
        Set<Integer> courseNumSet = new HashSet<>();

        for(int num : course) {
            courseNumSet.add(num);
        }

        Map<String, Integer> map = new HashMap<>();

        for(String order : orders) {
            char[] orderArr = order.toCharArray();
            Arrays.sort(orderArr);
            dfs(new String(orderArr), new StringBuilder(), 0, map);
        }

        LinkedHashMap<String, Integer> sortedMap = map.entrySet().stream().sorted((o1, o2) -> {
            if(o1.getKey().length() == o2.getKey().length()) {
                return Integer.compare(o2.getValue(), o1.getValue());
            }
            return Integer.compare(o1.getKey().length(), o2.getKey().length());
        }).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldMap, newMap) -> oldMap,
                LinkedHashMap::new
        ));

        List<String> resultList = new ArrayList<>();

        int nowNum = 0;
        int nowOrderCount = 0;

        for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if(key.length() != nowNum) {
                nowNum = key.length();
                nowOrderCount = value;
            }
            if(value >= 2 && courseNumSet.contains(key.length()) && value >= nowOrderCount) {
                resultList.add(key);
            }

        }

        resultList.sort((o1, o2) -> o1.compareTo(o2));

        return resultList.toArray(new String[0]);
    }

    private void dfs(String order, StringBuilder key, int index, Map<String, Integer> map) {
        if(index >= order.length()) {
            return;
        }

        for (int i = index; i < order.length(); i++) {
            key.append(order.charAt(i));

            if(key.length() >= 2) {
                map.put(key.toString(), map.getOrDefault(key.toString(), 0) + 1);
            }

            dfs(order, key, i + 1, map);
            key.deleteCharAt(key.length() - 1);
        }
    }


    public static void main(String[] args) {
        Solution21ByLv2 application = new Solution21ByLv2();
        String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
        int[] course = {2,3,4};
        String[] orders2 = {"ABCDE", "AB", "CD", "ADE", "XYZ", "XYZ", "ACD"};
        int[] course2 = {2,3,5};
        String[] orders3 = {"XYZ", "XWY", "WXA"};
        int[] course3 = {2,3,4};

        String[] result = application.solution(orders, course);
        String[] result2 = application.solution(orders2, course2);
        String[] result3 = application.solution(orders3, course3);

        System.out.println("result = " + Arrays.toString(result));
        // ["AC", "ACDE", "BCFG", "CDE"]
        System.out.println("result = " + Arrays.toString(result2));
        // ["ACD", "AD", "ADE", "CD", "XYZ"]
        System.out.println("result = " + Arrays.toString(result3));
        // ["WX", "XY"]
    }

}