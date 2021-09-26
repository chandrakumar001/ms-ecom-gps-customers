# ms-ecom-gps-customers

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Application URL Swagger](#Application-URL-Swagger)
* [Cloudfoundry](#Cloudfoundry)
* [Kubernetes](#Kubernetes)
* [Additional Information-Optional](#Additional-Information-Optional)

## General info
This project for Customers microservice and used lasted version spring boot, swagger(OpenAPI),Code Generator swagger.

#### Technologies

 * Java: 11
 * Spring boot/data jpa/Swagger(OpenAPI)
 * In-memory database: H2 database
 * Groovy-Spock(Junit test)
 * Karate framework(Integration-test)
 * Maven 3.6.3
 * CI/CD-Jekins Pipeline

#### Project Structure
    src
      integration-test
      main
      test
   
## Setup

#### Clone the project
   
   
        git clone https://github.com/chandrakumar001/ms-customer

#### Running Tests and Skipping Unit Tests and Integration Tests

* To skip all tests (unit and integration tests) when install the package into the local repository via command line add the parameter -DskipTests=true (The flag is honored by Surefire, Failsafe and the Compiler Plugin).

        
        cd ms-customer
        mvn clean install -DskipTests=true 

* To explicit skip unit tests just add the parameter  -Dskip.unit.tests=true.

        
        mvn test
        mvn clean install -Dskip.unit.tests=true
    
* To explicit skip integration tests via command line just add the parameter -Dskip.integration.test=true:

        
        mvn verify
        mvn verify -Dskip.unit.tests=false -Dskip.integration.test=true
        
Note:
    
        mvn test   : will run only Unit test case
        mvn verify : will run  Unit test + Intergation test case
        mvn clean install : will run Unit test + Intergation test case
                    
#### Packaging
        
  You can package by invoking the following command. 
        
        mvn package -Dmaven.test.skip=true

## Application-URL-Swagger:
  Perform all operation like create, update,delete and show all list.
  
  Local Swagger:
  <http://localhost:8080/swagger-ui.html>
   
  Cloud:
   <https://ecom-gps-customers.apps.chandran-edu.com/ecoms/gps/swagger-ui.html>

    
    Sample Output: please refer output folder

#### Database:

 To Store the data into table, I used `customer_id` primary key as UUID format
 after create any object,if would you like `update/delete/getById` we should pass primary key
  
 
 To identity the object is unique, I used `email_id` as candidate key
 
In Order to track object,I Implemented Audit model such `createdBy,creationDate,lastModifiedBy,lastModifiedDate and action(created,deleted,updated)`

##### SQL CREATE VIEW Statement
    
    In SQL, a view is a virtual table based on the result-set of an SQL statement.
    
    A view contains rows and columns, just like a real table. The fields in a view are fields from one or more real tables in the database.
    You can add SQL statements and functions to a view and present the data as if the data were coming from one single table.
    

##### Mock database data scripts:

    src/main/resources/data.sql
    src/main/resources/schema.sql

##### In-memory-Database URL:


https://ecom-gps-customers.apps.chandran-edu.com/ecoms/gps/h2-console/
    
    url: jdbc:h2:mem:testdb
    username: sa
    password: <empty>
      
POST: https://ecom-gps-customers.apps.chandran-edu.com/ecoms/gps/v1/customers

The email is unique of table and please change email id

    {
      "emailId": "chandrakumar@chandran-edu.com",
      "customerName": {
        "firstName": "chandrakumar",
        "lastName": "ovanan"
      },
      "age": "29",
      "favouriteColour": "blue"
    }

output:
    
    {
      "customerId": "ff8816c5-0c93-42de-9e59-5db52b96848e",
      "data": {
        "emailId": "chandrakumar@chandran-edu.com",
        "customerName": {
          "firstName": "chandrakumar",
          "lastName": "ovanan"
        },
        "age": "29",
        "favouriteColour": "blue"
      }
    }

GET   https://ecom-gps-customers.apps.chandran-edu.com/ecoms/gps/v1/customers/ff8816c5-0c93-42de-9e59-5db52b96848e
   
    {
      "customerId": "ff8816c5-0c93-42de-9e59-5db52b96848e",
      "data": {
        "emailId": "chandrakumar@chandran-edu.com",
        "customerName": {
          "firstName": "chandrakumar",
          "lastName": "ovanan"
        },
        "age": "29",
        "favouriteColour": "blue"
      }
    }

  
PUT  https://ecom-gps-customers.apps.chandran-edu.com/ecoms/gps/v1/customers/ff8816c5-0c93-42de-9e59-5db52b96848e

    {
      "emailId": "chandrakumar@chandran-edu.com",
      "customerName": {
        "firstName": "chandrakumar",
        "lastName": "o"
      },
      "age": "30",
      "favouriteColour": "blue"
    }
     
 output:
    
    {
      "customerId": "ff8816c5-0c93-42de-9e59-5db52b96848e",
      "data": {
        "emailId": "chandrakumar@chandran-edu.com",
        "customerName": {
          "firstName": "chandrakumar",
          "lastName": "o"
        },
        "age": "30",
        "favouriteColour": "blue"
      }
    }

Delete: https://ecom-gps-customers.mybluemix.net/v1/people/9ff7233a-9c63-4dbd-ab33-0d539aae0905
     
     refer output folder :: "delete-customer.PNG"

GET  https://ecom-gps-customers.mybluemix.net/v1/people
    show all list of customer
    
    refer output folder :: "get-customer.PNG"
    
#### Error Message
    
    src/main/resources/messages.properties
        
    
## Cloudfoundry

    ibmcloud login -a https://cloud.ibm.com -u passcode -p <passcode>
    ibmcloud target --cf
    ibmcloud cf push  -f cloudfoundry/manifest.yml  --vars-file cloudfoundry/dev-vars.yml

## Kubernetes
    
  At first time, application setup execture: 'kubectl-execute.bat'
  
  This bat file having all configuration and apply the kubernetes cluster, this will step the such like <b>deployment,service,ingress,HPA,VPA and network policy</b>
   
<b>This application will be deploy kubernetes cluster via CI/CD pipleline</b>  

refer: output folder

         kubernate-pods.png


<b>Unit-Test case report:</b>

    refer: output/unitest-case-report.PNG
    
<b>Integration-Test case report: </b>

         refer: output/cucumber-report-it-test.PNG

     
## Additional-Information-Optional
#### Jenkins CD/CD pipeline step

 - [x] A completed task Locally Download and install jenkins 
 - [x] A completed task Access the  below endpoint
 
     http://localhost:9090/
     
refer: output folder
     
     cicd-pipeline.png
     
Install Jenkins Plugin:

       `    Cucumber reports
            Github integration plugin
            Blue Ocean plugin
            Token Macro Plugin
            owasp Plugin
            Kubernetes
        '
Refer:
 
    https://www.jenkins.io/projects/blueocean/about/#:~:text=%22%22%20Blue%20Ocean%20is%20a%20new,Delivery%20(CD)%20Pipelines%20%22%22
        
####   SonarQube:
    
   Code quality for application
    
        http://localhost:9000/projects
    
##### Application Name:

     ms       --> Means Microservice
     ecom     --> Means Project Name
     gps      --> Generanl Purpose of system(Grouping the funtionalities)   
     customer --> Application Name
     
     Example: ecom-gps-customers     
  
  
##### Spring data JPA:

Soft Delete: 
    
    It means that you are flagging a record as deleted in a particular table, instead of actually being deleting the record. 
    
Hard Delete: 
    
    It means you are completely removing the record from the table        

##### Application Scaling:

Scaling Horizontally:

Incoming requests to your application are automatically load balanced across all instances of your application, and each instance handles tasks in parallel with every other instance. 

    ibmcloud cf scale ecom-gps-customers -i 2

Scaling Vertically:

Vertically scaling an application changes the disk space limit or memory limit that Cloud Foundry applies to all instances of the application


-k DISK to change the disk space limit applied to all instances of your application

-m MEMORY must be an integer followed by either an M, for megabytes, or G, for gigabytes


    ibmcloud cf scale ecom-gps-customers -k 512M
    ibmcloud cf scale ecom-gps-customers -m 1G
    
Show all apps:
        
        ibmcloud cf ecom-gps-customers
        ibmcloud cf logs ecom-gps-customers
        
##### Request tracking and logging by using below component 

logging: Clean up activity for before request and after request by using mdc approach 

Components | ORDER
-----------| -------------
logging    | 0

##### Enable  OWASP’s for this application
 
In the pom.xml file, just the remove the comment block


                    <plugin>
                           <groupId>org.owasp</groupId>
                           <artifactId>dependency-check-maven</artifactId>
                           <version>6.1.1</version>
                           <executions>
                               <execution>
                                   <goals>
                                       <goal>check</goal>
                                   </goals>
                               </execution>
                           </executions>
                       </plugin>
                       
##### Enable Sonarqube for this application

                        <plugin>
                           <groupId>org.sonarsource.scanner.maven</groupId>
                           <artifactId>sonar-maven-plugin</artifactId>
                           <version>3.3.0.603</version>
                           <executions>
                               <execution>
                                   <phase>verify</phase>
                                   <goals>
                                       <goal>sonar</goal>
                                   </goals>
                               </execution>
                           </executions>
                       </plugin>             
                
Refer URL: 
    
    https://springbootdev.com/2018/03/13/spring-data-jpa-auditing-with-createdby-createddate-lastmodifiedby-and-lastmodifieddate/
    https://piotrminkowski.com/2020/02/20/microservices-api-documentation-with-springdoc-openapi/
    https://docs.run.pivotal.io/appsman-services/autoscaler/using-autoscaler-cli.html
    