package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay15 {
    /**

     [https://www.acmicpc.net/problem/2056]
     백준 2056 - 작업

     -- 문제
     이 문제는 각 작업마다 걸리는 시간이 있으며 어떠한 작업을 완료하기 위해서는 선행 작업
     이 있다. 각 작업마다 걸리는 시간과 선행작업이 주어질 때 모든 작업을 완료하기 위한
     최소 시간을 구하는 문제이다.

     -- 문제 분석 및 해결
     이 문제는 예전 기사 시험 준비 중 학습한 일정 관리 모델인 CPM이 떠오르는 문제였다.
     CPM은 모든 작업을 그래프로 표현하고 마지막 작업을 완료하기 위한 시간을 구하는 문제
     가 주로 나왔는데 그 문제에서는 각 작업마다의 선행 루트들이 여러 개라면 가장 오래
     걸리는 시간 기준으로 필요 시간을 계산할 수 있었다, 이 문제도 동일한 개념으로 보였
     으며, 결국에 각 작업마다 선행 작업 중 가장 오래 걸리는 시간을 누적한다면 해결할 수
     있을 것으로 보인다.

     -- 첫 번째 시도
     알고리즘은 dp를 사용하였으며 현재 작업에서 선행 노드의 DP에 저장된 값 중 가장 큰
     값을 구하고 현재 dp에 현재 작업의 가중치와 선행 작업 최대 가중치를 더한 값을 저장
     해 나가며 마지막에 저장된 dp 값을 반환하여 해결을 시도하였다.

     -- 두 번째 시도 및 해결
     백준에 제출 후 테스트 중 실패가 발생하였다. 코드 흐름상 문제가 없어야 할 것이므로 문
     제를 살펴보니 "모든 작업이 완료하는 최소 시간" 이라는 문구가 눈에 띄었다, CPM에서는
     마지막 작업까지 모든 작업이 간선으로 연결되어 있지만, 그렇지 않은 경우도 있을 것이다.
     어떤 작업은 중간에 이어지지 않고 해당 작업으로 종료될 수 있으며 해당 작업이 마지막에
     완료한 작업 시간보다 더 걸릴 수 있다. 그렇기 때문에 dp 반복 중에 각 dp 저장 후 최대
     값을 따로 갱신하고 최대 값을 반환하여 문제를 해결하였다.

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