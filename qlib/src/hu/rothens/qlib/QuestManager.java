package hu.rothens.qlib;

import hu.rothens.qlib.model.QuestDef;
import hu.rothens.qlib.model.QuestState;
import hu.rothens.qlib.tools.QDBLoader;

import java.util.HashMap;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestManager {
    private HashMap<Integer, QuestState> questUsers;
    private HashMap<Integer, QuestDef> questDefs;

    public QuestManager(){
        questUsers = new HashMap<Integer, QuestState>();
        questDefs = new HashMap<Integer, QuestDef>();
    }

    public void loadDefs(QDBLoader loader){
        loader.load(questDefs);
        for(QuestDef qd : questDefs.values()){
            System.out.println(qd);
        }
    }
}
