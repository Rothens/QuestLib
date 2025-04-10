package hu.rothens.qlib;

import hu.rothens.qlib.model.QuestRequestTest;
import hu.rothens.qlib.tools.JsonLoaderTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    QuestRequestTest.class,
        JsonLoaderTest.class,
        QuestManagerTest.class
})
public class QLibTestSuite {
}
