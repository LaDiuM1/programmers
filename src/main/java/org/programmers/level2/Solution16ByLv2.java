package org.programmers.level2;

import java.util.Stack;

public class Solution16ByLv2 {

    /**
     * 현재 문제는 뒤에 있는 수 중에 현재 값보다 큰 값들과의 비교이다,
     * 그렇다면 뒤의 요소들이 현재 요소보다 큰 값만 남겨져 있어야 하며 가장 가까운 인덱스를 찾아야 하니,
     * 큰 값만 비교, 마지막 저장 순서라는 두개의 키워드에서 떠오른 가장 효율적인 방법은 스택 자료구조의 사용이었다.
     * 요소를 역방향으로 순회하여 현재의 값보다 작은 값들은 스택에서 제거하고 (큰 값만 비교)
     * 현재의 요소를 스택에 푸시(가장 최근에 저장한 요소)한다면 아주 효율적으로 문제 해결이 가능할 것이다.
     * 스택이 비어져있다면 -1, 아니라면 마지막 저장된 요소만 결과 배열에 저장해 나간다면 해결 될 것으로 보인다.
     */

    public int[] solution(int[] numbers) {
        int[] result = new int[numbers.length];
        Stack<Integer> stack = new Stack<>();

        for(int i = numbers.length -1; i >= 0; i--) {
            int nowValue = numbers[i];

            while(!stack.isEmpty() && stack.peek() <= nowValue) stack.pop();

            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nowValue);
        }

        return result;
    }

}