package org.programmers.level3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution8ByLv3Test {

    /*
    [https://school.programmers.co.kr/learn/courses/30/lessons/64064]
    2019 카카오 개발자 겨울 인턴십
    불량 사용자


    개발팀 내에서 이벤트 개발을 담당하고 있는 "무지"는 최근 진행된 카카오이모티콘
    이벤트에 비정상적인 방법으로 당첨을 시도한 응모자들을 발견하였습니다.
    이런 응모자들을 따로 모아 불량 사용자라는 이름으로 목록을 만들어서 당첨 처리 시 제외하도록
    이벤트 당첨자 담당자인 "프로도" 에게 전달하려고 합니다. 이 때 개인정보 보호을 위해 사용자 아이디 중
    일부 문자를 '*' 문자로 가려서 전달했습니다. 가리고자 하는 문자 하나에 '*' 문자 하나를 사용하였고 아이디 당
    최소 하나 이상의 '*' 문자를 사용하였습니다.
    "무지"와 "프로도"는 불량 사용자 목록에 매핑된 응모자 아이디를 제재 아이디 라고 부르기로 하였습니다.

    이벤트 응모자 아이디 목록이 담긴 배열 user_id와 불량 사용자 아이디 목록이 담긴 배열 banned_id가 매개변수로 주어질 때,
    당첨에서 제외되어야 할 제재 아이디 목록은 몇가지 경우의 수가 가능한 지 return 하도록 solution 함수를 완성해주세요.
     */

    @Test
    public void solution8byLv3Test1() {
        Solution8ByLv3 solution8ByLv3 = new Solution8ByLv3();

        String[] testCase1 = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] testCase2 = {"fr*d*", "abc1**"};

        int result = solution8ByLv3.solution(testCase1, testCase2);

        assertThat(result).isEqualTo(2);
    }

    @Test
    public void solution8byLv3Test2() {
        Solution8ByLv3 solution8ByLv3 = new Solution8ByLv3();

        String[] testCase1 = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] testCase2 = {"*rodo", "*rodo", "******"};

        int result = solution8ByLv3.solution(testCase1, testCase2);

        assertThat(result).isEqualTo(2);
    }


}