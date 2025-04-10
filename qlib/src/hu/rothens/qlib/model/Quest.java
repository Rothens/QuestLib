package hu.rothens.qlib.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * This class holds a specific quest object.
 * Created by Rothens on 2015.03.31..
 */
@Getter
@EqualsAndHashCode(of = {"def"})
@Slf4j
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
            log.error("hash={} not found", requestHash);
        }
    }

    public boolean isRelated(QuestSubject sub, RequestType st){
        for(QuestRequest qr: def.getQuestRequest()){
            if(qr.getSubjectId() == sub.getSubjectId() && qr.getRequestType() == st) return true;
        }
        return false;
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


}
