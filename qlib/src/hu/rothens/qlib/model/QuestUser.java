package hu.rothens.qlib.model;

import hu.rothens.qlib.QuestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents a User, which has quests that it can do.
 * Created by Rothens on 2015.03.31..
 */
public class QuestUser {
    private final int id;
    private final HashSet<Integer> finishedQuests;
    private final HashMap<Integer, Quest> inProgressQuests;
    private final HashSet<QuestDef> available;
    private final QuestManager manager;


    public QuestUser(int id, QuestManager manager) {
        this.id = id;
        finishedQuests = new HashSet<>();
        inProgressQuests = new HashMap<>();
        available = new HashSet<>();
        this.manager = manager;
    }

    public QuestUser(int id, HashSet<Integer> finishedQuests, HashMap<Integer, Quest> inProgressQuests, HashSet<QuestDef> available, QuestManager manager) {
        this.id = id;
        this.finishedQuests = finishedQuests;
        this.inProgressQuests = inProgressQuests;
        this.available = available;
        this.manager = manager;
    }

    /**
     * Notifies this User about an event which could affect active quests.
     * If a given quest is complete, it'll look up the quests it touches, and if all prerequisites are met, it'll be
     * added to the available list.
     * @param qs The subject of the event.
     * @param type The type of the event.
     * @param cnt The amount of the event.
     */
    public synchronized void notify(QuestSubject qs, RequestType type, int cnt){
        ArrayList<Quest> finished = new ArrayList<>();
        for(Quest q: inProgressQuests.values()){
            if(q.notify(qs, type, cnt)){
                finished.add(q);
                finishedQuests.add(q.getDef().getId());

            }
        }
        for(Quest q : finished) {
            inProgressQuests.remove(q.getDef().getId());
        }
        for(Quest q : finished){
            for(int touch : q.getDef().getTouch()){
                QuestDef qd = manager.getDef(touch);
                if(qd != null && !available.contains(qd) && finishedQuests.containsAll(qd.getPrerequisites())){
                    available.add(qd);
                }
            }

        }
    }

    public int getId() {
        return id;
    }

    public HashSet<Integer> getFinishedQuests() {
        return finishedQuests;
    }

    public HashMap<Integer, Quest> getInProgressQuests() {
        return inProgressQuests;
    }

    public HashSet<QuestDef> getAvailable() {
        return available;
    }
}
