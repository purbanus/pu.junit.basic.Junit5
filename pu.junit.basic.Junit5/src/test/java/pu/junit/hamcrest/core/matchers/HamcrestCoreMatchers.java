package pu.junit.hamcrest.core.matchers;

//====================================================================================================================
//BELANGRIJK
//In Eclipse kan hij de volgende twee imports niet vinden. Deze moet je dus met de hand toevoegen
//===================================================================================================================== 
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Dit komt uit https://www.baeldung.com/hamcrest-core-matchers
 */
public class HamcrestCoreMatchers
{
/**
3.1. is(T) and is(Matcher<T>)

The is(T) takes an object as a parameter to check equality and is(Matcher<T>) takes another matcher 
allowing equality statement to be more expressive.
We can use this with almost all of the methods:
*/
@Test
public void testIs()
{
String testString = "hamcrest core";

assertThat(testString, is("hamcrest core"));
assertThat(testString, is(equalTo("hamcrest core")));
}
/**
3.2. equalTo(T)

The equalTo(T) takes an object as a parameter and checks its equality against another object. This is frequently used with is(Matcher<T>):
*/
@Test
public void testEquals()
{
String actualString = "equalTo match";
List<String> actualList = List.of("equalTo", "match");

assertThat(actualString, is(equalTo("equalTo match")));
assertThat(actualList, is(equalTo(List.of("equalTo", "match"))));
}
/**
We can also use equalToObject(Object operand) – which checks equality and doesn’t enforce that 
two objects should of same static type:
*/
@Test
public void testEqualToObject()
{
Object original = 100;
assertThat(original, equalToObject(100));
}
/**
3.3. not(T) and not(Matcher<T>)

The not(T) and not(Matcher<T>) are used to check non-equality of given objects. First takes an object as an 
argument and second take another matcher:
*/
@Test
public void testNot()
{
String testString = "troy kingdom";

assertThat(testString, not("german kingdom"));
assertThat(testString, is(not(equalTo("german kingdom"))));
assertThat(testString, is(not(instanceOf(Integer.class))));
}
/**
3.4. nullValue() and nullValue(Class<T>)

The nullValue() check for null value against the examined object. The nullValue(Class<T>) checks for 
nullability of given class type object:
*/
@Test
public void testNullValue()
{
Integer nullObject = null;

assertThat(nullObject, is(nullValue()));
assertThat(nullObject, is(nullValue(Integer.class)));
}
/**
3.5. notNullValue() and notNullValue(Class<T>)

These are a shortcut to frequently used is(not(nullValue)). These check for non-null-equality of 
an object or with the class type:
*/
@Test
public void testNotNullValue()
{
Integer testNumber = 123;

assertThat(testNumber, is(notNullValue()));
assertThat(testNumber, is(notNullValue(Integer.class)));
}
/**
3.6. instanceOf(Class<?>)

The instanceOf(Class<?>) matches if the examined object is an instance of the specified Class type.
To verify, this method internally calls the isIntance(Object) of Class class:
*/
@Test
public void testInstanceOf()
{
assertThat("instanceOf example", is(instanceOf(String.class)));
}
/**
3.7. isA(Class<T> type)

The isA(Class<T> type) is a shortcut to the above instanceOf(Class<?>). It takes the exact same 
type of argument as an instanceOf(Class<?>):
*/
@Test
public void testIsA()
{
assertThat("Drogon is biggest dragon", isA(String.class));
}
/**
3.8. sameInstance()

The sameInstance() matches if two reference variables point to the same object in a heap:
*/
@Test
public void testSameInstance()
{
String string1 = "Viseron";
String string2 = string1;

assertThat(string1, is(sameInstance(string2)));
}
/**
3.9. any(Class<T>)

The any(Class<T>)checks if the class is of the same type as actual object:
*/
@Test
public void testIsAny()
{
	// Beetje rare naam, any. Zou verwachten dat je dan een paar opties opgeeft en dattie dan een 
	// van de opties matcht, maar dat doet anyOf
assertThat("test string", is(any(String.class)));
assertThat("test string", is(any(Object.class)));
}
/**
 * 3.10. allOf(Matcher<? extends T>…) and anyOf(Matcher<? extends T>…)

We can use allOf(Matcher<? extends T>…) to assert if actual object matches against all of the 
specified conditions:
*/
@Test
public void testAllOf()
{
String testString = "Achilles is powerful";
assertThat(testString, allOf(startsWith("Achi"), endsWith("ul"), containsString("Achilles")));
}
/**
The anyOf(Matcher<? extends T>…) behaves like allOf(Matcher<? extends T>… ) but matches if the 
examined object matches any of the specified conditions:
*/
@Test
public void testAnyOf()
{
String testString = "Hector killed Achilles";
assertThat(testString, anyOf(startsWith("Hec"), containsString("baeldung")));
}
/**
3.11. hasItem(T) and hasItem(Matcher<? extends T>)

These match if the examined Iterable collection matches with given object or 
matcher inside hasItem() or hasItem(Matcher<? extends T>).
Let’s understand how this works:
*/
@Test
public void testHasItem()
{
List<String> list = List.of("java", "spring", "baeldung");

assertThat(list, hasItem("java"));
assertThat(list, hasItem(isA(String.class)));
}
/**
Similarly, we can also assert against more than one items using hasItems(T…) and hasItems(Matcher<? 
extends T>…):
*/
@Test
public void testHasItems()
{
List<String> list = List.of("java", "spring", "baeldung");

assertThat(list, hasItems("java", "baeldung"));
assertThat(list, hasItems(isA(String.class), endsWith("ing")));
}
/**
3.12. both(Matcher<? extends T>) and either(Matcher<? extends T>)

As the name suggests, the both(Matcher<? extends T>) matches when both of the specified conditions match the examined object:
*/
@Test
public void testBoth()
{
String testString = "daenerys targaryen";
assertThat(testString, both(startsWith("daene")).and(containsString("yen")));
}
/**
and either(Matcher<? extends T>)matches when either of the specified conditions matches the examined object:
*/
@Test
public void testEither()
{
String testString = "daenerys targaryen";
assertThat(testString, either(startsWith("tar")).or(containsString("targaryen")));
}
/**
4. String Comparison

We can use containsString(String) or containsStringIgnoringCase(String) to assert if the 
actual string contains test string:
*/
@Test
public void testContainsString()
{
String testString = "Rhaegar Targaryen";
assertThat(testString, containsString("aegar"));
assertThat(testString, containsStringIgnoringCase("AEGAR"));
}
/**
Or startsWith(String) and startsWithIgnoringCase(String) to assert if the actual string starts with 
test string:
*/
@Test
public void testStartsWith()
{
String testString = "Rhaegar Targaryen";
assertThat(testString, startsWith("Rhae"));
assertThat(testString, startsWithIgnoringCase("rhae"));
}
/**
We can also use endsWith(String) or endsWithIgnoringCase(String) to assert if the actual string ends with 
test string:
*/
@Test
public void testEndsWith()
{
String testString = "Rhaegar Targaryen";
assertThat(testString, endsWith("aryen"));
assertThat(testString, endsWithIgnoringCase("ARYEN"));
}

}
