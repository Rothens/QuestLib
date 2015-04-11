package hu.rothens.qlib.model;

import hu.rothens.qlib.QuestManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestUser {
    private final int id;
    private final ArrayList<Integer> finishedQuests;
    private final ArrayList<Quest> inProgressQuests;
    private final HashSet<QuestDef> available;
    private final QuestManager manager;


    public QuestUser(int id, QuestManager manager) {
        this.id = id;
        finishedQuests = new ArrayList<>();
        inProgressQuests = new ArrayList<>();
        available = new HashSet<>();
        this.manager = manager;
    }

    public synchronized void notify(QuestSubject qs, RequestType type, int cnt){
        ArrayList<Quest> finished = new ArrayList<>();
        for(Quest q: inProgressQuests){
            if(q.notify(qs, type, cnt)){
                finished.add(q);
                finishedQuests.add(q.getDef().getId());

            }
        }
        inProgressQuests.removeAll(finished);
        for(Quest q : finished){
            for(int touch : q.getDef().getTouch()){
                QuestDef qd = manager.getDef(touch);
                if(qd != null && !available.contains(qd) && finishedQuests.containsAll(qd.getPrerequisites())){
                    available.add(qd);
                }
            }

        }
    }

}
