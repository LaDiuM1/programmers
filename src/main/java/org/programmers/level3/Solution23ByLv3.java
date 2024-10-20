package org.programmers.level3;

public class Solution23ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/258705]
     2024 KAKAO WINTER INTERNSHIP
     산 모양 타일링

     이 문제는 정삼각형을 2n+1 만큼 일렬로 교차하여 채우고 윗변의 특정 위치에
     다시 정삼각형을 올린 후 정삼각형과 정삼각형을 두개 이어붙힌 형태의 마름모
     모양을 채워 넣을 수 있는 경우의 수를 구하는 문제이다.

     -- 구상
     이 문제는 문제를 보는 순간 굉장히 막막하였다, 특정 모양의 도형을 채우는
     경우의 수라는 것은 완전 탐색으로 진행하면 로직이 굉장히 복잡해지기 때문
     이다. 그래서 완전 탐색으로 풀 수 있는지 제한사항을 확인해 보니 n이 무려
     100,000까지로 입출력 예에서 n이 4일 때 149, 10일 때 7704처럼 기하
     급수적으로 증가하는 것을 보고 완전 탐색으로 풀면 천문학적인 경우의 수를
     처리할 수 없다는 걸 깨닫게 되었다, 그래서 문제를 다시 분석해 보았으며
     문제는 도형과 삼각형이었지만 마름모 모양은 지문을 봤을 때 정삼각형을 두
     개 이어붙인 형태라고 하였고, 이는 변이 맞물린 연속된 두 개의 도형이라는
     것을 의미하며 완성된 도형의 모양을 봤을 때 마름모꼴을 채운다는 것은 단순
     히 배열이 연속되나 연속되지 않나를 의미한다는 걸 알게 되었다, 그렇다면
     이는 도형을 숫자로 단순화가 가능하며 각 숫자(연속 배열)의 배치로 표현이
     가능하기 때문에 윗변의 삼각형의 존재 여부에 따라 배치를 한번 그려보았고
     각 배치가 주변 연속된 배열 모양 완성에 어떤 영향을 주는지 숫자로 표현
     해보았다, 연속된 두 개의 배열마다 묶은 후 실수로 표현해봤을 때, 밑변의
     배열은 1, 2, 3 형태, 윗변은 2.5, 3.5 형태로 중간에 낀 형태로 표현할
     수 있었고 주변의 영향은 밑변은 -1, +1, 윗변은 -0.5, +0.5까지 주변
     배열에 영향을 준다는 걸 알게 되었다. 도형의 배치를 숫자로 표현하고 주
     변에 줄 수 있는 영향을 풀다 보니 이 문제는 dp로 풀 수 있는 전형적인
     문제라는 것을 깨닫게 되었고 이후 코드 작성 후 문제 풀이까지는 일사천
     리로 진행되었다.

     -- 구현
     1.
     밑변의 길이가 n 2 + 1이라고 하였으니 두 개씩 배치할 수 있는 수는
     n 2이므로 n 2 길이의 배열을 선언하고 여기에 윗변의 최대 개수를
     고려하여 길이 2의 배열을 선언하고 여기에 윗변의 위치를 저장한다.

     2.
     길이 + 윗변 삼각형의 개수만큼의 dp 정보를 담을 배열을 선언하고 각
     요소마다 우측으로 영향을 줄 수 있는 길이를 저장한다, 윗변이 위치한
     곳은 우측으로 3개만큼 영향을 주고 밑변은 2개만큼 영향을 주므로 각
     배열마다 영향을 주는 길이를 저장한다.

     3.
     dp 배열 크기를 dp 정보가 담긴 배열의 + 1만큼 선언하고 dp 정보가
     담긴 배열 크기만큼 요소를 역순으로 반복하는 반복문을 선언한다.

     4.
     반복문에서는 인덱스 + dp 정보 배열의 요소의 길이가 총 길이를 넘어
     간다면 dp 배열의 + 1 요소에 1을 더한 값을 현재 요소에 저장한다.
     저장 시 지문에도 나와있듯 크기가 무한정 커질 수 있으니 10007을
     나눈 나머지를 저장한다.
     만약 총 길이를 넘지 않는다면 이전 요소 + 1(현재 요소 선택 시의 값)
     에 dp 정보에 담긴 요소 길이만큼 뒤에 있는 합을 더하여 현재 요소에
     저장한다. 이는 현재 요소를 선택했을 때의 값과 이전까지의 조합의 수
     총합, 그리고 선택했을 때 영향을 미치는 조합을 제외한 그 후 의 합을
     구하기 위함이다.

     5. 최종적으로 dp 첫 번째 요소에 저장된 합을 반환하여 모든 요소 배열
     의 조합의 수를 10007로 나눈 값에 1을 더하여(모든 배열이 정삼각형으로
     채워진 경우)반환한다.


     -- 총평
     가장 최근의 문제라 처음 보는 유형에 문제를 보는 순간 막막하였지만, 결국
     문제를 하나하나 분해한다면 아주 어려운 문제가 아님을 알게 되었고, 기존의
     알고리즘 지식 내에서 충분히 해결할 수 있었다는 게 뿌듯함이 느껴졌다.

     */

    public int solution(int n, int[] tops) {
        int length = n * 2;
        boolean[] topsPosition = new boolean[length * 2];
        int numOfTops = 0;

        for(int i = 0; i < tops.length; i++) {
            if(tops[i] == 1) {
                topsPosition[i * 2 + numOfTops] = true;
                numOfTops++;
            }
        }

        int[] dpInfos = new int[length + numOfTops];
        int[] dp = new int[dpInfos.length + 1];

        for(int i = 0; i < dpInfos.length; i++) {
            if(topsPosition[i]) {
                dpInfos[i] = 3;
            } else {
                dpInfos[i] = 2;
            }
        }

        for(int i = dpInfos.length - 1; i >= 0; i--) {
            if(dpInfos[i] + i > dpInfos.length - 1) {
                dp[i] = (dp[i + 1] + 1) % 10007;
            } else {
                dp[i] = (dp[i + 1] + dp[i + dpInfos[i]] + 1) % 10007;
            }
        }

        return (dp[0] + 1) % 10007;
    }

    public static void main(String[] args) {
        Solution23ByLv3 solution = new Solution23ByLv3();

        int n = 10;
        int[] tops = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        int result = solution.solution(n, tops);
        System.out.println("result = " + result);
    }



}