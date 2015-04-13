package hu.rothens.qlib.tools;

import hu.rothens.qlib.QuestManager;
import hu.rothens.qlib.model.Quest;
import hu.rothens.qlib.model.QuestDef;
import hu.rothens.qlib.model.QuestUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Rothens on 2015.04.08..
 */
public final class SQLiteManager implements UDBManager {
    private final String dbName;
    private final QuestManager questManager;

    public SQLiteManager(String dbName, QuestManager questManager) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC not found!");
        }
        this.dbName = dbName;
        this.questManager = questManager;
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
                        "cnt INTEGER DEFAULT 0," +
                        "PRIMARY KEY (qid, uid, rid)); ");
                PreparedStatement avail = conn.prepareStatement("CREATE TABLE IF NOT EXISTS AVAILABLE (" +
                        "uid INTEGER NOT NULL," +
                        "qid INTEGER NOT NULL," +
                        "PRIMARY KEY (qid, uid)); ");
        ){
            fin.execute();
            ipr.execute();
            avail.execute();
            System.out.println("db created");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public QuestUser getUserData(int id) {
        try(
                Connection conn = getConnection();
                PreparedStatement fin = conn.prepareStatement("SELECT qid FROM FINISHED WHERE uid=?;");
                PreparedStatement ipr = conn.prepareStatement("SELECT * FROM PROGRESS WHERE uid=?;");
                PreparedStatement avail = conn.prepareStatement("SELECT qid FROM AVAILABLE WHERE uid=?;")
        ){
            fin.setInt(1, id);
            ipr.setInt(1, id);
            avail.setInt(1, id);
            HashSet<Integer> finished = new HashSet<>();
            HashSet<QuestDef> available = new HashSet<>();
            HashMap<Integer, Quest> progress = new HashMap<>();
            ResultSet rs = fin.executeQuery();
            while(rs.next()){
                finished.add(rs.getInt("qid"));
            }
            rs.close();
            rs = avail.executeQuery();
            while(rs.next()){
                available.add(questManager.getDef(rs.getInt("qid")));
            }
            rs.close();
            rs = ipr.executeQuery();
            while(rs.next()){
                int qid = rs.getInt("qid");
                int rid = rs.getInt("rid");
                int cnt = rs.getInt("cnt");
                Quest q = progress.get(qid);
                if(q == null){
                    q = new Quest(questManager.getDef(qid));
                }
                q.setRequest(rid, cnt);
                progress.put(qid, q);

            }
            rs.close();

            return new QuestUser(id,finished,progress, available, questManager);

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<QuestUser> getAllUserData() {
        HashSet<Integer> ids = new HashSet<>();
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT uid FROM AVAILABLE " +
                                "UNION SELECT uid FROM FINISHED " +
                                "UNION SELECT uid FROM PROGRESS");
                ResultSet rs = ps.executeQuery()
        ){
            while(rs.next()){
                ids.add(rs.getInt("uid"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<QuestUser> users = new ArrayList<>();
        for(int i : ids){
            users.add(getUserData(i));
        }
        return users;
    }

    @Override
    public void finishQuest(QuestUser qu, Quest q) {
        try(
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM PROGRESS WHERE qid=? AND uid=?;");
                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO FINISHED (qid, uid, finishedAt) VALUES (?,?,?);")
        ){
            ps.setInt(1, q.getDef().getId());
            ps.setInt(2, qu.getId());
            ps.execute();
            ps2.setInt(1, q.getDef().getId());
            ps2.setInt(2, qu.getId());
            ps2.setLong(3, System.currentTimeMillis());
            ps2.execute();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateProgress(QuestUser qu, Quest q) {
        try(
                Connection conn = getConnection();
                PreparedStatement del = conn.prepareStatement("DELETE FROM PROGRESS WHERE qid=? AND uid=?;");
                PreparedStatement upd = conn.prepareStatement("INSERT INTO PROGRESS (qid, uid, rid, cnt) VALUES (?,?,?,?);")
        ){
            del.setInt(1, q.getDef().getId());
            del.setInt(2, qu.getId());
            del.execute();

            HashMap<Integer, Integer> cnt = q.getCnt();
            upd.setInt(1, q.getDef().getId());
            upd.setInt(2, qu.getId());
            for(int i : cnt.keySet()){
                upd.setInt(3, i);
                upd.setInt(4, cnt.get(i));
                upd.execute();
            }


        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void setAvailable(QuestUser qu, int qid) {
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO AVAILABLE (uid, qid) VALUES (?, ?);")
        ){
            ps.setInt(1, qu.getId());
            ps.setInt(2, qid);
            ps.execute();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void clear() {
        try(
                Connection conn = getConnection();
                PreparedStatement ps1 = conn.prepareStatement("DROP TABLE AVAILABLE CASCADE;");
                PreparedStatement ps2 = conn.prepareStatement("DROP TABLE FINISHED CASCADE;");
                PreparedStatement ps3 = conn.prepareStatement("DROP TABLE PROGRESS CASCADE;")

        ) {
            ps1.execute();
            ps2.execute();
            ps3.execute();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
