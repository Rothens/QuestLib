package hu.rothens.qlib.model;

import hu.rothens.qlib.model.QuestRequest;
import hu.rothens.qlib.model.RequestType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuestRequestTest {
    @Test
    @DisplayName("QuestRequest equals method should compare subjectId and requestType only")
    void testEquals() {
        QuestRequest request1 = new QuestRequest(1, RequestType.KILL, 5);
        QuestRequest request2 = new QuestRequest(1, RequestType.KILL, 10);
        QuestRequest request3 = new QuestRequest(2, RequestType.KILL, 5);
        QuestRequest request4 = new QuestRequest(1, RequestType.GATHER, 5);

        assertEquals(request1, request2, "QuestRequests with same subject and type should be equal");
        assertNotEquals(request1, request3, "QuestRequests with different subjects should not be equal");
        assertNotEquals(request1, request4, "QuestRequests with different types should not be equal");
    }
}
