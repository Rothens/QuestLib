package hu.rothens.qlib.model;

/**
 * This class is for keeping the requested thing manageable
 * Created by Rothens on 2015.03.31..
 */
public class QuestRequest {
    private final Integer subjectId;
    private final SubjectType requestType;
    private final int count;

    public QuestRequest(Integer subjectId, SubjectType requestType, int count) {
        this.subjectId = subjectId;
        this.requestType = requestType;
        this.count = count;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public SubjectType getRequestType() {
        return requestType;
    }

    public int getCount() {
        return count;
    }
}
