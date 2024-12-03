package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

// Replace `YourFirstTest` and `YourSecondTest` with the actual class names of your test files.
@Suite
@SelectClasses({
    TestScrabbleModelConVertical.class, 
    TestScrabbleModelConHorizontal.class,
    TestScrabbleModel.class
})
public class AllTests {
    // This class remains empty; it's used only as a holder for the above annotations.
}
