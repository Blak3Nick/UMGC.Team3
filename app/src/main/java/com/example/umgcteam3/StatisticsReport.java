package com.example.umgcteam3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsReport {

    public static HashMap<String, List[]> data;
    public StatisticsReport() {
        this.data = new HashMap<>();

    }
    public void addData(String exName, List<Object> dates, List<Object> weights){
        List[] lists = new List[2];
        lists[0] = dates;
        lists[1] = weights;
        data.put(exName, lists);
    }
    public List[] getData(String exName) {
        return data.get(exName);
    }


}
