package org.programmers.level3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution12ByLv3Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/67259]
    2020 카카오 인턴십
    경주로 건설

    건설회사의 설계사인 죠르디는 고객사로부터 자동차 경주로 건설에 필요한 견적을 의뢰받았습니다.
    제공된 경주로 설계 도면에 따르면 경주로 부지는 N x N 크기의 정사각형 격자 형태이며 각 격자는 1 x 1 크기입니다.
    설계 도면에는 각 격자의 칸은 0 또는 1 로 채워져 있으며, 0은 칸이 비어 있음을 1은 해당 칸이 벽으로 채워져 있음을 나타냅니다.
    경주로의 출발점은 (0, 0) 칸(좌측 상단)이며, 도착점은 (N-1, N-1) 칸(우측 하단)입니다. 죠르디는 출발점인 (0, 0) 칸에서
    출발한 자동차가 도착점인 (N-1, N-1) 칸까지 무사히 도달할 수 있게 중간에 끊기지 않도록 경주로를 건설해야 합니다.
    경주로는 상, 하, 좌, 우로 인접한 두 빈 칸을 연결하여 건설할 수 있으며, 벽이 있는 칸에는 경주로를 건설할 수 없습니다.
    이때, 인접한 두 빈 칸을 상하 또는 좌우로 연결한 경주로를 직선 도로 라고 합니다.
    또한 두 직선 도로가 서로 직각으로 만나는 지점을 코너 라고 부릅니다.
    건설 비용을 계산해 보니 직선 도로 하나를 만들 때는 100원이 소요되며, 코너를 하나 만들 때는 500원이 추가로 듭니다.
    죠르디는 견적서 작성을 위해 경주로를 건설하는 데 필요한 최소 비용을 계산해야 합니다.

    도면의 상태(0은 비어 있음, 1은 벽)을 나타내는 2차원 배열 board가 매개변수로 주어질 때,
    경주로를 건설하는데 필요한 최소 비용을 return 하도록 solution 함수를 완성해주세요.

    [제한사항]
    board는 2차원 정사각 배열로 배열의 크기는 3 이상 25 이하입니다.
    board 배열의 각 원소의 값은 0 또는 1 입니다.
    도면의 가장 왼쪽 상단 좌표는 (0, 0)이며, 가장 우측 하단 좌표는 (N-1, N-1) 입니다.
    원소의 값 0은 칸이 비어 있어 도로 연결이 가능함을 1은 칸이 벽으로 채워져 있어 도로 연결이 불가능함을 나타냅니다.
    board는 항상 출발점에서 도착점까지 경주로를 건설할 수 있는 형태로 주어집니다.
    출발점과 도착점 칸의 원소의 값은 항상 0으로 주어집니다.
     */

    @Test
    public void solution12byLv3Test1() {
        Solution12ByLv3 solution12ByLv3 = new Solution12ByLv3();

        int[][] arr1 = {{0,0,0},{0,0,0},{0,0,0}};

        assertThat(solution12ByLv3.solution(arr1)).isEqualTo(900);
    }

    @Test
    public void solution12byLv3Test2() {
        Solution12ByLv3 solution12ByLv3 = new Solution12ByLv3();

        int[][] arr2 = {{0,0,0,0,0,0,0,1},{0,0,0,0,0,0,0,0},{0,0,0,0,0,1,0,0},{0,0,0,0,1,0,0,0},{0,0,0,1,0,0,0,1},{0,0,1,0,0,0,1,0},{0,1,0,0,0,1,0,0},{1,0,0,0,0,0,0,0}};

        assertThat(solution12ByLv3.solution(arr2)).isEqualTo(3800);
    }

    @Test
    public void solution12byLv3Test3() {
        Solution12ByLv3 solution12ByLv3 = new Solution12ByLv3();

        int[][] arr3 = {{0,0,1,0},{0,0,0,0},{0,1,0,1},{1,0,0,0}};

        assertThat(solution12ByLv3.solution(arr3)).isEqualTo(2100);
    }

    @Test
    public void solution12byLv3Test4() {
        Solution12ByLv3 solution12ByLv3 = new Solution12ByLv3();

        int[][] arr4 = {{0,0,0,0,0,0},{0,1,1,1,1,0},{0,0,1,0,0,0},{1,0,0,1,0,1},{0,1,0,0,0,1},{0,0,0,0,0,0}};

        assertThat(solution12ByLv3.solution(arr4)).isEqualTo(3200);
    }


}