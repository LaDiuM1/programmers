package org.programmers.level3;

import java.util.Stack;

public class Solution19ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/81303]
     * 2021 카카오 채용연계형 인턴십
     * 표 편집
     *
     * 이 문제는 엑셀에서 행을 삭제하는 로직과 거의 같으며 차이점이라고 하면 행을 복구한 후
     * 커서 위치는 변함이 없다는 점이다. 그래서 현재의 커서위치를 사용하는 변수를 선언하여
     * 삭제 시 총 사이즈를 감소시키고 Stack에 삭제한 행 번호 저장, 복구 시 Stack에서 pop 후
     * 행 사이즈를 증가시키는 로직으로 구현하였다. 거기에 제한사항에 맞게 삭제, 복구 시 커서 위치를
     * 조건을 주어 조정 후 StringBuiler를 이용하여 결과를 반환하여 해결하였다.
     *
     */

    public String solution(int n, int k, String[] cmd) {
        Stack<Integer> deletedStack = new Stack<>();

        int cursor = k;
        int size = n;
        for(String commend : cmd) {
            char type = commend.charAt(0);
            switch (type) {
                case 'U' : {
                    cursor -= Integer.parseInt(commend.split(" ")[1]);
                    break;
                }
                case 'D' : {
                    cursor += Integer.parseInt(commend.split(" ")[1]);
                    break;
                }
                case 'C' : {
                    deletedStack.push(cursor);
                    size--;
                    if (cursor == size) cursor--;
                    break;
                }
                case 'Z' : {
                    if(!deletedStack.isEmpty()) {
                        int recovery = deletedStack.pop();
                        if (recovery <= cursor) cursor++;
                    }
                    size++;
                }
            }
        }

        StringBuilder result = new StringBuilder("O".repeat(size));
        while (!deletedStack.isEmpty()) {
            result.insert(deletedStack.pop(), "X");
        }

        return result.toString();
    }


    public static void main(String[] args) {
        Solution19ByLv3 application = new Solution19ByLv3();
        int n = 8;
        int k = 2;
        String[] cmd = {"D 2","C","U 3","C","D 4","C","U 2","Z","Z"};
        String[] cmd2 = {"D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C"};

        String result = application.solution(n, k, cmd);
        String result2 = application.solution(n, k, cmd2);
        System.out.println("result = " + result + (result.equals("OOOOXOOO")));
        System.out.println("result2 = " + result2 + (result2.equals("OOXOXOOO")));
    }

}