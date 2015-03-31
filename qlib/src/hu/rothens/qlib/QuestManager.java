package hu.rothens.qlib;

import hu.rothens.qlib.model.QuestDef;
import hu.rothens.qlib.model.QuestUser;
import hu.rothens.qlib.tools.QDBLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestManager {
    private HashMap<Integer, QuestUser> questUsers;
    private HashMap<Integer, QuestDef> questDefs;

    public QuestManager(){
        questUsers = new HashMap<Integer, QuestUser>();
        questDefs = new HashMap<Integer, QuestDef>();
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
        }
        
        for(QuestDef qd: questDefs.values()){
            System.out.printf("Quest #%d touches: [ ", qd.getId());
            for(int i : qd.getTouch()){
                System.out.printf("%d ",i);
            }
            System.out.printf("]\n");
        }

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


}
