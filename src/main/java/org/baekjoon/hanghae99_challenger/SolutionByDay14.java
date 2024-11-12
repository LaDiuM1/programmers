package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SolutionByDay14 {
    /**

     [https://www.acmicpc.net/problem/2179]
     백준 2179 - 비슷한 단어

     -- 문제
     이 문제는 입력 값으로 주어지는 단어들에서 두 단어의 시작 부분부터 시작하여 가장
     긴 부분까지 일치하는 개수를 찾고, 일치 개수가 동일할 시 입력 순서가 가장 빠른
     두 단어를 반환하는 문제이다.

     -- 문제 분석
     시작 부분부터의 일치 여부(접두사 일치 여부)를 확인한다는 점에서 이전에 해결한 프로
     그래머스의 자동 완성 문제가 떠올랐으며 해당 문제에서는 트라이(Trie) 자료구조 형태로
     구현하여 해결하였기 때문에, 해당 자료구조가 맨 처음 떠올랐다. 하지만 이 문제와의 가장
     큰 차이는 자동 완성 문제에서는 비교 집합군이 두 개가 아닌 다른 모든 집합이었기 때문에
     트리 구조 방식으로 접근하였고 이 문제는 비교군이 두 단어이기 때문에 문제를 단순화 가능
     할 것으로 보였다. 일단 두 단어의 접두사 일치 여부 판단 방법은 굉장히 많을 것이겠지만
     당장 효율적인 방법으로 떠오르는 것은 정렬을 이용하는 것이다. 자바의 정렬은 평균적으로
     O(n log n)의 시간복잡도를 가지는 것으로 알고 있기 때문에 이 문제에서의 접두사 일치
     여부 판단에는 충분히 효율적일 것이다. 그렇다면 정렬 후 각 요소들을 순회 및 비교하여
     접두사 일치 개수가 가장 큰 단어 집합들을 구하고 입력 값을 순회하여 해당 집합에서 존재
     하는지 확인 후 가장 처음으로 만난 집합의 단어를 기준으로 입력 값에서 순차적으로 두 번
     찾으면 곧 문제의 조건인 가장 큰 접두사들 중 입력 값이 가장 빠른 순서의 두 단어 출력
     이라는 조건이 만족될 것이다.

     -- 구현
     1. 입력 단어들을 복사하여 정렬한다.
     2. 가장 큰 접두사의 단어 집합들을 저장할 HashSet을 선언한다.
     3. 정렬된 배열을 순회하여 현재 요소와 다음 요소를 비교하여 접두사 개수를 찾는다.
     4. 접두사 개수가 이전까지의 가장 큰 접두사 개수보다 크다면 집합을 초기화하고
        현재의 접두사 개수로 가장 큰 접두사 개수를 초기화한다.
     5. 만약 현재 접두사 개수와 현재까지 가장 큰 접두사 개수가 일치하면 집합에 접두사를
        저장한다.
     6. 원본 배열을 순회하여 단어의 시작 부분부터 가장 큰 접두사 크기까지의 단어를 구하고
        (접두사) 집합에서 존재 여부를 확인한다.
     7. 현재의 단어의 접두사가 집합에 존재한다면 결과에 현재 단어를 저장하고 현재의 접두사
        기준으로 나머지 배열에서 접두사가 일치하는 단어를 찾아 두번째 단어로 저장한 뒤 찾은
        두 단어를 반환하여 해결한다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        String[] words = new String[n];
        String[] sortedWords = new String[n];
        for(int i = 0; i < n; i++) words[i] = br.readLine();

        System.arraycopy(words, 0, sortedWords, 0, n);
        Arrays.sort(sortedWords);

        Set<String> wordSet = new HashSet<>();
        int maxLength = 0;
        for(int i = 0; i < n - 1; i++) {
            String word1 = sortedWords[i];
            String word2 = sortedWords[i + 1];

            int count = 0;
            for(int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if(word1.charAt(j) != word2.charAt(j)) break;
                count++;
            }
            if(count > maxLength) {
                wordSet.clear();
                maxLength = count;
            }
            if(count == maxLength) {
                wordSet.add(word1.substring(0, count));
            }
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(words[i].length() < maxLength) continue;
            String substring = words[i].substring(0, maxLength);
            if(wordSet.contains(substring)) {
                result.add(words[i]);
                for(int j = i + 1; j < n; j++) {
                    if(words[j].length() < maxLength) continue;
                    if(words[j].substring(0, maxLength).equals(substring)) {
                        result.add(words[j]);
                        break;
                    }
                }
                break;
            }
        }

        result.forEach(System.out::println);
    }

}