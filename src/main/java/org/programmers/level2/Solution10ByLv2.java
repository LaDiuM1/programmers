package org.programmers.level2;

import java.util.Collections;
import java.util.PriorityQueue;

public class Solution10ByLv2 {

    /**
     * 이번 문제는 자료구조와 논리만 맞다고 꼭 정답이라고 할 수 없다는 걸 알게된 문제였다.
     * 제한사항에 입력 자료가 최대 100만, 매개변수가 기본적으로 int형으로 설정되어 있어
     * 21억을 초과할 일이 없다고 생각하였으나 논리상으로 정확해도 계속 테스트 케이스 7 ~ 10번에서 실패가 나왔다.
     * 푸는 구상은 좌표 X를 이동하며 이동된 좌표 X 기준으로 각 원의 Y 거리를 계산하여 각 Y위치 사이에 정수 개수를 반환하도록 논리를 구상하였는데
     * 테스트 케이스가 7번부터 계속 실패하여 논리적 오류만 주구장창 찾았었다.
     * 하지만 논리적 오류를 아무리 확인해도 발견할 수 없어 아얘 디버그를 켜서 계산과정을 확인하면서 체크했는데
     * 값이 NaN이 나오는 경우가 발견되었다. 루트 계산에서 값이 NaN이 나올 수 있는 케이스는 값이 0인 경우밖에 없는데
     * 이미 Math함수로 최소값을 0과 1로 맞춰둔 상태에서 어떻게 음수가 나오는 것인지 생각하다가 오버플로우가 나와서 값이 반전된
     * 경우가 있다고 깨닫게 되었다, 그래서 인수들을 모두 long형으로 바꾸니 거짓말처럼 테스트를 통과하였다.
     * 이번 문제의 교훈은 자료구조와 메모리 효율성도 중요하지만 제한사항에 맞춰 최대값의 맞춰 자료형을 선택하는게 중요하다는 걸 알게 되었다.
     */

    public long solution(long r1, long r2) {
        long count = 0;

        for (long x = 0; x <= r2; x++) {
            double r1YPosition = Math.max(Math.sqrt(Math.max(r1 * r1 - x * x, 0)), 1);
            double r2YPosition = Math.max(Math.sqrt(Math.max(r2 * r2 - x * x, 0)), 0);

            count += (long) Math.floor(r2YPosition) - (long) Math.ceil(r1YPosition) + 1;
        }

        return count * 4;
    }

}