package pu.junit.parameterized.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import pu.junit.assertthat.Person;

public class ParameterizedTests
{
@BeforeAll
public static void beforeAll()
{
//	System.out.println( "ClassPath:" );
//	StringHelper.printClassPath();
}
/**
 * 3. First Impression

Let’s say we have an existing utility function, and we’d like to be confident about its behavior:
Zie Numbers class
 * Parameterized tests are like other tests except that we add the @ParameterizedTest annotation:

JUnit 5 test runner executes this below test — and consequently, the isOdd method — six times. And each time, it assigns 
a different value from the @ValueSource array to the number method parameter.
So, this example shows us two things we need for a parameterized test:
    a source of arguments, in this case, an int array
    a way to access them, in this case, the number parameter
There is still another aspect not evident with this example, so we’ll keep looking.
 */
@ParameterizedTest
@ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE}) // six numbers
void isOdd_ShouldReturnTrueForOddNumbers(int number)
{
    assertTrue(Numbers.isOdd(number));
}
/**
 * 4. Argument Sources

As we should know by now, a parameterized test executes the same test multiple times with different arguments.
And we can hopefully do more than just numbers, so let’s explore.

4.1. Simple Values

With the @ValueSource annotation, we can pass an array of literal values to the test method. Zie Strings class
We expect this method to return true for null for blank strings. So, we can write a parameterized test to assert this behavior:
As we can see, JUnit will run this test two times and each time assigns one argument from the array to the method parameter.
*/
@ParameterizedTest
@ValueSource( strings = { "", "  "} )
void isBlank_ShouldReturnTrueForNullOrBlankStrings( String input )
{
	assertTrue( Strings.isBlank( input ) );
}
/**
 * One of the limitations of value sources is that they only support these types:
    short (with the shorts attribute)
    byte (bytes attribute)
    int (ints attribute)
    long (longs attribute)
    float (floats attribute)
    double (doubles attribute)
    char (chars attribute)
    java.lang.String (strings attribute)
    java.lang.Class (classes attribute)
Also, we can only pass one argument to the test method each time.
Before going any further, note that we didn’t pass null as an argument. That’s another limitation — we can’t pass null through a @ValueSource, even for String and Class.

4.2. Null and Empty Values
As of JUnit 5.4, we can pass a single null value to a parameterized test method using @NullSource:
Since primitive data types can’t accept null values, we can’t use the @NullSource for primitive arguments.
*/
@ParameterizedTest
@NullSource
void isBlank_ShouldReturnTrueForNullInputs(String input)
{
    assertTrue(Strings.isBlank(input));
}
/**
 * Quite similarly, we can pass empty values using the @EmptySource annotation:
@EmptySource passes a single empty argument to the annotated method.
For String arguments, the passed value would be as simple as an empty String. Moreover, this parameter source can provide 
empty values for Collection types and arrays.
*/
@ParameterizedTest
@EmptySource
void isBlank_ShouldReturnTrueForEmptyStrings(String input)
{
    assertTrue(Strings.isBlank(input));
}
/**
 * To pass both null and empty values, we can use the composed @NullAndEmptySource annotation:
As with the @EmptySource, the composed annotation works for Strings, Collections, and arrays.
*/
@ParameterizedTest
@NullAndEmptySource
void isBlank_ShouldReturnTrueForNullAndEmptyStrings(String input) 
{
    assertTrue(Strings.isBlank(input));
}
/**
To pass a few more empty string variations to the parameterized test, we can combine @ValueSource, @NullSource, and @EmptySource:
*/
@ParameterizedTest
@NullAndEmptySource
@ValueSource(strings = {"  ", "\t", "\n"})
void isBlank_ShouldReturnTrueForAllTypesOfBlankStrings(String input)
{
    assertTrue(Strings.isBlank(input));
}
/**
 * 4.3. Enum

To run a test with different values from an enumeration, we can use the @EnumSource annotation.
For example, we can assert that all month numbers are between 1 and 12:
*/
@ParameterizedTest
@EnumSource(Month.class) // passing all 12 months
void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) 
{
    int monthNumber = month.getValue();
    assertTrue(monthNumber >= 1 && monthNumber <= 12);
}
/**
Or, we can filter out a few months by using the names attribute.
We could also assert the fact that April, September, June and November are 30 days long:
*/
@ParameterizedTest
@EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
void someMonths_Are30DaysLong(Month month) 
{
    final boolean isALeapYear = false;
    assertEquals(30, month.length(isALeapYear));
}
/**
By default, the names will only keep the matched enum values.
We can turn this around by setting the mode attribute to EXCLUDE:
*/
@ParameterizedTest
@EnumSource(
  value = Month.class,
  names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"},
  mode = EnumSource.Mode.EXCLUDE)
void exceptFourMonths_OthersAre31DaysLong(Month month)
{
    final boolean isALeapYear = false;
    assertEquals(31, month.length(isALeapYear));
}
/**
In addition to literal strings, we can pass a regular expression to the names attribute:
Quite similar to @ValueSource, @EnumSource is only applicable when we’re going to pass just one argument per test execution.
*/
@ParameterizedTest
@EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
void fourMonths_AreEndingWithBer(Month month)
{
    EnumSet<Month> months = EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
    assertTrue(months.contains(month));
}
/**
 * CSV Literals

Suppose we’re going to make sure that the toUpperCase() method from String generates the expected uppercase value. @ValueSource won’t be enough.
To write a parameterized test for such scenarios, we have to
    Pass an <b>input value</b> and an <b>expected value</b> to the test method
    Compute the <b>actual result with those input values</b>
    <b>Assert the actual value with the expected value</b>
So, we need argument sources capable of passing multiple arguments.
The @CsvSource is one of those sources:
The @CsvSource accepts an array of comma-separated values, and each array entry corresponds to a line in a CSV file.
This source takes one array entry each time, splits it by a comma, and passes each array to the annotated test method as separate parameters.
*/
@ParameterizedTest
@CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected)
{
    String actualValue = input.toUpperCase();
    assertEquals(expected, actualValue);
}
/**
 * By default, the comma is the column separator, but we can customize it using the delimiter attribute:
Now it’s a colon-separated value, so still a CSV.
*/
@ParameterizedTest
@CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected)
{
    String actualValue = input.toLowerCase();
    assertEquals(expected, actualValue);
}
/**
 * 4.5. CSV Files

Instead of passing the CSV values inside the code, we can refer to an actual CSV file.
For example, we could use a CSV file like this:
input,expected
test,TEST
tEst,TEST
Java,JAVA
We can load the CSV file and ignore the header column with @CsvFileSource:
The resources attribute represents the CSV file resources on the classpath to read. And, we can pass multiple files to it.
The numLinesToSkip attribute represents the number of lines to skip when reading the CSV files. By default, @CsvFileSource
does not skip any lines, but this feature is usually useful for skipping the header lines as we did here.
Just like the simple @CsvSource, the delimiter is customizable with the delimiter attribute.
In addition to the column separator, we have these capabilities:
    The line separator can be customized using the lineSeparator attribute — a newline is the default value.
    The file encoding is customizable using the encoding attribute — UTF-8 is the default value.
*/
@ParameterizedTest
@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
void toUpperCase_ShouldGenerateTheExpectedUppercaseValueCSVFile(String input, String expected)
{
    String actualValue = input.toUpperCase();
    assertEquals(expected, actualValue);
}
/**
 * 4.6. Method

The argument sources we’ve covered so far are somewhat simple and share one limitation. It’s hard or impossible to pass complex objects using them.
One approach to providing more complex arguments is to use a method as an argument source.
Let’s test the isBlank method with a @MethodSource:
*/
@ParameterizedTest
@MethodSource("provideStringsForIsBlank")
void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected)
{
    assertEquals(expected, Strings.isBlank(input));
}
/**
The name we supply to @MethodSource needs to match an existing method.
So, let’s next write provideStringsForIsBlank, a static method that returns a Stream of Arguments:
Here we’re returning a stream of arguments, but it’s not a strict requirement. For example, we can return any other 
collection-like interfaces like List. 
*/
private static Stream<Arguments> provideStringsForIsBlank() {
    return Stream.of(
      Arguments.of(null, true),
      Arguments.of("", true),
      Arguments.of("  ", true),
      Arguments.of("not blank", false)
    );
}
@ParameterizedTest
@MethodSource("providePersons")
void shouldReturnRightName( Person aPerson, String aExpectedName )
{
    assertEquals(aExpectedName, aPerson.getName() );
}
private static Stream<Arguments> providePersons() 
{
    return Stream.of(
          Arguments.of( new Person( "Peter", "Amsterdam" ), "Peter")
        , Arguments.of( new Person( "Marjo", "Amsterdam" ), "Marjo")
    );
}
@ParameterizedTest
@MethodSource("providePersonsWithNameAndAddress")
void shouldReturnRightNameAndAddresds( Person aPerson, String aExpectedName, String aExpectedAddress )
{
    assertEquals(aExpectedName , aPerson.getName() );
    assertEquals(aExpectedAddress , aPerson.getAddress() );
}
private static Stream<Arguments> providePersonsWithNameAndAddress() 
{
    return Stream.of(
          Arguments.of( new Person( "Peter", "Amsterdam" ), "Peter", "Amsterdam" )
        , Arguments.of( new Person( "Marjo", "Jordaan" ), "Marjo", "Jordaan")
    );
}
/**
 * If we’re going to provide just one argument per test invocation, then it’s not necessary to use the Arguments abstraction:
When we don’t provide a name for the @MethodSource, JUnit will search for a source method with the same name as the test method.
*/
@ParameterizedTest
@MethodSource // hmm, no method name ...
void isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument( String input )
{
	assertTrue( Strings.isBlank( input ) );
}

private static Stream<String> isBlank_ShouldReturnTrueForNullOrBlankStringsOneArgument()
{
	return Stream.of( null, "", "  " );
}
/**
 * Sometimes, it’s useful to share arguments between different test classes. In these cases, we can refer to a source method outside of the current class by its fully qualified name:
Using the FQN#methodName format, we can refer to an external static method.
*/
//class StringsUnitTest {
    @ParameterizedTest
    @MethodSource( "pu.junit.parameterized.tests.StringParams#blankStrings")
    void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input)
    {
        assertTrue(Strings.isBlank(input));
    }
//}
/**
 * 4.7. Field

Using a method as the argument source proved to be a useful way to supply the test data. Consequently, starting with JUnit 5.11, we can now use a similar feature with static fields, through the experimental annotation @FieldSource:
As we can see, the annotation points to a static field referencing the test data, which can be represented as a Collection, 
an Iterable, an object array, or a Supplier<Stream>. After that, the parameterized test will be executed for each test input. 
*/
static List<String> cities = Arrays.asList("Madrid", "Rome", "Paris", "London");

@ParameterizedTest
@FieldSource("cities")
void isBlank_ShouldReturnFalseWhenTheArgHasAtLEastOneCharacter(String arg) 
{
    assertFalse(Strings.isBlank(arg));
}
/**
 * Similar to @MethodSource, if the name of the static field matches the name of the test, the value of the annotation can be omitted:
 */
static String[] isEmpty_ShouldReturnFalseWhenTheArgHasAtLEastOneCharacter = { "Spain", "Italy", "France", "England" };

@ParameterizedTest
@FieldSource
void isEmpty_ShouldReturnFalseWhenTheArgHasAtLEastOneCharacter(String arg)
{
    assertFalse(arg.isEmpty());
}
/**
 * 4.8. Custom Argument Provider

Another advanced approach to pass test arguments is to use a custom implementation of an interface called ArgumentsProvider:
Zie BlankStringsArgumentProvider class
Then we can annotate our test with the @ArgumentsSource annotation to use this custom provider:
*/@ParameterizedTest
@ArgumentsSource(BlankStringsArgumentsProvider.class)
void isBlank_ShouldReturnTrueForNullOrBlankStringsArgProvider(String input) {
    assertTrue(Strings.isBlank(input));
}
/**
Let’s make the custom provider a more pleasant API to use with a custom annotation.
4.9. Custom Annotation

Suppose we want to load the test arguments from a static variable:
*/
static Stream<Arguments> arguments = Stream.of(
  Arguments.of(null, true), // null strings should be considered blank
  Arguments.of("", true),
  Arguments.of("  ", true),
  Arguments.of("not blank", false)
);
@ParameterizedTest
@VariableSource("arguments")
void isBlank_ShouldReturnTrueForNullOrBlankStringsVariableSource( String input, boolean expected) 
{
    assertEquals(expected, Strings.isBlank(input));
}
/**

Actually, JUnit 5 does not provide @VariableSource. However, we can roll our own solution.

First, we can create an annotation:
Zie VariableSource interface
Then we need to somehow consume the annotation details and provide test arguments. JUnit 5 provides two abstractions to achieve those:
    AnnotationConsumer to consume the annotation details
    ArgumentsProvider to provide test arguments
So, we next need to make the VariableArgumentsProvider class read from the specified static variable and return its value as test arguments:
Zie VariableArgumentsProvider class

And it works like a charm. 
*/
/**
 * /**
5. Repeatable Argument Source Annotations

In the previous section, we used various annotations to supply arguments for our parameterized tests. Starting with JUnit version 5.11, 
most of these annotations were improved and became repeatable. As a result, we can annotate a parameterized test multiple times 
with the same argument source annotation.
For example, we can use @MethodSource twice to execute the test with all the elements supplied by two different methods:
As we can see, this can be a convenient way of running the test with data coming from different sources. Here are all the 
argument source annotations supporting this feature:
    @ValueSource
    @EnumSource
    @MethodSource
    @FieldSource
    @CsvSource
    @CsvFileSource
    @ArgumentsSource
*/
static List<String> asia() {
    return Arrays.asList("Japan", "India", "Thailand");
}

static List<String> europe() {
    return Arrays.asList("Spain", "Italy", "England");
}

@ParameterizedTest
@MethodSource("asia")
@MethodSource("europe")
void whenStringIsLargerThanThreeCharacters_thenReturnTrue(String country)
{
    assertTrue(country.length() > 3);
}
/**
 * 6. Argument Conversion

Now we know how to use various argument source annotations to supply primitive test data to our tests. Argument converters 
are a convenient way of mapping the primitive arguments of a parameterized test to more complex data structures.

6.1. Implicit Conversion
Let’s re-write one of those @EnumTests with a @CsvSource:
*/
@ParameterizedTest
@CsvSource({"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"}) // Passing strings
void someMonths_Are30DaysLongCsv(Month month) 
{
    final boolean isALeapYear = false;
    assertEquals(30, month.length(isALeapYear));
}
/**
This seems like it shouldn’t work, but it somehow does.
JUnit 5 converts the String arguments to the specified enum type. To support use cases like this, JUnit Jupiter provides several 
built-in implicit type converters.
The conversion process depends on the declared type of each method parameter. The implicit conversion can convert the String instances 
to types such as the following:
    UUID 
    Locale
    LocalDate, LocalTime, LocalDateTime, Year, Month, etc.
    File and Path
    URL and URI
    Enum subclasses

6.2. Explicit Conversion

We sometimes need to provide a custom and explicit converter for arguments.
Suppose we want to convert strings with the yyyy/mm/dd format to LocalDate instances.
First, we need to implement the ArgumentConverter interface:
Zie SlashyDateConverter class
Then we should refer to the converter via the @ConvertWith annotation:
*/
@ParameterizedTest
@CsvSource({"2018/12/25,2018", "2019/02/11,2019"})
void getYear_ShouldWorkAsExpected( @ConvertWith(SlashyDateConverter.class) LocalDate date, int expected )
{
    assertEquals(expected, date.getYear());
}
/**
 * 7. Argument Accessor

By default, each argument provided to a parameterized test corresponds to a single method parameter. Consequently, when passing 
a handful of arguments via an argument source, the test method signature gets very large and messy.
One approach to address this issue is to encapsulate all passed arguments into an instance of ArgumentsAccessor and retrieve 
arguments by index and type.
Zie Person class:
To test the fullName() method, we’ll pass four arguments: firstName, middleName, lastName, and the expected fullName. We can use 
the ArgumentsAccessor to retrieve the test arguments instead of declaring them as method parameters:
*/
@ParameterizedTest
@CsvSource({"Isaac,,Newton,Isaac Newton", "Charles,Robert,Darwin,Charles Robert Darwin"})
void fullName_ShouldGenerateTheExpectedFullName(ArgumentsAccessor argumentsAccessor) {
    String firstName = argumentsAccessor.getString(0);
    String middleName = (String) argumentsAccessor.get(1);
    String lastName = argumentsAccessor.get(2, String.class);
    String expectedFullName = argumentsAccessor.getString(3);

    Person2 person = new Person2(firstName, middleName, lastName);
    assertEquals(expectedFullName, person.fullName());
}
/**
Here, we’re encapsulating all passed arguments into an ArgumentsAccessor instance and then, in the test method body, retrieving each passed argument with its index. In addition to just being an accessor, type conversion is supported through get* methods:
    getString(index) retrieves an element at a specific index and converts it to String — the same is true for primitive types.
    get(index) simply retrieves an element at a specific index as an Object.
    get(index, type) retrieves an element at a specific index and converts it to the given type.
*/
/**
 * 8. Argument Aggregator

Using the ArgumentsAccessor abstraction directly may make the test code less readable or reusable. In order to address these issues, 
we can write a custom and reusable aggregator.
To do that, we implement the ArgumentsAggregator interface:
Zie PersonAggregator class
And then we reference it via the @AggregateWith annotation:
The PersonAggregator takes the last three arguments and instantiates a Person class out of them.
*/
@ParameterizedTest
@CsvSource({"Isaac Newton,Isaac,,Newton", "Charles Robert Darwin,Charles,Robert,Darwin"})
void fullName_ShouldGenerateTheExpectedFullName(
  String expectedFullName,
  @AggregateWith(PersonAggregator.class) Person2 person) 
{
    assertEquals(expectedFullName, person.fullName());
}
/**
 * 9. Customizing Display Names

By default, the display name for a parameterized test contains an invocation index along with a String representation of 
all passed arguments:
├─ someMonths_Are30DaysLongCsv(Month)
│     │  ├─ [1] APRIL
│     │  ├─ [2] JUNE
│     │  ├─ [3] SEPTEMBER
│     │  └─ [4] NOVEMBER
However, we can customize this display via the name attribute of the @ParameterizedTest annotation:
*/
@ParameterizedTest(name = "{index} {0} is 30 days long")
@EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
void someMonths_Are30DaysLongAgain(Month month)
{
    final boolean isALeapYear = false;
    assertEquals(30, month.length(isALeapYear));
}
/**
April is 30 days long surely is a more readable display name:
├─ someMonths_Are30DaysLong(Month)
│     │  ├─ 1 APRIL is 30 days long
│     │  ├─ 2 JUNE is 30 days long
│     │  ├─ 3 SEPTEMBER is 30 days long
│     │  └─ 4 NOVEMBER is 30 days long
The following placeholders are available when customizing the display name:

    {displayName} will be replaced with the display name of the method. In case the @Display annotation is provided, then the value is provided.
    {index} will be replaced with the invocation index. Simply put, the invocation index for the first execution is 1, for the second is 2, and so on.
    {arguments} is a placeholder for the complete, comma-separated list of arguments’ values.
    {argumentsWithNames} is a placeholder for named argument. These arguments are built with the structure
    arguments(named(NAME, ARG), …). This prints the given name and the actual parameter name.
    {argumentSetName} is a placeholder for the first parameter (name of the set) provided in the factory method argumentSet.
    {argumentSetNameOrArgumentsWithNames} is a placeholder for the first parameter provided in the factory method argumentSet.
    {0}, {1}, … are placeholders for individual arguments.

 */
/**
 * 9.1. Using the arguments(…ARGS) Factory

For our example, we’ll need some test classes. Let’s consider the Country and CountryUtil classes:
For the following example, we’ll display the arguments, then a phrase containing the name of the person and the domain they 
worked on:
*/
@DisplayName("Big Countries - Simple")
@ParameterizedTest(name = "[{arguments}]: ''{0}'' with population ''{1}''")
@FieldSource("simpleArguments")
public void givenBigCountryData_usingCountryUtil_isBigCountry_shouldReturnTrue_simple( String name, long population)
{
    Country country = new Country(name, population);
    boolean isBigCountry = CountryUtil.isBigCountry(country);
    assertTrue(isBigCountry, "The country provided is not considered big!");
}
/** Hier stond oorspronkelijk */
//arguments("India", 1_450_935_791),
//arguments("China", 1_419_321_278),
//arguments("United States", 345_426_571)
//maar wat die arguments nou betekent....
@SuppressWarnings( "unused" )
private static List<Arguments> simpleArguments = Arrays.asList(
        Arguments.of("India", 1_450_935_791),
        Arguments.of("China", 1_419_321_278),
        Arguments.of("United States", 345_426_571)
);
/**
 * Als ik hier 9.2 in kopieer, krijg ik hele eigenaardige syntaxfouten. Die blijven bestaan als ik het weer
 * verwijder!
 * Aangezien het me toch niet zo interessant leek, doen we dat maar niet.
 * Laat 9.3, argumentSets, ook maar zitter
 */

}
