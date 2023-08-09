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
[![Gradle](https://img.shields.io/badge/Gradle-322130.svg?logo=gradle&logoColor=001a42&labelColor=323330&style=for-the-badge)](https://gradle.org/)

[![License: Unlicense](https://img.shields.io/badge/-Unlicense-blue.svg?logo=unlicense&logoColor=white&style=for-the-badge)](License "License")
[![CI Status](https://img.shields.io/github/actions/workflow/status/RMNorbert/InquireNet/maven.yml?branch=main&labelColor=323330&label=Tests&style=for-the-badge)](https://github.com/RMNorbert/InquireNet/actions "Java CI TEST")
[![Last Commit](https://img.shields.io/github/last-commit/RMNorbert/InquireNet?logo=github&label=Last%20Commit&labelColor=323330&style=for-the-badge&display_timestamp=committer)](https://github.com/RMNorbert/InquireNet/commits "Commit History")
[![Coverage](https://img.shields.io/badge/-83％-blue.svg?logo=85&label=coverage&labelColor=323330&logoColor=white&style=for-the-badge)]()


</div>
<!-- MANPAGE: END EXCLUDED SECTION -->

# InquireNet

[Table of content:](#description)
- [Used technologies](#used-technologies)
- [Features](#features)
- [Getting started](#getting-started)

## Description:

InquireNet is an interactive question and answer platform that leverages the power of AI for automated answers. It is built using Java, Spring, JDBC, and other technologies to provide a robust and user-friendly web application. In addition to the public question and answer functionality, InquireNet offers users the flexibility to maintain privacy. If users prefer not to publicize their questions, they can utilize the chat feature to ask their queries directly to the AI. This ensures that users have multiple avenues to seek answers and engage with the platform based on their preferences.

# Dockerized version can be pulled from DockerHub:
https://hub.docker.com/repository/docker/7nrm/inquirenet/general

## Used technologies:

 Backend
  - Java (version 17.0.7),
  - Spring Boot (version 3.1), Spring Security( version 6.1.0), JWT , Actuator, Webflux
  - JDBC (version 6.0.9)
  
 Frontend
  - JavaScript,
  - Vite,
  - OpenAi (version 3.2.1)
  
 Database
  - PostgreSQL
  
  Others
  - Docker,
  - CI/CD GitHub workflows
  - OpenApi (version 3.0)

## Features

**User Registration and Authentication:**
 
Users can create accounts and log in to the platform using secure authentication mechanisms implemented with Spring Security and JSON Web Tokens (JWT). This ensures that user data is protected and only accessible to authorized individuals.

**Question and Answer Functionality:**

Users can post questions on any topic and receive answers from other community members. The platform provides a user-friendly interface for submitting, viewing and updating questions, answers, replies to answers, to participate in discussions.

**AI Automated Answers:** 

Users get AI provided answers to their questions instantly, powered by OpenAI, enhancing the overall user experience. These AI-powered answers are generated using state-of-the-art natural language processing techniques, ensuring accurate and insightful responses. 

**Chat with AI:**

Users have the ability to directly engage in chat conversations with the AI. This interactive chat feature allows users to have dynamic and real-time exchanges with the AI, enabling them to explore their questions in more depth and receive personalized responses. The user can store the chat and delete stored chats.

**User Interaction:**

InquireNet allows users to upvote or downvote answers, comment on questions and answers, and engage in meaningful discussions.

**Search Functionality:**

Users can search for specific questions or topics of interest to quickly find relevant information.

**Actuator:**

Exposes endpoints for custom metrics, health, and management purposes.

**Custom Features Endpoint:**

Allows dynamic configuration and management of application features.

**OpenApi Documentation:**

Generates and serves API documentation using the OpenApi 3 framework.

## Getting Started

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

## Prerequisites

To set up the project, follow these steps:
    
Make sure you have the following dependencies installed before proceeding with the installation:

#### 1.   Java Development Kit (JDK):
   Ensure that you have the Java Development Kit installed on your machine. You can download the JDK from the Oracle website and install it according to the provided instructions.

#### 2.    Spring Boot: 
   Install Spring Boot, which is the framework used for building the application. You can find the necessary resources and installation instructions on the official Spring Boot website. Choose the appropriate version for your project and follow the installation guide.

#### 3.    JDBC Database (e.g., PostgreSQL):
   In order to use a JDBC database with the application, such as PostgreSQL, make sure you have the necessary database server installed on your system. You can download and install PostgreSQL from the official PostgreSQL website and configure it as required.

### Installation

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

1. **Clone the repository:**

2. **Configure the project:**
    Once you have cloned the repository, you need to configure the project by providing the necessary environment variables and database connection details. This includes setting up the database connection URL, username, and password, as well as any other environment-specific configuration values. Make sure to update the configuration files (application.properties, .env) with the appropriate values.

3. **Build and run the project:**
    InquireNet can be built and run using your preferred development environment or command-line tools. Here are a few options:

     Using Maven: Open a terminal or command prompt, navigate to the project's root directory, and run the following command:
    ```bash
    mvn spring-boot:run
    ```
    This command will build the project, resolve the dependencies, and start the server.

4. ***Access the application:***
    Once the server is up and running, you can access the InquireNet application through the provided URL. Open a web browser and enter the appropriate URL (e.g., http://localhost:8080) to access the application. You can create your account to start asking and answering questions on the platform.

    Note: The URL and port number may vary depending on your configuration.

## License

This project is licensed under the Unlicense License - see the [License](License) file for details.

Acknowledgments

    OpenAI for providing advanced AI capabilities.
    Spring Framework for the powerful Java-based development environment.
    PostgreSQL for the reliable and scalable database solution.
