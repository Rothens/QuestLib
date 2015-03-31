package hu.rothens.qlib.model;

import java.util.ArrayList;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestUser {
    private final int id;
    private ArrayList<Integer> finishedQuests;
    private ArrayList<Integer> inProgressQuests;

    public QuestUser(int id) {
        this.id = id;
        finishedQuests = new ArrayList<Integer>();
        inProgressQuests = new ArrayList<Integer>();
    }
}
