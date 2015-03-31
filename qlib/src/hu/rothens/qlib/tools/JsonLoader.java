package hu.rothens.qlib.tools;

import hu.rothens.qlib.model.Quest;
import hu.rothens.qlib.model.QuestDef;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Rothens on 2015.03.31..
 */
public class JsonLoader implements QDBLoader {
    String file;
    public JsonLoader(String file){
        this.file = file;
    }

    @Override
    public void load(HashMap<Integer, QuestDef> quests) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONArray array = (JSONArray) jsonParser.parse(new FileReader(file));
            for (Object anArray : array) {
                JSONObject o = (JSONObject) anArray;
                Integer id = ((Long) o.get("id")).intValue();
                String desc = (String) o.get("description");
                String ong = (String) o.get("ongoing");
                String onf = (String) o.get("onfinished");

                ArrayList<Integer> qgivers = new ArrayList<Integer>();
                ArrayList<Integer> qpreq = new ArrayList<Integer>();
                Object ob = o.get("questgivers");
                if (ob != null && ob instanceof JSONArray) {
                    for (Long aLong : (Iterable<Long>) ob) {
                        qgivers.add(aLong.intValue());
                    }
                }

                ob = o.get("prerequisites");
                if (ob != null && ob instanceof JSONArray) {
                    for (Long aLong : (Iterable<Long>) ob) {
                        qpreq.add(aLong.intValue());
                    }
                }


                QuestDef qd = new QuestDef(id, desc, ong, onf, qgivers, null /*qrequest*/, qpreq);
                if(quests.containsKey(qd.getId())){
                    System.err.println("QUEST ID COLLISION!");
                    System.err.println(qd.toString() + " collides with " + quests.get(qd.getId()));
                } else {
                    quests.put(id, qd);
                }

            }

        } catch (Exception e){
            System.out.println(e);
        }
    }
}
