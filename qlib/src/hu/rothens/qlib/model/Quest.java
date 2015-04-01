package hu.rothens.qlib.model;

/**
 * This class holds a specific quest object.
 * Created by Rothens on 2015.03.31..
 */
public class Quest {
    final QuestDef def;


    public Quest(QuestDef def) {
        this.def = def;
    }

    public QuestDef getDef() {
        return def;
    }
}
