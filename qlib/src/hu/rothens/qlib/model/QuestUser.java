package hu.rothens.qlib.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestUser {
    private final int id;
    private final ArrayList<Integer> finishedQuests;
    private final ArrayList<Quest> inProgressQuests;


    public QuestUser(int id) {
        this.id = id;
        finishedQuests = new ArrayList<>();
        inProgressQuests = new ArrayList<>();
    }

    public void notify(QuestSubject qs, RequestType type, int cnt){
        ArrayList<Quest> finished = new ArrayList<>();
        for(Quest q: inProgressQuests){
            if(q.notify(qs, type, cnt)){
                finished.add(q);
            }
        }
    }
}
