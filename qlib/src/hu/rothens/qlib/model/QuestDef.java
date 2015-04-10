package hu.rothens.qlib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class is a flyweight for prototyping a quest.
 * Created by Rothens on 2015.03.31..
 */
public class QuestDef {
    private final int id;
    private final String description;
    private final String ongoing;
    private final String onfinished;
    private final ArrayList<Integer> prerequisites;
    private final ArrayList<Integer> touch;
    private final ArrayList<Integer> questGivers;
    private final HashSet<QuestRequest> questRequest;

    public QuestDef(int id, String description, String ongoing, String onfinished, ArrayList<Integer> questGivers, HashSet<QuestRequest> questRequest, ArrayList<Integer> prerequisites) {
        this.id = id;
        this.description = description;
        this.ongoing = ongoing;
        this.onfinished = onfinished;
        this.questGivers = questGivers;
        this.questRequest = questRequest;
        this.prerequisites = prerequisites;
        touch = new ArrayList<Integer>();
    }

    public void addTouch(int i){
        touch.add(i);
    }

    /**
     * Returns the ids of the quests this quest is prerequisite of.
     * @return an ArrayList containing the ids
     */
    public ArrayList<Integer> getTouch() {
        return touch;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getOngoing() {
        return ongoing;
    }

    public String getOnfinished() {
        return onfinished;
    }

    public ArrayList<Integer> getPrerequisites() {
        return prerequisites;
    }

    public ArrayList<Integer> getQuestGivers() {
        return questGivers;
    }

    public HashSet<QuestRequest> getQuestRequest() {
        return questRequest;
    }

    @Override
    public String toString() {
        return String.format("QuestDef{id=%d, description='%s', ongoing='%s', onfinished='%s'}", id, description, ongoing, onfinished);
    }
}
