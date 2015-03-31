package hu.rothens.qlib;

import hu.rothens.qlib.tools.JsonLoader;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * Created by Rothens on 2015.03.31..
 */
public class Test {

    public static void main(String[] args) {
        QuestManager questManager = new QuestManager();
        questManager.loadDefs(new JsonLoader("qdb.json"));
    }



}
