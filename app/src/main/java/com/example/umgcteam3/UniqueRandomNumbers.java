package com.example.umgcteam3;

import java.util.ArrayList;
import java.util.Collections;

public class UniqueRandomNumbers {
    public static int[] getRandomNumbers( int upperLimit, int numbersNeeded){
        int[] numbers = new int[numbersNeeded];
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<upperLimit; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i=0; i<numbersNeeded; i++) {
            numbers[i] = list.get(i);
        }
        return numbers;
    }
}
