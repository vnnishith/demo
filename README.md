Spring Boot User Management
===================================
This is a sample project to provide example on simple CRUD operations pertaining to User management using REST endpoints. 

## Main Technoglogies Used
* Spring Boot - 2.2.6
* MySQL-5.7
* Java 1.8 
* Hibernate
* Swagger UI
* JUnit5 along with Mockito
 

### Functionalities Provided
* User Management - Registration, Profile Retreival, Profile Deletion, Phone Addition and Removal from Profile
* Admin Management - Retrieve list of all users registered with the application.
* Swagger UI - used for documenting all services

### Authorizations
* Basic Auth - Is used to access any resource associated [CRUD] operations associated with a registered user.
* Acces Token Auth - An access token has to passed in the headers to access any resource kept under /v1/admin

### Endpoints
#### * /v1/user/register - POST [Public - No Authorization]

```java
Sample Request:
{
  "userName": "test53686",
  "password": "test123",
  "confirmPassword": "test123",
  "emailAddress": "nishithv534n@gmail.com",
  "preferredPhoneNumber": "+353123456789",
  "phoneModel": "ADNROID",
  "phoneName": "test"
}
Sample Response:
{
    "message": "success",
    "data": {
        "userId": "ef765676-4b5f-4705-9160-a48d3a186767",
        "userName": "test533686",
        "emailAddress": "nishithv5334n@gmail.com",
        "preferredPhoneNumber": "+353123456789",
        "phones": [
            {
                "phoneId": "dbfcaa1d-45a4-4e9f-95a9-6a37451ca2e2",
                "phoneName": "test",
                "phoneModel": "ANDROID",
                "phoneNumber": "+353123456789"
            }
        ]
    }
}
```

#### * /v1/user - GET [Basic Auth]
```java
Request Header:
Authorization : Basic dGVzdDUzNjY6dGVzdDEyMw==
Sample Response:
{
    "message": "success",
    "data": {
        "userId": "66e012c2-36bb-4569-af0c-c7908cd7da36",
        "userName": "test5366",
        "emailAddress": "tsamp3534n@gmail.com",
        "preferredPhoneNumber": "+353-9992501495",
        "phones": [
            {
                "phoneId": "7066b3e7-ce61-46d3-9a6f-e41704d3936e",
                "phoneName": "test",
                "phoneModel": "ANDROID",
                "phoneNumber": "+353-9992501495"
            },
            {
                "phoneId": "8066b3e7-ce61-46d3-9a6f-e41704d3936e",
                "phoneName": "test2",
                "phoneModel": "ANDROID",
                "phoneNumber": "+353-9993501495"
            }
        ]
    }
}    
```
#### * /v1/user/{userId} - DELETE [Basic Auth]
```java
Request Header:
Authorization : Basic dGVzdDUzNjY6dGVzdDEyMw==
Sample Response:
{
    "message": "success",
    "data": "Successfully deleted User"
}
```
#### * /v1/user/{userId}/preferred-phone-number - PUT [Basic Auth]
```java
Request Header:
Authorization : Basic dGVzdDUzNjY6dGVzdDEyMw==
Sample Request:
{
  "phoneNumber": "+353123456789"
}
Response: Same as /v1/user - GET
```
####* /v1/user/{userId}/phone - POST [Basic Auth]
```java
Request Header:
Authorization : Basic dGVzdDUzNjY6dGVzdDEyMw==
Sample Request:
{
	"phoneNumber" :"+353-993501495",
	"phoneName" : "test2",
	"phoneModel" : "ANDROID"
	
}
Sample Response:
{
    "message": "success",
    "data": {
        "phoneId": "27843185-ea43-4d12-ab14-080f25ca8cf7",
        "phoneName": "test2",
        "phoneModel": "ANDROID",
        "phoneNumber": "+353-993501495"
    }
}
```
#### * /v1/user/{userId}/phone - DELETE [Basic Auth]
```java
Request Header:
Authorization : Basic dGVzdDUzNjY6dGVzdDEyMw==
Sample Request:
{
  "phoneNumber": "+353123456789"
}
Sample Response:
{
    "message": "success",
    "data": "Sucessfully Removed"
}
```
#### * /v1/admin/list-users - GET [Basic Auth]
```java
Request Header:
Authorization : Access-Token dGVzdDUzNjY6dGVzdDEyMw==
Optional Parameter: pageNumber [Starting from 1]
Sample Response:
{
    "message": "success",
    "data": {
        "pageNumber": 1,
        "paginatedData": [
            {
                "userId": "66e012c2-36bb-4569-af0c-c7908cd7da36",
                "userName": "test5366",
                "emailAddress": "tsamp3534n@gmail.com",
                "preferredPhoneNumber": "+353-9992501495",
                "phones": [
                    {
                        "phoneId": "27843185-ea43-4d12-ab14-080f25ca8cf7",
                        "phoneName": "test2",
                        "phoneModel": "ANDROID",
                        "phoneNumber": "+353-993501495"
                    },
                    {
                        "phoneId": "7066b3e7-ce61-46d3-9a6f-e41704d3936e",
                        "phoneName": "test",
                        "phoneModel": "ANDROID",
                        "phoneNumber": "+353-9992501495"
                    },
                    {
                        "phoneId": "8066b3e7-ce61-46d3-9a6f-e41704d3936e",
                        "phoneName": "test2",
                        "phoneModel": "ANDROID",
                        "phoneNumber": "+353-9993501495"
                    }
                ]
            },
            {
                "userId": "ef765676-4b5f-4705-9160-a48d3a186767",
                "userName": "test533686",
                "emailAddress": "nishithv5334n@gmail.com",
                "preferredPhoneNumber": "+353123456789",
                "phones": [
                    {
                        "phoneId": "dbfcaa1d-45a4-4e9f-95a9-6a37451ca2e2",
                        "phoneName": "test",
                        "phoneModel": "ANDROID",
                        "phoneNumber": "+353123456789"
                    }
                ]
            },
            {
                "userId": "fa7ee011-2c08-478c-9313-254549fd2640",
                "userName": "test536",
                "emailAddress": "nisv353n@gmail.com",
                "preferredPhoneNumber": "+353-7992501495",
                "phones": [
                    {
                        "phoneId": "9f38a71d-f00d-4a9b-a391-344c388f63ea",
                        "phoneName": "test",
                        "phoneModel": "ANDROID",
                        "phoneNumber": "+353-8992501495"
                    }
                ]
            }
        ],
        "perPageLimit": 5,
        "totalCount": 3,
        "offset": 0
    }
}
```

### Errors
Custom error codes are written in the application and can be used to identify the type of error.
```java
Sample Error Response:
{
    "code": 1003,
    "message": "Email not unique"
}
```
### Setup and build instructions
* Setup MySQL Database and Maven.
*  Create a database guest_profile.
*  Replace the database specific configuration in application.properties [or application-production.properties based on profile]
*  The embedded server start on port 8080 by default and can be configured in application.properties. 
*  Execute the following to execute the application.
```java
mvn package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
Alternatively, the application can also be running this command
```java
mvn package
mvn spring-boot:run 
```
