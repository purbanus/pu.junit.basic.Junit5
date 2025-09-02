package pu.junit.assertthat;

public class Person
{
public final String name;
public final String address;
public Person( String aName, String aAddress )
{
	super();
	name = aName;
	address = aAddress;
}
public String getName()
{
	return name;
}
public String getAddress()
{
	return address;
}
@Override
public String toString()
{
	return "Person [name=" + name + ", address=" + address + "]";
}

}
