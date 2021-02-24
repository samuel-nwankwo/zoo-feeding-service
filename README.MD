# Feeding-Time at the Zoo

This project consists of service that will allow zoo-keepers to track their feeding operations by registering a group 
of animals and, the foods they ate during an event.
The project uses the model illustrated 
[here](http://www.databaseanswers.org/data_models/5_minute_tutorials/feeding_time_at_the_zoo.htm). 
This project uses of Springboot, Hibernate, HSQLDB and, Swagger-UI.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and 
testing purposes.

### Prerequisites
Before you start, make sure you have git installed on your machine.
You can get the latest version of git [here](https://git-scm.com/downloads).

In addition, you will also need Java 11 installed on your machine you can find the downloadable files 
[here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

For windows, you will need to set the JAVA_HOME environment variable to the Java 11 installation folder on your system.
1. You can get to your environment variables by typing "edit the system environment variables" and selecting the first option.
2. In the bottom right corner of the screen select Environment Variables.
3. Then in the "User variables for myusername", Select new if you do not already have a JAVA_HOME environment variable 
   if you do then select edit.
4. The Variable name should be set to JAVA_HOME. 
5. The Variable value should be the path to your Java 11 installation.
   For example C:\Program Files\Java\jdk11.0
Then you are all set!


### Installing
1. Open cmd prompt or shell and navigate to the directory where you would like to keep the project.
For example cd: C:\Users\myuser\myproject.
2. Clone the repository using the git command git clone https://github.com/samuel-nwankwo/zoo-feeding-service.git.
3. Navigate to the project directory using the command cd zoo-feeding-service.
You should now be in C:\Users\myuser\myproject\zoo-feeding-service.
4. Run mvnw install
5. mvnw spring-boot:run
6. Go to [localhost](http://localhost:8080)
you should see a list of already made events in the json format.
7. Now go to http://localhost:8080/swagger-ui.html.
You will find api endpoints available here
8. Select the event controller.
9. Select getAllEvents. 
10. Press the "try it out" button in the right-hand corner
11. Press execute. 

You will see the same list of all the events.
Now you can try to make new events on your own.


### Running Tests

To run tests use the command mvnw test.


### Built With
[Maven](https://maven.apache.org/) - Dependency Management
[Swagger-UI]() - Visual Api Documentation
[SpringBoot](https://spring.io/projects/spring-boot) - Initial Starter Dependencies 
[Spring Data Jpa](https://spring.io/projects/spring-data-jpa) - Data Access
[HSQLDB]() - Embedded Database
[Lombok]() - annotation processing
### Authors
Samuel Nwankwo

### Acknowledgments
[Matt Raible](https://developer.okta.com/blog/2018/07/19/simple-crud-react-and-spring-boot)
[Database Answers](http://www.databaseanswers.org/data_models/5_minute_tutorials/feeding_time_at_the_zoo.htm)
