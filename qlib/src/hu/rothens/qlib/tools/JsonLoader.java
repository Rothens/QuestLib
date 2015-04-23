package hu.rothens.qlib.tools;

import hu.rothens.qlib.model.QuestDef;
import hu.rothens.qlib.model.QuestRequest;
import hu.rothens.qlib.model.RequestType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Rothens on 2015.03.31..
 */
public class JsonLoader implements QDBLoader {
    private final String file;
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
                HashSet<QuestRequest> reqs = new HashSet<>();
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

                ob = o.get("required");
                if(ob != null && ob instanceof JSONArray){
                    JSONArray ja = (JSONArray) ob;
                    if(ja.isEmpty()) {
                        System.out.println("No requirements for quest: " + id);
                        continue;
                    }
                    for(JSONObject rq : (Iterable<JSONObject>) ob){
                        int i =((Long) rq.get("id")).intValue();
                        RequestType rt = RequestType.values()[((Long) rq.get("type")).intValue()];
                        int c = ((Long) rq.get("count")).intValue();
                        reqs.add(new QuestRequest(i, rt, c));
                    }
                } else {
                    System.out.println("No requirements for quest: " + id);
                    continue;
                }


                QuestDef qd = new QuestDef(id, desc, ong, onf, qgivers, reqs, qpreq);
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
