# user-service
###### This repository contains the source code of the user-service, a simple spring-boot application that: insert, retrieve, update and delete a user. A valid user is an adult person who reside in France.

# Developed with
* SpringBoot 2.5.6 
* Java 11(JDK Corretto 11.0.13)
* lombok
* spring-boot-starter-aop -> for aop logging of the controller(the exception are logged in a different way to make it more custom)
* spring-boot-starter-data-jpa and h2 -> to use the in-memory db h2 with spring data jpa
* springfox-swagger2 -> to create a json that describe the endpoints
* springfox-swagger-ui -> to create a page with a UI that let you try the endpoint giving you information and examples


# About the service
#### REQUIRED fields of a user:
##### username -> can't be null or empty string, must be unique in the whole database(h2 in memory database it's used in this app to facilitate the installation and execution) otherwise an UsernameEmptyException will be thrown;
##### birthDate -> can't be null and must be in the format dd/MM/yyyy otherwise an BirthDateInvalidException will be thrown;
##### countryOfResidence -> can't be null(otherwise a CountryOfResidenceEmptyException will be thrown) and must be France(ignoring letter case because will be saved anyway as all lowercase) otherwise a CountryOfResidenceNotFrException will be thrown
#### OPTIONAL fields of a user:
##### phone number-> in order to be valid and saved on the db it has to match one of the following pattern:
* 1234567890
* 123-456-7890
* (123)456-7890
* (123)4567890
##### or a PhoneNumberInvalidException will be thrown. The phone number will be saved on the DB in the form of:1234567890
##### gender->can be only female or male(ignoring letter case as it will be saved all lower case on the DB) otherwise a GenderInvalidException will be thrown.

### MODELS
```javascript
    {
    "UserDtoRequest": {
        "type": "object",
        "properties": {
            "birthDate": {
                "type": "string",
                "description": "The birth date of the User"
            },
            "countryOfResidence": {
                "type": "string",
                "description": "The country of residence of the User"
            },
            "gender": {
                "type": "string",
                "description": "(optional) The gender of the User(only male or female ignoring uppercase or lowercase. On the database will be saved lowercase)"
            },
            "phoneNumber": {
                "type": "string",
                "description": "(optional) The phone number of the User in the format: 1234567890 or 123-456-7890 or (123)456-7890 or (123)4567890. On the database will be anyway saved in the format 1234567890"
            },
            "username": {
                "type": "string",
                "description": "The username of the User"
            }
        },
        "title": "UserDtoRequest",
        "description": "Request: details about the User(no id field)"
    },
    {
    "UserDtoResponse": {
        "type": "object",
        "properties": {
            "birthDate": {
                "type": "string",
                "description": "The birth date of the User"
            },
            "countryOfResidence": {
                "type": "string",
                "description": "The country of residence of the User"
            },
            "gender": {
                "type": "string",
                "description": "(optional) The gender of the User(only male or female ignoring uppercase or lowercase. On the database will be saved lowercase)"
            },
            "id": {
                "type": "integer",
                "format": "int64",
                "description": "The id of the User"
            },
            "phoneNumber": {
                "type": "string",
                "description": "(optional) The phone number of the User in the format: 1234567890 or 123-456-7890 or (123)456-7890 or (123)4567890. On the database will be anyway saved in the format 1234567890"
            },
            "username": {
                "type": "string",
                "description": "The username of the User"
            }
        },
        "title": "UserDtoResponse",
        "description": "Response: details about the User (with id field)"
    }
```

# Details about endpoints-paths:
## /user/
### POST -> Insert a User: In order to use this api you have to: provide a valid UserDtoRequest to create a new User in the in memory database
#### parameters:
in: "body",
name: "userDtoRequest",
description: "UserDtoRequest that represent the User you need to insert",
required: true,
schema: UserDtoRequest
#### responses:
schema: UserDtoResponse

## /user/{userId}
### GET -> Retrieve a User from a userId: In order to use this api you have to: provide a valid userId to find a specific User already inserted in the in memory database
#### parameters:
in: "path",
name: "userId",
description: "userId value of the User you need to retrieve",
required: true,
type: "string"
#### responses:
schema: UserDtoResponse

## /user/{userId}                        
### PUT -> Update a User: In order to use this api you have to: provide a valid UserDtoRequest to update the User retrieved by userId and username(you can't update the username field after creation of the User) in the in memory database
#### parameters:
##### in: "path",
name: "userId",
description: "userId value of the User you need to update",
required: true,
type: "string"
##### in: "body",
name: "userDtoRequest",
description: "UserDtoRequest that represent the new data of the User you need to update",
required: true,
schema: UserDtoRequest

#### responses:
schema: UserDtoResponse

## /user/{userId}
### DELETE -> Delete a User from a userId: In order to use this api you have to: provide a valid userId to delete a specific User already inserted in the in memory database
#### parameters:
in: "path",
name: "userId",
description: "userId value of the User you need to delete",
required: true,
type: "string"
#### responses:
200: 
description: "OK"


#### It is possible to access the in memory database(after running the application) at the following link(jdbc url: jdbc:h2:mem:testdb):
#### http://localhost:8080/h2-console
#### to see and try the endpoints with all the information, ready to use, with a user-friendly interface go to the following link(after running the application):
#### http://localhost:8080/swagger-ui.html
#### you will see all the endpoint with request and response documented, you will also be able to try sending request to the application without using postman.
## If you prefer use postman you will find a postman collection in the root folder of the project, just import it in your postman and you will be ready to start sending requests to the application.
## Please execute the requests following the numerical order indicated on the postman requests

# TEST: UserUtility is 100 percent unit tested

# How to download and install the project
#### Download the zip or clone the Git repository.
1. Unzip the zip file (if you downloaded one)
2. Import the project in your favourite IDE
3. Example with Eclipse:
* Open Eclipse
* File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
* Select the right project
* Choose the Spring Boot Application file (search for @SpringBootApplication)
* Right-Click on the file and Run as Java Application
* You are all Set