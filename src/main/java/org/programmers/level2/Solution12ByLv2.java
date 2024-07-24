package org.programmers.level2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Solution12ByLv2 {

    /**
     * 얼마전에 습득한 PriorityQueue를 바로 알차게 사용할 수 있는 문제였다.
     * 문제의 요지는 과제를 끝내고 나서 다음 과제를 진행하는 것이 아닌 현재 진행중인
     * 과제가 있더라도 다음 과제가 들어오면 해당 과제부터 처리해야하는 것이다.
     * 이는 정확히 Stack의 구조와 일치하여 구상을 간단하게 할 수 있었다.
     * 입력된 plans들의 시간을 분으로 변경한 후 시간순으로 오름차순으로 정렬하여 큐에 저장,
     * 이후 큐에서 하나씩 꺼내어 스택에 넣은 후 스택에서 마지막 요소별로 처리하면 되는 간단한 문제이다.
     * 이제부터는 효율적 처리와 간단한 구현 두 가지 선택지로 좁혀졌는데,
     * 각 플랜의 시간을 다음 시간과 비교하여 여러 조건으로 처리하는 방법과
     * 시작 시간부터 1분 단위로 증가하여 다음 시작 시간이 되면 스택에 추가하고 스택에서는 마지막 요소만 처리하면 되는
     * 간단한 구현, 두 가지 선택지가 구상되었다.
     * 효율적 처리는 시간복잡도를 고려하여 아주 높은 시간에서는 효율적일 수 있으나
     * 현재 문제의 제한 사항은 00:00 ~ 23:59분까지로, 즉 하루라는 시간으로 한정되어 있었기 때문에
     * 최대치가 고작해야 1440번의 반복에 불과한 매우 짧은 시간에 처리 가능한 문제였기 때문에
     * 분단위로 증가하는, 현실 관점에서 구현하는 간단한 구현으로 정하여 해결한 문제였다.
     */

    public static class PlanInfo {
        int startMinutes;
        int playMinutes;
        String name;

        public PlanInfo (int startMinutes, int playMinutes, String name) {
            this.startMinutes = startMinutes;
            this.playMinutes = playMinutes;
            this.name = name;
        }
    }

    public String[] solution(String[][] plans) {
        List<String> resultList = new ArrayList<>();
        Stack<PlanInfo> workStack = new Stack<>();
        PriorityQueue<PlanInfo> plansQueue = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p1.startMinutes, p2.startMinutes)
        );

        for (String[] plan : plans) {
            String name = plan[0];
            int hourInMinute = Integer.parseInt(plan[1].split(":")[0]) * 60;
            int minute = Integer.parseInt(plan[1].split(":")[1]) + 1;
            int startMinutes = hourInMinute + minute;
            int playMinutes = Integer.parseInt(plan[2]);

            plansQueue.add(new PlanInfo(startMinutes, playMinutes, name));
        }

        PlanInfo nowPlan = null;

        int nowMinutes = plansQueue.peek().startMinutes;

        while(resultList.size() < plans.length) {
            if (!plansQueue.isEmpty() && nowPlan == null) nowPlan = plansQueue.peek();

            if(nowPlan != null && nowPlan.startMinutes == nowMinutes) {
                workStack.push(plansQueue.poll());
                nowPlan = null;
            }

            if(!workStack.isEmpty()) {
                PlanInfo workingPlan = workStack.lastElement();
                workingPlan.playMinutes--;

                if(workingPlan.playMinutes == 0) resultList.add(workStack.pop().name);

            }
            nowMinutes++;
        }

        return resultList.toArray(new String[0]);
    }

}