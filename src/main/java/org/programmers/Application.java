package org.programmers;

public class Application {

    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];

        for(int i = 0; i < n; i++) {
            answer[i] = getMapRow(arr1[i], arr2[i], n);
        }

        return answer;
    }

    private String getMapRow(int a, int b, int n) {
        StringBuilder sb = new StringBuilder();

        for(int i = 1; i <= n;  i++) {
            int cal1 = 0;
            int cal2 = 0;

            if(a > 0) {
                cal1 = a % 2;
                a /= 2;
            }
            if(b > 0) {
                cal2 = b % 2;
                b /= 2;
            }

            sb.append((cal1 | cal2) != 0 ? "#" : " ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Application application = new Application();

        int n = 5;
        int[] arr1 = {9, 20, 28, 18, 11};
        int[] arr2 = {30, 1, 21, 17, 28};

        String[] solution = application.solution(n, arr1, arr2);
        for (String s : solution) {
            System.out.println(s);
        }
    }

}