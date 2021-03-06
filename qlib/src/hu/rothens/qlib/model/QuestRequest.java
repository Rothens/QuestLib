package hu.rothens.qlib.model;

/**
 * This class is for keeping the requested thing manageable
 * Created by Rothens on 2015.03.31..
 */
public class QuestRequest {
    private final Integer subjectId;
    private final RequestType requestType;
    private final int count;

    public QuestRequest(Integer subjectId, RequestType requestType, int count) {
        this.subjectId = subjectId;
        this.requestType = requestType;
        this.count = count;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestRequest that = (QuestRequest) o;

        return requestType == that.requestType && subjectId.equals(that.subjectId);

    }

    @Override
    public int hashCode() {
        return 31 * subjectId*count + requestType.ordinal();
    }
}
