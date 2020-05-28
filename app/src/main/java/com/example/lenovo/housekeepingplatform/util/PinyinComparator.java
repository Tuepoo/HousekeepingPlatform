package com.example.lenovo.housekeepingplatform.util;

import com.example.lenovo.housekeepingplatform.module.city.SortModel;

import java.util.Comparator;

/**
 * Created by lenovo on 2019/4/7.
 */

public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        if (o1.getSortLetters().equals("@")
                || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")
                || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
