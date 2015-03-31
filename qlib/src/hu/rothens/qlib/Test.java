package hu.rothens.qlib;

import hu.rothens.qlib.tools.JsonLoader;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Rothens on 2015.03.31..
 */
public class Test {

    public static void main(String[] args) {
        QuestManager questManager = new QuestManager();
        questManager.loadDefs(new JsonLoader("qdb.json"));
        System.out.println("-------------------doable without anything----------------");
        ArrayList<Integer> done = new ArrayList<Integer>();
        questManager.printDoable(done);
        System.out.println("-----------------doable with the first quest--------------");
        done.add(1);
        questManager.printDoable(done);
        System.out.println("-----------------doable with two quests-------------------");
        done.add(2);
        questManager.printDoable(done);
        System.out.println("-----------------doable with three quests-----------------");
        done.add(3);
        questManager.printDoable(done);
    }



}
