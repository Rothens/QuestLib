package hu.rothens.qlib.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * This class is for keeping the requested thing manageable
 * Created by Rothens on 2015.03.31..
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"subjectId", "requestType"})
@ToString
public class QuestRequest {
    private final Integer subjectId;
    private final RequestType requestType;
    private final int count;
}
