package org.programmers.level2;

import java.util.*;

public class Solution9ByLv2 {

    /**
     *  쉬운 문제라고 생각하였으나 최대 적의 수를 정렬하는 과정에서 기존에 알고 있던 자료구조로는 속도에서
     *  문제가 있다고 생각하였다.
     *  기존에는 tree 정렬을 이용하려면 Map과 Set을 이용해야 한다 생각하여 Set은 중복을 허용하지 않고
     *  Map은 최대 숫자의 개수를 저장하는 방식을 사용해야 하므로 구현이 복잡해질 우려가 있다 생각하였다.
     *
     *  일단 구상 자체는 최대 적의 수를 내림차순 정렬하여 무적권과 비교하여 소모 여부를 결정하는 것이었는데
     *  List를 사용해서 갱신 후 항상 정렬을 한다면 속도에서 어마어마한 손해를 볼 것이다.
     *  내가 원하는 것은 중복을 허용하며 Tree 구조로 정렬을 지원하는 함수였는데 검색을 해보니
     *  PriorityQueue<> 라는 컬렉션 프레임워크가 해당 기능을 지원하고 있었다.
     *  아래 코드는 구상과 일치하는 구현을 했을 때 상당히 효율적인 코드가 나옴을 확인할 수 있었고
     *  이 문제는 개인적으로 아주 유익한 문제라고 생각한다.
     *  자료구조 지식 하나만 추가했는데 문제 해결력이 비약적으로 상승 한 것 같은 느낌이다.
     *
     */

    public int solution(int n, int k, int[] enemy) {
        int round = 0;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int numEnemy : enemy) {
            if (n - numEnemy >= 0) {
                n -= numEnemy;
                maxHeap.add(numEnemy);
            } else {
                if (k > 0) {
                    if(!maxHeap.isEmpty() && maxHeap.peek() > numEnemy) {
                        n += maxHeap.poll();
                        n -= numEnemy;
                        maxHeap.add(numEnemy);
                    }
                    k--;
                } else {
                    break;
                }
            }
            round++;
        }
        return round;
    }

}