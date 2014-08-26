# Configuration


Simulations are described in XML files in the form:

~~~xml
<simulation>
	<space name="space1">...</space>
	<space name="space2">...</space>
</simulation> 
~~~

The simulator will define each space if not already defined, then it will start putting data into the space.  
The `space` element can have the following attributes:

* `name`: name of the space to simulate
* `sleep`: sleep duration in milliseconds between space operations. Default is no sleep.
* `size`: number of tuples to insert in the space. Default is infinity.
* `batchSize`: number of tuples to include in each operation. Default is 1000.
* `distributionRole`: role that the simulator should join the space as. Default is leech.
* `operation`: operation to perform on the space. Default is put.

For example the following configuration:

~~~xml
<simulation>
	<space name="People" size="3" sleep="2000" batchSize="1">
		<sequence name="ID" />
		<firstName />
		<lastName />
		<birthDate />
		<address />
		<city />
		<regex name="Phone" regex="[2-9][0-9]{2}-[0-9]{3}-[0-9]{4}" />
		<emailAddress name="Email" />
	</space>
</simulation>
~~~ 

results in a space named `People` containing 3 tuples, each put every 2 seconds:

| ID                    | FirstName                      | LastName                      | BirthDate                  | Address                      | City                      | Phone                      | Email                         |
|-----------------------|--------------------------------|-------------------------------|----------------------------|------------------------------|---------------------------|----------------------------|-------------------------------|
| 1                     | Gloria                         | Marks                         | 1971-07-11T16:00:00.000    | 568 Maplecraft Crescent      | Axson                     | 638-875-6170               | jriddle@everyma1l.net         |
| 2                     | Lee                            | Holcomb                       | 1968-02-20T16:00:00.000    | 1137 Hartford St             | Milledgeville             | 228-385-5324               | stownsend@yah00.net           |
| 3                     | Charlotte                      | Baker                         | 1967-07-23T16:00:00.000    | 974 Black Path               | Macon                     | 837-813-6375               | lostit@b1zmail.co.uk          |


## XML Schema

The XML schema for configuration files is available at [simulation.xsd](https://github.com/TIBCOSoftware/as-tools/blob/master/com.tibco.as.io.simulation/src/main/resources/simulation.xsd)

## Complete Example

A example that demonstrates all configuration elements is available at [all.xml](https://github.com/TIBCOSoftware/as-tools/blob/master/as-simulator/src/test/resources/all.xml)
