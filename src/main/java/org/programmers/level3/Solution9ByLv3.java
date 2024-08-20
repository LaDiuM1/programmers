package org.programmers.level3;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class Solution9ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/67258]
     * 2020 카카오 인턴십
     * 보석 쇼핑
     *
     * 처음에는 맨 처음 만나는 가장 짧은 구간을 탐색하는 건줄 알았으나 전체 배열에서
     * 가장 짧은 경로를 찾는 문제였었다, 그래서 처음에는 보석 종류 개수와 Map에 담긴
     * 보석의 종류가 동일하다면 LinkedHashMap의 첫번째 요소의 인덱스와 size가 일치하는 시점의
     * 반복자 인덱스 번호를 반환하려 하였으나 테스트 코드에서 실패하여 문제를 잘 읽어보니 모든 구간에서
     * 가장 짧은 경로를 찾는 문제였었다, 그래서 기존 코드에 약간의 수정을 통해 문제를 해결하였는데
     * 사이즈가 일치하는 시점에 경로를 저장하는 건 같으나 현재 저장된 경로보다 새로운 경로가 짧은지
     * 확인하는 조건과, 새로운 경로가 짧다면 반복자 인덱스를 새로운 경로 시작지점 + 1로 다시 세팅하고
     * map을 초기화하여 탐색하는 방법으로 해결하였다. 이 코드가 문제를 해결하기 위한 최선의 코드는 아니겠지만
     * 기존 코드를 활용하여 변경된 요구에 대한 빠른 해결 능력도 활용 될 수 있는 문제였다고 생각한다.
     *
     */

    public int[] solution(String[] gems) {
        int numOfType = new HashSet<>(List.of(gems)).size();
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int[] result = new int[2];

        for(int i = 0; i < gems.length; i++) {
            map.remove(gems[i]);
            map.put(gems[i], i + 1);

            if(map.size() == numOfType) {
                int start = map.values().stream().findFirst().get();
                int end = i + 1;
                if(result[0] + result[1] == 0 || result[1] - result[0] > end - start) {
                    result[0] = start;
                    result[1] = end;
                    i = Math.min(start + 1, gems.length - 1);
                    map.clear();
                }
            }
        }
        return result;
    }

}