package hu.rothens.qlib.model;

/**
 * Created by Rothens on 2015.03.31..
 */
public class QuestRequest {
    private Integer subjectId;
    private SubjectType requestType;
    private int count;

    public QuestRequest(Integer subjectId, SubjectType requestType, int count) {
        this.subjectId = subjectId;
        this.requestType = requestType;
        this.count = count;
    }
}
