package org.programmers.level3;

import org.programmers.Solution;

import java.util.*;

public class Solution31ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/150367]
     2023 KAKAO BLIND RECRUITMENT
     표현 가능한 이진트리

     이 문제는 이진수에서 0은 더미 노드, 1은 노드라고 했을 때 차수 상관없이 수직선
     기준 이진수의 순서와 동일한 위치에 노드가 있다고 했을 때 이진 트리로 성립하는
     지 확인하는 문제이며 포화 이진 트리를 가정하고 이진수를 정수로 변환한 값이 문
     제로 주어진다.

     -- 문제 분석
     첫 번째로 이진 트리 성립 여부를 확인하는 방법부터 생각해 보았다. 이진 트리는
     가장 일반적인 트리 구조로 루트부터 자식 노드가 두 개씩 있는 트리 구조이다.
     그렇다면 자신이 노드이고 부모 노드가 더미 노드라면 이 형태는 이진 트리로
     성립하지 않으므로 해당 조건으로 이진 트리 여부를 확인하면 될 것으로 보인다.
     다음으로는 이진 트리를 그리면서 해당 조건을 만족하는지 확인하면 된다. 이진
     트리는 말 그대로 이진 트리이기 때문에 재귀를 사용하여 이진 탐색으로 확인하면
     정확히 이진 트리를 그려가며 확인할 수 있을 것이다. 한 가지 남은 문제는 입력
     값은 정수로 주어지는데 이걸 이진법으로 변환하면 맨 왼쪽이 0일 경우 손실된다는
     것이다. 입력 값은 포화 이진 트리로 주어진다고 하였으니 부족한 0을 채워 넣어야
     한다. 다행히 포화 이진 트리의 길이는 쉽게 구하는 게 가능하다. 차수가 증가할수록
     노드가 이전 차수보다 2배씩 증가한다는 것이다. 그렇다면 정수를 2진수로 변환하고
     변환된 길이가 포화 이진 트리 길이보다 작다면 부족한 만큼 앞에 0을 채워 넣으면
     해결될 것으로 보인다.

     -- 구현
     1. 정수를 2진법으로 변환 후 포화 이진 트리 길이보다 작다면 앞에 0을 채워 넣는다.
     2. 이진 탐색 메서드를 선언 후 자기 기준 왼쪽, 오른쪽으로 이진 탐색 메서드를 재귀
        하며 매개변수로 넘어온 부모가 더미 노드, 자신이 노드일 경우 false를 반환한다.
        탐색 깊이는 길이를 매개변수로 받아 재귀가 깊어질 때마다 길이를 절반씩 나누어
        길이가 0보다 클 때만 탐색하도록 하며 왼쪽과 오른쪽 탐색 메서드를 and 연산자
        를 사용하여 모든 값이 true일 때만 true를 반환하도록 한다.
     3. 입력 값 배열을 모두 순회하며 탐색 메서드를 실행하고 이진 탐색 메서드의 결과의
        참, 거짓 여부에 따라 결과 배열에 저장해나가며 문제를 해결한다.

     */

    public int[] solution(long[] numbers) {
        int[] results = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            // 포화 이진트리 길이에 맞게 앞에 0을 채워 넣음
            String binaryStr = getBinaryStr(numbers[i]);
            int left = 0;
            int right = binaryStr.length() - 1;
            // 이진 탐색으로 부모가 0, 자식이 1인 경우로 이진 트리 성립 여부 검사
            results[i] = isBinaryTree(left, right, '1', right, binaryStr) ? 1 : 0;
        }

        return results;
    }

    private static String getBinaryStr(long numbers) {
        String binaryStr = Long.toString(numbers, 2);
        int fullTreeLength = 0;
        int multiplier = 1;
        // 포화 이진트리는 차수마다 노드 수가 이전 차수보다 제곱으로 증가하는 특성을 이용하여 필요 길이 계산
        while(fullTreeLength < binaryStr.length()) {
            fullTreeLength += multiplier;
            multiplier *= 2;
        }
        // 필요 길이에 부족한 만큼 앞에 0 삽입
        return "0".repeat(fullTreeLength - binaryStr.length()) + binaryStr;
    }

    private boolean isBinaryTree(int left, int right, char isParentDummy, int length, String binaryStr) {
        int mid = (left + right) / 2;
        char isCurrentDummy = binaryStr.charAt(mid);
        if((isCurrentDummy == '1' && isParentDummy == '0')) return false;

        if(length / 2 > 0){ // 길이를 절반씩 줄여나가 0보다 클 때만 탐색
            return isBinaryTree(left, mid - 1, isCurrentDummy, length / 2 ,binaryStr)
                    && isBinaryTree(mid + 1, right, isCurrentDummy, length / 2, binaryStr);
        }

        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        long[] numbers = {7, 42, 5};
        System.out.println(Arrays.toString(solution.solution(numbers)));
        // [1, 1, 0]
    }
}