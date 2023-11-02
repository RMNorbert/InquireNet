<div align="center">

[<img src="https://github.com/RMNorbert/InquireNet/blob/main/My%20project.png" alt="InquireNet" width="100">](README.md)

[![Java](https://img.shields.io/badge/Java-blue.svg?logo=openjdk&logoColor=white&labelColor=323330&style=for-the-badge)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Maven](https://img.shields.io/badge/Maven-322130.svg?logo=apachemaven&logoColor=ec2400&labelColor=323330&style=for-the-badge)](https://maven.apache.org/)
[![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-brightgreen.svg?logo=spring&labelColor=323330&style=for-the-badge)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/-Spring%20Security-darkgreen.svg?logo=springsecurity&labelColor=323330&style=for-the-badge)](https://spring.io/projects/spring-security)

[![Cucumber](https://img.shields.io/badge/-cucumber-1c1c2c.svg?logo=cucumber&logoColor=0197f6&labelColor=323330&style=for-the-badge)](https://cucumber.io/)
[![Selenium](https://img.shields.io/badge/-selenium-1c1c2c.svg?logo=selenium&logoColor=0197f6&labelColor=323330&style=for-the-badge)](https://www.selenium.dev/)
[![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-blue.svg?logo=postgresql&logoColor=0197f6&labelColor=323330&style=for-the-badge)](https://www.postgresql.org)
[![Flyway](https://img.shields.io/badge/-flyway-blue.svg?logo=flyway&logoColor=darkred&labelColor=323330&style=for-the-badge)](https://flywaydb.org/)
[![Docker](https://img.shields.io/badge/-docker-1c1c2c.svg?logo=docker&logoColor=0197f6&labelColor=323330&style=for-the-badge)](https://www.docker.com/)

[![Java Script](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)](https://www.javascript.com/)
[![Vite](https://img.shields.io/badge/Vite-purple.svg?logo=vite&logoColor=F7DF1E&labelColor=323330&style=for-the-badge)](https://vitejs.dev/)
[![OpenAI](https://img.shields.io/badge/-OpenAi-1c1c2c.svg?logo=openai&labelColor=323330&style=for-the-badge)](https://openai.com)
[![OpenAPI 3](https://img.shields.io/badge/OpenApi-1c1c2c.svg?logo=openapiinitiative&logoColor=66FF01&labelColor=323330&style=for-the-badge)](https://www.openapis.org/)

[![License: Unlicense](https://img.shields.io/badge/-Unlicense-blue.svg?logo=unlicense&logoColor=white&style=for-the-badge)](License "License")
[![CI Status](https://img.shields.io/github/actions/workflow/status/RMNorbert/InquireNet/maven.yml?branch=main&label=Tests&labelColor=323330&style=for-the-badge)](https://github.com/RMNorbert/InquireNet/actions "Java CI TEST")
[![Last Commit](https://img.shields.io/github/last-commit/RMNorbert/InquireNet?logo=github&label=Last%20Commit&labelColor=323330&style=for-the-badge&display_timestamp=committer)](https://github.com/RMNorbert/InquireNet/commits "Commit History")
[![Coverage](https://img.shields.io/badge/-83％-blue.svg?logo=85&label=coverage&logoColor=white&labelColor=323330&style=for-the-badge)]()

</div>

# InquireNet
---

[Table of content:](#description)
- [Features](#features)
- [Used Technologies](#used-technologies)
- [Getting Started](#getting-started)
- [Default User Credentials](#default-user-credentials)
  
---
## Description:

InquireNet is an interactive question and answer platform that leverages the power of AI for automated answers. 

It is built using Java, Spring, JDBC, and other technologies to provide a robust and user-friendly web application. In addition to the public question and answer functionality, InquireNet offers users the flexibility to maintain privacy. If users prefer not to publicize their questions, they can utilize the chat feature to ask their queries directly to the AI. This ensures that users have multiple avenues to seek answers and engage with the platform based on their preferences.

---
## Features
- **Build support for both Maven (main branch) and Gradle build (Gradle branch)**

- **User registration and authentication**
 
- **Posting , managing , searching, voting for questions and answers**

- **AI automated answers** 

- **Chat with AI & managing previous chats** 

- **Actuator**

- **Custom Features Endpoint**

- **OpenApi Documentation**

- **Flyway**

- **User Analytics**

- **User Reputation System**

- **Cucumber with Selenium tests with Firefox(deb) 103 ESR( on cucumber branch)**

---
# Dockerized version can be pulled from DockerHub:
https://hub.docker.com/repository/docker/7nrm/inquirenet/general

---
## Used Technologies:

 **Backend**
  - Java: Version 17.0.7 (Maven and Gradle build)
  - Spring Boot: Version 3.1
        Spring Security: Version 6.1.0
        JWT (JSON Web Tokens)
        Actuator
        Webflux
  - JDBC: Version 6.0.9
    
 **Frontend**
  - JavaScript,
  - Vite,
  - OpenAi: Version 3.2.1
  
 **Database**
  - PostgreSQL
  - H2 Database: Used during integration tests
  - Flyway: For managing database schema changes and data migrations
    
  **Others**
  - Gherkin, Cucumber, Selenium
  - Docker,
  - CI/CD GitHub workflows
  - OpenApi: Version 3.0

---
## Getting Started

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

## Prerequisites

To set up the project, follow these steps:
    
Make sure you have the following dependencies installed before proceeding with the installation:

###  To deploy InquireNet using Docker containers, follow these steps:

#### [Install Docker](https://www.docker.com/get-started/):
  
  **For Linux**: Follow the instructions on the official Docker website.
  
  **For Windows or macOS**: Install Docker Desktop for an easy-to-use Docker environment.

#### After installing Docker:
 Ensure it's running by opening a terminal or command prompt and running the command 
 ```
 docker --version
 ```

Note: Docker is optional and recommended for deployment scenarios. If you're using Docker, it can help manage dependencies and ensure consistent environments.

---

###  To run InquireNet without using Docker:

#### 1.   Java Development Kit (JDK):
   Ensure that you have the Java Development Kit installed on your machine. You can download the JDK from the Oracle website and install it according to the provided instructions.

#### 2.    JDBC Database (e.g., PostgreSQL):
   In order to use a JDBC database with the application, such as PostgreSQL, make sure you have the necessary database server installed on your system. You can download and install PostgreSQL from the official PostgreSQL website and configure it as required.

#### 3.    Flyway:
   In order to use Flyway with the application and initialize the database, make sure you have the Flyway installed on your system. You can download and install Flyway from the official Flyway website and configure it as required.

---
### Installation

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

---
1. **Clone the repository:**

```
git@github.com:RMNorbert/InquireNet.git
```

---
2. **Configure the project:**

   Once you have cloned the repository, you need to configure the project by providing the necessary environment variables and database connection details.

   This includes setting up the **database connection URL, username, and password**, as well as any other environment-specific configuration values in the **docker-compose.yml** and the **database.properties** which located in **src/main/resources**. Make sure to update these files with the appropriate values.

  By default inside the **database.properties** the **datasource url** is set for running the application with docker.

---
3. **For Flyway migration:**

   Currently the tables initialization and the basic data insertion is done by Flyway.

   3.1, You can use the following command to initialize or migrate any changes in the future:
   
    ```
   flyway -url=jdbc:postgresql://localhost:5432/InquireNet -user=postgres -password=postgres -locations=filesystem:./src/main/resources/db/ migrate
    ```

   3.2, You can use the following commands as well to do the initialization or migration with sh file:

   ```
   chmod +x run-flyway-migrations.sh
   ```

   ```
   ./run-flyway-migrations.sh
   ```

---
4. **Build and run the project:**

   InquireNet can be built and run using your preferred development environment or command-line tools. Here are a few options:

   4.1,  Using Maven: Open a terminal or command prompt, navigate to the project's root directory, and run the following command:

    ```
    mvn spring-boot:run
    ```

    This command will build the project, resolve the dependencies, and start the backend server.

    Navigate to the project's frontend directory, and run the following command:

    ```
    npm run dev
    ```

   This command will start the frontend server.

---
   4.2,  Run the dockerized version with:

   Navigate to the project directory containing the **docker-compose.yml** file.

   Run the following command to build and start the project:

   ```
   docker-compose up --build
   ```
   
   The docker-compose.yml file defines the services and configurations needed for running your application in a Docker container. It simplifies deployment and ensures consistent setups across environments

---
5. ***Access the application:***

    Once the server is up and running, you can access the InquireNet application through the provided URL. Open a web browser and enter the appropriate URL (e.g., by default **http://localhost:3000** or in case of **docker : http://localhost:8080**) to access the application. You can create your account to start asking and answering questions on the platform.

    Note: The URL and port number may vary depending on your configuration.

---

## Default User Credentials
   
   For quicker testing purposes, you can login to the application by using the following credentials:

   | Username      | Password      |
   | ------------- | ------------- |
   | admin_user    | password      |
   | user          | password      |

---
## End To End Testing Through Postman
https://postman.com/galactic-satellite-387943/workspace/inquire-test

---
## License

This project is licensed under the Unlicense License - see the [License](License) file for details.

---

## Acknowledgments

    OpenAI for providing advanced AI capabilities.
    Spring Framework for the powerful Java-based development environment.
    PostgreSQL for the reliable and scalable database solution.
