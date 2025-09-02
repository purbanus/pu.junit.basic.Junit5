package pu.junit.hamcrest.text.matchers;

/**
 * Dit komt uit https://www.baeldung.com/hamcrest-text-matchers
 */
//====================================================================================================================
//BELANGRIJK
//In Eclipse kan hij de volgende twee imports niet vinden. Deze moet je dus met de hand toevoegen
//===================================================================================================================== 
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

public class HamcrestTextMatchers
{
/**
 * 3. Text Equality Matchers

We can, of course, check if two Strings are equal using the standard isEqual() matcher.
In addition, we have two matchers that are specific to String types: equalToIgnoringCase() and 
equalToIgnoringWhiteSpace().
Let’s check if two Strings are equal – ignoring case:
*/
@Test
public void whenTwoStringsAreEqual_thenCorrect() {
    String first = "hello";
    String second = "Hello";

    assertThat(first, equalToIgnoringCase(second));
}
/**
We can also check if two Strings are equal – ignoring leading and trailing whitespace:
*/
@Test
public void whenTwoStringsAreEqualWithWhiteSpace_thenCorrect() {
    String first = "hello";
    String second = "   hello   "; // Hier stond een hoofdletter H maar hij ignored gee whitespace

    assertThat(first, equalToIgnoringWhiteSpace(second));
}
/**
4. Empty Text Matchers

We can check if a String is blank, meaning it contains only whitespace, by using the blankString() and 
blankOrNullString() matchers:
*/
@Test
public void whenStringIsBlank_thenCorrect() {
    String first = "  ";
    String second = null;
    
    assertThat(first, blankString());
    assertThat(first, blankOrNullString());
    assertThat(second, not(blankString()));
    assertThat(second, is( blankOrNullString()));
}
/**
On the other hand, if we want to verify if a String is empty, we can use the emptyString() matchers:
*/
@Test
public void whenStringIsEmpty_thenCorrect() {
    String first = "";
    String second = null;

    assertThat(first, emptyString());
    assertThat(first, emptyOrNullString());
    assertThat(second, emptyOrNullString());
}
/**
Pattern Matchers

We can also check if a given text matches a regular expression using the matchesPattern() function:
*/
@Test
public void whenStringMatchPattern_thenCorrect() {
    String first = "hello";

    assertThat(first, matchesPattern("[a-z]+"));
}
/**
6. Sub-String Matchers

We can determine if a text contains another sub-text by using the containsString() function or 
containsStringIgnoringCase():
*/
@Test
public void whenVerifyStringContains_thenCorrect() {
    String first = "hello";

    assertThat(first, containsString("lo"));
    assertThat(first, containsStringIgnoringCase("EL"));
}
/**
If we expect the sub-strings to be in a specific order, we can call the stringContainsInOrder() matcher:
*/
@Test
public void whenVerifyStringContainsInOrder_thenCorrect() {
    String first = "hello";
    
    assertThat(first, stringContainsInOrder("e","l","o"));
}
/**
Next, let’s see how to check that a String starts with a given String:
*/
@Test
public void whenVerifyStringStartsWith_thenCorrect() {
    String first = "hello";

    assertThat(first, startsWith("he"));
    assertThat(first, startsWithIgnoringCase("HEL"));
}
/**
And finally, we can check if a String ends with a specified String:
*/
@Test
public void whenVerifyStringEndsWith_thenCorrect() {
    String first = "hello";

    assertThat(first, endsWith("lo"));
    assertThat(first, endsWithIgnoringCase("LO"));
}
}
