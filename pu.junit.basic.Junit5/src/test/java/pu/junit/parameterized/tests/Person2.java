package pu.junit.parameterized.tests;

class Person2 {

String firstName;
String middleName;
String lastName;

public Person2( String aFirstName, String aMiddleName, String aLastName )
{
	super();
	firstName = aFirstName;
	middleName = aMiddleName;
	lastName = aLastName;
}

public String fullName() {
    if (middleName == null || middleName.trim().isEmpty()) {
        return String.format("%s %s", firstName, lastName);
    }

    return String.format("%s %s %s", firstName, middleName, lastName);
}
}