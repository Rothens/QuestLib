package hu.rothens.qlib.model;

import java.util.HashMap;

/**
 * This class holds a specific quest object.
 * Created by Rothens on 2015.03.31..
 */
public class Quest {
    final QuestDef def;
    private HashMap<Integer, Integer> cnt;


    public Quest(QuestDef def) {

        this.def = def;
        cnt = new HashMap<>();
        for(QuestRequest qr: def.getQuestRequest()){
            cnt.put(qr.hashCode(), 0);
        }
    }

    public QuestDef getDef() {
        return def;
    }
}
