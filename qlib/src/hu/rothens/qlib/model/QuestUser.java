package hu.rothens.qlib.model;

import java.util.ArrayList;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestUser {
    private final int id;
    private final ArrayList<Integer> finishedQuests;
    private final ArrayList<Quest> inProgressQuests;


    public QuestUser(int id) {
        this.id = id;
        finishedQuests = new ArrayList<Integer>();
        inProgressQuests = new ArrayList<Quest>();
    }
}
