package org.programmers.level2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution13ByLv2Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/169199]
    리코쳇 로봇

    문제 설명
    리코쳇 로봇이라는 보드게임이 있습니다.

    이 보드게임은 격자모양 게임판 위에서 말을 움직이는 게임으로,
    시작 위치에서 목표 위치까지 최소 몇 번만에 도달할 수 있는지 말하는 게임입니다.

    이 게임에서 말의 움직임은 상, 하, 좌, 우 4방향 중 하나를 선택해서 게임판 위의 장애물이나
    맨 끝에 부딪힐 때까지 미끄러져 이동하는 것을 한 번의 이동으로 칩니다.

    다음은 보드게임판을 나타낸 예시입니다.
    ...D..R
    .D.G...
    ....D.D
    D....D.
    ..D....
    여기서 "."은 빈 공간을, "R"은 로봇의 처음 위치를, "D"는 장애물의 위치를, "G"는 목표지점을 나타냅니다.
    위 예시에서는 "R" 위치에서 아래, 왼쪽, 위, 왼쪽, 아래, 오른쪽, 위 순서로 움직이면 7번 만에 "G" 위치에 멈춰 설 수 있으며,
    이것이 최소 움직임 중 하나입니다.

    게임판의 상태를 나타내는 문자열 배열 board가 주어졌을 때,
    말이 목표위치에 도달하는데 최소 몇 번 이동해야 하는지 return 하는 solution함수를 완성하세요.
    만약 목표위치에 도달할 수 없다면 -1을 return 해주세요.
    */

    @Test
    public void solution13byLv2Test1() {
        String[] testCase1 = {
                "...D..R",
                ".D.G...",
                "....D.D",
                "D....D.",
                "..D...."};

        Solution13ByLv2 solution13ByLv2 = new Solution13ByLv2();

        int result = solution13ByLv2.solution(testCase1);

        assertThat(result).isEqualTo(7);
    }

    @Test
    public void solution13byLv2Test12() {
        String[] testCase1 = {
                ".D.R",
                "....",
                ".G..",
                "...D"
        };

        Solution13ByLv2 solution13ByLv2 = new Solution13ByLv2();

        int result = solution13ByLv2.solution(testCase1);

        assertThat(result).isEqualTo(-1);
    }

}