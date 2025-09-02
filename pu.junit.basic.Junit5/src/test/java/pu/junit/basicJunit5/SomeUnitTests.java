package pu.junit.basicJunit5;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * For instance, the below class will create a suite that contains one test class. Please note that the classes 
 * don’t have to be in one single package.
 */

@Suite
@SelectClasses({BasicJunit5Tests.class})
public class SomeUnitTests {}
