package pu.junit.basicJunit5;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * 7. Test Suites
To continue with the new features of JUnit 5, we’ll explore the concept of aggregating multiple test classes in a test suite, 
so that we can run those together. JUnit 5 provides two annotations, @SelectPackages and @SelectClasses, to create test suites.
Keep in mind that at this early stage, most IDEs don’t support these features.
@SelectPackages is used to specify the names of packages to be selected when running a test suite. In our example, it will run all tests. The second annotation, @SelectClasses, is used to specify the classes to be selected when running a test suite:
*/

@Suite
@SelectPackages("pu.junit.basicJunit5")
//@ExcludePackages("com.baeldung.suites")
public class AllUnitTests
{
}
