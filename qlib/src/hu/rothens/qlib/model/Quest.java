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

    public void setRequest(int requestHash, int amt){
        Integer i = cnt.get(requestHash);
        if(i != null){
            cnt.put(requestHash, amt);
        } else {
            System.out.printf("hash=%d not found", requestHash);
        }
    }

    public boolean notify(QuestSubject sub, RequestType st, int amt){
        boolean ret = true;
        for(QuestRequest qr : def.getQuestRequest()){
            int c = cnt.get(qr.hashCode());
            if(qr.getSubjectId() == sub.getSubjectId() && qr.getRequestType() == st){
                c+= amt;
                cnt.put(qr.hashCode(), c);
            }
            if(c < qr.getCount()) ret = false;

        }

        return ret;
    }

    @Override
    public int hashCode() {
        return def.getId();
    }

    public QuestDef getDef() {
        return def;
    }

    public HashMap<Integer, Integer> getCnt() {
        return cnt;
    }
}
