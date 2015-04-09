package hu.rothens.qlib.tools;

import hu.rothens.qlib.model.QuestUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Rothens on 2015.04.08..
 */
public final class SQLiteManager implements UDBManager {
    private final String dbName;

    public SQLiteManager(String dbName) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC not found!");
        }
        this.dbName = dbName;
        init();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:sqlite:%s.db", dbName));
    }

    private void init(){
        try(
                Connection conn = getConnection();
                PreparedStatement fin = conn.prepareStatement("CREATE TABLE IF NOT EXISTS FINISHED (" +
                        "qid INTEGER NOT NULL," +
                        "uid INTEGER NOT NULL," +
                        "finishedAt LONG," +
                        "PRIMARY KEY (qid, uid)); ");
                PreparedStatement ipr = conn.prepareStatement("CREATE TABLE IF NOT EXISTS PROGRESS (" +
                        "qid INTEGER NOT NULL," +
                        "uid INTEGER NOT NULL," +
                        "rid INTEGER NOT NULL," +
                        "cnt INTEGER DEFAULT 0" +
                        "PRIMARY KEY (qid, uid, rid)); ");
        ){
            fin.execute();
            ipr.execute();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void saveUserData(QuestUser qu) {

    }

    @Override
    public QuestUser getUserData(int id) {
        return null;
    }

    @Override
    public ArrayList<QuestUser> getAllUserData() {
        return null;
    }
}
