package hu.rothens.qlib.tools;

import hu.rothens.qlib.model.QuestDef;

import java.util.HashMap;

/**
 * Created by Rothens on 2015.03.31..
 */
public interface QDBLoader {
    public void load(HashMap<Integer, QuestDef> quests);
}
