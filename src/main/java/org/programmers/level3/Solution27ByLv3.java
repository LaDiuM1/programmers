package org.programmers.level3;

import java.util.*;

public class Solution27ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/340210]
     [PCCP 기출문제] 4번 / 수식 복원하기

     이 문제는 2 ~ 9진수 사이에 있는 방정식의 값을 구하는 문제이며 해가 있는
     다른 값들을 예시로 사용하여 방정식의 값이 명확한 경우 해당 값을 정답으로
     제출하는 문제이다.

     -- 문제 분석
     이 문제는 풀이가 명확하다. 해가 있는 식들을 2 ~ 9진수까지 사용하여 식이
     성립하는지 확인 후 사용된 진수들의 교집합을 구하여 방정식에 대입하여 유일
     한 결과로 성립하는지 확인하면 되는 문제이다.

     -- 구현
     이 문제는 java의 정수 래퍼 클래스의 toString 및 parse 메서드를 이용하면
     너무 쉽게 풀리는 문제이기 때문에 진법 변환 감각을 익힐 겸 직접 진법 변환
     메서드들을 직접 구현했으며. 해결 과정은 아래처럼 진행하였다.
     1. 교집합을 확인하기 위한 2 ~ 9의 정수 집합을 선언
     2. 주어진 수식들을 순회하여 수식과 방정식을 분리
     3. 계산식을 순회할 때 값의 각 자릿수를 확인하여 최댓값을 구하고 최댓값 미만의
        값들은 진수 교집합에서 제거하여 1차 최적화
     4. 해가 있는 값들을 순회하여 각 식들을 교집합에 있는 진수의 값들과 일치하는지
        확인, 일치하는 진수 집합들과 원본 집합의 교집합으로 원본 집합 갱신
     5. 방정식들을 순회하여 교집합의 진수들을 순회하여 값이 유일한지 확인 후 값이
        유일하면(해가 구해지면) 해당 값으로 방정식 값 저장, 값이 여러 개(해가 없
        으면) 방정식의 값을 "?"로 치환 후 결과 리스트에 저장 후 반환

     */

    public String[] solution(String[] expressions) {
        // 사용 진수 집합
        Set<Integer> radixSet = new HashSet<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9));
        // 계산식 리스트
        List<String[]> expressionList = new ArrayList<>();
        // 방정식 인덱스 번호 집합
        LinkedHashSet<Integer> equationIndexes = new LinkedHashSet<>();

        int minRadix = -1; // 최소 진수 확인
        for(int i = 0; i < expressions.length; i++) {
            String[] expression = expressions[i].split(" ");
            String validStr = expression[0] + expression[2];
            for(int j = 0; j < validStr.length(); j++) {
                minRadix = Math.max(validStr.charAt(j) - '0' + 1, minRadix);
            }
            expressionList.add(expression);
            if(expression[4].equals("X")) equationIndexes.add(i);
        }

        // 최소 진수 미만 값 제거
        for(int i = 2; i < minRadix; i++) {
            radixSet.remove(i);
        }

        // 해가 있는 값들을 순회하며 방정식이 아닌 값들의 진수들의 교집합 확인
        for(int i = 0; i < expressionList.size(); i++) {
            if(equationIndexes.contains(i)) continue;
            String[] expressionArr = expressionList.get(i);

            setUsedRadixSet(expressionArr, radixSet);
        }

        // 결과 반환 리스트
        List<String> resultList = new ArrayList<>();

        // 방정식 값들을 순회하며 진수 집합의 계산 결과가 단일 결과 일시 방정식에 단일 결괏값 저장
        // 결과가 여러 개 일시 방정식에 물음표 저장
        for (Integer equationIndex : equationIndexes) {
            String[] equation = expressionList.get(equationIndex);
            Set<Integer> radixExpressionResult = new HashSet<>();
            for (int radix : radixSet) {
                radixExpressionResult.add(getRadixExpressionResult(radix, equation));
            }

            if(radixExpressionResult.size() == 1) {
                int answerValue = 0;
                for (int i : radixExpressionResult) {
                    answerValue = i;
                }
                equation[4] = String.valueOf(answerValue);
            } else {
                equation[4] = "?";
            }

            resultList.add(String.join(" ", equation));
        }

        return resultList.toArray(new String[0]);
    }

    private void setUsedRadixSet(String[] expressionArr, Set<Integer> radixSet) {
        Set<Integer> nowRadixSet = new HashSet<>();
        for(int i = 2; i <= 9; i++) {
            if (radixSet.contains(i) && validRadix(i, expressionArr)) nowRadixSet.add(i);
        }

        radixSet.retainAll(nowRadixSet);
    }

    private boolean validRadix(int radix, String[] expressionArr) {
        int resultValue = Integer.parseInt(expressionArr[4]);
        int radixResultValue = getRadixExpressionResult(radix, expressionArr);

        return resultValue == radixResultValue;
    }

    private int getRadixExpressionResult(int radix, String[] expressionArr) {
        int value1 = Integer.parseInt(expressionArr[0]);
        char expression = expressionArr[1].charAt(0);
        int value2 = Integer.parseInt(expressionArr[2]);

        int radix10Value1 = parseRadixToInteger(value1, radix);
        int radix10Value2 = parseRadixToInteger(value2, radix);

        if(expression == '+') {
            return parseIntegerToRadix(radix10Value1 + radix10Value2, radix);
        } else {
            return parseIntegerToRadix(radix10Value1 - radix10Value2, radix);
        }
    }

    private int parseRadixToInteger(int radixValue, int radix) {
        int value = 0;
        int carry = 1;
        while(radixValue > 0) {
            value += radixValue % 10 * carry;
            radixValue /= 10;
            carry *= radix;
        }

        return value;
    }

    private int parseIntegerToRadix(int value, int radix) {
        int radixValue = 0;
        int carry = 1;

        while(value > 0) {
            radixValue += value % radix * carry;
            value /= radix;
            carry *= 10;
        }

        return radixValue;
    }

    public static void main(String[] args) {
        Solution27ByLv3 application = new Solution27ByLv3();

        String[] expressions1 = {"2 - 1 = 1", "2 + 2 = X", "7 + 4 = X", "8 + 4 = X"};
        System.out.println(Arrays.toString(application.solution(expressions1)));
        // answer : [2 + 2 = 4, 7 + 4 = 12, 8 + 4 = 13]
    }
}