package org.programmers.level3;

import java.util.*;

public class Solution26ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/258709]
     2024 KAKAO WINTER INTERNSHIP
     주사위 고르기

     이 문제는 각 주사위마다 특정 숫자로 구성되어 있으며 모든 주사위가 다른 구성
     을 가지고 있고 총 주사위 개수인 n의 절반을 가져간 후 가장 승률이 높은 조합
     을 찾는 문제이다.

     -- 문제 분석
     이전 문제에서 단순해 보이는 문제에 시간 초과를 경험했기 때문에 같은 출제처
     인 이 문제도 신중하게 접근하였다. 일단 첫 번째로 완전 탐색으로 시간 안에
     해결할 수 있는 문제인지 확인 하기 위해 최대치의 연산 횟수를 계산해 보았다.
     주사위 개수인 n이 최대 10이고 절반을 선택하기 때문에 10c5, 즉 조합의 수는
     252가지가 나오며 주사위는 6면으로 이루어져 있고 보유한 주사위마다 한 개씩을
     선택하여 모든 주사위를 던질 때마다 나올 수 있는 순열의 개수는 6^(n/2)이다.
     승리 수를 구하려면 보유한 순열과 상대 순열을 모두 비교하여야 하므로 경우의
     수가 제곱으로 증가하기 때문에 결과적으로 최대 횟수는 10c5 / 2 * ((6^5)^2)
     = 7,618,738,176로 완전 탐색으로 진행하면 너무나도 당연하게 시간 초과가
     발생하는 문제로 보인다. 그렇기 때문에 다른 최적화 방법을 구상해 보았으나
     결국 모든 승리 수를 구해야 하는 문제로 보이기 때문에 각 자료구조와 최적화
     알고리즘을 사용하여 연산 횟수를 최소화 하는 방향으로 해결 방법을 정하였다.

     -- 구현
     첫 번째로 주사위의 조합의 수를 구한 후 주사위 조합마다 주사위를 굴린 결과
     를 구하였다. 여기까지는 연산 횟수가 6^(n/2) * 10c5로 별다른 최적화 없이
     재귀를 통하여 구하였으며 중요한 부분은 승리 수 비교이다. 일단 TreeMap을
     사용하여 결과로 나온 승리 수를 내림차순으로, 값으로는 조합에 사용된 인덱스
     번호를 저장하는 구조를 선언하였고 순열에서 반대쪽 순열과 비교하는 반복문을
     선언 후 승리 수를 구하는 로직을 작성하였다. 승리 수는 우리 쪽 순열은 순차
     적으로 모든 요소를 순회하고 각 순회에서 상대 쪽 전체 순열과 비교할 때는
     list의 이분 탐색을 이용하였다. 승리 수라는 것은 결국 상대 전체 순열에서
     현재 순회 숫자가 정렬된 순열에서 몇 번째에 들어가는지 구하면 이분 탐색으로
     구한 인덱스 번호가 곧 승리 수가 될 것이고 이는 현재 인덱스 번호 이하의 수는
     모두 현재 값보다 작다는 의미이기 때문에 이분 탐색으로 구한 인덱스 번호를 승
     리 수로 계산하여 조합에 누적해나갔다. 이분 탐색으로 각 요소를 순회할 때마다
     O(log N)의 시간 동안 인덱스 번호를 탐색하고 모든 요소를 순회하여 비교하면
     O(N log N)의 시간 동안 모든 승리 수를 구할 수 있을 것이다, 여기에 추가로
     최적화를 위해 HashMap으로 이미 비교한 값을 map에 저장하여 곧바로 승리 수
     를 구하는 캐시 구조를 적용하여 최적화를 진행하였다. 각 조합마다 누적되어
     구해진 승리 수를 TreeMap에 저장하였고 내림차순으로 정렬된 TreeMap의 첫
     번째 요소의 값을 꺼내어 조합의 인덱스 번호를 반환하여 문제를 해결하였다.

     */
    public int[] solution(int[][] dice) {
        int n = dice.length;

        TreeMap<Integer, int[]> playResultTreeMap = new TreeMap<>((o1, o2) -> Integer.compare(o2, o1));
        List<List<Integer>> combinationValues = new ArrayList<>();
        List<int[]> combinationInfo = new ArrayList<>();
        searchDiceCombination(0,n / 2, new ArrayList<>(), combinationValues, combinationInfo, dice);

        int size = combinationValues.size();
        for(int i = 0; i < size; i++) {
            int j = size - i - 1;
            int winCount = 0;
            List<Integer> nowValues = combinationValues.get(i);
            List<Integer> comparisonValues = combinationValues.get(j);
            Map<Integer, Integer> visited = new HashMap<>();
            for(Integer value : nowValues) {
                if(visited.containsKey(value)) {
                    winCount += visited.get(value);
                    continue;
                }
                int position = Collections.binarySearch(comparisonValues, value);
                if(position < 0) {
                    position = -position - 1;
                    winCount += position;
                } else {
                    while(position > 0 && comparisonValues.get(position - 1).equals(value)) position--;
                    winCount += position;
                }
                visited.put(value, position);
            }

            playResultTreeMap.put(winCount, combinationInfo.get(i));
        }

        int[] result = playResultTreeMap.firstEntry().getValue();
        for(int i = 0; i < result.length; i++) {
            result[i] += 1;
        }

        return result;
    }

    private void searchDiceCombination(int start, int depth, List<Integer> nowCombinationIndexes, List<List<Integer>> combinationResults,
                                       List<int[]> indexList, int[][] dice) {
        if(depth == nowCombinationIndexes.size()) {
            boolean[][] visited = new boolean[nowCombinationIndexes.size()][6];
            List<Integer> sumValues = new ArrayList<>();
            addRollDiceResults(0, nowCombinationIndexes, new ArrayList<>(), sumValues, visited, dice);
            sumValues.sort((o1, o2) -> Integer.compare(o1, o2));
            combinationResults.add(sumValues);
            indexList.add(nowCombinationIndexes.stream().mapToInt(Integer::intValue).toArray());
            return;
        }

        for(int i = start; i < dice.length; i++) {
            nowCombinationIndexes.add(i);
            searchDiceCombination(i + 1, depth, nowCombinationIndexes, combinationResults, indexList, dice);
            nowCombinationIndexes.remove(nowCombinationIndexes.size() - 1);
        }
    }

    private void addRollDiceResults(int start, List<Integer> indexes, List<Integer> now,
                                    List<Integer> rollResults, boolean[][] visited, int[][] dice) {
        if(now.size() == indexes.size()) {
            rollResults.add(now.stream().mapToInt(Integer::intValue).sum());
            return;
        }

        for(int i = start; i < indexes.size(); i++) {
            for(int j = 0; j < 6; j++) {
                if(!visited[i][j]) {
                    visited[i][j] = true;
                    now.add(dice[indexes.get(i)][j]);
                    addRollDiceResults(i + 1, indexes, now, rollResults, visited, dice);
                    visited[i][j] = false;
                    now.remove(now.size() - 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution26ByLv3 solution = new Solution26ByLv3();

        int[][] dice = 	{{40, 41, 42, 43, 44, 45}, {43, 43, 42, 42, 41, 41}, {1, 1, 80, 80, 80, 80}, {70, 70, 1, 1, 70, 70}};
//        int[][] dice = 	{{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}};
//        int[][] dice = 	{{40, 41, 42, 43, 44, 45}, {43, 43, 42, 42, 41, 41}, {1, 1, 80, 80, 80, 80},
//                {70, 70, 1, 1, 70, 70}, {40, 41, 42, 43, 44, 45}, {43, 43, 42, 42, 41, 41}};
//        int[][] dice = 	{{1, 2, 3, 4, 5, 6}, {2, 2, 4, 4, 6, 6}};

        int[] result = solution.solution(dice); // [1, 3]
        System.out.println("result = " + Arrays.toString(result));
    }
}