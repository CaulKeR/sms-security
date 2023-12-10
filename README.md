# Anti-Phishing Application

The Anti-Phishing Application is a Java-based application that helps detect and prevent phishing attacks. It integrates
with the Google Web Risk API to evaluate the safety of URLs and identify potential phishing threats.

## Subscription management

*System phone number* - phone number specified in `src/main/resources/application.yaml` at property `app.system-phone`

1. **Subscription to service**: User need to send SMS with text 'START' to *System phone number*
2. **Unsubscription to service**: User need to send SMS with text 'STOP' to *System phone number*

## Architecture Overview

The application follows a layered architecture pattern, separating concerns into different components. The main
architectural components are:

1. **Presentation Layer**: This layer handles the user interface and user interactions. It includes components such as
   controllers, views, and templates. Controllers receive HTTP requests, process them, and return appropriate responses.
2. **Service Layer**: This layer contains the business logic of the application. It includes components such as services
   and service interfaces. Services encapsulate the business logic and interact with repositories to perform CRUD
   operations on the data.
3. **Repository Layer**: This layer is responsible for data access and persistence. It includes components such as
   repositories and entities. Repositories provide an abstraction for interacting with the database, allowing developers
   to perform database operations without writing SQL queries directly. Entities represent the data model and are mapped
   to database tables.
4. **Configuration Layer**: This layer handles the configuration of the application. It includes components such as
   configuration classes, properties files, and dependency injection. Configuration classes define beans and configure
   various aspects of the application, such as database connections, security settings, and third-party integrations.
5. **External Services**: The application may interact with external services, such as APIs or databases. These services
   are typically accessed through client libraries or connectors.
6. **Testing**: The application should include tests to ensure the correctness and reliability of the code.
7. **Deployment**: The application can be deployed to various environments, such as local development, staging, or
   production. Deployment involves configuring the application for the target environment, managing dependencies, and
   ensuring scalability and reliability.

## Prerequisites

To run the application, you need to have the following prerequisites:

- Java 17+
- Maven
- Postgres 9.6+
- Docker hub

## Getting Started

To get started with the Anti-Phishing Application, follow these steps:

1. Clone the repository: `git clone https://github.com/CaulKeR/sms-security`
2. Build the project: `./mvnw clean package`
3. Run the application: `docker-compose up`
4. If it is your first start run initial script `src/main/resources/database/init.sql`

## Configuration

The application requires a valid API key for the Google Web Risk API. You can configure the API key by setting
the `app.uri-validation.google.api-key` property in the `src/main/recources/application.yaml` file.

## Usage

Call .../swagger-ui/index.html to get API description

## Third Party Dependencies

- Url Detector (https://github.com/linkedin/URL-Detector?tab=readme-ov-file#url-detector) under Apache License, Version
  2.0
