package hu.rothens.qlib.tools;

import hu.rothens.qlib.model.QuestUser;

import java.util.ArrayList;

/**
 * Created by Rothens on 2015.04.08..
 */
public interface UDBManager {
    public void saveUserData(QuestUser qu);
    public QuestUser getUserData(int id);
    public ArrayList<QuestUser> getAllUserData();
}
