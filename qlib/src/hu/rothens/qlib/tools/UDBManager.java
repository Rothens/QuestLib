package hu.rothens.qlib.tools;

import hu.rothens.qlib.model.Quest;
import hu.rothens.qlib.model.QuestUser;

import java.util.ArrayList;

/**
 * Created by Rothens on 2015.04.08..
 */
public interface UDBManager {
    public QuestUser getUserData(int id);
    public ArrayList<QuestUser> getAllUserData();
    public void finishQuest(QuestUser qu, Quest q);
    public void updateProgress(QuestUser qu, Quest q);
    public void setAvailable(QuestUser qu, int qid);
    public void acceptQuest(QuestUser qu, Quest q);
    public QuestUser newUser(int id);
    public void clear();
}
