package pu.junit.assertthat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
/**
 * We need only to implement the matchSafely method which checks that the target is indeed a positive integer and the describeTo 
 * method which produces a failure message in case the test does not pass.
 */
public class IsPositiveInteger extends TypeSafeMatcher<Integer>
{
@Override
public void describeTo(Description description) {
    description.appendText("a positive integer");
}

//@Factory bestaat nioet
public static Matcher<Integer> isAPositiveInteger() {
    return new IsPositiveInteger();
}

@Override
protected boolean matchesSafely(Integer integer) {
    return integer > 0;
}

}
