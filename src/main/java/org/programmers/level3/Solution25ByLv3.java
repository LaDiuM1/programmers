package org.programmers.level3;

import java.util.*;

public class Solution25ByLv3 {

    /**
     [https://school.programmers.co.kr/learn/courses/30/lessons/258707]
     2024 KAKAO WINTER INTERNSHIP
     n + 1 카드게임

     이 문제는 초기에 n / 3의 카드를 가지고 시작하며 라운드마다 카드
     두 장을 뽑고 코인을 사용하여 가질 카드를 선택할 수 있고 보유한
     카드 두 장으로 n + 1 숫자를 만들면 다음 라운드로 넘어갈 수 있
     으며 최대 도달 가능한 라운드를 찾는 문제이다.

     -- 구상 및 해결 과정
     1. 첫 번째 시도
     이 문제는 문제 지문을 읽고 바로 떠오르는 생각은 완전 탐색이었다.
     일반적으로 최대수는 DFS로 모든 수를 탐색하여 문제를 해결하나
     이 문제는 각 라운드마다 진출 가능한 경우가 새로 분기가 되기 때
     문에 넓이 우선 탐색으로 진행하였고 약간의 최적화를 위해 우선순위
     큐로 라운드가 높은 순서대로 탐색을 진행하는 코드를 작성하였다.

     코드를 작성 완료 후 결과를 제출하였는데 8번 테스트부터 시간 초과
     가 발생하였다, 원인을 분석해 보니 n이 20에 코인이 10인 경우에도
     탐색 수가 수천개로 증가하였고 문제의 제한 사항은 n이 1000까지로
     어마어마한 경우의 수가 발생한다는 것을 감각적으로 알 수 있었다.

     2. 두 번째 시도
     그렇다면 완전 탐색으로는 제한시간 안에 절대로 푸는게 불가능한 문제
     라는 이야기가 되므로 각 라운드마다 최적의 조건을 찾아 카드를 버릴
     지 소모하고 받을지 판단해야 한다는 것이다. 그리고 머릿속으로 해당
     상황에 대한 시뮬레이션을 그려보니 카드를 받는 경우와 받지 않는 경
     우의 탐색 분기가 여전히 완전 탐색으로 진행해야 할 정도로 상태 변화
     깊이가 매우 깊다는 걸 느끼게 되었다. 출제자는 이렇게 아주 복잡한
     상태 변화의 최적값을 찾는 문제를 레벨 3으로 냈을리가 없다고 생각
     했다. 그러면 문제를 최대한 단순화해보자. 라운드를 최대로 진행하는
     것은 결국 코인을 가장 효율적으로 사용하는 경우의 수를 찾는 것이다.
     하지만 이는 이미 완전 탐색이 막힌 시점에서 미래의 가치까지 생각하여
     코인을 사용할 수 있는 방법을 찾을 수 없다. 그렇다면 미래의 가치를
     현재의 가치로 끌고 오면 되지 않을까 하는 생각이 문득 떠올랐고 패를
     받고 안 받고 가 아닌 패를 모두 버린 다음 버린 패에서 선택하는 게
     미래의 가치를 현재의 가치로 바꾸는 방법이라고 생각했다. 그렇다면
     풀이가 매우 쉬워진다. 각 라운드마다 패를 일단 모두 버린 후 덱에
     있는 패로만 라운드를 진행한다. 그리고 더 이상 진행할 수 없다면
     코인을 사용하여 버린 패중에 골라오면 되는 것이다. 그렇게 버린패
     를 가져온다면 그 패를 뽑았던 라운드에서 코인을 사용하여 패를 가
     져온 것과 완전히 동일한 조건이 되며 버린 패를 가져와 다음 라운드
     로 진행할 수 있는지만 확인하면 되는 아주 단순한 해결 방법이었다.
     현재 방법으로도 통과할 수 있을것이라는 건 감각적으로 알았지만 첫
     시도에서 시간 초과가 발생했으므로 좀 더 신중하게 최적화를 진행하였다.
     모든 카드가 중복 요소가 없는 시점에서 필요한 카드는 n + 1 - 현재
     카드 숫자이며 HashSet 자료구조로 각 그룹에서 원하는 숫자가 존재
     하는지 상수 시간에 찾을 수 있게 코드를 작성하였고, 코드를 제출하니
     가장 오래 걸린 시간도 23ms로 개인적으로 아주 만족하는 시간 안에
     문제를 해결할 수 있었다.

     */

    public int solution(int coin, int[] cards) {
        int n = cards.length;
        int numOfFirstTakes = n / 3;
        Set<Integer> takes = new HashSet<>();
        Set<Integer> drops = new HashSet<>();
        Queue<Integer> deck = new LinkedList<>();

        for(int i = 0; i < n; i++) {
            if(i < numOfFirstTakes) {
                takes.add(cards[i]);
            } else {
                deck.add(cards[i]);
            }
        }

        int nowRound = 1;
        nextRound :
        while(deck.size() >= 2) {
            for(int i = 0; i < 2; i++) {
                drops.add(deck.poll());
            }

            // 덱 자체에서 조합 가능한 경우 다음 라운드
            for(int cardNo : takes) {
                int requireNo = n + 1 - cardNo;
                if(cardNo != requireNo && takes.contains(requireNo)) {
                    takes.remove(cardNo);
                    takes.remove(requireNo);
                    nowRound++;
                    continue nextRound;
                }
            }

            // 현재 덱과 버린 패중 조합 가능한 패가 있으면 코인 하나 소모하고 다음 라운드
            if(coin >= 1) {
                for(int cardNo : takes) {
                    int requireNo = n + 1 - cardNo;
                    if(drops.contains(requireNo)) {
                        takes.remove(cardNo);
                        drops.remove(requireNo);
                        coin--;
                        nowRound++;
                        continue nextRound;
                    }
                }
            }

            // 버린 패에서 조합 가능한 패가 있으면 코인 두개 소모하고 다음 라운드
            if(coin >= 2) {
                for(int cardNo : drops) {
                    int requireNo = n + 1 - cardNo;
                    if(cardNo != requireNo && drops.contains(requireNo)) {
                        drops.remove(cardNo);
                        drops.remove(requireNo);
                        coin -= 2;
                        nowRound++;
                        continue nextRound;
                    }
                }
            }

            // 넘길 수 없으면 종료
            return nowRound;
        }

        return nowRound;
    }

    public static void main(String[] args) {
        Solution25ByLv3 solution = new Solution25ByLv3();

        int coin = 4;
        int[] cards = {3, 6, 7, 2, 1, 10, 5, 9, 8, 12, 11, 4};

        int result = solution.solution(coin, cards);
        System.out.println("result = " + result);
    }
}