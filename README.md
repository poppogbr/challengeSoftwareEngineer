To develop this project i used the following stack:
* Java 8
* JAX-RS
* JPA
* Dozer
* Junit
* Mockito

I divided the architecture into three layers:
* Input layer with REST interfaces
* Layer with EJB of business logic
* Access layer to the DB with DAOs

The services shown are:
* Create a new task: POST - /rest/task/new
* List of open tasks for specified user: GET /rest/task/all/open/{userId}
* Details of a specific task: GET - /rest/task/detail/{uniqueId}
* Close a task: PUT - /rest/task/close/{uniqueId}

Installation:
* Make the mvn clean install command
* Recover target task-0.0.1-SNAPSHOT.war
* Download Payara Server 5.193.1 (Full) from https://www.payara.fish/software/downloads/all-downloads/
* Create a JDBC Connection Pools and define the connection to the DB
* Create a JDBC Resources called jdbc/taskList and associate the pool created above
* Deploy war
* Restart Payara
* To install the schema call the REST service http://<URL>:<PORT>/task-list/rest/installer/schema
* Start SoapUI and import the xml with rest services from soapUI Project