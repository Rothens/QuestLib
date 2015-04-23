package hu.rothens.qlib;

import hu.rothens.qlib.model.*;
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
        QuestUser qu = questUsers.get(id);
        if(qu == null){
            System.out.println("createUser");
            qu = new QuestUser(id, this);
            qu.addAvailable(getStartingQuests());
            questUsers.put(id, qu);
            if(manager != null){
                manager.newUser(id);
            }
        }
        return qu;
    }

    public void loadDefs(QDBLoader loader){
        loader.load(questDefs);
        this.loader = loader;
        System.out.printf("-----------loaded %d quests---------\n", questDefs.size());
        for(QuestDef qd : questDefs.values()){
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

    }

    public void loadProgress(UDBManager manager){
        this.manager = manager;
        ArrayList<QuestUser> data = manager.getAllUserData();
        for(QuestUser qu : data){
            questUsers.put(qu.getId(), qu);
            System.out.println(qu.getId() + " -> " + qu.getAvailable().size() + " " + qu.getInProgressQuests().size());

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
            System.out.println("notifying user: " + user);
            qu.notify(qs, type, cnt);
        }
    }

    public void updateProgress(QuestUser qu, Quest q){
        if(manager != null){
            System.out.println("updateProgress: " + qu.getId() + " quest: " + q.getDef().getId());
            manager.updateProgress(qu, q);
        }
    }

    public void setAvailable(QuestUser qu, int id){
        if(manager != null){
            manager.setAvailable(qu, id);
        }
    }

    public void setFinished(QuestUser qu, Quest q){
        if(manager != null){
            manager.finishQuest(qu, q);
        }
    }

    public void acceptQuest(int id, int questId){
        QuestUser qu = getQuestUser(id);
        QuestDef qd = getDef(questId);
        Quest q;
        if(qu != null && qd != null && (q=qu.acceptQuest(qd))!=null){
            //TODO: Dispatch events here
            if(manager != null){
                manager.acceptQuest(qu, q);
                System.out.println("accepted quest!");
            }
        }

    }

}
