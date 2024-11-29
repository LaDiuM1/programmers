package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay24 {
    /**

     [https://www.acmicpc.net/problem/17609]
     백준 17609 - 회문

     -- 문제
     문자열에서 어느 방향으로 문자열을 읽어도 똑같은 문자열을 회문이라고 하며 만약
     문자 1개를 제거하여 회문이 되는 경우 유사 회문이라고 한다. 문자열 배열이 주어
     졌을 때 회문, 유사 회문, 또는 그 외인지 여부를 판단하는 문제이다.

     -- 문제 분석
     이 문제는 결국 문자열이 대칭 관계를 이루는지 확인하는 문제이다. 유사 회문은
     문자를 빼는 시도를 한 번으로 제한하였으므로 대칭 관계 확인은 중앙 기준으로
     왼쪽의 모양 기준으로 오른쪽에서 하나를 빼서 일치하는지, 그 반대의 경우가
     일치하는지 두 번 확인이 필요하다. 이 구상에서 가장 좋은 알고리즘은 투 포인터
     로 보이며 이를 사용하면 해결 흐름은 아래와 같아진다.

     1. 입력 값을 순차적으로 확인하며 문자열의 왼쪽 문자 위치와 오른쪽 문자 위치를
        기준으로 각각 left, right 변수로 선언
     2. left 위치의 문자와 right 위치의 문자가 일치하는지 확인하며 left, right의
        위치가 같아질 때까지 반복
     3. 반복 중에 일치하지 않는 문자가 있다면 현재의 위치를 백업하고 right를 다음
        위치로 이동(오른쪽 문자 제거, 왼쪽 기준 확인)
     4. 만약 다시 일치하지 않는 문자가 있다면 left, right를 백업 위치로 복구 후
        왼쪽 문자를 제거하고 나머지 일치 여부 확인 (오른쪽 기준 확인)
     5. 두 번째 방향을 탐색 중 일치하지 않는 문자가 있다면 유사 회문 조건을 만족하지
        못하므로 그 외의 결과로 저장하고 다음 탐색으로 이동
     6. 마지막까지 문자가 대칭을 이루었다면 회문인지, 유사 회문인지 비교 진행. 만약
        재시도 없이 모든 문자가 대칭이었다면 회문, 재시도가 존재하였다면 유사 회문으로
        저장 후 다음 탐색으로 이동. 모든 탐색을 완료하면 결과를 반환

     -- 회고
     이 문제에서의 해결 논점은 유사 회문 판단 시 "문자를 제거"한다는 기준을 정확히
     해석하는 것으로 보인다. 하지만 문제 자체가 대칭 구조를 확인하는 것을 빠르게
     파악했다면 큰 무리 없이 금방 두 번의 확인으로 충분하다는 것을 알 것이니 비교적
     빠른 구상이 가능한 문제로 보인다. 그렇지만 최근에 표준화된 알고리즘 문제만 풀어
     문제의 목적이 특정 알고리즘을 사용하도록 유도해 알고리즘 사용 매칭 여부만 판단
     하면 쉽게 푸는 문제만 접하여서 이렇게 스스로 해결을 위한 최적화 기준을 생각하는
     문제가 훨씬 사고력을 활용할 수 있어 좋아 보인다. 이제 항해 99 프로젝트도 막바지
     니 끝나면 좋은 문제들을 찾아 선택적으로 풀어봐야겠다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        next :
        for(int  i = 0; i < n; i++) {
            String str = br.readLine();
            int left = 0;
            int right = str.length() - 1;
            int breakLeft = -1;
            int breakRight = -1;
            boolean flag = false;

            while(left <= right) {
                if(str.charAt(left) != str.charAt(right)) {
                    if(breakLeft == -1) {
                        breakLeft = left;
                        breakRight = right--;
                    } else if(!flag){
                        left = breakLeft + 1;
                        right = breakRight;
                        flag = true;
                    } else { // 양방향에서 모두 재시도를 한 경우 그 외 케이스
                        sb.append(2).append('\n');
                        continue next;
                    }
                } else {
                    left++;
                    right--;
                }
            }
            // 재시도 없이 모두 일치하면 회문
            if(breakLeft == -1) {
                sb.append(0).append('\n');
            } else { // 재시도가 있었다면 유사회문
                sb.append(1).append('\n');
            }
        }

        bw.write(sb.toString());
        bw.close();
    }

}
