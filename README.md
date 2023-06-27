<!-- MANPAGE: BEGIN EXCLUDED SECTION -->
<div align="center">
[![InquireNet](https://github.com/RMNorbert/InquireNet/blob/RMNorbert-patch-2/My%20project.png)](README.md)
![InquireNet](https://github.com/RMNorbert/InquireNet/blob/RMNorbert-patch-2/My%20project.png)
[![GitHub latest release version](https://img.shields.io/github/v/release/RMNorbert/InquireNet.svg?style=flat)](https://github.com/RMNorbert/InquireNet/releases/latest)
[![Java](https://img.shields.io/badge/-Java-blue.svg?logo=java&labelColor=555555&style=for-the-badge)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-brightgreen.svg?logo=spring&labelColor=555555&style=for-the-badge)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-blue.svg?logo=postgresql&labelColor=555555&style=for-the-badge)](https://www.postgresql.org)
[![OpenAI](https://img.shields.io/badge/-OpenAi-ff69b4.svg?logo=openai&labelColor=555555&style=for-the-badge)](https://openai.com)

[![License: Unlicense](https://img.shields.io/badge/-Unlicense-blue.svg?style=for-the-badge)](LICENSE "License")
[![CI/CD](https://img.shields.io/github/workflow/status/RMNorbert/InquireNet/CI?label=CI%2FCD&logo=github&style=for-the-badge)](https://github.com/RMNorbert/InquireNet/actions "CI/CD")
[![Last Commit](https://img.shields.io/github/last-commit/RMNorbert/InquireNet?label=&style=for-the-badge&display_timestamp=committer)](https://github.com/RMNorbert/InquireNet/commits "Commit History")


</div>
<!-- MANPAGE: END EXCLUDED SECTION -->

# InquireNet

[Description for the project](#description)
- [Used technologies](#used-technologies)
- [Features](#features)
- [Getting started](#getting-started)

## Description:

InquireNet is an interactive question and answer platform that leverages the power of AI for automated answers. It is built using Java, Spring, JDBC, and other technologies to provide a robust and user-friendly web application. In addition to the public question and answer functionality, InquireNet offers users the flexibility to maintain privacy. If users prefer not to publicize their questions, they can utilize the chat feature to ask their queries directly to the AI. This ensures that users have multiple avenues to seek answers and engage with the platform based on their preferences.

## Used technologies:

 Backend
  - Java,
  - Spring Boot, Spring Security, JWT
  - JDBC
  
 Frontend
  - JavaScript,
  - Vite,
  - OpenAi
  
 Database
  - PostgreSQL
  
  Others
  - Docker (In progress),
  - CI/CD GitHub workflows (In progress)

## Features

   User Registration and Authentication: Users can create accounts and log in to the platform to ask and answer questions.

   Question and Answer Functionality: Users can post questions on any topic and receive answers from other community members.

   AI Automated Answers: Users get AI provided answers to their questions instantly, powered by OpenAI, enhancing the overall user experience. These AI-powered answers are generated using state-of-the-art natural language processing techniques, ensuring accurate and insightful responses. 

   Chat with AI: Users have the ability to directly engage in chat conversations with the AI. This interactive chat feature allows users to have dynamic and real-time exchanges with the AI, enabling them to explore their questions in more depth and receive personalized responses.

   User Interaction: InquireNet allows users to upvote or downvote answers, comment on questions and answers, and engage in meaningful discussions.

## In progress Features

   Search Functionality: Users can search for specific questions or topics of interest to quickly find relevant information.

## Getting Started

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

## Prerequisites

    
Make sure you have the following dependencies installed before proceeding with the installation:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [JDBC Database (e.g., PostgreSQL)](https://www.postgresql.org)

In addition, you will need the following dependencies specified in the project configuration (`pom.xml`):

- `spring-boot-starter-security` (version 3.1.0)
- `jjwt-api` (version 0.11.5)
- `jjwt-impl` (version 0.11.5)
- `jjwt-jackson` (version 0.11.5)
- `spring-boot-starter-web` (version 3.1.0)
- `snakeyaml` (version 2.0)
- `postgresql` (runtime scope)
- `spring-boot-starter-test` (version 3.1.0, test scope)
- `spring-security-test` (version 6.1.0, test scope)
- `spring-boot-starter-jdbc` (version 3.1.0)
- `spring-jdbc` (version 6.0.9)
- `javax.persistence-api` (version 2.2)
- `spring-boot-starter-validation`
- `lombok` (version 1.18.26, optional)

Additionally, you will need the following dependencies specified in the `package.json` file:

- `dotenv` (version 16.1.4)
- `jwt-decode` (version 3.1.2)
- `openai` (version 3.2.1)
- `react` (version 18.2.0)
- `react-dom` (version 18.2.0)
- `react-icons` (version 4.9.0)
- `react-router-dom` (version 6.12.1)

Installation

Follow these instructions to get a copy of the InquireNet project up and running on your local machine for development and testing purposes.

1. **Clone the repository:**
   Start by cloning the InquireNet repository to your local machine. Open a terminal or command prompt and run the following command:

   ```bash
   git clone https://github.com/RMNorbert/InquireNet.git
   ```
This command will create a local copy of the repository on your machine.

2. **Configure the project:**
    Once you have cloned the repository, you need to configure the project by providing the necessary environment variables and database connection details. This includes setting up the database connection URL, username, and password, as well as any other environment-specific configuration values. Make sure to update the configuration files (application.properties, .env) with the appropriate values.

3. **Build and run the project:**
    InquireNet can be built and run using your preferred development environment or command-line tools. Here are a few options:

     Using an IDE: Import the project into your favorite IDE (e.g., IntelliJ, Eclipse) as a Maven project. Build the project to resolve the dependencies and then run the main application class (InquireNetApplication.java) to start the server.

     Using Maven: Open a terminal or command prompt, navigate to the project's root directory, and run the following command:
    ```bash
    mvn spring-boot:run
    ```
    This command will build the project, resolve the dependencies, and start the server.

4. ***Access the application:***
    Once the server is up and running, you can access the InquireNet application through the provided URL. Open a web browser and enter the appropriate URL (e.g., http://localhost:8080) to access the application. You can create your account to start asking and answering questions on the platform.

    Note: The URL and port number may vary depending on your configuration.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Acknowledgments

    OpenAI for providing advanced AI capabilities.
    Spring Framework for the powerful Java-based development environment.
    PostgreSQL for the reliable and scalable database solution.
