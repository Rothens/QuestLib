package hu.rothens.qlib;

import hu.rothens.qlib.model.QuestDef;
import hu.rothens.qlib.model.QuestSubject;
import hu.rothens.qlib.model.QuestUser;
import hu.rothens.qlib.model.RequestType;
import hu.rothens.qlib.tools.QDBLoader;
import hu.rothens.qlib.tools.UDBManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestManager {
    private final HashMap<Integer, QuestUser> questUsers;
    private final HashMap<Integer, QuestDef> questDefs;
    private final HashSet<QuestDef> startingQuests;
    private QDBLoader loader;
    private UDBManager manager;

    public QuestManager(){
        questUsers = new HashMap<>();
        questDefs = new HashMap<>();
        startingQuests = new HashSet<>();
    }

    public QuestUser getQuestUser(int id){
        return questUsers.get(id);
    }

    public void loadDefs(QDBLoader loader){
        loader.load(questDefs);
        System.out.printf("-----------loaded %d quests---------\n", questDefs.size());
        for(QuestDef qd : questDefs.values()){
            System.out.println(qd);
            for(int i : qd.getPrerequisites()){
                if(!questDefs.containsKey(i)){
                    System.err.printf("Unresolved dependency (%d) for quest: %s\n",i,qd);
                } else {
                    questDefs.get(i).addTouch(qd.getId());
                }
            }
            if(qd.getPrerequisites().isEmpty()){
                startingQuests.add(qd);
            }
        }

        for(QuestDef qd: questDefs.values()){
            System.out.printf("Quest #%d touches: [ ", qd.getId());
            for(int i : qd.getTouch()){
                System.out.printf("%d ",i);
            }
            System.out.printf("]\n");
        }

        System.out.println("---- Starting quests: ----");
        for(QuestDef qd: startingQuests){
            System.out.println(qd);
        }

    }

    public void loadProgress(UDBManager manager){
        this.manager = manager;
        ArrayList<QuestUser> data = manager.getAllUserData();
        for(QuestUser qu : data){
            questUsers.put(qu.getId(), qu);
        }

    }

    public HashSet<QuestDef> getStartingQuests() {
        return startingQuests;
    }

    public Collection<QuestDef> getDefs(){
        return questDefs.values();
    }

    public QuestDef getDef(int id){
        return questDefs.get(id);
    }

    public void printDoable(ArrayList<Integer> done){
        ArrayList<Integer> doable = new ArrayList<Integer>();

        for(QuestDef qd : questDefs.values()){
            if(done.contains(qd.getId())) continue;
            boolean d = true;
            for(int preq : qd.getPrerequisites()){
                if(!done.contains(preq)){
                    d = false;
                    break;
                }
            }
            if(d){
                doable.add(qd.getId());
            }
        }


        for(Integer i : doable){
            System.out.println(questDefs.get(i));
        }
    }

    public void notify(int user, QuestSubject qs, RequestType type, int cnt){
        QuestUser qu = getQuestUser(user);
        if(qu != null){
            qu.notify(qs, type, cnt);
        }
    }

}
