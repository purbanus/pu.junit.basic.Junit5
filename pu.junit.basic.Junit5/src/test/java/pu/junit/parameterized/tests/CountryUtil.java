package pu.junit.parameterized.tests;

public class CountryUtil
{

private static final long TRESHOLD = 100_000_000;

public static boolean isBigCountry( Country country )
{
	return country.population() > TRESHOLD;
}

}