package org.programmers.level4;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Solution2ByLv4 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/17685]
     2018 KAKAO BLIND RECRUITMENT
     [3차] 자동완성

     이 문제는 검색 시 자동완성 기능처럼 사전에 저장된 단어들에서 단어를 입력
     하면 어디까지 입력해야 검색어를 특정할 수 있는지 확인하는 문제이다.

     -- 문제 분석
     문제를 보자마자 떠오르는 생각은 그래프 탐색이었다. 하지만 그래프는 인근
     정점의 정보만 가지고 있으므로 시작점부터 동일 경로 여부를 탐색하기에는
     부적합했다. 그래서 동일 경로 탐색을 생각해 보다가 문득 트리 구조가 정확히
     그런 구조라는 것을 금방 떠올릴 수 있었다. 트리구조에서는 동일한 집합을
     부모 노드로 하여 자식 노드로 계속 이어지는 구조이므로 현재 문제에서 탐색
     조건에 완벽히 부합한다. 이 문제에서는 해당 경로로 향하는 집합의 수도 확인
     해야 하므로 노드의 수를 카운트하는 트리 구조를 직접 구현하여 문제를 해결
     해보자.

     -- 구현
     1. 트리를 구성할 문자 클래스를 선언하고 검색에 사용할 key, 현재 노드의
        경로의 수가 몇 개 있는지 확인할 count, 자식 트리를 저장할 HashMap을
        선언, 그리고 HashMap의 해시코드를 key 필드로 비교하기 위해 HashCode
        오버라이딩
     2. 루트 노드들을 저장할 HashMap 선언, 이후 문자열 배열을 순회하며 각
        문자열의 첫 문자를 루트로 맵 초기화
     3. 문자열 배열을 다시 순회하여 각 문자열의 첫 문자를 루트로 하여 두 번째
        문자부터 루트의 자식으로 연결하여 트리 구조 초기화
     4. 다시 문자열 배열을 순회하며 첫 문자부터 트리에서 탐색 진행, 경로에서
        현재 경로로 진행하는 경로의 수를 확인하여 탐색 진행 여부 검증, 탐색
        시도 횟수만큼 카운트하여 문제에서의 총 입력 횟수를 구한 후 반환하여
        문제 해결

     -- 총평
     레벨4 문제는 기존 자료구조나 알고리즘을 응용하는 단계라는 느낌이 들었고
     이 문제에서도 기존 자료구조의 변형을 금방 떠올려 문제를 빠르게 해결할 수
     있었다. 이는 알고리즘과 자료구조에 대한 이해가 높아지고 있다는 의미로
     해석할 수 있을 것이다. 이에 더해 문제 풀이 자체도 즐기기 시작하고 있으
     므로 실력이 지금 기점으로 점점 더 빠르게 도약할 수 있을 것만 같은 느낌
     이다.

     */

    private static class CharTree {
        char key; int count = 1;
        Map<Character, CharTree> childTree = new HashMap<>();

        public CharTree(char key) {
            this.key = key;
        }
        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    public int solution(String[] words) {
        Map<Character, CharTree> rootMap = new HashMap<>();

        // 루트 생성
        for (String word : words) {
            char key = word.charAt(0);
            if(rootMap.containsKey(key)) {
                rootMap.get(key).count++;
            } else {
                rootMap.put(key, new CharTree(word.charAt(0)));
            }
        }

        // 트리 초기화
        for (String word : words) {
            CharTree curTree = rootMap.get(word.charAt(0));
            for(int j = 1; j < word.length(); j++) {
                char key = word.charAt(j);
                if (!curTree.childTree.containsKey(key)) {
                    curTree.childTree.put(key, new CharTree(key));
                    curTree = curTree.childTree.get(key);
                } else {
                    curTree = curTree.childTree.get(key);
                    curTree.count++;
                }
            }
        }

        int totalCount = 0;
        // 트리 탐색
        for (String word : words) {
            totalCount++;
            CharTree curTree = rootMap.get(word.charAt(0));
            for(int j = 1; j < word.length(); j++) {
                char key = word.charAt(j);
                if(curTree.count < 2 || !curTree.childTree.containsKey(key)) break;
                curTree = curTree.childTree.get(key);
                totalCount++;
            }
        }

        return totalCount;
    }

    public static void main(String[] args) {
        Solution2ByLv4 solution = new Solution2ByLv4();
        String[] words = {"word","war","warrior","world"};
        System.out.println(solution.solution(words));
        // 7
    }
}