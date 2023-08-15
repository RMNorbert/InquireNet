<!-- MANPAGE: BEGIN EXCLUDED SECTION -->
<div align="center">

[<img src="https://github.com/RMNorbert/InquireNet/blob/main/My%20project.png" alt="InquireNet" width="100">](README.md)

[![Java](https://img.shields.io/badge/Java-blue.svg?logo=openjdk&logoColor=white&labelColor=323330&style=for-the-badge)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-brightgreen.svg?logo=spring&labelColor=323330&style=for-the-badge)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/-Spring%20Security-darkgreen.svg?logo=springsecurity&labelColor=323330&style=for-the-badge)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-blue.svg?logo=postgresql&logoColor=0197f6&labelColor=323330&style=for-the-badge)](https://www.postgresql.org)
[![Docker](https://img.shields.io/badge/-docker-blue.svg?logo=docker&logoColor=0197f6&labelColor=323330&style=for-the-badge)](https://www.docker.com/)

[![OpenAI](https://img.shields.io/badge/-OpenAi-1c1c2c.svg?logo=openai&labelColor=323330&style=for-the-badge)](https://openai.com)
[![JWT](https://img.shields.io/badge/JWT-323330?style=for-the-badge&logo=jsonwebtokens&logoColor=red)](https://jwt.io/)
[![Java Script](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)](https://www.javascript.com/)
[![Vite](https://img.shields.io/badge/Vite-purple.svg?logo=vite&logoColor=F7DF1E&labelColor=323330&style=for-the-badge)](https://vitejs.dev/)
[![OpenAPI 3](https://img.shields.io/badge/OpenApi-66FF01.svg?logo=openapiinitiative&logoColor=66FF01&labelColor=323330&style=for-the-badge)](https://www.openapis.org/)
[![Maven](https://img.shields.io/badge/Maven-322130.svg?logo=apachemaven&logoColor=ec2400&labelColor=323330&style=for-the-badge)](https://maven.apache.org/)


[![License: Unlicense](https://img.shields.io/badge/-Unlicense-blue.svg?logo=unlicense&logoColor=white&style=for-the-badge)](License "License")
[![CI Status](https://img.shields.io/github/actions/workflow/status/RMNorbert/InquireNet/maven.yml?branch=main&label=Tests&labelColor=323330&style=for-the-badge)](https://github.com/RMNorbert/InquireNet/actions "Java CI TEST")
[![Last Commit](https://img.shields.io/github/last-commit/RMNorbert/InquireNet?logo=github&label=Last%20Commit&labelColor=323330&style=for-the-badge&display_timestamp=committer)](https://github.com/RMNorbert/InquireNet/commits "Commit History")
[![Coverage](https://img.shields.io/badge/-83％-blue.svg?logo=85&label=coverage&logoColor=white&labelColor=323330&style=for-the-badge)]()

</div>
<!-- MANPAGE: END EXCLUDED SECTION -->

# InquireNet

[Table of content:](#description)
- [Used Technologies](#used-technologies)
- [Features](#features)
- [Getting Started](#getting-started)

## Description:

InquireNet is an interactive question and answer platform that leverages the power of AI for automated answers. It is built using Java, Spring, JDBC, and other technologies to provide a robust and user-friendly web application. In addition to the public question and answer functionality, InquireNet offers users the flexibility to maintain privacy. If users prefer not to publicize their questions, they can utilize the chat feature to ask their queries directly to the AI. This ensures that users have multiple avenues to seek answers and engage with the platform based on their preferences.

# Dockerized version can be pulled from DockerHub:
https://hub.docker.com/repository/docker/7nrm/inquirenet/general

## Used Technologies:

 Backend
  - Java: Version 17.0.7 (Maven and Gradle build)
  - Spring Boot: Version 3.1
        Spring Security: Version 6.1.0
        JWT (JSON Web Tokens)
        Actuator
        Webflux
  - JDBC: Version 6.0.9
    
 Frontend
  - JavaScript,
  - Vite,
  - OpenAi: Version 3.2.1
  
 Database
  - PostgreSQL
  - H2 Database: Used during integration tests
    
  Others
  - Docker,
  - CI/CD GitHub workflows
  - OpenApi: Version 3.0

## Features
- **Build support for both Maven (main branch), Gradle build (Gradle branch)**

- **User registration and authentication**
 
- **Posting , managing , searching, voting for questions and answers**

- **AI automated answers** 

- **Chat with AI & managing previous chats** 

- **Actuator**

- **Custom Features Endpoint**

- **OpenApi Documentation**


## Getting Started

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

## Prerequisites

To set up the project, follow these steps:
    
Make sure you have the following dependencies installed before proceeding with the installation:

#### 1.   Java Development Kit (JDK):
   Ensure that you have the Java Development Kit installed on your machine. You can download the JDK from the Oracle website and install it according to the provided instructions.

#### 2.    JDBC Database (e.g., PostgreSQL):
   In order to use a JDBC database with the application, such as PostgreSQL, make sure you have the necessary database server installed on your system. You can download and install PostgreSQL from the official PostgreSQL website and configure it as required.

### Installation

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

1. **Clone the repository:**

2. **Configure the project:**
    Once you have cloned the repository, you need to configure the project by providing the necessary environment variables and database connection details. This includes setting up the database connection URL, username, and password, as well as any other environment-specific configuration values in the docker-compose.yml and the application.properties which located in src/main/resources. Make sure to update these files with the appropriate values.

4. **Build and run the project:**
    InquireNet can be built and run using your preferred development environment or command-line tools. Here are a few options:

     Using Maven: Open a terminal or command prompt, navigate to the project's root directory, and run the following command:
    ```bash
    mvn spring-boot:run
    ```
    This command will build the project, resolve the dependencies, and start the server.

5. ***Access the application:***
    Once the server is up and running, you can access the InquireNet application through the provided URL. Open a web browser and enter the appropriate URL (e.g., http://localhost:8080) to access the application. You can create your account to start asking and answering questions on the platform.

    Note: The URL and port number may vary depending on your configuration.

## License

This project is licensed under the Unlicense License - see the [License](License) file for details.

Acknowledgments

    OpenAI for providing advanced AI capabilities.
    Spring Framework for the powerful Java-based development environment.
    PostgreSQL for the reliable and scalable database solution.
