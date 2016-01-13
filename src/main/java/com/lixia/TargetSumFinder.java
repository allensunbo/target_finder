package com.lixia;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Stack;

public class TargetSumFinder {

    /*private static final int[] DATA = new int[]{
            26546550, 448850, 39900, 680896, 7897000, 146400, 352400, 3373100, 697000, 722625, 56480400, 104950, 2168700, 833000, 2733611, 1074666, 84607, 3829803, 8799, 3251200, 10000, 1538697, 1030198, 1284273, 2438199, 749048, 3765596};*/

    public static void find(int[] DATA, int TARGET_SUM) {
        Arrays.sort(DATA);
        /*for (int i = 0; i < DATA.length; i++) {
            System.out.println(DATA[i]);
        }*/
        System.out.println("===========================");
        GetAllSubsetByStack get = new GetAllSubsetByStack(TARGET_SUM);
        get.populateSubset(DATA, 0, DATA.length);
    }


    public static class GetAllSubsetByStack {
        private int TARGET_SUM;
        private Stack<Integer> stack = new Stack<Integer>();
        private int sumInStack = 0;

        GetAllSubsetByStack(int TARGET_SUM) {
            this.TARGET_SUM = TARGET_SUM;
        }

        public void populateSubset(int[] data, int fromIndex, int endIndex) {
            if (sumInStack == TARGET_SUM) {
                print(stack);
            }

            for (int currentIndex = fromIndex; currentIndex < endIndex; currentIndex++) {

                if (sumInStack + data[currentIndex] <= TARGET_SUM) {
                    stack.push(data[currentIndex]);
                    sumInStack += data[currentIndex];
                    populateSubset(data, currentIndex + 1, endIndex);
                    sumInStack -= (Integer) stack.pop();
                }
            }
        }

        private void print(Stack<Integer> stack) {
            StringBuilder sb = new StringBuilder();
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            sb.append(formatter.format(TARGET_SUM / 100.00)).append(" = ");
            for (Integer i : stack) {
                String moneyString = formatter.format(i / 100.00);
                sb.append(moneyString).append("+");
            }
            System.out.println(sb.deleteCharAt(sb.length() - 1).toString());
        }
    }
}