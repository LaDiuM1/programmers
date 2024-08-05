package org.programmers.level2;

import java.util.Arrays;

public class Solution20ByLv2 {

    int[] info;
    int[] maxDiffTargetStatus;
    int maxDiffScore = Integer.MIN_VALUE;

    public int[] solution(int n, int[] info) {
        this.info = info;
        this.maxDiffTargetStatus = new int[info.length];

        dfs(n, 10, new int[info.length], 0);

        return maxDiffScore <= 0 ? new int[]{-1} : maxDiffTargetStatus;
    }

    private void dfs(int n, int index, int[] target, int nowDiffScore) {
        if (n * 10 + nowDiffScore < maxDiffScore) return;

        if (index == -1) {
            if (nowDiffScore >= maxDiffScore && n == 0) {
                maxDiffScore = nowDiffScore;
                maxDiffTargetStatus = target;
            }
            return;
        }

        for(int i = 0; i <= n && i <= info[index] + 1; i++) {
            int[] newTarget = Arrays.copyOf(target, target.length);
            newTarget[index] = i;
            int newScore = i > info[index] ? 10 - index : info[index] > 0 ? -(10 - index) : 0;

            dfs(n - i, index - 1, newTarget, nowDiffScore + newScore);
        }
    }

}